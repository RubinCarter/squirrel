package org.squirrelframework.foundation.fsm;

/**
 * @author rubin 2020年10月29日
 */
public abstract class AnonymousContextCall<T> implements ContextCall<T> {

    @Override
    public String name() {
        return getClass().getSimpleName();
    }

    @Override
    public final String toString() {
        return "instance#"+getClass().getName();
    }

}
