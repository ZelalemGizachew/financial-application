package com.zelalem.wallet.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID walletId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "current_balance")
    private Double currentBalance;

    public Double getCurrentBalance() {
        if (currentBalance == null) return 0.00;
        return BigDecimal.valueOf(currentBalance).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        currentBalance = 0.0;
    }
}
