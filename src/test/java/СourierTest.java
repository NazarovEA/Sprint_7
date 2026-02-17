import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class СourierTest {
    // аннотация BeforeEach показывает, что метод будет выполняться перед каждым тестовым методом
    @BeforeEach
    public void setUp() {
        // повторяющуюся для разных ручек часть URL лучше записать в переменную в методе Before
        // если в классе будет несколько тестов, указывать её придётся только один раз
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    // создаём метод автотеста
    @Test
    public void getMyInfoStatusCode() {
        String json = "{\"login\": \"ninja\",\"password\": \"1234\",\"firstName\": \"saske\"}";
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
        response.then().assertThat().body("ok: true", notNullValue())
                .and()
                .statusCode(201);
    }
}
