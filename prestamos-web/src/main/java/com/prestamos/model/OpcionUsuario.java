package com.prestamos.model;




import java.util.Date;

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
@Table(name="OPCION_USUARIO")
public class OpcionUsuario {

	@Id
	@SequenceGenerator(name="S_OPCION_USUARIO", sequenceName="S_OPCION_USUARIO", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_OPCION_USUARIO")
	@NotNull
	@Column(name="OPCION_USUARIO_ID")
	private Long opcionUsuarioId;
	
	
	@ManyToOne
	@JoinColumn(name="SUB_MENU_ID")
	private SubMenu subMenuId;
	
	@ManyToOne
	@JoinColumn(name="USUARIO_ID")
	private Usuario usuarioId;
	
	@Column(name="ESTADO")
	private Long estado;
	
	@Column(name="FECHA_REGISTRO")
	private Date fechaRegistro;
	
	public Long getOpcionUsuarioId() {
		return opcionUsuarioId;
	}

	public void setOpcionUsuarioId(Long opcionUsuarioId) {
		this.opcionUsuarioId = opcionUsuarioId;
	}

	
	public SubMenu getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(SubMenu subMenuId) {
		this.subMenuId = subMenuId;
	}

	public Usuario getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Usuario usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}
