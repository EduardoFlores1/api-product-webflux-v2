package com.ejercicio.validador_nombre.router.handler;

import com.ejercicio.validador_nombre.model.dto.ProductDTO;
import com.ejercicio.validador_nombre.service.ProductService;
import com.ejercicio.validador_nombre.validator.ObjectValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductService productService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<ProductDTO> products = productService.findAll()
                .map(ProductDTO::from);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(products, ProductDTO.class);
    }

    public Mono<ServerResponse> getAllAsStream(ServerRequest request) {
        Flux<ProductDTO> products = productService.getAllAsStream()
                .map(ProductDTO::from);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(products, ProductDTO.class);
    }

    public Mono<ServerResponse> getPrevious(ServerRequest request) {
        Flux<String> previous = productService.getPrevious();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(previous, String.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("productId"));
        Mono<ProductDTO> product = productService.findById(id)
                .map(ProductDTO::from);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(product, ProductDTO.class);
    }

    public Mono<ServerResponse> insertOne(ServerRequest request) {
        return request
                .bodyToMono(ProductDTO.class)
                .doOnNext(objectValidator::validate)
                .map(ProductDTO::toCreateEntity)
                .flatMap(productService::insert)
                .map(ProductDTO::from)
                .flatMap(response ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(response), ProductDTO.class));

    }

    public Mono<ServerResponse> updateById(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("productId"));
        return request
                .bodyToMono(ProductDTO.class)
                .doOnNext(objectValidator::validate)
                .map(dto -> dto.toUpdateEntity(id))
                .flatMap(productService::updateById)
                .map(ProductDTO::from)
                .flatMap(response ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(response), ProductDTO.class));

    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("productId"));

        return ServerResponse
                .status(HttpStatus.NO_CONTENT)
                .body(productService.deleteById(id), Void.class);
    }

}
