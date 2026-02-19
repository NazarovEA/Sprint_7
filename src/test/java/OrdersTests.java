import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersTests {
    // аннотация BeforeEach показывает, что метод будет выполняться перед каждым тестовым методом
    @BeforeEach
    public void setUp() {
        // повторяющуюся для разных ручек часть URL лучше записать в переменную в методе Before
        // если в классе будет несколько тестов, указывать её придётся только один раз
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test //успешное создание заказа 201 (c цветом black)
    public void successfulOrderCreateOneColor() {
        String json = "{\"firstName\": \"Naruto\",\"lastName\": \"Uchiha\",\"address\": \"Konoha, 142 apt.\",\"metroStation\": 4,\"phone\": \"+7 800 355 35 35\",\"rentTime\": 5,\"deliveryDate\": \"2020-06-06\",\"comment\": \"Saske, come back to Konoha\",\"color\": [\"BLACK\"]}";
        Response response =
                // метод given() помогает сформировать запрос
                given()
                        .header("Content-type", "application/json")
                        // указываем протокол и данные авторизации
                        .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue()) //как вернуть ответ с айдишником
                .and()
                .statusCode(201);
        System.out.println(response.body().asString());
    }
    @Test //успешное создание заказа 201 (c цветом black and grey)
    public void successfulOrderCreateTwoColors() {
        String json = "{\"firstName\": \"Naruto\",\"lastName\": \"Uchiha\",\"address\": \"Konoha, 142 apt.\",\"metroStation\": 4,\"phone\": \"+7 800 355 35 35\",\"rentTime\": 5,\"deliveryDate\": \"2020-06-06\",\"comment\": \"Saske, come back to Konoha\",\"color\": [\"BLACK\", \"GREY\"]}";
        Response response =
                // метод given() помогает сформировать запрос
                given()
                        .header("Content-type", "application/json")
                        // указываем протокол и данные авторизации
                        .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue()) //как вернуть ответ с айдишником
                .and()
                .statusCode(201);
        System.out.println(response.body().asString());
    }
    @Test //успешное создание заказа 201 (без цвета)
    public void successfulOrderCreateNotColors() {
        String json = "{\"firstName\": \"Naruto\",\"lastName\": \"Uchiha\",\"address\": \"Konoha, 142 apt.\",\"metroStation\": 4,\"phone\": \"+7 800 355 35 35\",\"rentTime\": 5,\"deliveryDate\": \"2020-06-06\",\"comment\": \"Saske, come back to Konoha\",\"color\": []}";
        Response response =
                // метод given() помогает сформировать запрос
                given()
                        .header("Content-type", "application/json")
                        // указываем протокол и данные авторизации
                        .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
        System.out.println(response.body().asString());
    }
}