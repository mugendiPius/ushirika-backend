package com.mdau.ushirika.module.payment.repository;

import com.mdau.ushirika.module.auth.entity.User;
import com.mdau.ushirika.module.payment.entity.PeerContribution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PeerContributionRepository extends JpaRepository<PeerContribution, UUID> {

    Page<PeerContribution> findAllByRecipientOrderByCreatedAtDesc(User recipient, Pageable pageable);

    Page<PeerContribution> findAllByPayerOrderByCreatedAtDesc(User payer, Pageable pageable);
}
