package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    @Test
    void creationWithBuilder() {
        Product product = Product.builder()
                .id(1L)
                .name("쥐돌이")
                .maker("냥이월드")
                .price(5000)
                .build();

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("쥐돌이");
        assertThat(product.getMaker()).isEqualTo("냥이월드");
        assertThat(product.getPrice()).isEqualTo(5000);
        assertThat(product.getImageUrl()).isNull();
    }

    @Test
    void change() {
        Product product = Product.builder()
                .id(1L)
                .name("쥐돌이")
                .maker("냥이월드")
                .price(5000)
                .build();

        product.change(Product.builder()
                        .name("쥐순이")
                        .maker("코드숨")
                        .price(10000)
                        .imageUrl("http://localhost:8080/rat")
                        .build());

        assertThat(product.getName()).isEqualTo("쥐순이");
        assertThat(product.getMaker()).isEqualTo("코드숨");
        assertThat(product.getPrice()).isEqualTo(10000);
        assertThat(product.getImageUrl())
                .isEqualTo("http://localhost:8080/rat");
    }

    @Test
    void createWithNoAttributes() {
        Product product = new Product();

        assertThat(product.getName()).isNull();
        assertThat(product.getMaker()).isNull();
        assertThat(product.getPrice()).isNull();
        assertThat(product.getImageUrl()).isNull();
    }

    @Test
    void testBuilderToString() {
        String product = Product.builder()
                .id(1L)
                .name("쥐돌이")
                .maker("냥이월드")
                .price(5000)
                .imageUrl("http://image.kyobobook.co.kr/newimages/giftshop_new/goods/400/1095/hot1602809707085.jpg")
                .toString();

        assertThat(product)
                .isEqualTo("Product.ProductBuilder(" +
                        "id=1" +
                        ", name=쥐돌이" +
                        ", maker=냥이월드" +
                        ", price=5000" +
                        ", imageUrl=http://image.kyobobook.co.kr/newimages/giftshop_new/goods/400/1095/hot1602809707085.jpg" +
                        ')');
    }
}
