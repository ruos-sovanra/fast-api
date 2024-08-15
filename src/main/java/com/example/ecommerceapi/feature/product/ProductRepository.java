package com.example.ecommerceapi.feature.product;

import com.example.ecommerceapi.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByIsDeletedFalse(Pageable pageable);

    Page<Product> findAllByCategory_UuidAndIsDeletedFalse(String uuid, Pageable pageable);

    Optional<Product> findProductByUuid(String uuid);

    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.isDeleted = false")
    Optional<Product> findById(@Param("id") Long id);

    Optional<Product> findByName(String name);
}
