package com.example.rewards.controller;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Random;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RewardsResourceTest {
    @Test
    public void givenCustomerDoesNotExists_whenCustomerRewardsIsRetrieved_then400IsReceived()
            throws ClientProtocolException, IOException {

        // Given
        Random random = new Random();
        int customerId = random.nextInt(10);
        HttpUriRequest request = new HttpGet( "http://localhost:8083/rewards/" + customerId );

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        // Then
        assertThat(
                httpResponse.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);

    }

    @Test
    public void givenCustomerExists_whenCustomerRewardsIsRetrieved_then200IsReceived()
            throws ClientProtocolException, IOException {

        // Given

        int customerId = 11;
        HttpUriRequest request = new HttpGet( "http://localhost:8083/rewards/" + customerId );

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        // Then
        assertThat(
                httpResponse.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);

    }
}
