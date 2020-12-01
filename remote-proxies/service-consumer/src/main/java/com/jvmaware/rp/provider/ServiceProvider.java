package com.jvmaware.rp.provider;

import com.jvmaware.rp.config.ConfigProvider;
import com.jvmaware.rp.proxy.ServiceInvocationHandler;
import com.jvmaware.rp.services.RoomBookingService;

import java.lang.reflect.Proxy;

public class ServiceProvider {

    private static final ConfigProvider configProvider = new ConfigProvider();
    public static RoomBookingService roomBookingService() {
        return RoomBookingServiceProvider.INSTANCE;
    }

    private static class RoomBookingServiceProvider {
        private static final RoomBookingService INSTANCE =
                (RoomBookingService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                        new Class[]{RoomBookingService.class},
                        new ServiceInvocationHandler(configProvider));
    }
}
