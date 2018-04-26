package com.prestamos.vo;

import com.prestamos.model.Recaudo;

public class RecaudoVo {
	private Recaudo recaudoId;
	private String pagado;
	private Double pagoParcial;
	private Double valorCuota;
	private String pagadoNombre;
	
	public Recaudo getRecaudoId() {
		return recaudoId;
	}
	public void setRecaudoId(Recaudo recaudoId) {
		this.recaudoId = recaudoId;
	}
	public String getPagado() {
		return pagado;
	}
	public void setPagado(String pagado) {
		this.pagado = pagado;
	}
	public Double getPagoParcial() {
		return pagoParcial;
	}
	public void setPagoParcial(Double pagoParcial) {
		this.pagoParcial = pagoParcial;
	}
	public Double getValorCuota() {
		return valorCuota;
	}
	public void setValorCuota(Double valorCuota) {
		this.valorCuota = valorCuota;
	}
	public String getPagadoNombre() {
		return pagadoNombre;
	}
	public void setPagadoNombre(String pagadoNombre) {
		this.pagadoNombre = pagadoNombre;
	}
	
	
}
