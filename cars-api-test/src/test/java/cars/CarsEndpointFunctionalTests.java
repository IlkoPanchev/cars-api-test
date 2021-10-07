package cars;

import base.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.when;

public class CarsEndpointFunctionalTests extends BaseTest {

    @Test
    public void testGetResponse(){

        get("/cars").then().statusCode(200);
    }
}
