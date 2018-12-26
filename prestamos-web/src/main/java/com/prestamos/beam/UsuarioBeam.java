package com.prestamos.beam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//javax.enterprise.context.SessionScoped
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.prestamos.api.PrestamosException;
import com.prestamos.model.OpcionUsuario;
import com.prestamos.model.Rol;
import com.prestamos.model.SubMenu;
import com.prestamos.model.Usuario;
import com.prestamos.service.MenuService;
import com.prestamos.service.OpcionUsuarioService;
import com.prestamos.service.UsuarioService;

/**
 * @author Luis Miguel Gonzalez
 *
 */

@ManagedBean
@SessionScoped
@Controller
public class UsuarioBeam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3167612191485273221L;
	private static Logger log = Logger.getLogger(UsuarioBeam.class);

	ApplicationContext context = new ClassPathXmlApplicationContext(
			"classpath*:META-INF/spring/applicationContext.xml");
	UsuarioService usuarioService = context.getBean(UsuarioService.class);
	MenuService menuService = context.getBean(MenuService.class);
	OpcionUsuarioService opcionUsuarioService = context.getBean(OpcionUsuarioService.class);

	private List<Usuario> usuarioList;
	private Usuario usuarioSelect;
	private List<Rol> roles;

	// busqueda
	private String nombreBusqueda;
	private String documentoBusqueda;

	// creacion
	private String nombre;
	private String apellido;
	private String correo;
	private String clave;
	private String documento;
	private String login;
	private Long rol;

	// opciones usuario
	private DualListModel<String> opciones= new DualListModel<>();
	private List<String> usuariosSource = new ArrayList<>();
	private List<String> usuariosTarget = new ArrayList<>();

	/**
	 * Metodo encargado re buscar los usuario por los filtros seleccionados
	 */
	public void buscar() {
		String nom = getNombreBusqueda() != null ? getNombreBusqueda().toLowerCase() : getNombreBusqueda();
		setUsuarioList(usuarioService.getByFiltros(nom, getDocumentoBusqueda()));
	}

	/**
	 * Metodo encargado de abrir el pupup de crear usaurio
	 */
	public void abrirPupupCrear() {
		usuarioSelect = new Usuario();
		PrimeFaces.current().executeScript("PF('crearUsuario').show();");
	}
	
	/**
	 * Metodo encargado de abrir el popup de las opciones usuario
	 */
	public void abrirPopupOpcionesUsuario(Usuario usu) {
		List<SubMenu> submenuSource=menuService.getByAllSubMenu();
		List<OpcionUsuario> op = opcionUsuarioService.getByUsuario(usu.getUsuarioId());
		setUsuariosTarget(new ArrayList<>());
		setUsuariosSource(new ArrayList<>());
		for(SubMenu s: submenuSource){
			String opcionName = s.getNombre();
			getUsuariosSource().add(opcionName);
		}
		for(OpcionUsuario o: op){
			String opcionName = o.getSubMenuId().getNombre();
			getUsuariosTarget().add(opcionName);
			getUsuariosSource().remove(opcionName);
		}
		usuarioSelect = usu;
		setOpciones(new DualListModel<String>(getUsuariosSource(), getUsuariosTarget()));
		PrimeFaces.current().executeScript("PF('opcionesUsuario').show();");
	}

	public void abrirPopupEditar(Usuario usu) {
		setDocumento(usu.getIdentificacion());
		setNombre(usu.getNombre());
		setClave(usu.getClave());
		setLogin(usu.getLogin());
		setRol(usu.getRolId().getRolId());
		setCorreo(usu.getCorreo());
		usuarioSelect = usu;
		PrimeFaces.current().ajax().update("suarioForm:crearUsuarioForm");  
		PrimeFaces.current().executeScript("PF('crearUsuario').show();");
	}

	/**
	 * Metodo que crea los usuarios
	 */
	public void crearUsuario() {
		if (!validarCrear()) {
			return;
		}
		try {
			Usuario usuario = usuarioSelect;
			if (usuario.getUsuarioId() == null) {
				Usuario tem = usuarioService.getByIdentificacion(getDocumento());
				if (tem != null) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Ya  existe un usuario con el documento: " + getDocumento(), ""));
					return;
				}
			}
			usuario.setIdentificacion(getDocumento().trim());
			usuario.setFechaRegistro(new Date());
			usuario.setNombre(getNombre().toLowerCase());
			usuario.setClave(getClave());
			usuario.setLogin(getLogin());
			Rol roltemp = new Rol();
			roltemp.setRolId(getRol());
			usuario.setEstado("1");
			usuario.setRolId(roltemp);
			usuario.setCorreo(getCorreo());
			usuarioService.save(usuario);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario creado exitosamente", ""));
		} catch (PrestamosException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error creando usuario Contacte al tecnico", ""));
			log.error("Error creando usaurio: " + e.getMessage());
		}

		PrimeFaces.current().executeScript("PF('crearUsuario').hide();");
	}
	
	/**
	 * Metodo que guarda las opciones de usuario ingresadas
	 */
	public void guardarOpciones(){
		try {
			List<OpcionUsuario> opcionUsuarios = opcionUsuarioService.getByUsuario(getUsuarioSelect().getUsuarioId());
			for(OpcionUsuario o: opcionUsuarios){
				opcionUsuarioService.delete(o);
			}
			for(String s: getUsuariosTarget()){
				OpcionUsuario op= new OpcionUsuario();
				SubMenu submenu = menuService.getByName(s);
				op.setEstado(1l);
				op.setFechaRegistro(new Date());
				op.setSubMenuId(submenu);
				op.setUsuarioId(usuarioSelect);
				opcionUsuarioService.save(op);
				
			}
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Opciones guardadas exitosamente", ""));
		} catch (PrestamosException e) {
			log.error("error guardando opciones usuario: "+e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error guardando opciones, consulte a su proveedor del software", ""));
		}
		
	}
	public void onTransfer(TransferEvent event) {
		for(Object item : event.getItems()) {
			String name = (String) item;
			if(getUsuariosSource().contains(name)){
				getUsuariosSource().remove(name);
				getUsuariosTarget().add(name);
			}else{
				getUsuariosSource().add(name);
				getUsuariosTarget().remove(name);
			}
		}		
	}

	private Boolean validarCrear() {
		Boolean seCrea = true;

		if (getDocumento() == null || getDocumento().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El documento es obligatorio", ""));
			seCrea = false;
		}
		if (getNombre() == null || getNombre().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El nombre es obligatorio", ""));
			seCrea = false;
		}

		if (getRol() == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El ROl es obligatorio", ""));
			seCrea = false;
		}
		if (getClave() == null || getClave().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "La clave es obligatoria", ""));
			seCrea = false;
		}
		if (getLogin() == null || getLogin().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El login es obligatorio", ""));
			seCrea = false;
		}

		return seCrea;
	}

	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}

	public void setUsuarioList(List<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}

	public Usuario getUsuarioSelect() {
		return usuarioSelect;
	}

	public void setUsuarioSelect(Usuario usuarioSelect) {
		this.usuarioSelect = usuarioSelect;
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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

	public List<Rol> getRoles() {
		roles = usuarioService.getByAllRoles();
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public Long getRol() {
		return rol;
	}

	public void setRol(Long rol) {
		this.rol = rol;
	}

	public DualListModel<String> getOpciones() {
		return opciones;
	}

	public void setOpciones(DualListModel<String> opciones) {
		this.opciones = opciones;
	}

	public List<String> getUsuariosSource() {
		return usuariosSource;
	}

	public void setUsuariosSource(List<String> usuariosSource) {
		this.usuariosSource = usuariosSource;
	}

	public List<String> getUsuariosTarget() {
		return usuariosTarget;
	}

	public void setUsuariosTarget(List<String> usuariosTarget) {
		this.usuariosTarget = usuariosTarget;
	}
	
}
