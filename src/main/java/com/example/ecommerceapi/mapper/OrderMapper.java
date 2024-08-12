package com.example.ecommerceapi.mapper;

import com.example.ecommerceapi.domain.Order;
import com.example.ecommerceapi.domain.Product;
import com.example.ecommerceapi.feature.order.dto.OrderRequest;
import com.example.ecommerceapi.feature.order.dto.OrderResponse;
import com.example.ecommerceapi.feature.product.dto.ProductOrderResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "product", source = "product")
    @Mapping(target = "userName", source = "user.userName")
    OrderResponse toOrderResponse(Order order);
    Order toOrder(OrderRequest orderRequest);

    default String map(List<String> value) {
        return String.join(",", value);
    }

}