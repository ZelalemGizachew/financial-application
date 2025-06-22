package com.zelalem.transaction.model;

import com.zelalem.transaction.enums.TRANSACTION_STATUS;
import com.zelalem.transaction.enums.TRANSACTION_TYPE;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_transaction_history")
public class TransactionHistory {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "wallet_id")
    private UUID walletId;

    @Column(name = "amount")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TRANSACTION_TYPE type;

    @Column(name = "status")
    private TRANSACTION_STATUS status;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = "TX-" + generateRandomId(8); // Todo: Ex: TX-7GJQ4T2L
        }
    }

    private String generateRandomId(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder idBuilder = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            idBuilder.append(characters.charAt(index));
        }
        return idBuilder.toString();
    }
}
