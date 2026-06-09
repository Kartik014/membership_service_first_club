package com.firstclub.membership.services;

import com.firstclub.membership.DTO.MembershipHistoryResponseDTO;
import com.firstclub.membership.entity.MembershipHistory;
import com.firstclub.membership.interfaces.MembershipHistoryService;
import com.firstclub.membership.repository.MembershipHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipHistoryServiceImpl implements MembershipHistoryService {
    private final MembershipHistoryRepository membershipHistoryRepository;

    private MembershipHistoryResponseDTO mapToResponse(MembershipHistory history){
        return new MembershipHistoryResponseDTO(
                history.getAction(),
                history.getOldTier() != null ? history.getOldTier().getName() : null,
                history.getNewTier() != null ? history.getNewTier().getName() : null,
                history.getCreatedAt()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<MembershipHistoryResponseDTO> getMembershipHistory(Long membershipId) {
        List<MembershipHistory> history = membershipHistoryRepository.findByMembership_Id(membershipId).orElseThrow(() -> new RuntimeException("Membership history not found."));

        return history
                .stream()
                .map(h -> new MembershipHistoryResponseDTO(
                        h.getAction(),
                        h.getOldTier() != null ? h.getOldTier().getName() : null,
                        h.getNewTier() != null ? h.getNewTier().getName() : null,
                        h.getCreatedAt()
                ))
                .toList();
    }
}
