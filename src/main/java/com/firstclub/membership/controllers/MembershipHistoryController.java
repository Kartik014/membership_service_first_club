package com.firstclub.membership.controllers;

import com.firstclub.membership.interfaces.MembershipHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/membership-history")
public class MembershipHistoryController {
    private final MembershipHistoryService membershipHistoryService;
}
