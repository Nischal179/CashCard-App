package com.example.cashcard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CashCardJsonTest {

    @Autowired
    private JacksonTester<CashCard> json;

    @Test
    void cashCardSerializationTest() throws IOException {
        CashCard cashCard = new CashCard(99L, 123.45);
        // Load the JSON file from the classpath
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("expected.json");
        if (inputStream == null) {
            throw new IllegalStateException("Resource not found: expected.json");
        }
        String expectedJson = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        // Perform assertions
        assertThat(json.write(cashCard)).isStrictlyEqualToJson(expectedJson);
        assertThat(json.write(cashCard)).hasJsonPathValue("@.id");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id").isEqualTo(99);
        assertThat(json.write(cashCard)).hasJsonPathValue("@.amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);
    }
}
