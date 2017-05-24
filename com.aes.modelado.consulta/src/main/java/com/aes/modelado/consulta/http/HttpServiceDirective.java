package com.aes.modelado.consulta.http;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.*;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.time.LocalDate;
import java.util.concurrent.CompletionStage;

import com.aes.modelado.consulta.entity.Consulta;
import com.aes.modelado.consulta.entity.Respuesta;
import com.aes.modelado.consulta.service.ConsultaService;
import com.aes.modelado.pagos.util.JacksonJdk8;

/**
 * Created by AHernandezS on 22/03/2017.
 */

public class HttpServiceDirective extends AllDirectives {

	ConsultaService service = new ConsultaService();

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("routes");

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        HttpServiceDirective app = new HttpServiceDirective();

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute().flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost("0.0.0.0", 9092), materializer);

        System.out.println("Server online at http://localhost:9092/\nPress RETURN to stop...");
        System.in.read();

        binding.thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }

    private Route createRoute() {
        return route(
                pathPrefix("consultar", () ->
                        route(
                                post(() -> entity(JacksonJdk8.unmarshaller(Consulta.class), consultar -> handleOperation(consultar)))
                        )
                )
        );
    }

    private Route handleOperation(Consulta consultar) {
        System.out.println(LocalDate.now() + ": consultar ");
        return complete(StatusCodes.OK, service.consultar(consultar), Jackson.<Respuesta>marshaller());
    }

}