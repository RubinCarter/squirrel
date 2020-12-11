package org.squirrelframework.foundation.fsm.impl;

import org.squirrelframework.foundation.component.SquirrelComponent;
import org.squirrelframework.foundation.fsm.*;
import org.squirrelframework.foundation.fsm.builder.*;

import java.util.List;
import java.util.Map;

class TransitionBuilderImpl<T extends StateMachine<T, S, E, C>, S, E, C> implements 
    ExternalTransitionBuilder<T, S, E, C>, InternalTransitionBuilder<T, S, E, C>, LocalTransitionBuilder<T, S, E, C>, 
    From<T, S, E, C>, To<T, S, E, C>, On<T, S, E, C>, SquirrelComponent {

    private final Map<S, MutableState<T, S, E, C>> states;
    
    private MutableState<T, S, E, C> sourceState;
    
    private MutableState<T, S, E, C> targetState;
    
    private MutableTransition<T, S, E, C> transition;
    
    private final TransitionType transitionType;
    
    private final int priority;
    
    private final ExecutionContext executionContext;
    
    TransitionBuilderImpl(Map<S, MutableState<T, S, E, C>> states, TransitionType transitionType, 
            int priority, ExecutionContext executionContext) {
        this.states = states;
        this.transitionType = transitionType;
        this.priority = priority;
        this.executionContext = executionContext;
    }
    
    @Override
    public When<T, S, E, C> perform(Action<T, S, E, C> action) {
        transition.addAction(action);
        return this;
    }
    
    @Override
    public When<T, S, E, C> perform(List<? extends Action<T, S, E, C>> actions) {
        transition.addActions(actions);
        return this;
    }
    
    @Override
    public When<T, S, E, C> evalMvel(String expression) {
        Action<T, S, E, C> action = FSM.newMvelAction(expression, executionContext);
        transition.addAction(action);
        return this;
    }
    
    @Override
    public When<T, S, E, C> callMethod(final String methodName) {
        Action<T, S, E, C> action = FSM.newMethodCallActionProxy(methodName, executionContext);
        transition.addAction(action);
        return this;
    }

    @Override
    public When<T, S, E, C> fireEvent(List<E> events) {
        for (E event : events) {
            Action<T, S, E, C> action = FSM.newFireEventAction(event, executionContext);
            transition.addAction(action);
        }
        return this;
    }

    @Override
    public When<T, S, E, C> fireEvent(E event) {
        Action<T, S, E, C> action = FSM.newFireEventAction(event, executionContext);
        transition.addAction(action);
        return this;
    }

    @Override
    public On<T, S, E, C> on(E event) {
        transition = sourceState.addTransitionOn(event);
        transition.setTargetState(targetState);
        transition.setType(transitionType);
        transition.setPriority(priority);
        return this;
    }

    @Override
    public To<T, S, E, C> to(S stateId) {
        targetState = FSM.getState(states, stateId);
        return this;
    }
    
    @Override
    public To<T, S, E, C> toFinal(S stateId) {
        targetState = FSM.getState(states, stateId);
        if(!targetState.isFinalState()) {
            targetState.setFinal(true);
        }
        return this;
    }

    @Override
    public From<T, S, E, C> from(S stateId) {
        sourceState = FSM.getState(states, stateId);
        return this;
    }

    @Override
    public When<T, S, E, C> when(Condition<C> condition) {
        transition.setCondition(condition);
        return this;
    }
    
    @Override
    public When<T, S, E, C> whenMvel(String expression) {
        Condition<C> cond = FSM.newMvelCondition(expression, executionContext.getScriptManager());
        transition.setCondition(cond);
        return this;
    }
    
    @Override
    public To<T, S, E, C> within(S stateId) {
        sourceState = targetState = FSM.getState(states, stateId);
        return this;
    }
}
