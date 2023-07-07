package com.tfg.eHealth.utils;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySpecificationsBuilder<T> {

    private final List<SearchCriteria> params;

    public EntitySpecificationsBuilder() {
        this.params = new ArrayList<>();
    }

    public EntitySpecificationsBuilder<T> with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<T> build() {
        if (params.isEmpty()) {
            return null;
        }
        List<Specification<T>> specs = params.stream()
                .map(EntitySpecifications<T>::new)
                .collect(Collectors.toList());

        Specification<T> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specs.get(i))
                    : Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}
