package com.example.ecommerceapi.feature.order;

import com.example.ecommerceapi.domain.Order;
import com.example.ecommerceapi.domain.Product;
import com.example.ecommerceapi.domain.User;
import com.example.ecommerceapi.feature.order.dto.OrderRequest;
import com.example.ecommerceapi.feature.order.dto.OrderResponse;
import com.example.ecommerceapi.feature.order.dto.OrderUpdateRequest;
import com.example.ecommerceapi.feature.product.ProductRepository;
import com.example.ecommerceapi.feature.user.UserRepository;
import com.example.ecommerceapi.mapper.OrderMapper;
import com.example.ecommerceapi.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;


    @Override
    public OrderResponse createOrder(OrderRequest orderRequest, @AuthenticationPrincipal CustomUserDetail currentUser) {

        Order order = orderMapper.toOrder(orderRequest);

        Product product = productRepository.findProductByUuid(orderRequest.uuid()).orElseThrow(
                () -> new NoSuchElementException("Product not found")
        );
        User user = userRepository.findUserByUuid(currentUser.getUser().getUuid()).orElseThrow(
                () -> new NoSuchElementException("User not found")
        );

        var order_number = UUID.randomUUID().toString();

        order.setOrderDetailNumber(order_number);
        order.setUuid(UUID.randomUUID().toString());
        order.setQuantity(1);
        order.setProduct(product);
        order.setUser(user);
        order.setStatus("PENDING");
        orderRepository.save(order);

        return orderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponse getOrder(String uuid) {
        Order order = orderRepository.findByUuid(uuid).orElseThrow(
                () -> new NoSuchElementException("Order not found")
        );
        return orderMapper.toOrderResponse(order);
    }


    @Override
    public void deleteOrder(String uuid) {
        Order order = orderRepository.findByUuid(uuid).orElseThrow(
                () -> new NoSuchElementException("Order not found")
        );
        orderRepository.delete(order);

    }

    @Override
    public OrderResponse updateOrderStatus(String uuid, OrderUpdateRequest orderUpdateRequest) {
        Order order = orderRepository.findByUuid(uuid).orElseThrow(
                () -> new NoSuchElementException("Order not found")
        );
        order.setStatus(orderUpdateRequest.status());
        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(@AuthenticationPrincipal CustomUserDetail currentUser) {
        List<Order> orders = orderRepository.findByUser_Uuid(currentUser.getUser().getUuid());
        return orders.stream().map(orderMapper::toOrderResponse).toList();
    }

    @Override
    public OrderResponse updateQuantity(String uuid, int quantity) {

        Order order = orderRepository.findByUuid(uuid).orElseThrow(
                () -> new NoSuchElementException("Order not found")
        );

        order.setQuantity(quantity);

        return orderMapper.toOrderResponse(order);
    }
}
