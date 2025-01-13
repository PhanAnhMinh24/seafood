package com.sales.products.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales.products.pojo.response.ApiResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiService {
    @Autowired
    private RestTemplate restTemplate;

    // Hàm chung cho GET Request
    public <T> T callApiGet(String url, Class<T> responseType, String token) {
        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        return response.getBody();
    }

    public <T> List<T> callApiGet(String url, String token, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ParameterizedTypeReference<ApiResponse<T>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<ApiResponse<T>> response = restTemplate.exchange(url, HttpMethod.GET, entity, typeRef);
        ObjectMapper objectMapper = new ObjectMapper();
        return ObjectUtils.isEmpty(response.getBody()) ? new ArrayList<>() : response.getBody().getResults().stream()
                .map(result -> objectMapper.convertValue(result, responseType))
                .toList();
    }

    // Hàm chung cho POST Request
    public <T> T callApiPost(String url, Object requestBody, Class<T> responseType, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }

        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
        return response.getBody();
    }

    // Hàm chung cho POST Request
    public <T> List<T> callApiPost(String url, Object requestBody, String token, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }

        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
        ParameterizedTypeReference<ApiResponse<T>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<ApiResponse<T>> response = restTemplate.exchange(url, HttpMethod.POST, entity, typeRef);
        ObjectMapper objectMapper = new ObjectMapper();
        return ObjectUtils.isEmpty(response.getBody()) ? new ArrayList<>() : response.getBody().getResults().stream()
                .map(result -> objectMapper.convertValue(result, responseType))
                .toList();
    }
}
