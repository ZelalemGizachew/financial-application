package com.zelalem.transaction.service;

import com.zelalem.transaction.dto.request.api.BalanceUpdateRequestDto;
import com.zelalem.transaction.dto.response.api.WalletResponseDto;
import com.zelalem.transaction.enums.TRANSACTION_TYPE;
import com.zelalem.transaction.utils.ResponseTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class Gateway {
    @Value("${application.wallet.ms.wallets-base-endpoint}")
    private String baseEndpointWalletsUrl;

    @Value("${application.wallet.ms.debit-wallets}")
    private String debitWalletsUrl;

    @Value("${application.wallet.ms.credit-wallets}")
    private String creditWalletsUrl;

    public ResponseTemplate<WalletResponseDto> performWalletDebitCredit(UUID walletId, BalanceUpdateRequestDto balanceUpdateRequestDto, TRANSACTION_TYPE transactionType) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<BalanceUpdateRequestDto> httpEntity = new HttpEntity<>(balanceUpdateRequestDto, headers);

            String endpoint = "";
            switch (transactionType) {
                case DEPOSIT ->  endpoint = creditWalletsUrl;
                case WITHDRAW -> endpoint = debitWalletsUrl;
            }
            ResponseEntity<ResponseTemplate<WalletResponseDto>> response = restTemplate.exchange(baseEndpointWalletsUrl + walletId + endpoint, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
            });

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                return ResponseTemplate.<WalletResponseDto>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(response.getBody() != null ? response.getBody().getData() : null).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseTemplate.<WalletResponseDto>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(null).build();
        }
    }
}
