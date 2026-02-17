import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTests {
    // аннотация BeforeEach показывает, что метод будет выполняться перед каждым тестовым методом
    @BeforeEach
    public void setUp() {
        // повторяющуюся для разных ручек часть URL лучше записать в переменную в методе Before
        // если в классе будет несколько тестов, указывать её придётся только один раз
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test //успешный логин с 200, но без номера id
    public void createLogin() {
        String json = "{\"login\": \"nini9\",\"password\": \"12345\"}";
        Response response =
        // метод given() помогает сформировать запрос
        given()
                .header("Content-type", "application/json")
                // указываем протокол и данные авторизации
                .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body("id", notNullValue()) //как вернуть ответ с айдишником
                .and()
                .statusCode(200);
        System.out.println(response.body().asString());
    }
    @Test //запрос без логина и пароля
    public void createLoginNotLoginAndPass() {
        String json = "{\"login\": \"\",\"password\": \"\"}";
        Response response =
                // метод given() помогает сформировать запрос
                given()
                        .header("Content-type", "application/json")
                        // указываем протокол и данные авторизации
                        .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().body("message", notNullValue()) //как вернуть ответ с айдишником
                .and()
                .statusCode(400);
        System.out.println(response.body().asString());
    }
    @Test //запрос с несуществующими длгином и паролем 404
    public void doesNotExistLoginAndPass() {
        String json = "{\"login\": \"buba\",\"password\": \"1488\"}";
        Response response =
                // метод given() помогает сформировать запрос
                given()
                        .header("Content-type", "application/json")
                        // указываем протокол и данные авторизации
                        .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().assertThat().body("message", notNullValue()) //как вернуть ответ с айдишником
                .and()
                .statusCode(404);
        System.out.println(response.body().asString());
    }
}
