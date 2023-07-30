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
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.containers.Network
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

@Testcontainers
class CustomContainerSpec extends Specification{

    static GenericContainer API
    static MariaDBContainer DB
    static Network network
    static String apiHost
    static String apiPort

    ObjectMapper objectMapper = new ObjectMapper()

    def setupSpec() {
        network = Network.newNetwork()
        DB = new MariaDBContainer<>("mariadb:10.6.13")
                .withUsername("sbtc")
                .withPassword("password")
                .withDatabaseName("sbtc")
                .withNetworkAliases("mariadb")
                .withExposedPorts(3306)
                .waitingFor(Wait.forListeningPort())
                .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("mariadb")))
                .withNetwork(network)

        API = new GenericContainer<>(DockerImageName.parse("sbtc:latest"))
                .withExposedPorts(8080)
                .withNetwork(network)
                .withNetworkAliases("api")
                .waitingFor(Wait.forListeningPort())
                .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("api")))
                .dependsOn(DB)

        DB.start()
        API.start()

        apiHost = API.getHost()
        apiPort = API.getMappedPort(8080).toString()
    }

    def cleanupSpec(){
        API.close()
        DB.close()
        network.close()
    }

    def "test save student API contract"() {
        given: "context is loaded"

        when: "request is triggered"
        RestTemplate restTemplate = new RestTemplate()
        ResponseEntity<JsonNode> response = restTemplate.exchange("http://"+ apiHost +":" + apiPort + "/student/",
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
