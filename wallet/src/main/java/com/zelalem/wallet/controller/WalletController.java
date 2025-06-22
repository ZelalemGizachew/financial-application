package com.zelalem.wallet.controller;

import com.zelalem.wallet.dto.request.CreateWalletRequestDto;
import com.zelalem.wallet.service.WalletService;
import com.zelalem.wallet.utils.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallets")
public class WalletController {
    private final WalletService walletService;

/*Todo API Endpoints:
    ○ POST /wallets: (Internal use only, called by User Service) Create a new wallet.
        ■ Input: userId (string, associated with the wallet).
        ■ Output: walletId (UUID), userId, currentBalance (initialized to 0.00).
    ○ GET /wallets/{walletId}: Retrieve wallet details by walletId.
        ■ Output: walletId, userId, currentBalance.
    ○ GET /wallets/user/{userId}: Retrieve wallet details by userId.
        ■ Output: walletId, userId, currentBalance.
    ○ Internal Debit/Credit APIs: This service should expose internal, robust APIs
    (e.g., PUT /wallets/{walletId}/debit and PUT /wallets/{walletId}/credit) that are
    callable only by the Transaction Service to modify balances. These internal APIs
    should handle concurrency and atomicity for balance updates.
*/

    @PostMapping("/")
    public ResponseTemplate<?> createNewWallet(@RequestBody CreateWalletRequestDto createWalletRequestDto) {
        return walletService.createNewWallet(createWalletRequestDto);
    }

    @GetMapping("/{walletId}")
    public ResponseTemplate<?> getWalletByWalletId(@PathVariable("walletId") UUID walletId) {
        return walletService.getWalletByWalletId(walletId);
    }

    @GetMapping("/user/{userId}")
    public ResponseTemplate<?> getWalletByUserId(@PathVariable("userId") String userId) {
        return walletService.getWalletByUserId(userId);
    }
}
