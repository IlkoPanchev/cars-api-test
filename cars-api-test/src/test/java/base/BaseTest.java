package base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    @BeforeAll
    public void setUp(){
        RestAssured.baseURI = "https://cars-rest-jwt.herokuapp.com";
        RestAssured.basePath = "/api";
//        RestAssured.baseURI = "http://localhost:8080";
//        RestAssured.basePath = "/api";
    }
}
