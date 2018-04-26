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
@Table(name = "CREDITO")
public class Credito {

	@Id
	@SequenceGenerator(name = "S_CREDITO", sequenceName = "S_CREDITO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_CREDITO")
	@NotNull
	@Column(name = "CREDITO_ID")
	private Long creditoId;

	@ManyToOne
	@JoinColumn(name = "COBRADOR_ID")
	private Cobrador cobradorId;

	@ManyToOne
	@JoinColumn(name = "CLIENTE_ID")
	private Cliente clienteId;

	@ManyToOne
	@JoinColumn(name = "USUARIO_ID")
	private Usuario usuarioId;

	@Column(name = "NUMERO_CUOTAS")
	private Integer numeroCuotas;

	@Column(name = "INTERES")
	private Double interes;

	@Column(name = "FECHA_INICIO")
	private Date fechaInicio;

	@Column(name = "FECHA_FIN")
	private Date fechaFin;

	@Column(name = "FECHA_REGISTRO")
	private Date fechaRegistro;

	@Column(name = "VALOR_PAGAR")
	private Double valorPagar;

	@Column(name = "VALOR_CUOTA")
	private Double valorCuota;

	@Column(name = "ABONO_TOTAL")
	private Double abonoTotal;

	@Column(name = "SALDO")
	private Double saldo;

	@Column(name = "TOTAL_PRESTAMO")
	private Double totalPrestamo;

	@Column(name = "CUOTAS_PAGADAS")
	private Double cuotasPagadas;

	public Long getCreditoId() {
		return creditoId;
	}

	public void setCreditoId(Long creditoId) {
		this.creditoId = creditoId;
	}

	public Cobrador getCobradorId() {
		return cobradorId;
	}

	public void setCobradorId(Cobrador cobradorId) {
		this.cobradorId = cobradorId;
	}

	public Cliente getClienteId() {
		return clienteId;
	}

	public void setClienteId(Cliente clienteId) {
		this.clienteId = clienteId;
	}

	public Usuario getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Usuario usuarioId) {
		this.usuarioId = usuarioId;
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

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistror) {
		this.fechaRegistro = fechaRegistror;
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

	public Double getAbonoTotal() {
		return abonoTotal;
	}

	public void setAbonoTotal(Double abonoTotal) {
		this.abonoTotal = abonoTotal;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Double getTotalPrestamo() {
		return totalPrestamo;
	}

	public void setTotalPrestamo(Double totalPrestamo) {
		this.totalPrestamo = totalPrestamo;
	}

	public Double getCuotasPagadas() {
		return cuotasPagadas;
	}

	public void setCuotasPagadas(Double cuotasPagadas) {
		this.cuotasPagadas = cuotasPagadas;
	}

}
