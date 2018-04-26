package com.prestamos.beam;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.jboss.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.prestamos.model.Menu;
import com.prestamos.model.OpcionUsuario;
import com.prestamos.model.SubMenu;
import com.prestamos.model.Usuario;
import com.prestamos.service.MenuService;
import com.prestamos.service.OpcionUsuarioService;

@ManagedBean
@SessionScoped
@Controller
public class MenuBeam implements Serializable {

	
	
	
	/**
	 * Luis Miguel Gonzalez
	 */
	private static final long serialVersionUID = -2885838376417664466L;
	private static Logger log = Logger.getLogger(MenuBeam.class);
	

	ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/applicationContext.xml");
	MenuService menuService = context.getBean(MenuService.class);
	OpcionUsuarioService opcionUsuarioService = context.getBean(OpcionUsuarioService.class);
	
	ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	Map<String, Object> sessionMap = externalContext.getSessionMap();

	private List<Menu> menus;
    private List<OpcionUsuario> subMenus;
    
    public String tituloOpcion;
    
    public Usuario usuarioLogin(){  	
    	return (Usuario)  sessionMap.get("userLogin");
    }
    
    public void cerrrarSesion(){
    	sessionMap.put("userLogin",  null);
    	goToLogin();
    }
   
	
    public String redireccionar(SubMenu subMenu){		
		FacesContext fc = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath= servletContext.getContextPath();		
		String ruta = realPath+"/"+subMenu.getRuta();
		log.info("ruta:"+ruta);
		setTituloOpcion(subMenu.getNombre());
        try {
			fc.getExternalContext().redirect(ruta);
		} catch (IOException e) {
			log.error("Error en redirección: "+e.getMessage());			
		}
		return "";
	}
    
    public void goToLogin(){
    	FacesContext fc = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath= servletContext.getContextPath();		
		String ruta = realPath+"/index.jsf";
		log.info("ruta:"+ruta);
		setTituloOpcion("");
        try {
			fc.getExternalContext().redirect(ruta);
		} catch (IOException e) {
			log.error("Error en redirección: "+e.getMessage());			
		}		
    }
	

	public List<Menu> getMenus() {
		if (menus == null) {
			menus = menuService.getByAll();
		}		
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<OpcionUsuario> getSubMenus(String menuId) {
		 List<OpcionUsuario> list = new ArrayList<>();
		 for(OpcionUsuario s:getSubMenus()){				 
			 if(menuId.equals(s.getSubMenuId().getMenuId().getMenuId().toString())){				 
				 list.add(s);
			 }
		 }	 
		return list;
	}
	
	public List<OpcionUsuario> getSubMenus() {	
			if(usuarioLogin()==null){
				goToLogin();
			}
			subMenus =opcionUsuarioService.getByUsuario(usuarioLogin().getUsuarioId());				
	return subMenus;
	}


	public void setSubMenus(List<OpcionUsuario> subMenus) {
		this.subMenus = subMenus;
	}


	public  String getTituloOpcion() {
		return tituloOpcion;
	}


	public  void setTituloOpcion(String tituloOpcion) {
		this.tituloOpcion = tituloOpcion;
	}

	
}
