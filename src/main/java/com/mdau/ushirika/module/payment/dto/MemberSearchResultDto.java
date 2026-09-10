package com.mdau.ushirika.module.payment.dto;

/** One hit in the "pay for another member" picker. Deliberately no email — the payer only
 *  needs to recognise the person, not see their contact details. */
public record MemberSearchResultDto(
        String id,
        String fullName,
        String memberId
) {}
