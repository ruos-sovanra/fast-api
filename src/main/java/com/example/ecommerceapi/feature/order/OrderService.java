package com.example.ecommerceapi.feature.order;

import com.example.ecommerceapi.feature.order.dto.OrderRequest;
import com.example.ecommerceapi.feature.order.dto.OrderResponse;
import com.example.ecommerceapi.feature.order.dto.OrderUpdateRequest;
import com.example.ecommerceapi.security.CustomUserDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest, @AuthenticationPrincipal CustomUserDetail customUserDetail);
    OrderResponse getOrder(String uuid);
    void deleteOrder(String uuid);
    OrderResponse updateOrderStatus(String uuid, OrderUpdateRequest orderUpdateRequest);
    List<OrderResponse> getOrdersByUserId(@AuthenticationPrincipal CustomUserDetail currentUser);
    OrderResponse updateQuantity(String uuid,int quantity);
}
