package com.zelalem.transaction.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawRequestDto {
    @JsonProperty("walletId")
    @NotNull(message = "Wallet ID must be specified!")
    @NotBlank(message = "Wallet ID must be specified!")
    private UUID walletId;

    @NotNull
    @Min(value = 1)
    @JsonProperty("amount")
    private Double amount;
}
