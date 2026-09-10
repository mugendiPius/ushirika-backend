package com.mdau.ushirika.module.payment.entity;

import com.mdau.ushirika.common.entity.BaseEntity;
import com.mdau.ushirika.module.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * A completed "pay for another member" payment. Written only once the payer's Stripe checkout
 * has actually cleared and the amount has been settled against the recipient's account, so there
 * is no PENDING state here -- one row means the money moved.
 */
@Entity
@Table(name = "peer_contributions", indexes = {
        @Index(name = "idx_pc_payer",     columnList = "payer_id"),
        @Index(name = "idx_pc_recipient", columnList = "recipient_id"),
        @Index(name = "idx_pc_created_at", columnList = "created_at"),
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeerContribution extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pc_payer"))
    private User payer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pc_recipient"))
    private User recipient;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 3)
    @Builder.Default
    private String currency = "USD";

    /** The Stripe checkout session that paid for this — for reconciliation against the ledger. */
    @Column(name = "session_id", length = 100)
    private String sessionId;
}
