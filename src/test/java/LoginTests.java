import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTests {
    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void createLogin() {
        String json = "{\"login\": \"nini9\",\"password\": \"12345\"}";
        Response response =
        given()
                .header("Content-type", "application/json")
                .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body("id", notNullValue()) //как вернуть ответ с айдишником
                .and()
                .statusCode(200);
    }
    @Test
    public void createLoginNotLoginAndPass() {
        String json = "{\"login\": \"\",\"password\": \"\"}";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().body("message", notNullValue()) //как вернуть ответ с айдишником
                .and()
                .statusCode(400);
    }
    @Test
    public void doesNotExistLoginAndPass() {
        String json = "{\"login\": \"buba\",\"password\": \"1488\"}";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().body("message", notNullValue()) //как вернуть ответ с айдишником
                .and()
                .statusCode(404);
    }
}
