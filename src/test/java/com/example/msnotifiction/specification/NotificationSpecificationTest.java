package com.example.msnotifiction.specification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class NotificationSpecificationTest {

    @Test
    void shouldCreateSpecification() {
        var spec = NotificationSpecification.filter(null, null, null);
        assertNotNull(spec);
    }
}