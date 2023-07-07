package com.tfg.eHealth.utils;

import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Builder
public class EntitySpecifications<T> implements Specification<T> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Predicate p = null;
        String[] splitArray = criteria.getKey().split("\\.");
        List<String> split = Arrays.asList(splitArray);

        Path expression = getPath(root.get(split.get(0)), split.subList(1, split.size()));
        //        [split.get(split.size() - 1)]
        switch (expression.getJavaType().getName()) {
            case "java.lang.Boolean" -> {
                Boolean valueBool = Boolean.valueOf(criteria.getValue().toString());
                p = predicateBoolean(expression, builder, criteria.getKey(), criteria.getOperation(), valueBool);
            }
            case "java.time.LocalDate" -> {
                String valueDate = criteria.getValue().toString();
                p = predicateDate(expression, builder, criteria.getKey(), criteria.getOperation(), valueDate);
            }
            case "java.lang.String" -> {
                String valueString = criteria.getValue().toString();
                p = predicateString(expression, builder, criteria.getKey(), criteria.getOperation(), valueString);
            }
            case "java.lang.Long" -> {
                Long valueLong = Long.parseLong(criteria.getValue().toString());
                p = predicateLong(expression, builder, criteria.getKey(), criteria.getOperation(), valueLong);
            }
            case "java.lang.Integer" -> {
                Integer valueInteger = Integer.parseInt(criteria.getValue().toString());
                p = predicateInteger(expression, builder, criteria.getKey(), criteria.getOperation(), valueInteger);
            }
            case "com.tfg.eHealth.vo.EstadoEnum" -> {
                Integer estado = Integer.parseInt(criteria.getValue().toString());
                p = predicateInteger(expression, builder, criteria.getKey(), criteria.getOperation(), estado);
            }
            case "com.tfg.eHealth.vo.SexoEnum" -> {
                Integer sexo = Integer.parseInt(criteria.getValue().toString());
                p = predicateInteger(expression, builder, criteria.getKey(), criteria.getOperation(), sexo);
            }
            default -> logger.info(expression.getJavaType().getName());
        }
        return p;
    }

    private Predicate predicateInteger(Path path, CriteriaBuilder builder, String key, String operation, Integer valueInteger) {
        Predicate p = null;
        switch (operation) {
            case ":":
            case "/":
                p = builder.equal(
                        path, valueInteger);
                break;
            case ">":
            case "<":
            default:
        }
        return p;

    }

    private Predicate predicateLong(Path path, CriteriaBuilder builder, String key, String operation, Long valueLong) {
        Predicate p = null;
        switch (operation) {
            case ":":
            case "/":
                p = builder.equal(
                        path, valueLong);
                break;
            case ">":
            case "<":
            default:
                p = null;
        }
        return p;
    }

    private Predicate predicateString(Path path, CriteriaBuilder builder, String key, String operation, String valueString) {
        Predicate p = null;
        switch (operation) {
            case ":":
            case "/":
                p = builder.like(
                        path, "%" + valueString + "%");
                break;
            case ">":
            case "<":
            default:
                p = null;
        }
        return p;
    }

    private Predicate predicateDateTime(Path path, CriteriaBuilder builder, String key, String operation, String valueDate) {
        Predicate p = null;
        switch (operation) {
            case ":":
                LocalDateTime dtant;
                dtant = DateUtils.toLocalDateTime(valueDate, "dd-MM-yyyy");
                // number of days to add
                LocalDateTime dtnext = dtant.plusDays(1);
                p = builder.and(builder.greaterThanOrEqualTo(path, dtant),
                        builder.lessThanOrEqualTo(path, dtnext));
                break;
            case ">":
                dtant = DateUtils.toLocalDateTime(valueDate, "dd-MM-yyyy HH:mm");
                p = builder.greaterThanOrEqualTo(path, dtant);
                break;
            case "<":
                dtant = DateUtils.toLocalDateTime(valueDate, "dd-MM-yyyy HH:mm");
                p = builder.lessThanOrEqualTo(path, dtant);
                break;
            default:
                p = null;
        }
        return p;
    }

    private Predicate predicateDate(Path path, CriteriaBuilder builder, String key, String operation, String valueDate) {
        Predicate p = null;
        switch (operation) {
            case ":":
                LocalDate dtant;
                dtant = DateUtils.toLocalDate(valueDate, "dd-MM-yyyy");
                // number of days to add
                LocalDate dtnext = dtant.plusDays(1);
                p = builder.and(builder.greaterThanOrEqualTo(path, dtant),
                        builder.lessThanOrEqualTo(path, dtnext));
                break;
            case ">":
                dtant = DateUtils.toLocalDate(valueDate, "dd-MM-yyyy");
                p = builder.greaterThanOrEqualTo(path, dtant);
                break;
            case "<":
                dtant = DateUtils.toLocalDate(valueDate, "dd-MM-yyyy");
                p = builder.lessThanOrEqualTo(path, dtant);
                break;
            default:
                p = null;
        }
        return p;
    }

    private Predicate predicateBoolean(Path path, CriteriaBuilder builder, String key, String operation, Boolean valueBool) {
        Predicate p = null;
        switch (operation) {
            case ":":
            case "/":
                p = builder.equal(
                        path, valueBool);
                break;
            case ">":
            case "<":
            default:
                p = null;
        }
        return p;
    }

    private Path getPath(Path path, List<String> split) {
        if (split.size() == 0) {
            return path;
        }
        if (split.size() == 1) {
            return path.get(split.get(0));
        } else {
            Path objectPath = path.get(split.get(0));

            return getPath(objectPath, split.subList(1, split.size()));
//            return getRoot()
        }
    }
}

