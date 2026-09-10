package com.mdau.ushirika.module.payment.enums;

/** The obligation buckets a pooled payment is applied to, in a platform-configurable order.
 *  Each value maps 1:1 to a settle* method in PaymentAllocationService. */
public enum SettlementBucket {
    FINE,
    DUES,
    MGR,
    BENEVOLENCE_REPLENISHMENT,
    BENEVOLENCE_ENROLLMENT
}
