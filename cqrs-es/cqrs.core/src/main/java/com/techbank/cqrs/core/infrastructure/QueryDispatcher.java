package com.techbank.cqrs.core.infrastructure;

import com.techbank.cqrs.core.domain.BaseEntity;
import com.techbank.cqrs.core.queries.BaseQuery;
import com.techbank.cqrs.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <T extends BaseQuery, U extends BaseEntity> List<U> send(T query);
}
