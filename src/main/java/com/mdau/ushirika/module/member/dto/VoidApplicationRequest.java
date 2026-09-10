package com.mdau.ushirika.module.member.dto;

/** Body for POST /admin/membership/applications/{id}/void. */
public record VoidApplicationRequest(

        /** Optional short reason recorded on the application and in the audit log
         *  (e.g. "Duplicate — applicant used the wrong email"). */
        String reason
) {}
