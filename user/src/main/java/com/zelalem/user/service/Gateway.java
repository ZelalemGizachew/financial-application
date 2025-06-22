package com.zelalem.user.service;

import com.zelalem.user.dto.request.api.CreateWalletRequestDto;
import com.zelalem.user.dto.response.api.CreateWalletResponseDto;
import com.zelalem.user.dto.response.api.GetWalletByUserIdResponseDto;
import com.zelalem.user.utils.ResponseTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Gateway {

    @Value("${application.wallet.ms.wallets}")
    private String walletsUrl;

    @Value("${application.wallet.ms.wallets-by-userid}")
    private String walletsByUserIdUrl;

    public ResponseTemplate<CreateWalletResponseDto> createUserWallet(CreateWalletRequestDto createWalletRequestDto) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CreateWalletRequestDto> httpEntity = new HttpEntity<>(createWalletRequestDto, headers);
            ResponseEntity<ResponseTemplate<CreateWalletResponseDto>> response = restTemplate.exchange(walletsUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
            });

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                return ResponseTemplate.<CreateWalletResponseDto>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(response.getBody() != null ? response.getBody().getData() : null).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseTemplate.<CreateWalletResponseDto>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(null).build();
        }
    }

    public ResponseTemplate<GetWalletByUserIdResponseDto> getUserWalletByUserId(String userId) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CreateWalletRequestDto> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<ResponseTemplate<GetWalletByUserIdResponseDto>> response = restTemplate.exchange(walletsByUserIdUrl + userId, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
            });

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                return ResponseTemplate.<GetWalletByUserIdResponseDto>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(response.getBody() != null ? response.getBody().getData() : null).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseTemplate.<GetWalletByUserIdResponseDto>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(null).build();
        }
    }
}
