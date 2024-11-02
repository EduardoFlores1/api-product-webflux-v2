package com.ejercicio.validador_nombre.service;

import com.ejercicio.validador_nombre.exception.ServiceException;
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
    public Flux<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Flux<ProductEntity> getAllAsStream() {
        return productRepository.findAll()
                .delayElements(Duration.ofSeconds(1));
    }

    @Override
    public Flux<String> getPrevious() {
        return Flux.fromIterable(previous);
    }

    @Override
    public Mono<ProductEntity> findById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(ServiceException.notFound(id)));
    }

    @Override
    public Mono<ProductEntity> insert(ProductEntity product) {
        if (previous.contains(product.getName())) {
            return Mono.error(ServiceException.newInstance4XX("No puede ingresar un registro previamente eliminado"));
        }
        return productRepository.save(product);
    }

    @Override
    public Mono<ProductEntity> updateById(ProductEntity product) {
        return productRepository.findById(product.getId())
                .switchIfEmpty(Mono.error(ServiceException.notFound(product.getId())))
                .flatMap(founded -> {
                    founded.setName(product.getName());
                    return productRepository.save(founded);
                });
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
