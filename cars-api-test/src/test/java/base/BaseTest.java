package base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import users.UserAddBindingModel;

import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    @BeforeAll
    public void setUpHost() {

//        RestAssured.baseURI = "https://cars-rest-jwt.herokuapp.com";
//        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.baseURI = "https://staging-cars-rest-jwt.herokuapp.com";

    }

    public UserAddBindingModel createUserAddBindingModel(String username, String password, String email) {

        UserAddBindingModel userAddBindingModel = new UserAddBindingModel();
        userAddBindingModel.setUsername(username);
        userAddBindingModel.setPassword(password);
        userAddBindingModel.setEmail(email);
        Set<String> roles = Set.of("ROLE_USER");
        userAddBindingModel.setRoles(roles);

        return  userAddBindingModel;
    }
}
