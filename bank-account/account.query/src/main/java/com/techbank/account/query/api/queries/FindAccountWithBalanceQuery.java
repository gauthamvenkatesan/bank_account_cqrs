package com.techbank.account.query.api.queries;


import com.techbank.account.query.api.dto.QueryEqualityType;
import com.techbank.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {
    private double balance;
    private QueryEqualityType equalityType;
}
