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
@Table(name="RECAUDO")
public class Recaudo {

	@Id
	@SequenceGenerator(name="S_RECAUDO", sequenceName="S_RECAUDO", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_RECAUDO")
	@NotNull
	@Column(name="RECAUDO_ID")
	private Long recaudoId;
	
	@ManyToOne
	@JoinColumn(name = "USUARIO_ID")
	private Usuario usuarioId;
	
	@ManyToOne
	@JoinColumn(name = "CREDITO_ID")
	private Credito creditoId;
	
	
	@Column(name="FECHA_RECAUDO")
	private Date fechaRecaudo;
	
	@Column(name="FECHA_REGISTRO")
	private Date fechaRegistro;
	
	@Column(name="VALOR_RECAUDO")
	private Double valorRecaudo;

	public Long getRecaudoId() {
		return recaudoId;
	}

	public void setRecaudoId(Long recaudoId) {
		this.recaudoId = recaudoId;
	}

	public Usuario getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Usuario usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Credito getCreditoId() {
		return creditoId;
	}

	public void setCreditoId(Credito creditoId) {
		this.creditoId = creditoId;
	}

	public Date getRechaRecaudo() {
		return fechaRecaudo;
	}

	public void setFechaRecaudo(Date rechaRecaudo) {
		this.fechaRecaudo = rechaRecaudo;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Double getValorRecaudo() {
		return valorRecaudo;
	}

	public void setValorRecaudo(Double valorRecaudo) {
		this.valorRecaudo = valorRecaudo;
	}
	
}
