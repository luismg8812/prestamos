package com.prestamos.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@Entity
@Table(name="COBRADOR")
public class Cobrador {

	@Id
	@SequenceGenerator(name="S_COBRADOR", sequenceName="S_COBRADOR", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_COBRADOR")
	@NotNull
	@Column(name="COBRADOR_ID")
	private Long cobradorId;
	
	@Column(name="NOMBRE")
	private String nombre;
	
	@Column(name="DOCUMENTO")
	private String documento;
	
	@Column(name="BARRIO")
	private String barrio;
	
	@Column(name="DIRECCION")
	private String direccion;
	
	@Column(name="CELULAR")
	private Long celular;
	
	@Column(name="FIJO")
	private Long fijo;
	
	@Column(name="FECHA_REGISTRO")
	private Date fechaRegistro;
	
	@Column(name="CUMPLEAÑOS")
	private Date cumpleanos;
	
	

	public Long getCobradorId() {
		return cobradorId;
	}

	public void setCobradorId(Long cobradorId) {
		this.cobradorId = cobradorId;
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

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getCumpleanos() {
		return cumpleanos;
	}

	public void setCumpleanos(Date cumpleanos) {
		this.cumpleanos = cumpleanos;
	}

}
