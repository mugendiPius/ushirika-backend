package com.mdau.ushirika.module.payment.service;

import com.mdau.ushirika.common.exception.ResourceNotFoundException;
import com.mdau.ushirika.common.response.PagedResponse;
import com.mdau.ushirika.module.auth.entity.User;
import com.mdau.ushirika.module.auth.repository.UserRepository;
import com.mdau.ushirika.module.member.entity.MemberProfile;
import com.mdau.ushirika.module.member.repository.MemberProfileRepository;
import com.mdau.ushirika.module.payment.dto.MemberSearchResultDto;
import com.mdau.ushirika.module.payment.dto.PeerContributionDto;
import com.mdau.ushirika.module.payment.repository.PeerContributionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * "Pay for another member" — a member pays an amount toward another member's account without
 * choosing what it's for. The recipient is credited via the normal PaymentAllocationService
 * settlement (fines -> dues -> ... in the platform-configured order), leftover held as credit.
 * The actual checkout lives in PaymentBasketService (it owns the Stripe plumbing); this service
 * handles the recipient picker and the "paid on my behalf" history.
 */
@Service
@RequiredArgsConstructor
public class PeerContributionService {

    private static final int SEARCH_LIMIT = 10;

    private final UserRepository userRepository;
    private final MemberProfileRepository profileRepository;
    private final PeerContributionRepository peerContributionRepository;

    @Transactional(readOnly = true)
    public List<MemberSearchResultDto> searchRecipients(String query) {
        if (query == null || query.trim().length() < 2) return List.of();
        User me = currentUser();
        return userRepository.searchActiveMembers(query.trim(), me.getId(), PageRequest.of(0, SEARCH_LIMIT))
                .stream()
                .map(u -> new MemberSearchResultDto(
                        u.getId().toString(),
                        u.getFullName(),
                        profileRepository.findByUser(u).map(MemberProfile::getMemberId).orElse(null)))
                .toList();
    }

    @Transactional(readOnly = true)
    public PagedResponse<PeerContributionDto> received(Pageable pageable) {
        return PagedResponse.of(peerContributionRepository
                .findAllByRecipientOrderByCreatedAtDesc(currentUser(), pageable)
                .map(PeerContributionDto::received));
    }

    @Transactional(readOnly = true)
    public PagedResponse<PeerContributionDto> sent(Pageable pageable) {
        return PagedResponse.of(peerContributionRepository
                .findAllByPayerOrderByCreatedAtDesc(currentUser(), pageable)
                .map(PeerContributionDto::sent));
    }

    private User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found."));
    }
}
