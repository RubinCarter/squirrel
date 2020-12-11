package org.squirrelframework.foundation.fsm.impl;

import org.squirrelframework.foundation.fsm.Action;
import org.squirrelframework.foundation.fsm.StateMachine;

/**
 * @author rubin 2020年12月10日
 */
public class FireEventActionImpl<T extends StateMachine<T, S, E, C>, S, E, C>
        implements Action<T, S, E, C> {

    private final E event;
    private final ExecutionContext executionContext;

    FireEventActionImpl(E event, ExecutionContext executionContext) {
        this.event = event;
        this.executionContext = executionContext;
    }

    @Override
    public void execute(S from, S to, E event, C context, T stateMachine) {
        stateMachine.fire(this.event, context);
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }

    @Override
    public int weight() {
        return NORMAL_WEIGHT;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public long timeout() {
        return -1;
    }

    @Override
    public String toString() {
        return "event#"+ event;
    }

}
