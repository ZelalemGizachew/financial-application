package com.zelalem.user.service;

import com.zelalem.user.dto.request.CreateUserRequestDto;
import com.zelalem.user.dto.request.api.CreateWalletRequestDto;
import com.zelalem.user.dto.response.CreateUserResponseDto;
import com.zelalem.user.dto.response.api.CreateWalletResponseDto;
import com.zelalem.user.dto.response.api.GetWalletByUserIdResponseDto;
import com.zelalem.user.model.User;
import com.zelalem.user.repository.UserRepository;
import com.zelalem.user.utils.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final Gateway gateway;

    private final UserRepository userRepository;

    public ResponseTemplate<?> createUser(CreateUserRequestDto createUserRequestDto) {
        try {
            Optional<User> existingUserOptional = userRepository.findByUserId(createUserRequestDto.getUserId());
            if (existingUserOptional.isPresent()) {
                logger.info("User with id {} already exists", createUserRequestDto.getUserId());
                return ResponseTemplate.builder().code(HttpStatus.BAD_REQUEST.value()).message("Username/Email already exists.").data(null).build();
            }

            User user = userRepository.save(User.builder().userId(createUserRequestDto.getUserId()).build());
            ResponseTemplate<CreateWalletResponseDto> walletResponseRT = gateway.createUserWallet(CreateWalletRequestDto.builder().userId(user.getUserId()).build());
            if (walletResponseRT.getCode().equals(HttpStatus.CREATED.value())) {
                logger.info("Wallet created successfully");
                return ResponseTemplate.builder().code(HttpStatus.CREATED.value()).message("Success!").data(CreateUserResponseDto.builder().userId(user.getUserId()).walletId(walletResponseRT.getData().getWalletId()).build()).build();
            }
            else if (walletResponseRT.getCode().equals(HttpStatus.BAD_REQUEST.value())) {
                logger.info("Existing wallet found by the specified userId {}", createUserRequestDto.getUserId());
                return walletResponseRT;
            }
            else {
                logger.error("Wallet creation failed");
                throw new Exception(walletResponseRT.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while creating user", e);
            return ResponseTemplate.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(null).build();
        }
    }

    public ResponseTemplate<?> getUserDetails(String userId) {
        try {
            Optional<User> userOptional = userRepository.findByUserId(userId);
            if (userOptional.isPresent()) {
                ResponseTemplate<GetWalletByUserIdResponseDto>  walletResponseRT = gateway.getUserWalletByUserId(userId);
                if (walletResponseRT.getCode().equals(HttpStatus.OK.value())) {
                    logger.info("Wallet found by the specified userId {}", userId);
                    return ResponseTemplate.builder().code(HttpStatus.OK.value()).message("Success!").data(CreateUserResponseDto.builder().userId(userOptional.get().getUserId()).walletId(walletResponseRT.getData().getWalletId()).build()).build();
                }
                else if (walletResponseRT.getCode().equals(HttpStatus.NOT_FOUND.value())) {
                    logger.info("Wallet for user id {} not found", userId);
                    return walletResponseRT;
                }
                else {
                    logger.error("Wallet retrieval failed");
                    return walletResponseRT;
                }
            }
            else {
                logger.info("User with id {} not found", userId);
                return ResponseTemplate.builder().code(HttpStatus.NOT_FOUND.value()).message("User not found.").data(null).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while retrieving user details", e);
            return ResponseTemplate.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Something went wrong! Please try again.").data(null).build();
        }
    }
}
