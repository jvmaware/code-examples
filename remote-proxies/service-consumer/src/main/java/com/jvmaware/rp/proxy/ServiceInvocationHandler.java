package com.jvmaware.rp.proxy;

import com.jvmaware.rp.config.ConfigProvider;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.time.Duration;

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

    private final ConfigProvider configProvider;
    private final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public ServiceInvocationHandler(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var url = configProvider.getConfig().getBaseUrl() + configProvider.getConfig().getMappings().get(method.getName());
        var returnType = method.getReturnType();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(MessageFormat.format(url, args)))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<?> response = client.send(request, new HttpResponse.BodyHandler<Boolean>() {
            @Override
            public HttpResponse.BodySubscriber<Boolean> apply(HttpResponse.ResponseInfo responseInfo) {
                return null;
            }
        });
        return response.body();
    }
}
