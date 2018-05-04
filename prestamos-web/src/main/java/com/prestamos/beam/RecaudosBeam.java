package com.prestamos.beam;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
//javax.enterprise.context.SessionScoped
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.itextpdf.text.DocumentException;
import com.prestamos.api.PrestamosException;
import com.prestamos.model.Credito;
import com.prestamos.model.Recaudo;
import com.prestamos.model.Usuario;
import com.prestamos.service.ClienteService;
import com.prestamos.service.CobradorService;
import com.prestamos.service.CreditoService;
import com.prestamos.service.RecaudoService;
import com.prestamos.utils.Calculos;
import com.prestamos.utils.Impresion;
import com.prestamos.vo.CuadreCajaVo;
import com.prestamos.vo.RecaudoVo;

/**
 * @author luismg
 *
 */
@ManagedBean
@SessionScoped
@Controller
public class RecaudosBeam implements Serializable {

	private static final long serialVersionUID = -4092185363070853864L;
	private static Logger log = Logger.getLogger(RecaudosBeam.class);
	Integer maxCuotaSinPagar = 3;

	ApplicationContext context = new ClassPathXmlApplicationContext(
			"classpath*:META-INF/spring/applicationContext.xml");
	ClienteService clienteService = context.getBean(ClienteService.class);
	CobradorService cobradorService = context.getBean(CobradorService.class);
	CreditoService creditoService = context.getBean(CreditoService.class);
	RecaudoService recaudoService = context.getBean(RecaudoService.class);

	ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	Map<String, Object> sessionMap = externalContext.getSessionMap();

	private Long cobradorBusqueda;
	private Long clienteBusqueda;
	private Date fechaInicio;
	private Date fechaInicioBusqueda;
	private Date fechaFinBusqueda;
	private List<RecaudoVo> recaudoVoList;
	private List<Recaudo> recaudoList;

	private Double totalRecaudo;
	private Double efectivo;
	private Double prestamo;
	private Double totalEntregado;

	public Usuario usuarioLogin() {
		return (Usuario) sessionMap.get("userLogin");
	}

	/**
	 * Metodo que imprime el cuadre de caja por cada cobrador;
	 */
	public void imprimirCuadreCaja() {
		CuadreCajaVo cu = new CuadreCajaVo();
		cu.setEfectivo(getEfectivo());
		cu.setPrestado(getPrestamo());
		cu.setTotalEntregado(getTotalEntregado());
		cu.setTotalRecaudo(getTotalRecaudo());
		cu.setCobradorId(cobradorService.getById(getCobradorBusqueda()));
		try {
			Impresion.imprimirCuadreCaja(cu);
		} catch (FileNotFoundException e) {
			log.error("Error impriminedno cuadre de caja:"+e.getMessage());
			
		} catch (DocumentException e) {
			log.error("Error impriminedno cuadre de caja:"+e.getMessage());
		}
	}

	/**
	 * Medotodo que calcula el cuadre por cajero
	 */
	public void calcularRecaudosCuadre() {
		if (!validar()) {
			return;
		}
		Date inicio = null;
		Date fin = null;
		try {
			inicio = Calculos.fechaInicial(getFechaInicio());
			fin = Calculos.fechaFinal(getFechaInicio());
		} catch (ParseException e) {
			log.error("error en calculo de fechas: " + e.getMessage());
		}
		List<Recaudo> re2 = new ArrayList<>();
		List<Recaudo> re = recaudoService.getByFechasFechasAndCobrador(inicio, fin, getCobradorBusqueda());
		for (Recaudo r : re) {
			if (r.getCreditoId().getCobradorId().getCobradorId() == getCobradorBusqueda()) {
				re2.add(r);
			}
		}
		Double total = 0.0;
		for (Recaudo r : re2) {
			total += (r.getValorRecaudo() == null ? 0.0 : r.getValorRecaudo());
		}
		setTotalRecaudo(total);
	}

	/**
	 * Metod que resta el efectivo y lo prestado y lo suma en resultado
	 */
	public void calcularResultado() {
		setTotalEntregado(getEfectivo() + getPrestamo());
	}

