package com.techbank.cqrs.core.infrastructure;

import com.techbank.cqrs.core.events.BaseEvent;

import java.util.List;

public interface EventStore {
    void save(String aggregateIdentifier, Iterable<BaseEvent> events, int expectedVersion);
    List<BaseEvent> getEvents(String aggregateId);
}
