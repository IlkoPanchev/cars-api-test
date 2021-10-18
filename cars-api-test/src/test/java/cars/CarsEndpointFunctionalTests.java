package cars;

import base.BaseTest;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import users.UserAddBindingModel;
import users.UserLoginBindingModel;

import java.math.BigDecimal;
import java.util.Set;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.number.OrderingComparison.greaterThan;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarsEndpointFunctionalTests extends BaseTest {

    private String token;

    @BeforeAll
    public void setUp() {

        RestAssured.basePath = "/api";
        //login user
        UserAddBindingModel userAddBindingModel = super.createUserAddBindingModel("username",
                "password",
                "email@email.bg");

        Response responseLogin = given()

                .contentType("application/json")
                .body(userAddBindingModel)
                .when()
                .post("/users/register")
                .then()
                .extract().response();

        this.token = responseLogin.jsonPath().getString("accessToken");
    }

    @AfterAll
    public void clear() {
        //logout user
        given()
                .header("Authorization", "Bearer " + this.token)
                .post("/users/logout");

    }

    //test createCar()

    @Test
    @Order(1)
    public void testCreateCarResponse() {

        Response response = given()
//                .log().all()
                .header("Authorization", "Bearer " + this.token)
                .param("brand", "Audi")
                .param("model", "A3")
                .param("description", "Description")
                .param("year", "2021")
                .param("price", "10000")
                .when()
                .post("/cars")
                .then()
//                .log().all()
                .extract().response();

        response.then().statusCode(201).assertThat().body("model", equalTo("A3"));


    }

    @Test
    @Order(2)
    public void testCreateCarResponseWithInvalidParamBrand() {

       given()
                .header("Authorization", "Bearer " + this.token)
                .param("brand", "")
                .param("model", "A3")
                .param("description", "Description")
                .param("year", "2021")
                .param("price", "10000")
                .when()
                .post("/cars")
                .then()
               .statusCode(400);

    }

    @Test
    @Order(3)
    public void testCreateCarResponseWithInvalidParamModel() {

        given()
                .header("Authorization", "Bearer " + this.token)
                .param("brand", "Audi")
                .param("model", "")
                .param("description", "Description")
                .param("year", "2021")
                .param("price", "10000")
                .when()
                .post("/cars")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(4)
    public void testCreateCarResponseWithInvalidParamDescription() {

        given()
                .header("Authorization", "Bearer " + this.token)
                .param("brand", "Audi")
                .param("model", "A3")
                .param("description", "")
                .param("year", "2021")
                .param("price", "10000")
                .when()
                .post("/cars")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(5)
    public void testCreateCarResponseWithInvalidParamYear() {

        given()
                .header("Authorization", "Bearer " + this.token)
                .param("brand", "Audi")
                .param("model", "A3")
                .param("description", "Description")
                .param("year", "1949")
                .param("price", "10000")
                .when()
                .post("/cars")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(6)
    public void testCreateCarResponseWithInvalidParamPrice() {

        given()
                .header("Authorization", "Bearer " + this.token)
                .param("brand", "Audi")
                .param("model", "A3")
                .param("description", "Description")
                .param("year", "1949")
                .param("price", "-1")
                .when()
                .post("/cars")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(7)
    public void testCreateCarResponseWithoutAuthorizationHeader() {

        given()
                .header("Content-Type", "application/json")
                .param("brand", "Audi")
                .param("model", "A3")
                .param("description", "Description")
                .param("year", "2021")
                .param("price", "10000")
                .when()
                .post("/cars")
                .then()
                .statusCode(401);

    }

    @Test
    @Order(8)
    public void testCreateCarResponseWithoutJwtToken() {

        given()
                .header("Authorization", "Bearer ")
                .param("brand", "Audi")
                .param("model", "A3")
                .param("description", "Description")
                .param("year", "2021")
                .param("price", "10000")
                .when()
                .post("/cars")
                .then()
                .statusCode(401);

    }

    @Test
    @Order(9)
    public void testCreateCarResponseWithInvalidJwtToken() {

        given()
                .header("Authorization", "Bearer " + this.token + "invalid")
                .param("brand", "Audi")
                .param("model", "A3")
                .param("description", "Description")
                .param("year", "2021")
                .param("price", "10000")
                .when()
                .post("/cars")
                .then()
                .statusCode(401);

    }

    //test getCar()
    @Test
    @Order(10)
    public void testGetCarResponse() {

        Response response = given()
                .pathParam("id", "1")
                .when()
                .get("/cars/{id}")
                .then()
                .extract().response();

        response.then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(1))
                .body("model", equalTo("A3"));


    }

    @Test
    @Order(11)
    public void testGetCarResponseWhenNoSuchId() {

        Response response = given()
                .pathParam("id", "100")
                .when()
                .get("/cars/{id}")
                .then()
                .extract().response();

        response.then()
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Not found car with id: 100"));


    }

    // test deleteCar()
    @Test
    @Order(12)
    public void testUpdateCarResponse() {

        Response response = given()
                .header("Authorization", "Bearer " + this.token)
                .pathParam("id", "1")
                .param("brand", "Update")
                .param("model", "A3")
                .param("description", "Description")
                .param("year", "2021")
                .param("price", "10000")
                .when()
                .put("/cars/{id}")
                .then()
//                .log().all()
                .extract().response();

        response.then().statusCode(200);

    }

    @Test
    @Order(13)
    public void testUpdateCarResponseWithInvalidParamBrand() {

        given()
                .header("Authorization", "Bearer " + this.token)
                .pathParam("id", "1")
                .param("brand", "")
                .param("model", "A3")
                .param("description", "Description")
                .param("year", "2021")
                .param("price", "10000")
                .when()
                .put("/cars/{id}")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(14)
    public void testUpdateCarResponseWithInvalidParamModel() {

        given()
                .header("Authorization", "Bearer " + this.token)
                .pathParam("id", "1")
                .param("brand", "Update")
                .param("model", "")
                .param("description", "Description")
                .param("year", "2021")
                .param("price", "10000")
                .when()
                .put("/cars/{id}")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(15)
    public void testUpdateCarResponseWithInvalidParamDescription() {

        given()
                .header("Authorization", "Bearer " + this.token)
                .pathParam("id", "1")
                .param("brand", "Update")
                .param("model", "A3")
                .param("description", "")
                .param("year", "2021")
                .param("price", "10000")
                .when()
                .put("/cars/{id}")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(16)
    public void testUpdateCarResponseWithInvalidParamYear() {

        given()
                .header("Authorization", "Bearer " + this.token)
                .pathParam("id", "1")
                .param("brand", "Update")
                .param("model", "A3")
                .param("description", "Description")
                .param("year", "1949")
                .param("price", "10000")
                .when()
                .put("/cars/{id}")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(17)
    public void testUpdateCarResponseWithInvalidParamPrice() {

        given()
                .header("Authorization", "Bearer " + this.token)
                .pathParam("id", "1")
                .param("brand", "Update")
                .param("model", "A3")
                .param("description", "Description")
                .param("year", "2021")
                .param("price", "-1")
                .when()
                .put("/cars/{id}")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(18)
    public void testUpdateCarResponseWithoutAuthorizationHeader() {

       given()
                .header("Content-Type", "application/json")
                .pathParam("id", "1")
                .param("brand", "Update")
                .param("model", "A3")
                .param("description", "Description")
                .param("year", "2021")
                .param("price", "10000")
                .when()
                .put("/cars/{id}")
                .then()
               .statusCode(401);



    }

    @Test
    @Order(19)
    public void testUpdateCarResponseWhenNoSuchId() {

        Response response = given()
                .header("Authorization", "Bearer " + this.token)
                .pathParam("id", "100")
                .param("brand", "Update")
                .param("model", "A3")
                .param("description", "Description")
                .param("year", "2021")
                .param("price", "10000")
                .when()
                .put("/cars/{id}")
                .then()
                .extract().response();

        response.then()
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Not found car with id: 100"));

    }

    // test deleteCar()
    @Test
    @Order(20)
    public void testDeleteCar() {

        Response response = given()
                .header("Authorization", "Bearer " + this.token)
                .param("brand", "BMW")
                .param("model", "M3")
                .param("description", "Description")
                .param("year", "2021")
                .param("price", "10000")
                .when()
                .post("/cars")
                .then()
//                .log()
//                .all()
                .extract().response();

        String id = response.jsonPath().getString("id");

        Response responseCreateCar = given()
                .header("Authorization", "Bearer " + token)
                .pathParam("id", id)
                .when()
                .delete("/cars/{id}")
                .then()
                .extract().response();

        responseCreateCar.then().statusCode(200);


    }

    @Test
    @Order(21)
    public void testDeleteCarWhenNoSuchId() {


        Response response = given()
                .header("Authorization", "Bearer " + token)
                .pathParam("id", "100")
                .when()
                .delete("/cars/{id}")
                .then()
                .extract().response();


        response.then()
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Not found car with id: 100"));

    }


    //test getCarsCount();
    @Test
    @Order(22)
    public void testGetCarsCountResponseWithoutRequestParams() {

        given().when()
                .get("/cars/count")
                .then().statusCode(200)
                .assertThat().body(equalTo("1"));
        ;
    }

    @Test
    @Order(23)
    public void testGetCarsCountResponseWithOwnerIdRequestParam() {

        given().queryParam("ownerId", "2")
                .when()
                .get("/cars/count")
                .then().statusCode(200)
                .assertThat().body(equalTo("1"));
    }

    @Test
    @Order(24)
    public void testGetCarsCountResponseWithOwnerIdRequestParamWhenNoSuchOwnerId() {

        Response response =  given().queryParam("ownerId", "100")
                .when()
                .get("/cars/count")
                .then().statusCode(200)
                .extract().response();

        String count = response.body().asString();
        Assertions.assertEquals("0", count);
    }

    @Test
    @Order(25)
    public void testGetCarsCountResponseWithKeywordRequestParam() {

        given().queryParam("keyword", "Update")
                .when()
                .get("/cars/count")
                .then().statusCode(200)
                .assertThat().body(equalTo("1"));
    }

    @Test
    @Order(26)
    public void testGetCarsCountResponseWithKeywordRequestParamWhenNoMatch() {

        Response response =  given().queryParam("keyword", "NoMatch")
                .when()
                .get("/cars/count")
                .then().statusCode(200)
                .extract().response();

        String count = response.body().asString();
        Assertions.assertEquals("0", count);

    }


    //test getAllCars()
    @Test
    @Order(27)
    public void testGetAllCarsResponseWithoutRequestParams() {

        given().log().all().when()
                .get("/cars")
                .then()
//                .log().all()
                .statusCode(200)
                .assertThat().body("size()", greaterThan(0));
        ;
    }

    @Test
    @Order(28)
    public void testGetAllCarsResponseWithOwnerIdRequestParam() {

        given().queryParam("ownerId", "2")
                .when()
                .get("/cars")
                .then()
                .statusCode(200)
                .assertThat().body("size()", is(1));
        ;
    }

    @Test
    @Order(29)
    public void testGetAllCarsResponseWithOwnerIdRequestParamWhenNoSuchOwner() {

        Response response = given()
                .queryParam("ownerId", "100")
                .when()
                .get("/cars")
                .then()
                .extract().response();

        String responseBody = response.body().asString();
        Assertions.assertEquals("[]", responseBody);
        ;
    }

    @Test
    @Order(30)
    public void testGetAllCarsResponseWithKeywordRequestParam() {

        given().queryParam("keyword", "Update")
                .when()
                .get("/cars")
                .then()
                .statusCode(200)
                .assertThat().body("size()", is(1));
        ;
    }

    @Test
    @Order(31)
    public void testGetAllCarsResponseWithKeywordRequestParamWhenNoMatch() {

        Response response = given().queryParam("keyword", "NoMatch")
                .when()
                .get("/cars")
                .then()
                .extract().response();;

        String responseBody = response.body().asString();
        Assertions.assertEquals("[]", responseBody);
        ;
    }

    @Test
    @Order(32)
    public void testGetAllCarsResponseWithPageSizeRequestParam() {

        given().header("Authorization", "Bearer " + this.token)
                .param("brand", "Fiat")
                .param("model", "Sedici")
                .param("description", "Description")
                .param("year", "2021")
                .param("price", "10000")
                .when()
                .post("/cars");

        given().queryParam("pageSize", "1")
                .when()
                .get("/cars")
                .then()
                .statusCode(200)
                .assertThat().body("size()", is(1));
        ;
    }

}
