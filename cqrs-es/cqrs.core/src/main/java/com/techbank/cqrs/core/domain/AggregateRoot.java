package com.techbank.cqrs.core.domain;

import com.techbank.cqrs.core.events.BaseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class AggregateRoot {
    protected String id;
    private int version = -1;

    private final List<BaseEvent> changes = new ArrayList<>();
    private Logger logger = Logger.getLogger(AggregateRoot.class.getName());

    public String getId() {
        return this.id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<BaseEvent> getUncommittedChanges(){
        return this.changes;
    }

    public void markChangesAsCommitted(){
        this.changes.clear();
    }

    protected void applyChange(BaseEvent event, boolean isNewEvent){
        try {
            var method = this.getClass().getMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        }catch(NoSuchMethodException noSuchMethodException){
            logger.warning("No method found for event: " + event.getClass().getSimpleName());
        }catch(Exception e){
            logger.warning("Error applying event: " + event.getClass().getSimpleName() + " - " + e.getMessage());
        }
        if (isNewEvent)
            this.changes.add(event);
    }

    public void raiseEvent(BaseEvent event){
        applyChange(event, true);
    }

    public void replayEvents(Iterable<BaseEvent> events){
        events.forEach(event -> applyChange(event, false));
    }
}
