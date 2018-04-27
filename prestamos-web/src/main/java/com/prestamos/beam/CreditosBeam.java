package com.prestamos.beam;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//javax.enterprise.context.SessionScoped
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.prestamos.api.PrestamosException;
import com.prestamos.model.Cliente;
import com.prestamos.model.Cobrador;
import com.prestamos.model.Credito;
import com.prestamos.service.ClienteService;
import com.prestamos.service.CobradorService;
import com.prestamos.service.CreditoService;
import com.prestamos.utils.Calculos;



/**
 * @author Luis Miguel Gonzalez
 *
 */

@ManagedBean
@SessionScoped
@Controller
public class CreditosBeam implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8352783656056889491L;
	private static Logger log = Logger.getLogger(CreditosBeam.class);
	
	ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/applicationContext.xml");
	ClienteService clienteService = context.getBean(ClienteService.class);
	CobradorService cobradorService = context.getBean(CobradorService.class);
	CreditoService creditoService = context.getBean(CreditoService.class);
	
	private Credito creditoSelect;
	
	//busqueda
	private List<Cobrador> cobradorList;
	private List<Cliente> clienteList;
	private Date fechaInicioBusqueda;
	private Date fechaFinBusqueda;
	private Long cobradorBusqueda;
	private Long clienteBusqueda;
	private List<Credito> creditoList;
	private Double total;
	private Double totalConInteres;
	
	//crear
	private Long cobradorId;
	private Long clienteId;
	private Integer numeroCuotas;
	private Double interes;
	private Date fechaInicio;
	private Date fechaFin;
	private Double valorPagar;
	private Double valorCuota;
	private Double totalPrestamo;
	
	public void abrirPopupEdicion(Credito credito){
		setCreditoSelect(credito);
		setCobradorId(credito.getCobradorId().getCobradorId());
		RequestContext.getCurrentInstance().execute("PF('editarCredito').show();");
	}
	
	public void editarCredito(){
		if(getCobradorId()==null){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El cobrador es obligatorio", ""));
			return;
		}
		try {
			Cobrador cobrador = new Cobrador();
			cobrador.setCobradorId(getCobradorId());
			getCreditoSelect().setCobradorId(cobrador);
			creditoService.update(getCreditoSelect());
			RequestContext.getCurrentInstance().execute("PF('editarCredito').hide();");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Crédito editado exitosamente", ""));
		} catch (PrestamosException e) {
			log.error("Error editando credito: "+e.getMessage());
		}
		
	}
	
	public void crearCredito(){
		if(!validaciones()){
			return;
		}
		try {
			Credito credito = new Credito();
			Cliente cliente = new Cliente();
			Cobrador cobrador = new Cobrador();
			cliente.setClienteId(getClienteId());
			cobrador.setCobradorId(getCobradorId());
			credito.setClienteId(cliente);
			credito.setCobradorId(cobrador);
			credito.setFechaFin(getFechaFin());
			credito.setFechaInicio(getFechaInicio());
			credito.setFechaRegistro(new Date());
			credito.setInteres(getInteres());
			credito.setSaldo(getValorPagar());
			credito.setNumeroCuotas(getNumeroCuotas());
			credito.setTotalPrestamo(getTotalPrestamo());
			//credito.setUsuarioId(usuarioId)
			credito.setValorCuota(getValorCuota());
			credito.setValorPagar(getValorPagar());
			creditoService.save(credito);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Crédito creado exitosamente", ""));
			RequestContext.getCurrentInstance().execute("PF('crearCliente').hide();");
		} catch (PrestamosException e) {
			log.error("Error creando credito: "+e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error creando crédito", ""));
		}
		
	}
	
	public void buscar(){
		try {
			setCreditoList(creditoService.getByFiltros(getClienteBusqueda(), getCobradorBusqueda(),getFechaInicioBusqueda(),getFechaFinBusqueda()));
			Double totalTemp=0.0;
			Double totalInteres = 0.0;
			for(Credito c: getCreditoList()){
				totalTemp+= c.getTotalPrestamo();
				totalInteres+=c.getValorPagar();
			}
			setTotal(totalTemp);
			setTotalConInteres(totalInteres);
		} catch (PrestamosException e) {
			log.error("Error en busqueda de prestamos: "+e.getMessage());
		}
		
	}
	
	public void abrirPupupCrear(){
		creditoSelect=new Credito();
		RequestContext.getCurrentInstance().execute("PF('crearCredito').show();");
	}
	
	public void calculoAutomatico(){
		if(!validaciones()){
			return;
		}
		log.info("entra calculo automatico");
		Double interes1 = getInteres()/100;
		Double totalPagar=  getTotalPrestamo()+( getTotalPrestamo()*interes1);
		setFechaFin(Calculos.diasSindomingo(getFechaInicio(),getNumeroCuotas(),1)); 
		setValorCuota(totalPagar/getNumeroCuotas());
		setValorPagar(totalPagar);		
	}
	
	private Boolean validaciones(){
		Boolean valido=Boolean.TRUE; 
		if(getClienteId()==null || getCobradorId()==null || getInteres()==null || getFechaInicio()==null || getTotalPrestamo()==null ||getNumeroCuotas()==null){			
			setValorCuota(null);
			setValorPagar(null);
			valido=Boolean.FALSE;
			return valido;
		}
		if(getInteres()<1 || getInteres()>100){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El interes tiene que estar entre 1 y 100", ""));
			valido=Boolean.FALSE; 
			
		}
		if(getNumeroCuotas()<1 || getNumeroCuotas()>200){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El numero de cuotas tiene que estar entre 1 y 500", ""));
			valido=Boolean.FALSE;
			
		}
		return valido;
	}
	
	

	public List<Cobrador> getCobradorList() {
		cobradorList = cobradorService.getByAll();
		return cobradorList;
	}
	public void setCobradorList(List<Cobrador> cobradorList) {
		this.cobradorList = cobradorList;
	}
	public List<Cliente> getClienteList() {
		clienteList = clienteService.getByAll();
		return clienteList;
	}
	public void setClienteList(List<Cliente> clienteList) {
		this.clienteList = clienteList;
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
	public Long getCobradorBusqueda() {
		return cobradorBusqueda;
	}
	public void setCobradorBusqueda(Long cobradorBusqueda) {
		this.cobradorBusqueda = cobradorBusqueda;
	}
	public Long getClienteBusqueda() {
		return clienteBusqueda;
	}
	public void setClienteBusqueda(Long clienteBusqueda) {
		this.clienteBusqueda = clienteBusqueda;
	}
	public List<Credito> getCreditoList() {
		return creditoList;
	}
	public void setCreditoList(List<Credito> creditoList) {
		this.creditoList = creditoList;
	}

	public Credito getCreditoSelect() {
		return creditoSelect;
	}

	public void setCreditoSelect(Credito creditoSelect) {
		this.creditoSelect = creditoSelect;
	}

	public Long getCobradorId() {
		return cobradorId;
	}

	public void setCobradorId(Long cobradorId) {
		this.cobradorId = cobradorId;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public Integer getNumeroCuotas() {
		return numeroCuotas;
	}

	public void setNumeroCuotas(Integer numeroCuotas) {
		this.numeroCuotas = numeroCuotas;
	}

	public Double getInteres() {
		return interes;
	}

	public void setInteres(Double interes) {
		this.interes = interes;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Double getValorPagar() {
		return valorPagar;
	}

	public void setValorPagar(Double valorPagar) {
		this.valorPagar = valorPagar;
	}

	public Double getValorCuota() {
		return valorCuota;
	}

	public void setValorCuota(Double valorCuota) {
		this.valorCuota = valorCuota;
	}

	public Double getTotalPrestamo() {
		return totalPrestamo;
	}

	public void setTotalPrestamo(Double totalPrestamo) {
		this.totalPrestamo = totalPrestamo;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getTotalConInteres() {
		return totalConInteres;
	}

	public void setTotalConInteres(Double totalConInteres) {
		this.totalConInteres = totalConInteres;
	}
	
	
	
}
