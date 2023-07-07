package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private static final String API_BASE_URL = "http://localhost:8080/transfer/";

    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public Transfer[] viewTransfers() {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL, HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException ex) {
            throw new RuntimeException("Error getting transfer details", ex);
        }
        return transfers;
    }

    public Transfer getTransfersById(int transferId) {
        try {
            Transfer transfer = restTemplate.getForObject(API_BASE_URL + transferId, Transfer.class);
            return transfer;
        } catch (RestClientResponseException | ResourceAccessException ex) {
            throw new RuntimeException("Error getting transfer details for transfer ID: " + transferId, ex);
        }
    }

    public Transfer createTransfer(Transfer newTransfer) {
        try {
            Transfer transfer = restTemplate.postForObject(API_BASE_URL, makeTransferEntity(newTransfer), Transfer.class);
            return transfer;
        } catch (RestClientResponseException | ResourceAccessException ex) {
            throw new RuntimeException("Error creating transfer.", ex);
        }
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);

    }
}