	/**
	 * Medodo encargardo re imprimir los recaudos para los cabradores
	 */
	public void imprimirRecudosDia() {
		if (!getRecaudoVoList().isEmpty()) {
			try {
				Impresion.imprimirRecaudosDia(getRecaudoVoList());
			} catch (Exception e) {
				log.error("error imprimiendo recaudos día: " + e.getMessage());
			}

		}
	}

	/**
	 * Metodo que busca la informacion de todos los recaudos realizados con los
	 * filtros
	 */
	public void buscarInfoRecaudo() {
		setRecaudoList(recaudoService.getByFiltros(getClienteBusqueda(), getCobradorBusqueda(),
				getFechaInicioBusqueda(), getFechaFinBusqueda()));
	}

	/**
	 * Metodo que realiza el guardado de los recaudos diariamente por cobrador
	 */
	public void guardarRecoudos() {
		if (!validarGuardado()) {
			return;
		}
		posGuardar();

	}

	public void posGuardar() {
		try {
			for (RecaudoVo r : getRecaudoVoList()) {
				if (r.getPagado().equals("true")) {
					Credito creditoTemp = r.getRecaudoId().getCreditoId();
					Recaudo recaudoTemp = r.getRecaudoId();
					recaudoTemp.setFechaRecaudo(getFechaInicio());
					recaudoTemp.setFechaRegistro(new Date());
					recaudoTemp.setUsuarioId(usuarioLogin());
					recaudoTemp.setValorRecaudo(r.getPagoParcial());

					Double abonoTotal = (creditoTemp.getAbonoTotal() == null ? 0.0 : creditoTemp.getAbonoTotal())
							+ r.getPagoParcial();
					Double cuotasPagadas = (creditoTemp.getCuotasPagadas() == null ? 0.0
							: creditoTemp.getCuotasPagadas()) + 1;
					Double saldo = (creditoTemp.getSaldo() == null ? 0.0 : creditoTemp.getSaldo()) - r.getPagoParcial();
					creditoTemp.setAbonoTotal(abonoTotal);
					creditoTemp.setCuotasPagadas(cuotasPagadas);
					creditoTemp.setSaldo(saldo);
					recaudoService.save(recaudoTemp);
					creditoService.save(creditoTemp);
				}
			}
		} catch (PrestamosException e) {
			log.error("Error en guardado de recaudos: " + e.getMessage());
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Recaudos realizados Exitosamente ", ""));
	}

	private Boolean validarGuardado() {
		Boolean valido = Boolean.TRUE;
		for (RecaudoVo vo : getRecaudoVoList()) {
			if (vo.getPagado().equals("true")) {
				Integer numeroCuotaHoy = Calculos.cuotasEntreFechas(vo.getRecaudoId().getCreditoId().getFechaInicio(),
						new Date());
				List<Recaudo> recaudosHechos = recaudoService
						.getByCredito(vo.getRecaudoId().getCreditoId().getCreditoId());
				if ((numeroCuotaHoy - recaudosHechos.size()) > maxCuotaSinPagar) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"El credito para el señ@r: "
											+ vo.getRecaudoId().getCreditoId().getClienteId().getNombre()
											+ " tiene mas de tres cuotas si pagar.",
									""));
					// se crea variable para boton de guardado en la clave de
					// borrado dependienvo del valor muestra el boton a donde
					// redirecciona despues de logueo propietario
					sessionMap.put("botonBorrado", "btnGuardarRecaudo");
					RequestContext.getCurrentInstance().execute("PF('clavePropietarioRecaudo').show();");
					valido = Boolean.FALSE;
					break;
				}
			}

		}
		return valido;
	}

	/**
	 * Metodo que genera los recaudos pendientes para el día
	 */
	public void buscarRecaudosDia() {
		if (!validar()) {
			return;
		}
		try {
			setRecaudoVoList(new ArrayList<>());
			List<Credito> creditoslist = creditoService.getByFiltros(null, getCobradorBusqueda(), null, null);
			for (Credito c : creditoslist) {
				RecaudoVo reVo = new RecaudoVo();
				List<Recaudo> recaTemp = new ArrayList<>();
				try {
					recaTemp = recaudoService.getByFechas(Calculos.fechaInicial(getFechaInicio()),
							Calculos.fechaFinal(getFechaInicio()), c.getCreditoId());
				} catch (ParseException e) {
					log.error("Error en recaudo fechas: " + e.getMessage());
				}
				if (recaTemp.isEmpty()) {
					Recaudo re = new Recaudo();
					re.setCreditoId(c);
					reVo.setRecaudoId(re);
					reVo.setPagoParcial(c.getValorCuota());
					getRecaudoVoList().add(reVo);
				}

			}
		} catch (PrestamosException e) {
			log.info("Error cargando prestamos:" + e.getMessage());
		}
	}

	public void cambiarEstado(RecaudoVo vo) {

		log.info("estado: " + vo.getPagado());
		Integer numeroCuotaHoy = Calculos.cuotasEntreFechas(vo.getRecaudoId().getCreditoId().getFechaInicio(),
				new Date());
		List<Recaudo> recaudosHechos = recaudoService.getByCredito(vo.getRecaudoId().getCreditoId().getCreditoId());
		if ((numeroCuotaHoy - recaudosHechos.size()) > maxCuotaSinPagar) {
			FacesContext.getCurrentInstance()
					.addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"El credito para el señ@r: "
											+ vo.getRecaudoId().getCreditoId().getClienteId().getNombre()
											+ " tiene mas de tres cuotas si pagar.",
									""));
		}
	}

	private boolean validar() {
		Boolean valido = Boolean.TRUE;
		if (getCobradorBusqueda() == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El cobrador es obligatorio", ""));
			valido = Boolean.FALSE;
		}
		if (getFechaInicio() == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "La fecha es obligatoria", ""));
			valido = Boolean.FALSE;
		}
		return valido;
	}

	public Long getCobradorBusqueda() {
		return cobradorBusqueda;
	}

	public void setCobradorBusqueda(Long cobradorBusqueda) {
		this.cobradorBusqueda = cobradorBusqueda;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public List<RecaudoVo> getRecaudoVoList() {
		return recaudoVoList;
	}

	public void setRecaudoVoList(List<RecaudoVo> recaudoVoList) {
		this.recaudoVoList = recaudoVoList;
	}

	public Long getClienteBusqueda() {
		return clienteBusqueda;
	}

	public void setClienteBusqueda(Long clienteBusqueda) {
		this.clienteBusqueda = clienteBusqueda;
	}

	public Date getFechaInicioBusqueda() {
		return fechaInicioBusqueda;
	}

	public void setFechaInicioBusqueda(Date fechaInicioBusqueda) {
		this.fechaInicioBusqueda = fechaInicioBusqueda;
	}

	public Date getFechaFinBusqueda() {
		return fechaFinBusqueda;
	}

	public void setFechaFinBusqueda(Date fechaFinBusqueda) {
		this.fechaFinBusqueda = fechaFinBusqueda;
	}

	public List<Recaudo> getRecaudoList() {
		return recaudoList;
	}

	public void setRecaudoList(List<Recaudo> recaudoList) {
		this.recaudoList = recaudoList;
	}

	public Double getTotalRecaudo() {
		return totalRecaudo;
	}

	public void setTotalRecaudo(Double totalRecaudo) {
		this.totalRecaudo = totalRecaudo;
	}

	public Double getEfectivo() {
		if (efectivo == null) {
			efectivo = 0.0;
		}
		return efectivo;
	}

	public void setEfectivo(Double efectivo) {
		this.efectivo = efectivo;
	}

	public Double getPrestamo() {
		if (prestamo == null) {
			prestamo = 0.0;
		}
		return prestamo;
	}

	public void setPrestamo(Double prestamo) {
		this.prestamo = prestamo;
	}

	public Double getTotalEntregado() {
		return totalEntregado;
	}

	public void setTotalEntregado(Double totalEntregado) {
		this.totalEntregado = totalEntregado;
	}

}
