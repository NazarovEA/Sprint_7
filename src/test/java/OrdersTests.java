import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersTests {
    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void successfulOrderCreateOneColor() {
        String json = "{\"firstName\": \"Naruto\",\"lastName\": \"Uchiha\",\"address\": \"Konoha, 142 apt.\",\"metroStation\": 4,\"phone\": \"+7 800 355 35 35\",\"rentTime\": 5,\"deliveryDate\": \"2020-06-06\",\"comment\": \"Saske, come back to Konoha\",\"color\": [\"BLACK\"]}";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue()) //как вернуть ответ с айдишником
                .and()
                .statusCode(201);
    }
    @Test
    public void successfulOrderCreateTwoColors() {
        String json = "{\"firstName\": \"Naruto\",\"lastName\": \"Uchiha\",\"address\": \"Konoha, 142 apt.\",\"metroStation\": 4,\"phone\": \"+7 800 355 35 35\",\"rentTime\": 5,\"deliveryDate\": \"2020-06-06\",\"comment\": \"Saske, come back to Konoha\",\"color\": [\"BLACK\", \"GREY\"]}";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue()) //как вернуть ответ с айдишником
                .and()
                .statusCode(201);
    }
    @Test
    public void successfulOrderCreateNotColors() {
        String json = "{\"firstName\": \"Naruto\",\"lastName\": \"Uchiha\",\"address\": \"Konoha, 142 apt.\",\"metroStation\": 4,\"phone\": \"+7 800 355 35 35\",\"rentTime\": 5,\"deliveryDate\": \"2020-06-06\",\"comment\": \"Saske, come back to Konoha\",\"color\": []}";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }
}