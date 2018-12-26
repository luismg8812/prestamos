package com.prestamos.beam;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
//javax.enterprise.context.SessionScoped
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.jboss.logging.Logger;
import org.primefaces.PrimeFaces;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.prestamos.api.PrestamosException;
import com.prestamos.model.Usuario;
import com.prestamos.service.UsuarioService;

/**
 * @author Luis Miguel Gonzalez
 *
 */

@ManagedBean
@SessionScoped
@Controller
public class AutenticacionBeam implements Serializable {

	private static final long serialVersionUID = -4092185363070853864L;
	private static Logger log = Logger.getLogger(AutenticacionBeam.class);

	ApplicationContext context = new ClassPathXmlApplicationContext(
			"classpath*:META-INF/spring/applicationContext.xml");
	UsuarioService usuarioService = context.getBean(UsuarioService.class);

	ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	Map<String, Object> sessionMap = externalContext.getSessionMap();

	private String usuario;
	private String password;

	private String propietarioBorrar;
	private String mensajeError;
	
	public void clave() {
		log.info("mensaje: "+getPassword());
	}
	
	
	/**
	 * Metodo de logueo para la lave del propietario desde recaudos
	 */
	public void clavePropietario() {
		
		if (getPropietarioBorrar() == null || getPropietarioBorrar().isEmpty() || getPassword() == null
				|| getPassword().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El usuario y la contrseña son obligatorios", ""));
			setMensajeError("El usuario y la contrseña son obligatorios");
			return;
		}
		Usuario propietario = usuarioService.getByName(getPropietarioBorrar());
		if (propietario != null) {
			//compara que sea la misma clave y que tenga rol 2=propietario
			if (propietario.getClave().equals(getPassword()) && propietario.getRolId().getRolId()==2 ) {
				// dependiendo de el valor de la variable se avida el boton de
				// lo que tiene que ir a hacer despeus de que se loguee el
				// propietario
				String accion = (String) sessionMap.get("botonBorrado");
				activar(accion);
				setPropietarioBorrar(null);
				setPassword(null);
				
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Usuario o clave erroneos o no es un propietario", ""));
				setMensajeError("Usuario o clave erroneos o no es un propietario");
				
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Usuario o clave erroneos", ""));
			setMensajeError("Usuario o clave erroneos");
			
		}
	}

	/**
	 * Metodo que controla el logueo
	 */
	public void login() {
		if (!validar()) {
			return;
		}
		log.info("paso las validaciones");
		FacesContext fc = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String realPath = servletContext.getContextPath();
		String ruta = realPath + "/menu.jsf";
		try {
			fc.getExternalContext().redirect(ruta);
		} catch (IOException e) {
			log.fatal("Error en redirección" + e.getMessage());
		}
	}

	/**
	 * Metodo querealiza las validaciones de los campos del login
	 * 
	 * @return
	 */
	private boolean validar() {
		Boolean dejaPasar = Boolean.TRUE;
		Usuario usuarioTemp = null;
		try {
			usuarioTemp = usuarioService.getByName(getUsuario());
			if (usuarioTemp == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Por favor valide las credenciales ingresadas", ""));
				dejaPasar = Boolean.FALSE;
			} else {
				if (!usuarioTemp.getClave().equals(getPassword())) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Contraseña incorrecta, por valor intente nuevamente", ""));
					dejaPasar = Boolean.FALSE;
				}
			}
		} catch (PrestamosException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ha Ocurrido un error, por favor contactar a su distribuidor del software", ""));
			log.error("mensaje de fatal: " + e.getMessage());
		}
		if (dejaPasar) {
			sessionMap.put("userLogin", usuarioTemp);
		}

		return dejaPasar;
	}
	
	private void activar(String accion){	
		switch (accion) {
		case "btnGuardarRecaudo":
			PrimeFaces.current().executeScript("document.getElementById('recaudosDiaForm:clavePropietarioForm:btnRecaudos').click();");	
			PrimeFaces.current().executeScript("PF('clavePropietarioRecaudo').hide();");
			break;
		case "btnGuardarCredito":		
			PrimeFaces.current().executeScript("document.getElementById('creditosForm:crearCreditoForm:clavePropietarioForm:btnCreditos').click();");	
			PrimeFaces.current().executeScript("PF('clavePropietarioCredito').hide();");
			break;
			
		default:
			break;
		}
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPropietarioBorrar() {
		return propietarioBorrar;
	}

	public void setPropietarioBorrar(String propietarioBorrar) {
		this.propietarioBorrar = propietarioBorrar;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
}
