package com.aes.modelado.consulta.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Consulta implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty
	private String 			referenciaFactura;

	public String getReferenciaFactura() {
		return referenciaFactura;
	}

	public void setReferenciaFactura(String referenciaFactura) {
		this.referenciaFactura = referenciaFactura;
	}
}
