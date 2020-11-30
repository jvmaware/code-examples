package com.jvmaware.rp.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * The single point of interaction between client and remote service.
 * This implementation captures all the method calls triggered on the corresponding
 * proxy instance of the actual remote service.
 * <p>
 * Based on the service and method, it will trigger the corresponding API requests
 * and provides the caller with the results.
 *
 * @author gaurs
 */
public class ServiceInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
