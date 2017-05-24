package com.aes.modelado.pagos.service;

import com.aes.modelado.pagos.entity.Factura;
import com.aes.modelado.pagos.entity.Pago;
import com.aes.modelado.pagos.entity.Respuesta;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PagosService {
	
	public Respuesta pagar(Pago pago){
		
		Respuesta response = new Respuesta();
		Factura factura= new Factura();
		
		factura.setReferenciaFactura(pago.getFactura().getReferenciaFactura());
		response.setFactura(factura);
		response.setMensaje("COMPLETO!");
		
		try {
			
			URL url = new URL("http://localhost:8080/RESTfulExample/r/router/pagar");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"referenciaFactura\":"+pago.getFactura().getReferenciaFactura()+",\"totalPagar\":\""+pago.getFactura().getValor()+"\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			StringBuffer sb;
			
			sb = new StringBuffer();
			
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			
			ObjectMapper mapper = new ObjectMapper();
			
			response = mapper.readValue(output, Respuesta.class);

			conn.disconnect();

		  } catch (JsonGenerationException e){
	            e.printStackTrace();
	        } catch (JsonMappingException e) {
	            e.printStackTrace();
	        }catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		 }

		
		return response;
	}

}
