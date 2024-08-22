package com.challenge.discountcalculator;

import com.challenge.discountcalculator.domain.discount.DiscountPolicyRepository;
import com.challenge.discountcalculator.domain.product.ProductRepository;
import com.challenge.discountcalculator.infrastructure.api.dto.*;
import com.challenge.discountcalculator.infrastructure.db.DiscountPolicyEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class DiscountCalculatorIntegrationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DiscountPolicyRepository discountPolicyRepository;

    @Test
    void shouldReturnProductByItsId() {
        // given
        var productId = "1107c751-820d-423d-b100-3a3890ae2b3d";

        // when
        var response = restTemplate.getForEntity("/api/v1/products/" + productId, ProductDto.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldCreateNewProduct() {
        // given
        var product = new ProductCreationRequest("test", BigDecimal.TEN);

        // when
        var response = restTemplate.postForEntity("/api/v1/products", product, ProductDto.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(productRepository.findById(response.getBody().id())).isNotEmpty();
    }

    @Test
    void shouldFailToCreateNewProductWhenInputIsInvalid() {
        // given
        var product = new ProductCreationRequest("test", BigDecimal.valueOf(-1));

        // when
        var response = restTemplate.postForEntity("/api/v1/products", product, ProductDto.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldFetchExistingDiscountPolicy() {
        // given
        var policyId = 101;

        // when
        var response = restTemplate.getForEntity("/api/v1/discount_policies/" + policyId, DiscountPolicyDto.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldCreateNewDiscountPolicy() {
        // given
        var existingProductId = "1945f2a3-47cf-421f-bb21-fe213de7a484";
        var product = new DiscountPolicyCreationRequest(existingProductId, 5, DiscountPolicyEntity.DiscountType.PERCENTAGE, BigDecimal.TWO);

        // when
        var response = restTemplate.postForEntity("/api/v1/discount_policies", product, DiscountPolicyDto.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(discountPolicyRepository.findById(response.getBody().id())).isNotEmpty();
    }

    @Test
    void shouldFailToCreateNewDiscountPolicyWhenProductIdIsInvalid() {
        // given
        var invalidProductId = "1945f2a3-47cf-421f-bb21-fe213de7a485";
        var product = new DiscountPolicyCreationRequest(invalidProductId, 1, DiscountPolicyEntity.DiscountType.PERCENTAGE, BigDecimal.TEN);

        // when
        var response = restTemplate.postForEntity("/api/v1/discount_policies", product, DiscountPolicyDto.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldFailToCreateNewDiscountPolicyWhenInputIsInvalid() {
        // given
        var malformedProductId = "fe213de7a484";
        var product = new DiscountPolicyCreationRequest(malformedProductId, 0, DiscountPolicyEntity.DiscountType.PERCENTAGE, BigDecimal.valueOf(-1));

        // when
        var response = restTemplate.postForEntity("/api/v1/discount_policies", product, DiscountPolicyDto.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldUpdateExistingDiscountPolicy() {
        // given
        var existingDiscountPolicy = 302L;
        String newProductId = "da4f892e-8262-45f2-a432-144ea3d8e5b2";
        var policy = new DiscountPolicyUpdateRequest(newProductId, 1, BigDecimal.TWO);

        // when
        var response = restTemplate.exchange("/api/v1/discount_policies/" + existingDiscountPolicy, HttpMethod.PUT, new HttpEntity<>(policy), DiscountPolicyDto.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        var updatedPolicy = discountPolicyRepository.findById(existingDiscountPolicy).get();
        assertThat(updatedPolicy.getApplicableProductId()).isEqualTo(policy.applicableProductId());
        assertThat(updatedPolicy.getMinimumProductAmount()).isEqualTo(policy.minimumAmount());
        assertThat(updatedPolicy.getDiscountValue().setScale(0)).isEqualTo(policy.discountValue().setScale(0));
    }

    @Test
    void shouldDeleteExistingDiscountPolicy() {
        // given
        var existingDiscountPolicy = 301L;

        // when
        var response = restTemplate.exchange("/api/v1/discount_policies/" + existingDiscountPolicy, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(discountPolicyRepository.findById(existingDiscountPolicy)).isEmpty();
    }

    @Test
    void shouldCalculatePriceWithAbsoluteDiscountWhenProductIsEligibleForDiscount() {
        // given
        var productId = "1107c751-820d-423d-b100-3a3890ae2b3d";
        var amount = 7;
        var url = UriComponentsBuilder.fromUriString("/api/v1/products/price")
                .queryParam("productId", productId)
                .queryParam("amount", amount)
                .build()
                .toUri();

        // when
        var response = restTemplate.getForEntity(url, PriceCalculationResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.productId()).isEqualTo(productId);
        assertThat(body.amount()).isEqualTo(amount);
        assertThat(body.price()).isEqualTo(new BigDecimal("200.00")); // 7 * 210 - 10
    }

    @Test
    void shouldCalculatePriceWithPercentageDiscountWhenProductIsEligibleForDiscount() {
        // given
        var productId = "77b69433-d28e-4339-bd92-1c67c08d1be8";
        var amount = 3;
        var url = UriComponentsBuilder.fromUriString("/api/v1/products/price")
                .queryParam("productId", productId)
                .queryParam("amount", amount)
                .build()
                .toUri();

        // when
        var response = restTemplate.getForEntity(url, PriceCalculationResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.productId()).isEqualTo(productId);
        assertThat(body.amount()).isEqualTo(amount);
        assertThat(body.price()).isEqualTo(new BigDecimal("229.50")); // 3 * 90 - 15% (40.5)
    }

    @Test
    void shouldCalculatePriceWithoutDiscountWhenProductAmountIsBelowThreshold() {
        // given
        var productId = "1107c751-820d-423d-b100-3a3890ae2b3d";
        var amount = 3;
        var url = UriComponentsBuilder.fromUriString("/api/v1/products/price")
                .queryParam("productId", productId)
                .queryParam("amount", amount)
                .build()
                .toUri();

        // when
        var response = restTemplate.getForEntity(url, PriceCalculationResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.productId()).isEqualTo(productId);
        assertThat(body.amount()).isEqualTo(amount);
        assertThat(body.price()).isEqualTo(new BigDecimal("90.00"));
    }

    @Test
    void shouldCalculatePriceWithoutDiscountWhenProductDoesntHaveDiscountConfigured() {
        // given
        var productId = "ab092fb5-0f36-4f18-9a3e-b51c6495431d";
        var amount = 3;
        var url = UriComponentsBuilder.fromUriString("/api/v1/products/price")
                .queryParam("productId", productId)
                .queryParam("amount", amount)
                .build()
                .toUri();

        // when
        var response = restTemplate.getForEntity(url, PriceCalculationResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.productId()).isEqualTo(productId);
        assertThat(body.amount()).isEqualTo(amount);
        assertThat(body.price()).isEqualTo(new BigDecimal("37.50"));
    }
}
