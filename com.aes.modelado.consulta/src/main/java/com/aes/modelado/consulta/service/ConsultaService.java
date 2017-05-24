package com.aes.modelado.consulta.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.aes.modelado.consulta.entity.Consulta;
import com.aes.modelado.consulta.entity.Factura;
import com.aes.modelado.consulta.entity.Respuesta;

public class ConsultaService {
	
	public Respuesta consultar(Consulta consulta){
		
		Respuesta response = new Respuesta();
		Factura factura= new Factura();
		
		factura.setReferenciaFactura(consulta.getReferenciaFactura());
		response.setFactura(factura);
		response.setMensaje("COMPLETO!");
		
		try {
			
			URL url = new URL("http://localhost:8080/RESTfulExample/r/router/pagar");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"referenciaFactura\":"+consulta.getReferenciaFactura()+"\"}";

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
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();
 
		 }

		
		return response;
	}

}
