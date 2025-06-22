package com.zelalem.transaction.controller;

import com.zelalem.transaction.dto.request.DepositRequestDto;
import com.zelalem.transaction.dto.request.TransferRequestDto;
import com.zelalem.transaction.dto.request.WithdrawRequestDto;
import com.zelalem.transaction.model.TransactionHistory;
import com.zelalem.transaction.service.TransactionHelperService;
import com.zelalem.transaction.service.TransactionService;
import com.zelalem.transaction.utils.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionHelperService transactionHelperService;

/*Todo: ● API Endpoints:
            ○ POST /transactions/deposit: Deposit funds into a wallet.
                ■ Input: walletId (UUID), amount (decimal, positive).
                ■ Output: transactionId (UUID), walletId, amount, type (DEPOSIT), status (SUCCESS/FAILED), timestamp.
            ○ POST /transactions/withdraw: Withdraw funds from a wallet.
                ■ Input: walletId (UUID), amount (decimal, positive).
                ■ Output: transactionId (UUID), walletId, amount, type (WITHDRAW), status (SUCCESS/FAILED), timestamp.
                ■ Constraint: A withdrawal should only succeed if the wallet has sufficient funds (enforced by Wallet Service).
            ○ POST /transactions/transfer: Transfer funds between wallets.
                ■ Input: sourceWalletId (UUID), destinationWalletId (UUID), amount decimal, positive).
                ■ Output: transactionId (UUID), sourceWalletId, destinationWalletId, amount, type (TRANSFER), status (SUCCESS/FAILED), timestamp.
                ■ Constraint: A transfer should only succeed if the source wallet has sufficient funds (enforced by Wallet Service).
*/

    @PostMapping("/deposit")
    public ResponseTemplate<?> deposit(@Validated @RequestBody DepositRequestDto depositRequestDto) {
        return transactionHelperService.deposit(depositRequestDto);
    }

    @PostMapping("/withdraw")
    public ResponseTemplate<?> withdraw(@Validated @RequestBody WithdrawRequestDto withdrawRequestDto) {
        return transactionHelperService.withdraw(withdrawRequestDto);
    }

    @PostMapping("/transfer")
    public ResponseTemplate<?> transfer(@Validated @RequestBody TransferRequestDto transferRequestDto) {
        return transactionService.transfer(transferRequestDto);
    }
}
