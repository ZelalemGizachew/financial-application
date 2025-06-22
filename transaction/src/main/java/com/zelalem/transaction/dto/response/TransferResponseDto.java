package com.zelalem.transaction.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zelalem.transaction.enums.TRANSACTION_STATUS;
import com.zelalem.transaction.enums.TRANSACTION_TYPE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("sourceWalletId")
    private UUID sourceWalletId;

    @JsonProperty("destinationWalletId")
    private UUID destinationWalletId;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("type")
    private TRANSACTION_TYPE type;

    @JsonProperty("status")
    private TRANSACTION_STATUS status;

    @JsonProperty("timestamp")
    private Timestamp timestamp;

}
