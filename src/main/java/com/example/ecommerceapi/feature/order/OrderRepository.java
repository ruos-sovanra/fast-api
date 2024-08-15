package com.example.ecommerceapi.feature.order;

import com.example.ecommerceapi.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser_Uuid(String uuid,Pageable pageable);
    Optional<Order> findByUuid(String uuid);
}
