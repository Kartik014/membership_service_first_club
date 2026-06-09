package com.firstclub.membership.services;

import com.firstclub.membership.enums.CriteriaType;
import com.firstclub.membership.interfaces.CriteriaEvaluator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TierEvaluationService {
    private final List<CriteriaEvaluator> criteriaEvaluators;
    private Map<CriteriaType, CriteriaEvaluator> evaluatorMap;

    @PostConstruct
    public void init() {
        evaluatorMap = criteriaEvaluators.stream()
                .collect(Collectors.toMap(
                        CriteriaEvaluator::supportedType,
                        Function.identity()
                ));
    }

    public CriteriaEvaluator getEvaluator(CriteriaType type){
        return evaluatorMap.get(type);
    }
}
