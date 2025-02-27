package com.techbank.cqrs.core.queries;

import com.techbank.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler<T extends  BaseQuery> {
    List<BaseEntity> handle(T query);
}
