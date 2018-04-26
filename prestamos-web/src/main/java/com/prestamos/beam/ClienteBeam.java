package com.prestamos.beam;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//javax.enterprise.context.SessionScoped
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.prestamos.api.PrestamosException;
import com.prestamos.model.Cliente;
import com.prestamos.service.ClienteService;



/**
 * @author Luis Miguel Gonzalez
 *
 */

@ManagedBean
@SessionScoped
@Controller
public class ClienteBeam implements Serializable {

	private static final long serialVersionUID = -4092185363070853864L;
	private static Logger log = Logger.getLogger(ClienteBeam.class);
	
	ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/applicationContext.xml");
	ClienteService clienteService = context.getBean(ClienteService.class);
	

	private List<Cliente> clientesList;
	private Cliente clienteSelect;
	
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
	private Boolean creditoActivo;
	private Double cupoCredito;	

	/**
	 * Metodo encargado re buscar los clientes por los filtros seleccionados
	 */
	public void buscar(){
		String nom= getNombreBusqueda()!=null?getNombreBusqueda().toLowerCase():getNombreBusqueda();
		setClientesList(clienteService.getByFiltros(nom,getDocumentoBusqueda()));
	}
	
	/**
	 * Metodo encargado de abrir el pupup de crear cliente
	 */
	public void abrirPupupCrear(){
		clienteSelect=new Cliente();
		RequestContext.getCurrentInstance().execute("PF('crearCliente').show();");
	}
	
	public void abrirPopupEditar(Cliente cli){
		
		setBarrio(cli.getBarrio());
		setCelular(cli.getCelular());
		setCreditoActivo(cli.getCreditoActivo()==1?true:false);
		setCupoCredito(cli.getCupoCredito());
		setDireccion(cli.getDireccion());
		setDocumento(cli.getDocumento());
		setFijo(cli.getFijo());
		setNombre(cli.getNombre());
		clienteSelect=cli;
		RequestContext.getCurrentInstance().update("clientesForm:crearClientesForm");
		RequestContext.getCurrentInstance().execute("PF('crearCliente').show();");
	}
	
	/**
	 * Metodo que crea los clientes
	 */
	public void crearCliente(){
		if(!validarCrear()){
			return;
		}
		try {		
			Cliente cliente =clienteSelect;
			if(cliente.getClienteId()==null){
				Cliente tem=clienteService.getByDocumento(getDocumento());
				if(tem!=null){
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya  existe un cliente con el documento: "+getDocumento(), ""));
					return;
				}
			}
			cliente.setBarrio(getBarrio());	
			cliente.setCelular(getCelular());
			cliente.setCreditoActivo(getCreditoActivo()==Boolean.TRUE?1l:0l);
			cliente.setCupoCredito(getCupoCredito());
			cliente.setDireccion(getDireccion());
			cliente.setDocumento(getDocumento().trim());
			cliente.setFechaRegistro(new Date());
			cliente.setFijo(getFijo());			
			cliente.setNombre(getNombre().toLowerCase());
			clienteService.save(cliente);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente creado exitosamente", ""));
		} catch (PrestamosException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Error creando cliente Contacte al tecnico", ""));
			log.error("Error creando cliente: "+e.getMessage());
		}
		
		RequestContext.getCurrentInstance().execute("PF('crearCliente').hide();");
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
		if(getCreditoActivo()==null ){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El credito activo es obligatorio", ""));
			seCrea=false;
		}
		if(getCupoCredito()==null ){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El cupo credito es obligatorio", ""));
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

	public List<Cliente> getClientesList() {
		return clientesList;
	}


	public void setClientesList(List<Cliente> clientesList) {
		this.clientesList = clientesList;
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

	public Boolean getCreditoActivo() {
		return creditoActivo;
	}

	public void setCreditoActivo(Boolean creditoActivo) {
		this.creditoActivo = creditoActivo;
	}

	public Double getCupoCredito() {
		return cupoCredito;
	}

	public void setCupoCredito(Double cupoCredito) {
		this.cupoCredito = cupoCredito;
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
	
}
