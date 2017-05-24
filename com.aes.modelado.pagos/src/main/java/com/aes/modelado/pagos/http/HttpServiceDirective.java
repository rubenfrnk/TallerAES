package com.aes.modelado.pagos.http;

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

import com.aes.modelado.pagos.entity.Pago;
import com.aes.modelado.pagos.entity.Respuesta;
import com.aes.modelado.pagos.service.PagosService;

/**
 * Created by AHernandezS on 22/03/2017.
 */

public class HttpServiceDirective extends AllDirectives {

	PagosService service = new PagosService();

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("routes");

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        HttpServiceDirective app = new HttpServiceDirective();

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute().flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost("0.0.0.0", 9090), materializer);

        System.out.println("Server online at http://localhost:9090/\nPress RETURN to stop...");
        System.in.read();

        binding.thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }

    private Route createRoute() {
        return route(
                pathPrefix("pagar", () ->
                        route(
                                post(() -> entity(com.aes.modelado.pagos.util.JacksonJdk8.unmarshaller(Pago.class), pagar -> handleOperation(pagar)))
                        )
                )
        );
    }

    private Route handleOperation(Pago pago) {
        System.out.println(LocalDate.now() + ": pagar ");
        return complete(StatusCodes.OK, service.pagar(pago), Jackson.<Respuesta>marshaller());
    }

}