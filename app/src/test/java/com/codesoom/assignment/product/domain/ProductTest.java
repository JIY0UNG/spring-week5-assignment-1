package com.codesoom.assignment.product.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    @Test
    void creationWithBuilder() {
        Product product = Product.builder()
                .id(1L)
                .name("쥐돌이")
                .maker("냥이월드")
                .price(BigDecimal.valueOf(5000))
                .build();

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("쥐돌이");
        assertThat(product.getMaker()).isEqualTo("냥이월드");
        assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(5000));
        assertThat(product.getImageUrl()).isNull();
    }

    @Test
    void change() {
        Product product = Product.builder()
                .id(1L)
                .name("쥐돌이")
                .maker("냥이월드")
                .price(BigDecimal.valueOf(5000))
                .imageUrl("http://localhost:8080/cat")
                .build();

        Product source = Product.builder()
                .name("쥐순이")
                .maker("코드숨")
                .price(BigDecimal.valueOf(10000))
                .imageUrl("http://localhost:8080/rat")
                .build();

        product.change(
                source.getName(),
                source.getMaker(),
                source.getPrice(),
                source.getImageUrl()
        );

        assertThat(product.getName()).isEqualTo("쥐순이");
        assertThat(product.getMaker()).isEqualTo("코드숨");
        assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(10000));
        assertThat(product.getImageUrl())
                .isEqualTo("http://localhost:8080/rat");
    }
}
