package com.example.ecommerceapi.feature.order;

import com.example.ecommerceapi.feature.order.dto.OrderRequest;
import com.example.ecommerceapi.feature.order.dto.OrderResponse;
import com.example.ecommerceapi.feature.order.dto.OrderUpdateRequest;
import com.example.ecommerceapi.feature.order.dto.UpdateQuantitydto;
import com.example.ecommerceapi.security.CustomUserDetail;
import com.example.ecommerceapi.utils.BaseResponse;
import com.example.ecommerceapi.utils.CustomPageUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderRestController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create order")
    public BaseResponse<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest, @AuthenticationPrincipal CustomUserDetail currentUser) {
        return BaseResponse.<OrderResponse>ok()
                .setPayload(orderService.createOrder(orderRequest,currentUser));
    }



    @GetMapping("/user")
    @Operation(summary = "Get all orders by user id")
    public ResponseEntity<CustomPageUtils<OrderResponse>> getOrderByUserId(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           @AuthenticationPrincipal CustomUserDetail currentUser) {
        CustomPageUtils<OrderResponse> orderResponseCustomPage = orderService.getOrdersByUserId(page, size, currentUser);
        return ResponseEntity.ok(orderResponseCustomPage);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get order by id")
    public BaseResponse<OrderResponse> getOrder(@PathVariable String uuid) {
        return BaseResponse.<OrderResponse>ok().setPayload(orderService.getOrder(uuid));
    }


    @PatchMapping("/{uuid}/status")
    @Operation(summary = "Update order status")
    public BaseResponse<OrderResponse> updateOrderStatus(@PathVariable String uuid, OrderUpdateRequest orderUpdateRequest) {
        return BaseResponse.<OrderResponse>ok().setPayload(orderService.updateOrderStatus(uuid, orderUpdateRequest));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete order by id")
    public BaseResponse<Void> deleteOrder(@PathVariable String uuid) {
        orderService.deleteOrder(uuid);
        return BaseResponse.<Void>ok();
    }

    @PatchMapping("/{uuid}/quantity")
    @Operation(summary = "Update order quantity")
    public BaseResponse<OrderResponse> updateOrderQuantity(@PathVariable String uuid, @RequestBody UpdateQuantitydto quantity) {
        return BaseResponse.<OrderResponse>ok().setPayload(orderService.updateQuantity(uuid, quantity));
    }



}
