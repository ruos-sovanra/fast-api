package com.example.ecommerceapi.feature.product;

import com.example.ecommerceapi.domain.Brand;
import com.example.ecommerceapi.domain.Category;
import com.example.ecommerceapi.domain.Product;
import com.example.ecommerceapi.feature.brand.BrandRepository;
import com.example.ecommerceapi.feature.category.CategoryRepository;
import com.example.ecommerceapi.feature.category.dto.CategoryResponse;
import com.example.ecommerceapi.feature.product.dto.ProductRequest;
import com.example.ecommerceapi.feature.product.dto.ProductResponse;
import com.example.ecommerceapi.feature.product.dto.ProductUpdateRequest;
import com.example.ecommerceapi.mapper.ProductMapper;
import com.example.ecommerceapi.utils.CustomPageUtils;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;

    @Override
    public CustomPageUtils<ProductResponse> getAllProducts(int page, int size) {
        Page<Product> products = productRepository.findAllByIsDeletedFalse(PageRequest.of(page, size, Sort.by("id").descending()));
        return CustomPagination(products.map(productMapper::toProductResponse));
    }

    public CustomPageUtils<ProductResponse> CustomPagination(Page<ProductResponse> page){

        CustomPageUtils<ProductResponse> customPage = new CustomPageUtils<>();

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
    public ProductResponse getProductByName(String name) {
        Product product = productRepository.findProductByName(name)
                .orElseThrow(()-> new NoSuchElementException("Product not found"));
        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        Category category = categoryRepository.findByName(productRequest.categoryName())
                .orElseThrow(()-> new NoSuchElementException("Category not found"));
        Brand brand = brandRepository.findByName(productRequest.brandName()).orElse(null);

        product.setCategory(category);
        product.setBrand(brand);
        product.setImages(productRequest.images());
        productRepository.save(product);

        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Product not found"));
        Category category = categoryRepository.findByName(productRequest.categoryName())
                .orElseThrow(()-> new NoSuchElementException("Category not found"));
        Brand brand = brandRepository.findByName(productRequest.brandName()).orElse(null);
        product.setCategory(category);
        product.setBrand(brand);
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        product.setQuantity(productRequest.quantity());
        product.setImages(productRequest.images());
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
        product.setIsDeleted(true);
        productRepository.save(product);
    }

    @Override
    public ProductResponse updateProductImage(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Product not found"));
        product.setImages(request.images());
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }
}
