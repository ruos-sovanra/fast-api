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

        Product product = productRepository.findById(orderRequest.productId()).orElseThrow(
                () -> new NoSuchElementException("Product not found")
        );
        User user = userRepository.findById(currentUser.getUser().getId()).orElseThrow(
                () -> new NoSuchElementException("User not found")
        );

        var order_number = UUID.randomUUID().toString();

        order.setOrderDetailNumber(order_number);
        order.setQuantity(1);
        order.setProduct(product);
        order.setUser(user);
        order.setStatus("PENDING");
        orderRepository.save(order);

        return orderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Order not found")
        );
        return orderMapper.toOrderResponse(order);
    }


    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Order not found")
        );
        orderRepository.delete(order);

    }

    @Override
    public OrderResponse updateOrderStatus(Long id, OrderUpdateRequest orderUpdateRequest) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Order not found")
        );
        order.setStatus(orderUpdateRequest.status());
        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(orderMapper::toOrderResponse).toList();
    }

    @Override
    public OrderResponse updateQuantity(Long id, int quantity) {

        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Order not found")
        );

        order.setQuantity(quantity);

        return orderMapper.toOrderResponse(order);
    }
}
