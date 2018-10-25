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
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.prestamos.api.PrestamosException;
import com.prestamos.model.Cobrador;
import com.prestamos.service.CobradorService;



/**
 * @author Luis Miguel Gonzalez
 *
 */

@ManagedBean
@SessionScoped
@Controller
public class CobradorBeam implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2894012914551756005L;
	private static Logger log = Logger.getLogger(CobradorBeam.class);
	
	ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/applicationContext.xml");
	CobradorService cobradorService = context.getBean(CobradorService.class);
	

	private List<Cobrador> cobradorList;
	private Cobrador cobradorSelect;
	
	//busqueda
	private String nombreBusqueda;
	private String documentoBusqueda;
	
	//creacion
	private String nombre;
	private String documento;
	private String barrio;
	private String direccion;
	private Long celular;
	private Long fijo;	

	/**
	 * Metodo encargado re buscar los cobradores por los filtros seleccionados
	 */
	public void buscar(){
		String nom= getNombreBusqueda()!=null?getNombreBusqueda().toLowerCase():getNombreBusqueda();
		setCobradorList(cobradorService.getByFiltros(nom,getDocumentoBusqueda()));
	}
	
	/**
	 * Metodo encargado de abrir el pupup de crear cliente
	 */
	public void abrirPupupCrear(){
		cobradorSelect=new Cobrador();
		PrimeFaces.current().executeScript("PF('crearCobrador').show();");
	}
	
	public void abrirPopupEditar(Cobrador cli){
		
		setBarrio(cli.getBarrio());
		setCelular(cli.getCelular());

		setDireccion(cli.getDireccion());
		setDocumento(cli.getDocumento());
		setFijo(cli.getFijo());
		setNombre(cli.getNombre());
		cobradorSelect=cli;
		RequestContext.getCurrentInstance().update("cobradorForm:crearCobradorForm");
		PrimeFaces.current().executeScript("PF('crearCobrador').show();");
	}
	
	/**
	 * Metodo que crea los cobradores
	 */
	public void crearCobrador(){
		if(!validarCrear()){
			return;
		}
		try {		
			Cobrador cobrador =cobradorSelect;
			if(cobrador.getCobradorId()==null){
				Cobrador tem=cobradorService.getByDocumento(getDocumento());
				if(tem!=null){
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya  existe un cliente con el documento: "+getDocumento(), ""));
					return;
				}
			}
			cobrador.setBarrio(getBarrio());	
			cobrador.setCelular(getCelular());			
			cobrador.setDireccion(getDireccion());
			cobrador.setDocumento(getDocumento().trim());
			cobrador.setFechaRegistro(new Date());
			cobrador.setFijo(getFijo());			
			cobrador.setNombre(getNombre().toLowerCase());
			cobradorService.save(cobrador);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cobrador creado exitosamente", ""));
		} catch (PrestamosException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Error creando cobardor Contacte al tecnico", ""));
			log.error("Error creando Cobrador: "+e.getMessage());
		}
		
		PrimeFaces.current().executeScript("PF('crearCobrador').hide();");
	}
	
	private Boolean validarCrear() {
		Boolean seCrea=true;
		if(getBarrio()==null || getBarrio().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El barrio es obligatorio", ""));
			seCrea=false;
		}
		if(getCelular()==null ){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Celular es obligatorio", ""));
			seCrea=false;
		}	
		if(getDireccion()==null || getDireccion().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El direccion es obligatorio", ""));
			seCrea=false;
		}
		if(getDocumento()==null || getDocumento().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El documento es obligatorio", ""));
			seCrea=false;
		}
		if(getNombre()==null || getNombre().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El nombre es obligatorio", ""));
			seCrea=false;
		}
		if(getFijo()==null ){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El cupo credito es obligatorio", ""));
			seCrea=false;
		}
		
		return seCrea;
	}

	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Long getCelular() {
		return celular;
	}

	public void setCelular(Long celular) {
		this.celular = celular;
	}

	public Long getFijo() {
		return fijo;
	}

	public void setFijo(Long fijo) {
		this.fijo = fijo;
	}



	public String getNombreBusqueda() {
		return nombreBusqueda;
	}

	public void setNombreBusqueda(String nombreBusqueda) {
		this.nombreBusqueda = nombreBusqueda;
	}

	public String getDocumentoBusqueda() {
		return documentoBusqueda;
	}

	public void setDocumentoBusqueda(String documentoBusqueda) {
		this.documentoBusqueda = documentoBusqueda;
	}

	public List<Cobrador> getCobradorList() {
		return cobradorList;
	}

	public void setCobradorList(List<Cobrador> cobradorList) {
		this.cobradorList = cobradorList;
	}

	public Cobrador getCobradorSelect() {
		return cobradorSelect;
	}

	public void setCobradorSelect(Cobrador cobradorSelect) {
		this.cobradorSelect = cobradorSelect;
	}

	
	
	
}
