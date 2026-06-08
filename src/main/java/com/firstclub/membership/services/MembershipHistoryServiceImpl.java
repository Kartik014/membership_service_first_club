package com.firstclub.membership.services;

import com.firstclub.membership.interfaces.MembershipHistoryService;
import com.firstclub.membership.repository.MembershipHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembershipHistoryServiceImpl implements MembershipHistoryService {
    private final MembershipHistoryRepository membershipHistoryRepository;
}
