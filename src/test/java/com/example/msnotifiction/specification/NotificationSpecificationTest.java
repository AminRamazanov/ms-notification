package com.example.msnotifiction.specification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class NotificationSpecificationTest {

    @Test
    void shouldCreateSpecificationWithUserId() {
        var spec = NotificationSpecification.filter(1L, null, null);
        assertNotNull(spec);
    }

    @Test
    void shouldCreateSpecificationWithOrderId() {
        var spec = NotificationSpecification.filter(null, 2L, null);
        assertNotNull(spec);
    }

    @Test
    void shouldCreateSpecificationWithEmail() {
        var spec = NotificationSpecification.filter(null, null, "test@example.com");
        assertNotNull(spec);
    }

    @Test
    void shouldCreateSpecificationWithAllParameters() {
        var spec = NotificationSpecification.filter(1L, 2L, "test@example.com");
        assertNotNull(spec);
    }

    @Test
    void shouldCreateSpecificationWithNoParameters() {
        var spec = NotificationSpecification.filter(null, null, null);
        assertNotNull(spec);
    }

    @Test
    void shouldCreateSpecificationWithEmptyEmail() {
        var spec = NotificationSpecification.filter(null, null, "");
        assertNotNull(spec);
    }
}