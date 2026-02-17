import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CourierTests {
    // аннотация BeforeEach показывает, что метод будет выполняться перед каждым тестовым методом
    @BeforeEach
    public void setUp() {
        // повторяющуюся для разных ручек часть URL лучше записать в переменную в методе Before
        // если в классе будет несколько тестов, указывать её придётся только один раз
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test //курьера можно создать
    public void createCourier() {
        String json = "{\"login\": \"nini454\",\"password\": \"12345\",\"firstName\": \"ninini\"}";
                // метод given() помогает сформировать запрос
        Response response =
        given()
                .header("Content-type", "application/json")
                // указываем протокол и данные авторизации
                .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("ok", notNullValue())
                .and()
                .statusCode(201);
        System.out.println(response.body().asString());
    }
    @Test //нельзя создать двух одинаковых курьеров c одним логином
    public void createCourierOld() {
        String json = "{\"login\": \"nini8\",\"password\": \"12345\",\"firstName\": \"ninini\"}";
        Response response =
        // метод given() помогает сформировать запрос
        given()
                .header("Content-type", "application/json")
                // указываем протокол и данные авторизации
                .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("message", notNullValue())
                .and()
                .statusCode(409);
        System.out.println(response.body().asString());
    }
    @Test //чтобы создать курьера, нужно передать в ручку все обязательные поля.Передаем без логина и пароляц
    public void createCourierNotLoginAndPassword() {
        String json = "{\"firstName\": \"ninini\"}";
        Response response =
        // метод given() помогает сформировать запрос
        given()
                .header("Content-type", "application/json")
                // указываем протокол и данные авторизации
                .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("message", notNullValue())
                .and()
                .statusCode(400);
        System.out.println(response.body().asString());
    }
}
