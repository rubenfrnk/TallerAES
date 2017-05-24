package com.aes.modelado.compensacion.entity;

import java.io.Serializable;

import com.aes.modelado.compensacion.entity.Factura;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Compensacion implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty
	private Factura 		factura;
	
	public Factura getFactura() {
		return factura;
	}
	
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
}
