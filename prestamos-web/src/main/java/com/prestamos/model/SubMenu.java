package com.prestamos.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="SUB_MENU")
public class SubMenu {

	@Id
	@SequenceGenerator(name="S_SUB_MENU", sequenceName="S_SUB_MENU", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_SUB_MENU")
	@NotNull
	@Column(name="SUB_MENU_ID")
	private Long subMenuId;
	@NotNull
	
	@ManyToOne
	@JoinColumn(name="MENU_ID")
	private Menu menuId;
	
	@Column(name="NOMBRE")
	private String nombre;
	
	@Column(name="ESTADO")
	private Long estado;
	
	@Column(name="URL")
	private String url;
	
	@Column(name="RUTA")
	private String ruta;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Long getSubMenuId() {
		return subMenuId;
	}
	public void setSubMenuId(Long subMenuId) {
		this.subMenuId = subMenuId;
	}
	public Long getEstado() {
		return estado;
	}
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public Menu getMenuId() {
		return menuId;
	}
	public void setMenuId(Menu menuId) {
		this.menuId = menuId;
	}
	
	
	
}
