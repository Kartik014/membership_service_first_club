package com.firstclub.membership.services.evaluators;

import com.firstclub.membership.entity.TierCriteria;
import com.firstclub.membership.entity.User;
import com.firstclub.membership.entity.UserMembershipStats;
import com.firstclub.membership.enums.CriteriaType;
import com.firstclub.membership.interfaces.CriteriaEvaluator;
import org.springframework.stereotype.Component;

@Component
public class MonthlyOrderEvaluator implements CriteriaEvaluator {
    @Override
    public CriteriaType supportedType() {
        return CriteriaType.MONTHLY_ORDER;
    }

    @Override
    public boolean evaluate(User user, TierCriteria tierCriteria, UserMembershipStats userMembershipStats) {
        return userMembershipStats.getMonthlyOrders().compareTo(Integer.parseInt(String.valueOf(tierCriteria.getThresholdValue()))) >= 0;
    }
}
