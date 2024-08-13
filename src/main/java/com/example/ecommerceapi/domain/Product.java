package com.example.ecommerceapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String uuid;
    private String description;
    private double price;
    private int quantity;
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    private String image;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandId")
    private Brand brand;

    @OneToMany(mappedBy = "product")
    private List<Order> orders;

}
