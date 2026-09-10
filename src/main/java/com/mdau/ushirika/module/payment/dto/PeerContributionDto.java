package com.mdau.ushirika.module.payment.dto;

import com.mdau.ushirika.common.util.AppClock;
import com.mdau.ushirika.module.payment.entity.PeerContribution;

import java.math.BigDecimal;
import java.time.Instant;

/** One "paid on my behalf" / "I paid for" row. {@code direction} is RECEIVED or SENT relative
 *  to the member viewing it; {@code counterpartyName} is the other member. */
public record PeerContributionDto(
        String    id,
        String    direction,
        String    counterpartyName,
        BigDecimal amount,
        String    currency,
        Instant   createdAt
) {
    public static PeerContributionDto received(PeerContribution pc) {
        return new PeerContributionDto(pc.getId().toString(), "RECEIVED",
                pc.getPayer().getFullName(), pc.getAmount(), pc.getCurrency(),
                AppClock.serverInstant(pc.getCreatedAt()));
    }

    public static PeerContributionDto sent(PeerContribution pc) {
        return new PeerContributionDto(pc.getId().toString(), "SENT",
                pc.getRecipient().getFullName(), pc.getAmount(), pc.getCurrency(),
                AppClock.serverInstant(pc.getCreatedAt()));
    }
}
