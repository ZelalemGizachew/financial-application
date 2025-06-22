package com.zelalem.transaction.service;

import com.zelalem.transaction.dto.request.DepositRequestDto;
import com.zelalem.transaction.dto.request.WithdrawRequestDto;
import com.zelalem.transaction.dto.request.api.BalanceUpdateRequestDto;
import com.zelalem.transaction.dto.response.api.WalletResponseDto;
import com.zelalem.transaction.enums.TRANSACTION_STATUS;
import com.zelalem.transaction.enums.TRANSACTION_TYPE;
import com.zelalem.transaction.model.TransactionHistory;
import com.zelalem.transaction.repository.TransactionHistoryRepository;
import com.zelalem.transaction.utils.ResponseTemplate;
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
public class TransactionHelperService {
    private final Logger logger = LoggerFactory.getLogger(TransactionHelperService.class);
    private final TransactionHistoryRepository transactionHistoryRepository;

    private final Gateway gateway;

    @Transactional
    public ResponseTemplate<TransactionHistory> deposit(DepositRequestDto depositRequestDto) {
        try {
            ResponseTemplate<WalletResponseDto> response = gateway.performWalletDebitCredit(
                    depositRequestDto.getWalletId(),
                    BalanceUpdateRequestDto.builder().amount(depositRequestDto.getAmount()).build(),
                    TRANSACTION_TYPE.DEPOSIT
            );

            TransactionHistory transactionHistory = transactionHistoryRepository.save(
                    TransactionHistory.builder()
                            .walletId(depositRequestDto.getWalletId())
                            .amount(depositRequestDto.getAmount())
                            .type(TRANSACTION_TYPE.DEPOSIT)
                            .status(response.getCode().equals(HttpStatus.OK.value()) ? TRANSACTION_STATUS.SUCCESS :  TRANSACTION_STATUS.FAILED)
                            .build()
            );

            logger.info(transactionHistory.toString());
            return ResponseTemplate.<TransactionHistory>builder().code(response.getCode()).message(response.getMessage()).data(transactionHistory).build();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred while performing a deposit. {}", e.getMessage());
            return ResponseTemplate.<TransactionHistory>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(null).build();
        }
    }

    @Transactional
    public ResponseTemplate<TransactionHistory> withdraw(WithdrawRequestDto withdrawRequestDto) {
        try {
            ResponseTemplate<WalletResponseDto> response = gateway.performWalletDebitCredit(
                    withdrawRequestDto.getWalletId(),
                    BalanceUpdateRequestDto.builder().amount(withdrawRequestDto.getAmount()).build(),
                    TRANSACTION_TYPE.WITHDRAW
            );

            TransactionHistory transactionHistory = transactionHistoryRepository.save(
                    TransactionHistory.builder()
                            .walletId(withdrawRequestDto.getWalletId())
                            .amount(withdrawRequestDto.getAmount())
                            .type(TRANSACTION_TYPE.WITHDRAW)
                            .status(response.getCode().equals(HttpStatus.OK.value()) ? TRANSACTION_STATUS.SUCCESS :  TRANSACTION_STATUS.FAILED)
                            .build()
            );

            logger.info(transactionHistory.toString());
            return ResponseTemplate.<TransactionHistory>builder().code(response.getCode()).message(response.getMessage()).data(transactionHistory).build();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred while performing a withdraw. {}", e.getMessage());
            return ResponseTemplate.<TransactionHistory>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(null).build();
        }
    }
}
