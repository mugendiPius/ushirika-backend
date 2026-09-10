package com.mdau.ushirika.module.member.enums;

public enum ApplicationStatus {
    DRAFT,
    SUBMITTED,
    FORM_SENT,
    ONBOARDING_IN_PROGRESS,
    PAYMENT_SUBMITTED,
    APPROVED,
    REJECTED,
    /** Admin dismissed the application as invalid (e.g. a duplicate / wrong-email entry). Any
     *  auto-created applicant account that never progressed is torn down at void time so its
     *  unique email/phone are freed for a corrected application. Distinct from REJECTED, which
     *  means the person was actually turned away. */
    VOIDED
}
