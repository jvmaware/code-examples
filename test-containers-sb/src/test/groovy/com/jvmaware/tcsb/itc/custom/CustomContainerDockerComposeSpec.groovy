package com.jvmaware.tcsb.itc.custom

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import java.time.Duration

/**
 * A few implementation details for this test class:
 *
 * <ol>
 *     <li>@SpringBootTest</li>
 * </ol>
 *
 */
@Testcontainers
class CustomContainerDockerComposeSpec extends Specification{

    ObjectMapper objectMapper = new ObjectMapper()

    @Shared
    DockerComposeContainer environment = new DockerComposeContainer(new File("docker-compose.yml"))
            .withLocalCompose(true)
            .withExposedService("mariadb", 1,3306, Wait.forListeningPort().withStartupTimeout(Duration.ofMinutes(5)))
            .withLogConsumer("mariadb", new Slf4jLogConsumer(LoggerFactory.getLogger("mariadb")))
            .withExposedService("sbtc",1, 8080, Wait.forListeningPort())
            .withLogConsumer("sbtc", new Slf4jLogConsumer(LoggerFactory.getLogger("sbtc")))



    def "test save student API contract"() {
        given: "context is loaded"
        def hostPort = "http://${environment.getServiceHost("sbtc", 8080)}:${environment.getServicePort("sbtc", 8080)}"

        when: "request is triggered"
        RestTemplate restTemplate = new RestTemplate()
        ResponseEntity<JsonNode> response = restTemplate.exchange(hostPort + "/student/",
                HttpMethod.POST,
                httpEntity(),
                JsonNode.class)

        then: "correct response is returned"
        response.getStatusCode() == HttpStatus.CREATED
        def jsonNode = response.getBody()
        jsonNode.get("id").asInt() > 0
        jsonNode.get("firstName").asText() == "jvm"
        jsonNode.get("lastName").asText() == "aware"

        jsonNode.get("address").get("house").asLong() == 101L
        jsonNode.get("address").get("street").asText() == "jvmlane"
        jsonNode.get("address").get("city").asText() == "jvmverse"
    }

    def "test fetching student record"() {
        given: "context is loaded"
        def hostPort = "http://${environment.getServiceHost("sbtc", 8080)}:${environment.getServicePort("sbtc", 8080)}"

        when: "request is triggered"
        RestTemplate restTemplate = new RestTemplate()
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(hostPort + "/student/1", JsonNode)

        then: "correct response is returned"
        response.getStatusCode() == HttpStatus.OK
        def jsonNode = response.getBody()
        jsonNode.get("id").asInt() == 1
        jsonNode.get("firstName").asText() == "jvm"
        jsonNode.get("lastName").asText() == "aware"

        jsonNode.get("address").get("house").asLong() == 101L
        jsonNode.get("address").get("street").asText() == "jvmlane"
        jsonNode.get("address").get("city").asText() == "jvmverse"
    }

    private HttpEntity<JsonNode> httpEntity(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<JsonNode> requestEntity = new HttpEntity<>(prepareStudent(), httpHeaders)

        return requestEntity
    }

    private JsonNode prepareStudent(){
        var student = objectMapper.createObjectNode()

        student.put("firstName", "jvm")
        student.put("lastName", "aware")

        var address = objectMapper.createObjectNode()
        address.put("house", 101)
        address.put("street", "jvmlane")
        address.put("city", "jvmverse")
        student.set("address", address)

        return student
    }
}
