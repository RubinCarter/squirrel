package org.squirrelframework.foundation.fsm.impl;

import org.squirrelframework.foundation.component.SquirrelComponent;
import org.squirrelframework.foundation.fsm.Action;
import org.squirrelframework.foundation.fsm.MutableState;
import org.squirrelframework.foundation.fsm.StateMachine;
import org.squirrelframework.foundation.fsm.builder.EntryExitActionBuilder;
import org.squirrelframework.foundation.fsm.builder.When;

import java.util.List;

class EntryExitActionBuilderImpl<T extends StateMachine<T, S, E, C>, S, E, C> implements 
    EntryExitActionBuilder<T, S, E, C>, SquirrelComponent {
    
    private final boolean isEntryAction;
    
    private final MutableState<T, S, E, C> state;
    
    private final ExecutionContext executionContext;
    
    EntryExitActionBuilderImpl(MutableState<T, S, E, C> state, 
            boolean isEntryAction, ExecutionContext executionContext) {
        this.state = state;
        this.isEntryAction = isEntryAction;
        this.executionContext = executionContext;
    }

    @Override
    public When<T, S, E, C> perform(Action<T, S, E, C> action) {
        if(isEntryAction) {
            state.addEntryAction(action);
        } else {
            state.addExitAction(action);
        }
        return this;
    }

    @Override
    public When<T, S, E, C> perform(List<? extends Action<T, S, E, C>> actions) {
        if(isEntryAction) {
            state.addEntryActions(actions);
        } else {
            state.addExitActions(actions);
        }
        return this;
    }

    @Override
    public When<T, S, E, C> evalMvel(String expression) {
        Action<T, S, E, C> action = FSM.newMvelAction(expression, executionContext);
        perform(action);
        return this;
    }

    @Override
    public When<T, S, E, C> callMethod(String methodName) {
        Action<T, S, E, C> action = FSM.newMethodCallActionProxy(methodName, executionContext);
        perform(action);
        return this;
    }

    @Override
    public When<T, S, E, C> fireEvent(List<E> events) {
        for (E event : events) {
            Action<T, S, E, C> action = FSM.newFireEventAction(event, executionContext);
            perform(action);
        }
        return this;
    }

    @Override
    public When<T, S, E, C> fireEvent(E event) {
        Action<T, S, E, C> action = FSM.newFireEventAction(event, executionContext);
        perform(action);
        return this;
    }

}
