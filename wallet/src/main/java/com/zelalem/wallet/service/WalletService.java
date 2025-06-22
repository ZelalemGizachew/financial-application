package com.zelalem.wallet.service;

import com.zelalem.wallet.dto.request.BalanceUpdateRequestDto;
import com.zelalem.wallet.dto.request.CreateWalletRequestDto;
import com.zelalem.wallet.dto.response.CreateWalletResponseDto;
import com.zelalem.wallet.model.Wallet;
import com.zelalem.wallet.repository.WalletRepository;
import com.zelalem.wallet.utils.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {
    private final Logger logger = LoggerFactory.getLogger(WalletService.class);

    private final WalletRepository walletRepository;

    public ResponseTemplate<?> createNewWallet(CreateWalletRequestDto createWalletRequestDto) {
        try {
            Optional<Wallet> existingWalletOptional = walletRepository.findByUserId(createWalletRequestDto.getUserId());
            if (existingWalletOptional.isPresent()) {
                return ResponseTemplate.builder().code(HttpStatus.BAD_REQUEST.value()).message("A wallet for the specified user (" + createWalletRequestDto.getUserId() + ") already exists!").data(null).build();
            }

            Wallet wallet = walletRepository.save(Wallet.builder().userId(createWalletRequestDto.getUserId()).build());
            CreateWalletResponseDto responseDto = CreateWalletResponseDto.builder().walletId(wallet.getWalletId()).userId(wallet.getUserId()).currentBalance(wallet.getCurrentBalance()).build();

            return ResponseTemplate.builder().code(HttpStatus.CREATED.value()).message("Success!").data(responseDto).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseTemplate.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(null).build();
        }
    }

    public ResponseTemplate<?> getWalletByWalletId(UUID walletId) {
        try {
            Optional<Wallet> walletOptional = walletRepository.findById(walletId);
            if (walletOptional.isPresent()) {
                return ResponseTemplate.builder().code(HttpStatus.OK.value()).message("Success!").data(
                        CreateWalletResponseDto.builder().walletId(walletId).userId(walletOptional.get().getUserId()).currentBalance(walletOptional.get().getCurrentBalance()).build()
                ).build();
            }
            else {
                return ResponseTemplate.builder().code(HttpStatus.NOT_FOUND.value()).message("Wallet could not be found!").data(null).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred while retrieving wallet information by walletId. {}", e.getMessage());
            return ResponseTemplate.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(null).build();
        }
    }

    public ResponseTemplate<?> getWalletByUserId(String userId) {
        try {
            Optional<Wallet> walletOptional = walletRepository.findByUserId(userId);
            if (walletOptional.isPresent()) {
                return ResponseTemplate.builder().code(HttpStatus.OK.value()).message("Success!").data(
                        CreateWalletResponseDto.builder().walletId(walletOptional.get().getWalletId()).userId(walletOptional.get().getUserId()).currentBalance(walletOptional.get().getCurrentBalance()).build()
                ).build();
            }
            else {
                return ResponseTemplate.builder().code(HttpStatus.NOT_FOUND.value()).message("Wallet could not be found!").data(null).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred while retrieving wallet information by userId. {}", e.getMessage());
            return ResponseTemplate.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(null).build();
        }
    }

    @Transactional
    public ResponseTemplate<?> debit(UUID walletId, BalanceUpdateRequestDto balanceUpdateRequestDto) {
        try {
            Optional<Wallet> walletOptional = walletRepository.findById(walletId);
            if (walletOptional.isPresent()) {
                if (walletOptional.get().getCurrentBalance() > balanceUpdateRequestDto.getAmount()) {
                    Wallet wallet = walletOptional.get();
                    double currentBalance = wallet.getCurrentBalance() - balanceUpdateRequestDto.getAmount();
                    wallet.setCurrentBalance(currentBalance);
                    walletRepository.save(wallet);

//                    Todo: Async Call of the Notification Service
                    return ResponseTemplate.builder().code(HttpStatus.OK.value()).message("Success!").data(wallet).build();
                }
                else {
                    return  ResponseTemplate.builder().code(HttpStatus.BAD_REQUEST.value()).message("Insufficient balance!").data(null).build();
                }
            }
            else {
                return ResponseTemplate.builder().code(HttpStatus.NOT_FOUND.value()).message("Wallet Not Found!").data(null).build();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred while performing debit. {}", e.getMessage());
            return ResponseTemplate.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(null).build();
        }
    }

    @Transactional
    public ResponseTemplate<?> credit(UUID walletId, BalanceUpdateRequestDto balanceUpdateRequestDto) {
        try {
            Optional<Wallet> walletOptional = walletRepository.findById(walletId);
            if (walletOptional.isPresent()) {
                Wallet wallet = walletOptional.get();
                double currentBalance = wallet.getCurrentBalance() + balanceUpdateRequestDto.getAmount();
                wallet.setCurrentBalance(currentBalance);
                walletRepository.save(wallet);

//              Todo: Async Call of the Notification Service
                return ResponseTemplate.builder().code(HttpStatus.OK.value()).message("Success!").data(wallet).build();
            }
            else {
                return ResponseTemplate.builder().code(HttpStatus.NOT_FOUND.value()).message("Wallet Not Found!").data(null).build();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception occurred while performing credit. {}", e.getMessage());
            return ResponseTemplate.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(null).build();
        }
    }
}
