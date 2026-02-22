import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.CreateCourierModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierTests {

    @BeforeEach
    public void setUp() {
            RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void createCourier() {
        CreateCourierModel createCourierModel = new CreateCourierModel();
        createCourierModel.setLogin("Login_" + System.currentTimeMillis());
        createCourierModel.setPassword("P@ss123");
        createCourierModel.setFirstName("Ivan");

        Response response =
        given()
                .header("Content-type", "application/json")
                .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                .and()
                .body(createCourierModel)
                .when()
                .post("/api/v1/courier");
        response.then()
                .assertThat().body("ok", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    public void createCourierOld() {
        String name = "Login_" + System.currentTimeMillis();
        String json = "{\"login\": \"" + name + "\",\"password\": \"12345\",\"firstName\": \"ninini\"}";
        Response response =
        given()
                .header("Content-type", "application/json")
                .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
        response.then()
                .assertThat().body("ok", notNullValue())
                .and()
                .statusCode(201);
        Response response2 =
        given()
                .header("Content-type", "application/json")
                .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
        response2.then()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }


    @Test
    public void createCourierNotLoginAndPassword() {
        String json = "{\"firstName\": \"ninini\"}";
        Response response =
        given()
                .header("Content-type", "application/json")
                .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("message", notNullValue())
                .and()
                .statusCode(400);
    }

    @Test
    public void createCourier2() {
        CreateCourierModel createCourierModel = new CreateCourierModel();
        createCourierModel.setLogin("Login_" + System.currentTimeMillis());
        createCourierModel.setPassword("P@ss123");
        createCourierModel.setFirstName("Ivan");
        Response response = CourierSteps.createCourierStep(createCourierModel);
        response.then()
                .assertThat().body("ok", notNullValue())
                .and()
                .statusCode(201);
    }
}
