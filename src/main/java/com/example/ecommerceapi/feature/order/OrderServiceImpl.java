package com.example.ecommerceapi.feature.order;

import com.example.ecommerceapi.domain.Order;
import com.example.ecommerceapi.domain.Product;
import com.example.ecommerceapi.domain.User;
import com.example.ecommerceapi.feature.order.dto.OrderRequest;
import com.example.ecommerceapi.feature.order.dto.OrderResponse;
import com.example.ecommerceapi.feature.order.dto.OrderUpdateRequest;
import com.example.ecommerceapi.feature.order.dto.UpdateQuantitydto;
import com.example.ecommerceapi.feature.product.ProductRepository;
import com.example.ecommerceapi.feature.product.dto.ProductResponse;
import com.example.ecommerceapi.feature.user.UserRepository;
import com.example.ecommerceapi.mapper.OrderMapper;
import com.example.ecommerceapi.security.CustomUserDetail;
import com.example.ecommerceapi.utils.CustomPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

        order.setOrderDetailNumber(UUID.randomUUID().toString());
        order.setUuid(UUID.randomUUID().toString());
        order.setQuantity(1);
        order.setProduct(product);
        order.setUser(user);
        order.setTotalPrice(product.getPrice() * 1);
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
    public CustomPageUtils<OrderResponse> getOrdersByUserId(int page, int size,@AuthenticationPrincipal CustomUserDetail currentUser) {
       Page<Order> orders = orderRepository.findByUser_Uuid(currentUser.getUser().getUuid(), PageRequest.of(page, size, Sort.by("id").descending()));

         return CustomPagination(orders.map(orderMapper::toOrderResponse));
    }

    public CustomPageUtils<OrderResponse> CustomPagination(Page<OrderResponse> page){

        CustomPageUtils<OrderResponse> customPage = new CustomPageUtils<>();

        //check if page has next
        if(page != null && page.hasPrevious()){
            customPage.setPrevious(true); // Set to true if there is a previous page
        } else {
            customPage.setPrevious(false); // Set to false if there is no previous page
        }

        if(page != null && page.hasNext()){
            customPage.setNext(true); // Set to true if there is a next page
        } else {
            customPage.setNext(false); // Set to false if there is no next page
        }

        //set total
        customPage.setTotal((int) page.getTotalElements());
        customPage.setTotalElements(page.getTotalElements());

        //set total pages
        customPage.setResults(page.getContent());

        return customPage;
    }

    @Override
    public OrderResponse updateQuantity(String uuid, UpdateQuantitydto quantity) {

        System.out.println("quantity: " + quantity);
        System.out.println("uuid: " + uuid);

        if (quantity == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }

        Order order = orderRepository.findByUuid(uuid).orElseThrow(
                () -> new NoSuchElementException("Order not found")
        );

        var total_price = order.getProduct().getPrice() * quantity.quantity();

        order.setTotalPrice(total_price);
        order.setQuantity(quantity.quantity());

        orderRepository.save(order);

        return orderMapper.toOrderResponse(order);
    }
}
