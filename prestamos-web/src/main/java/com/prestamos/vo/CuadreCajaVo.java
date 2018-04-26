package com.prestamos.vo;

import com.prestamos.model.Cobrador;

public class CuadreCajaVo {
	private Double totalRecaudo;
	private Double prestado;
	private Double efectivo;
	private Double totalEntregado;
	private Cobrador cobradorId;
	
	public Double getTotalRecaudo() {
		return totalRecaudo;
	}
	public void setTotalRecaudo(Double totalRecaudo) {
		this.totalRecaudo = totalRecaudo;
	}
	public Double getPrestado() {
		return prestado;
	}
	public void setPrestado(Double prestado) {
		this.prestado = prestado;
	}
	public Double getEfectivo() {
		return efectivo;
	}
	public void setEfectivo(Double efectivo) {
		this.efectivo = efectivo;
	}
	public Double getTotalEntregado() {
		return totalEntregado;
	}
	public void setTotalEntregado(Double totalEntregado) {
		this.totalEntregado = totalEntregado;
	}
	public Cobrador getCobradorId() {
		return cobradorId;
	}
	public void setCobradorId(Cobrador cobradorId) {
		this.cobradorId = cobradorId;
	}
	
}
