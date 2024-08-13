package com.example.ecommerceapi.feature.brand;

import com.example.ecommerceapi.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByName(String name);

    Optional<Brand> findByUuid(String uuid);
}
