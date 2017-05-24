package com.aes.modelado.pagos.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pago implements Serializable{
	
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
