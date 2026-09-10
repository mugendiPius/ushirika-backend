package com.mdau.ushirika.module.payment.dto;

import com.mdau.ushirika.module.payment.enums.SettlementBucket;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UpdateSettlementPriorityRequest(

        /** Must list every SettlementBucket exactly once -- fully validated in the service. */
        @NotEmpty(message = "Order is required")
        List<SettlementBucket> order
) {}
