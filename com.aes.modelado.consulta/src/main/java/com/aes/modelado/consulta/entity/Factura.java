package com.aes.modelado.consulta.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Factura implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty
	private String 			referenciaFactura;
	@JsonProperty
	private BigDecimal 		totalPagar;
	
	public String getReferenciaFactura() {
		return referenciaFactura;
	}

	public void setReferenciaFactura(String referenciaFactura) {
		this.referenciaFactura = referenciaFactura;
	}
	
	public BigDecimal getValor() {
		return totalPagar;
	}
	
	public void setValor(BigDecimal valor) {
		this.totalPagar = valor;
	}
}
