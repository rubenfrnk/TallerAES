package com.aes.modelado.compensacion.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Respuesta implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty
	private Factura 		factura;
	@JsonProperty
	private String 			mensaje;
	
	public Factura getFactura() {
		return factura;
	}
	
	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
