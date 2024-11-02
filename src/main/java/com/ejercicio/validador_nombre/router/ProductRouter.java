package com.ejercicio.validador_nombre.router;

import com.ejercicio.validador_nombre.router.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRouter {

    private static final String PATH = "api/v1/products";

    @Bean
    public RouterFunction<ServerResponse> routes(ProductHandler handler) {
        return RouterFunctions.route()
                .GET(PATH, handler::findAll)
                .GET(PATH + "/stream", handler::getAllAsStream)
                .GET(PATH + "/previous/stream", handler::getPrevious)
                .GET(PATH + "/{productId}", handler::findById)
                .POST(PATH, handler::insertOne)
                .PUT(PATH + "/{productId}", handler::updateById)
                .DELETE(PATH + "/{productId}", handler::deleteById)
                .build();
    }
}
