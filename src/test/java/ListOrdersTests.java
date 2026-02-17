import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ListOrdersTests {

    // аннотация BeforeEach показывает, что метод будет выполняться перед каждым тестовым методом
    @BeforeEach
    public void setUp() {
        // повторяющуюся для разных ручек часть URL лучше записать в переменную в методе Before
        // если в классе будет несколько тестов, указывать её придётся только один раз
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test //успешное список заказов 200
    public void successRE() {
        Response response =
                (Response) given()
                        .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                        .get("/api/v1/orders");
        response.then().assertThat().body("orders", notNullValue())
                        .statusCode(200);
        System.out.println(response.body().asString());
    }
}