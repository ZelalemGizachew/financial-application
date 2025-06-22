package com.zelalem.transaction.service;

import com.zelalem.transaction.dto.request.DepositRequestDto;
import com.zelalem.transaction.dto.request.TransferRequestDto;
import com.zelalem.transaction.dto.request.WithdrawRequestDto;
import com.zelalem.transaction.dto.response.TransferResponseDto;
import com.zelalem.transaction.enums.TRANSACTION_STATUS;
import com.zelalem.transaction.enums.TRANSACTION_TYPE;
import com.zelalem.transaction.model.TransactionHistory;
import com.zelalem.transaction.utils.ResponseTemplate;
import jakarta.transaction.TransactionalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionHelperService transactionHelperService;

    @Transactional
    public ResponseTemplate<?> transfer(TransferRequestDto transferRequestDto) {
        try {
            ResponseTemplate<TransactionHistory> withdrawResponse = transactionHelperService.withdraw(WithdrawRequestDto.builder().walletId(transferRequestDto.getSourceWalletId()).amount(transferRequestDto.getAmount()).build());
            if (withdrawResponse.getCode().equals(HttpStatus.OK.value())) {
                ResponseTemplate<TransactionHistory> depositResponse = transactionHelperService.deposit(DepositRequestDto.builder().walletId(transferRequestDto.getDestinationWalletId()).amount(transferRequestDto.getAmount()).build());
                if (depositResponse.getCode().equals(HttpStatus.OK.value())) {
                    TransferResponseDto transferResponseDto = TransferResponseDto.builder()
                            .id(withdrawResponse.getData().getId())
                            .sourceWalletId(transferRequestDto.getSourceWalletId())
                            .destinationWalletId(transferRequestDto.getDestinationWalletId())
                            .amount(transferRequestDto.getAmount())
                            .type(TRANSACTION_TYPE.TRANSFER)
                            .status(TRANSACTION_STATUS.SUCCESS)
                            .timestamp(withdrawResponse.getData().getCreatedAt())
                            .build();

                    return ResponseTemplate.builder().code(HttpStatus.OK.value()).message("Success!").data(transferResponseDto).build();
                }
                else {
                    throw new TransactionalException("Failed to perform deposit!", new Throwable());
                }
            }
            else {
                throw new TransactionalException("Failed to perform withdrawal!", new Throwable());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred while performing a transfer. {}", e.getMessage());
            return ResponseTemplate.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(null).build();
        }
    }
}
