package com.volka.ecommerce.orderservice.repository;

import com.volka.ecommerce.orderservice.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    Optional<Catalog> findByProductId(String productId);
}
