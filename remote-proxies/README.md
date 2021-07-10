# Implementing remote proxies in java

This is an example for a blog post published on [jvmaware.com](https://jvmaware.com/remote-proxy/) describing how to implement remote proxy 
pattern in java.
 
The example includes the following components:

1. **service-contract**: An API contract between the service provider and the consumer.
2. **service-impl**: A demo implementation of the service-contract. This represents a remote service which is available for consumption.
3. **service-consumer**: The client, that invokes the remote **service-impl** using the remote proxies.

Please refer to the blog post for more details.