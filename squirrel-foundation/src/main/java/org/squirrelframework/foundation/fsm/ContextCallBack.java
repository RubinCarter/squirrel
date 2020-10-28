package org.squirrelframework.foundation.fsm;

/**
 * @author rubin 2020年10月28日
 */
public interface ContextCallBack<C> {

    C getContext(C context);

}
