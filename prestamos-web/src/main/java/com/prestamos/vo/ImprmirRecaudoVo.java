package com.prestamos.vo;

import java.util.Date;

public class ImprmirRecaudoVo {
	
 private String nombreCliente;
 private Double valorCredito;
 private Double valorCuota;
 private Double cantidad;
 private Date fecha;
 private Double abono;
public String getNombreCliente() {
	return nombreCliente;
}
public void setNombreCliente(String nombreCliente) {
	this.nombreCliente = nombreCliente;
}
public Double getValorCredito() {
	return valorCredito;
}
public void setValorCredito(Double valorCredito) {
	this.valorCredito = valorCredito;
}
public Double getValorCuota() {
	return valorCuota;
}
public void setValorCuota(Double valorCuota) {
	this.valorCuota = valorCuota;
}
public Double getCantidad() {
	return cantidad;
}
public void setCantidad(Double cantidad) {
	this.cantidad = cantidad;
}
public Date getFecha() {
	return fecha;
}
public void setFecha(Date fecha) {
	this.fecha = fecha;
}
public Double getAbono() {
	return abono;
}
public void setAbono(Double abono) {
	this.abono = abono;
}
 
 
}