package com.techbank.account.query.infrastructure;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositedEvent;
import com.techbank.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(AccountClosedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
}
