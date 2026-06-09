package com.firstclub.membership.interfaces;

import com.firstclub.membership.entity.TierCriteria;
import com.firstclub.membership.entity.User;
import com.firstclub.membership.entity.UserMembershipStats;
import com.firstclub.membership.enums.CriteriaType;

public interface CriteriaEvaluator {
    CriteriaType supportedType();
    boolean evaluate(User user, TierCriteria tierCriteria, UserMembershipStats userMembershipStats);
}
