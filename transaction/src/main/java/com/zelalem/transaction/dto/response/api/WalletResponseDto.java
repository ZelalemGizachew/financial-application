package com.zelalem.transaction.dto.response.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponseDto {
    @JsonProperty("walletId")
    private UUID walletId;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("currentBalance")
    private Double currentBalance;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

}
