package com.thecodereveal.shopease.controllers;

import com.thecodereveal.shopease.dto.ProductDto;
import com.thecodereveal.shopease.entities.Product;
import com.thecodereveal.shopease.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(required = false, name = "categoryId") String categoryId,
            @RequestParam(required = false, name = "typeId") String typeId,
            @RequestParam(required = false) String slug,
            HttpServletResponse response) {

        List<ProductDto> productList = new ArrayList<>();

        if (StringUtils.isNotBlank(slug)) {
            ProductDto productDto = productService.getProductBySlug(slug);
            if (productDto != null) {
                productList.add(productDto);
            }
        } else {
            UUID categoryUUID = (categoryId != null && !categoryId.equals("undefined")) ? UUID.fromString(categoryId) : null;
            UUID typeUUID = (typeId != null && !typeId.equals("undefined")) ? UUID.fromString(typeId) : null;
            productList = productService.getAllProducts(categoryUUID, typeUUID);
        }

        response.setHeader("Content-Range", String.valueOf(productList.size()));
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID id) {
        ProductDto productDto = productService.getProductById(id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        Product product = productService.addProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDto productDto, @PathVariable UUID id) {
        Product product = productService.updateProduct(productDto, id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
