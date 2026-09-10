package com.mdau.ushirika.module.payment.dto;

import com.mdau.ushirika.module.payment.enums.SettlementBucket;

import java.util.List;

/** The platform-wide order a pooled payment settles a member's obligations in. */
public record SettlementPriorityDto(List<SettlementBucket> order) {}
