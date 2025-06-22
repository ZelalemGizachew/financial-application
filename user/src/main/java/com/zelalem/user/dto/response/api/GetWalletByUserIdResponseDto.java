package com.zelalem.user.dto.response.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetWalletByUserIdResponseDto {
    @JsonProperty("walletId")
    private UUID walletId;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("currentBalance")
    private Double currentBalance;
}
