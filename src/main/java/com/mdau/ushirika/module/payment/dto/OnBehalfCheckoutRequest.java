package com.mdau.ushirika.module.payment.dto;

import com.mdau.ushirika.module.payment.enums.PreferredPaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

/** A member paying an amount toward another member's account. The payer completes the resulting
 * Stripe checkout with their own card; the recipient is credited only once it clears. */
public record OnBehalfCheckoutRequest(
        @NotNull UUID recipientId,
        @NotNull @DecimalMin(value = "5.00", message = "Minimum on-behalf payment is $5.00") BigDecimal amount,
        @NotBlank String successUrl,
        @NotBlank String cancelUrl,
        PreferredPaymentMethod paymentMethod
) {}
