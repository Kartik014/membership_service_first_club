package com.firstclub.membership.services.evaluators;

import com.firstclub.membership.entity.TierCriteria;
import com.firstclub.membership.entity.User;
import com.firstclub.membership.entity.UserMembershipStats;
import com.firstclub.membership.enums.CriteriaType;
import com.firstclub.membership.interfaces.CriteriaEvaluator;
import java.math.BigDecimal;

public class MonthlySpendEvaluator implements CriteriaEvaluator {
    @Override
    public CriteriaType supportedType() {
        return CriteriaType.MONTHLY_SPEND;
    }

    @Override
    public boolean evaluate(User user, TierCriteria tierCriteria, UserMembershipStats userMembershipStats) {
        return userMembershipStats.getMonthlySpend().compareTo(new BigDecimal(tierCriteria.getThresholdValue())) >= 0;
    }
}
