package com.mdau.ushirika.module.payment.controller;

import com.mdau.ushirika.common.response.ApiResponse;
import com.mdau.ushirika.common.response.PagedResponse;
import com.mdau.ushirika.module.payment.dto.MemberSearchResultDto;
import com.mdau.ushirika.module.payment.dto.OnBehalfCheckoutRequest;
import com.mdau.ushirika.module.payment.dto.PaymentInitDto;
import com.mdau.ushirika.module.payment.dto.PeerContributionDto;
import com.mdau.ushirika.module.payment.service.PaymentBasketService;
import com.mdau.ushirika.module.payment.service.PeerContributionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Member-facing "pay for another member" flow. Any authenticated member. */
@RestController
@RequestMapping("/payments/on-behalf")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@Tag(name = "Payments — On behalf", description = "Pay an amount toward another member's account")
public class PeerContributionController {

    private final PeerContributionService peerContributionService;
    private final PaymentBasketService paymentBasketService;

    @GetMapping("/members")
    @Operation(summary = "Type-ahead search for a member to pay on behalf of (name or email; returns name + member ID only)")
    public ResponseEntity<ApiResponse<List<MemberSearchResultDto>>> searchMembers(@RequestParam String q) {
        return ResponseEntity.ok(ApiResponse.ok(peerContributionService.searchRecipients(q)));
    }

    @PostMapping("/checkout")
    @Operation(summary = "Start a Stripe checkout to pay an amount toward another member's account")
    public ResponseEntity<ApiResponse<PaymentInitDto>> checkout(@Valid @RequestBody OnBehalfCheckoutRequest req) {
        return ResponseEntity.ok(ApiResponse.ok(paymentBasketService.startOnBehalfCheckout(req)));
    }

    @GetMapping("/received")
    @Operation(summary = "Payments other members have made on my behalf")
    public ResponseEntity<ApiResponse<PagedResponse<PeerContributionDto>>> received(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(peerContributionService.received(
                PageRequest.of(page, size, Sort.by("createdAt").descending()))));
    }

    @GetMapping("/sent")
    @Operation(summary = "Payments I have made on behalf of other members")
    public ResponseEntity<ApiResponse<PagedResponse<PeerContributionDto>>> sent(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ApiResponse.ok(peerContributionService.sent(
                PageRequest.of(page, size, Sort.by("createdAt").descending()))));
    }
}
