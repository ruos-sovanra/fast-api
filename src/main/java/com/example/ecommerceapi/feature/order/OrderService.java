package com.example.ecommerceapi.feature.order;

import com.example.ecommerceapi.feature.order.dto.OrderRequest;
import com.example.ecommerceapi.feature.order.dto.OrderResponse;
import com.example.ecommerceapi.feature.order.dto.OrderUpdateRequest;
import com.example.ecommerceapi.security.CustomUserDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest, @AuthenticationPrincipal CustomUserDetail customUserDetail);
    OrderResponse getOrder(Long id);
    void deleteOrder(Long id);
    OrderResponse updateOrderStatus(Long id, OrderUpdateRequest orderUpdateRequest);
    List<OrderResponse> getOrdersByUserId(Long userId);
    OrderResponse updateQuantity(Long id,int quantity);
}
