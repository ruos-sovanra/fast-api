package com.example.ecommerceapi.feature.order;

import com.example.ecommerceapi.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Uuid(String uuid);
    Optional<Order> findByUuid(String uuid);
}
