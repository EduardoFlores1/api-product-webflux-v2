package com.ejercicio.validador_nombre.service;

import com.ejercicio.validador_nombre.exception.ServiceException;
import com.ejercicio.validador_nombre.mapper.ProductMapper;
import com.ejercicio.validador_nombre.model.dto.ProductRequest;
import com.ejercicio.validador_nombre.model.dto.ProductResponse;
import com.ejercicio.validador_nombre.model.entity.ProductEntity;
import com.ejercicio.validador_nombre.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final Collection<String> previous = new ArrayList<>();

    @Override
    public Flux<ProductResponse> findAll() {
        return productRepository.findAll()
                .map(ProductMapper::entityToResponse);
    }

    @Override
    public Flux<ProductResponse> getAllAsStream() {
        return productRepository.findAll()
                .map(ProductMapper::entityToResponse)
                .delayElements(Duration.ofSeconds(1));
    }

    @Override
    public Flux<String> getPrevious() {
        return Flux.fromIterable(previous);
    }

    @Override
    public Mono<ProductResponse> findById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(ServiceException.notFound(id)))
                .map(ProductMapper::entityToResponse);
    }

    @Override
    public Mono<ProductResponse> insert(ProductRequest request) {
        if (previous.contains(request.getName())) {
            return Mono.error(ServiceException.newInstance4XX("No puede ingresar un registro previamente eliminado"));
        }
        return productRepository.save(ProductMapper.requestToEntity(request))
                .map(ProductMapper::entityToResponse);
    }

    @Override
    public Mono<ProductResponse> updateById(Long id, ProductRequest request) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(ServiceException.notFound(id)))
                .flatMap(founded -> {
                    founded.setName(request.getName());
                    return productRepository.save(founded);
                }).map(ProductMapper::entityToResponse);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(ServiceException.notFound(id)))
                .map(ProductEntity::getName)
                .doOnNext(previous::add)
                .doOnNext(name -> log.info("added {}", name))
                .then(productRepository.deleteById(id));
    }
}
