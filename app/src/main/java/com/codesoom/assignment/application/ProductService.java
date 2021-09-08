package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import com.github.dozermapper.core.Mapper;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Product 리소스
 * 수행할 작업을 정의하고, Domain layer에 수행을 위임한다.
 *
 * @see Product
 * @see ProductRepository
 */
@Service
@Transactional
public class ProductService {
    private final Mapper dozerMapper;
    private final ProductRepository productRepository;

    public ProductService(
        final Mapper dozerMapper, final ProductRepository productRepository
    ) {
        this.dozerMapper= dozerMapper;
        this.productRepository = productRepository;
    }

    /**
     * Product 목록 조회하고 리턴한다.
     *
     * @return Product 목록
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return findProduct(id);
    }

    public Product createProduct(ProductData productData) {
        Product product = dozerMapper.map(productData, Product.class);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductData productData) {
        Product product = findProduct(id);
        product.change(dozerMapper.map(productData, Product.class));
        return product;
    }

    public Product deleteProduct(Long id) {
        Product product = findProduct(id);

        productRepository.delete(product);

        return product;
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
