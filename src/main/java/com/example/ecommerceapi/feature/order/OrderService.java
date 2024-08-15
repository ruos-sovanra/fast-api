package com.example.ecommerceapi.feature.order;

import com.example.ecommerceapi.feature.order.dto.OrderRequest;
import com.example.ecommerceapi.feature.order.dto.OrderResponse;
import com.example.ecommerceapi.feature.order.dto.OrderUpdateRequest;
import com.example.ecommerceapi.feature.order.dto.UpdateQuantitydto;
import com.example.ecommerceapi.security.CustomUserDetail;
import com.example.ecommerceapi.utils.CustomPageUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest, @AuthenticationPrincipal CustomUserDetail customUserDetail);
    OrderResponse getOrder(String uuid);
    void deleteOrder(String uuid);
    OrderResponse updateOrderStatus(String uuid, OrderUpdateRequest orderUpdateRequest);
    CustomPageUtils<OrderResponse> getOrdersByUserId(int page, int size,@AuthenticationPrincipal CustomUserDetail currentUser);
    OrderResponse updateQuantity(String uuid, UpdateQuantitydto updateQuantitydto);
}
