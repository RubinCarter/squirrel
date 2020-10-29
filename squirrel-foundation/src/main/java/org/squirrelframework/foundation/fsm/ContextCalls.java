package org.squirrelframework.foundation.fsm;

/**
 * @author rubin 2020年10月28日
 */
public class ContextCalls {

    public static class Originals<C> extends AnonymousContextCall<C> {
        @Override
        public C getContext(C context) {
            return context;
        }
    }

    public static <C> Originals<C> originals()  {
        return new Originals<>();
    }

}
