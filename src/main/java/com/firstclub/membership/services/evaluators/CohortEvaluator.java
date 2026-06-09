package com.firstclub.membership.services.evaluators;

import com.firstclub.membership.entity.TierCriteria;
import com.firstclub.membership.entity.User;
import com.firstclub.membership.entity.UserMembershipStats;
import com.firstclub.membership.enums.CriteriaType;
import com.firstclub.membership.interfaces.CriteriaEvaluator;
import java.util.Arrays;
import java.util.List;

public class CohortEvaluator implements CriteriaEvaluator {
    @Override
    public CriteriaType supportedType() {
        return CriteriaType.COHORT;
    }

    @Override
    public boolean evaluate(User user, TierCriteria tierCriteria, UserMembershipStats userMembershipStats) {
        List<String> allowedCohorts = Arrays.stream(tierCriteria.getThresholdValue().split(","))
                        .map(String::trim)
                        .toList();

        return allowedCohorts.contains(user.getCohort().toString());
    }
}
