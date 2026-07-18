package org.example.knockin.global.util;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryReflectionUtils {
    public static <T> Expression<T> createProjection(Class<T> clazz, List<EntityPathBase<?>> qEntities, Map<String, Expression<?>> customMappings) {
        List<Expression<?>> expressions = new ArrayList<>();
        List<PathBuilder<?>> pathBuilders = qEntities.stream().map(q -> new PathBuilder<>(q.getType(), q.getMetadata())).collect(Collectors.toList());

        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();

            if (customMappings != null && customMappings.containsKey(fieldName)) {
                expressions.add(customMappings.get(fieldName));
                continue;
            }

            for (PathBuilder<?> builder : pathBuilders) {
                try {
                    builder.getType().getDeclaredField(fieldName);
                    expressions.add(builder.get(fieldName, (Class) field.getType()));
                    break;
                } catch (NoSuchFieldException e) {
                    // 필드가 없으면 다음 엔티티 탐색
                }
            }
        }

        return Projections.fields(clazz, expressions.toArray(new Expression[0]));
    }
}
