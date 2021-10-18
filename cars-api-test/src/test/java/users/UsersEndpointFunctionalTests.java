package users;

import base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersEndpointFunctionalTests extends BaseTest {

    private UserLoginBindingModel userLoginBindingModel;
    private UserAddBindingModel userAddBindingModel;


    @BeforeAll
    public void setUp() {

        RestAssured.basePath = "/api";
        this.userLoginBindingModel = this.createUserLoginBindingModel();
        this.userAddBindingModel = super.createUserAddBindingModel("username_1",
                "password",
                "email_1@email.bg");
    }

    @Test
    @Order(1)
    public void testRegisterResponse(){

        given()
                .contentType("application/json")
                .body(this.userAddBindingModel)
                .when()
                .post("/users/register")
                .then()
                .statusCode(200)
                .assertThat()
                .body("username", equalTo("username_1"))
                .body("email", equalTo("email_1@email.bg"))
                .body("tokenType", equalTo("Bearer"));

    }

    @Test
    @Order(2)
    public void testRegisterResponseInvalidUsername(){

        this.userAddBindingModel.setUsername("u");

        given()
                .contentType("application/json")
                .body(this.userAddBindingModel)
                .when()
                .post("/users/register")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(3)
    public void testRegisterResponseInvalidPassword(){

        this.userAddBindingModel.setPassword("p");

        given()
                .contentType("application/json")
                .body(this.userAddBindingModel)
                .when()
                .post("/users/register")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(4)
    public void testRegisterResponseInvalidEmail(){

        this.userAddBindingModel.setEmail("email.bg");

        given()
                .contentType("application/json")
                .body(this.userAddBindingModel)
                .when()
                .post("/users/register")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(5)
    public void testRegisterResponseExistingUsername(){

        this.userAddBindingModel.setUsername("username_1");

        given()
                .contentType("application/json")
                .body(this.userAddBindingModel)
                .when()
                .post("/users/register")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(6)
    public void testRegisterResponseExistingEmail(){

        this.userAddBindingModel.setEmail("emai_1@email.bg");

        given()
                .contentType("application/json")
                .body(this.userAddBindingModel)
                .when()
                .post("/users/register")
                .then()
                .statusCode(400);

    }


    @Test
    @Order(7)
    public void testLoginResponse() {

        given()
                .contentType("application/json")
                .body(this.userLoginBindingModel)
                .when()
                .post("/users/login")
                .then()
                .statusCode(200)
                .assertThat()
                .body("username", equalTo("username_1"))
                .body("email", equalTo("email_1@email.bg"))
                .body("tokenType", equalTo("Bearer"));

    }

    @Test
    @Order(8)
    public void testLoginResponseInvalidUsername() {

        this.userLoginBindingModel.setUsername("");

        given()
                .contentType("application/json")
                .body(this.userLoginBindingModel)
                .when()
                .post("/users/login")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(9)
    public void testLoginResponseInvalidPassword() {

        this.userLoginBindingModel.setPassword("");

        given()
                .contentType("application/json")
                .body(this.userLoginBindingModel)
                .when()
                .post("/users/login")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(10)
    public void testLoginResponseWrongUsername() {

        this.userLoginBindingModel.setUsername("WrongUsername");

        given()
                .contentType("application/json")
                .body(this.userLoginBindingModel)
                .when()
                .post("/users/login")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(11)
    public void testLoginResponseWrongPassword() {

        this.userLoginBindingModel.setPassword("WrongPassword");

        given()
                .contentType("application/json")
                .body(this.userLoginBindingModel)
                .when()
                .post("/users/login")
                .then()
                .statusCode(401);

    }

    @Test
    @Order(12)
    public void testLogoutUser(){

        this.userLoginBindingModel.setUsername("username_1");
        this.userLoginBindingModel.setPassword("password");

        Response responseLogin = given()
                .contentType("application/json")
                .body(this.userLoginBindingModel)
                .when()
                .post("/users/login")
                .then()
                .extract().response();

        String token = responseLogin.jsonPath().getString("accessToken");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/users/logout")
                .then()
                .statusCode(200);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/users/logout")
                .then()
                .statusCode(401);
    }

    private UserLoginBindingModel createUserLoginBindingModel() {

        UserLoginBindingModel userLoginBindingModel = new UserLoginBindingModel();
        userLoginBindingModel.setUsername("username_1");
        userLoginBindingModel.setPassword("password");

        return userLoginBindingModel;
    }

}
