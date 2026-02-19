import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.CreateCourierModel;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierTests {
    Random random = new Random();
    // аннотация BeforeEach показывает, что метод будет выполняться перед каждым тестовым методом
    @BeforeEach
    public void setUp() {
        // повторяющуюся для разных ручек часть URL лучше записать в переменную в методе Before
        // если в классе будет несколько тестов, указывать её придётся только один раз
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test //курьера можно создать
    public void createCourier() {
        CreateCourierModel createCourierModel = new CreateCourierModel();
        // Генерируем уникальный логин, чтобы не было конфликтов
        createCourierModel.setLogin("Login_" + System.currentTimeMillis());
        createCourierModel.setPassword("P@ss123");
        createCourierModel.setFirstName("Ivan");
        //String json = "{\"login\": \"nini48954\",\"password\": \"12345\",\"firstName\": \"ninini\"}";
                // метод given() помогает сформировать запрос
        Response response =
        given()
                //.log().all() // Лог запроса
                .header("Content-type", "application/json")
                // указываем протокол и данные авторизации
                .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                .and()
                .body(createCourierModel)
                .when()
                .post("/api/v1/courier");
        response.then()
                //.log().all() // Лог запроса
                .assertThat().body("ok", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test //нельзя создать двух одинаковых курьеров c одним логином
    public void createCourierOld() {
        String name = "Login_" + System.currentTimeMillis();
        String json = "{\"login\": \"" + name + "\",\"password\": \"12345\",\"firstName\": \"ninini\"}";
        Response response =
        // метод given() помогает сформировать запрос
        given()
                //.log().all()
                .header("Content-type", "application/json")
                // указываем протокол и данные авторизации
                .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
        response.then()
                //.log().all()
                .assertThat().body("ok", notNullValue())
                .and()
                .statusCode(201);
        Response response2 =
        given()
                //.log().all()
                .header("Content-type", "application/json")
                // указываем протокол и данные авторизации
                .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
        response2.then()
                //.log().all()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
        System.out.println(response2.body().asString());
    }


    @Test //чтобы создать курьера, нужно передать в ручку все обязательные поля.Передаем без логина и пароля
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

    @Test //курьера можно создать
    public void createCourier2() {
        CreateCourierModel createCourierModel = new CreateCourierModel();
        // Генерируем уникальный логин, чтобы не было конфликтов
        createCourierModel.setLogin("Login_" + System.currentTimeMillis());
        createCourierModel.setPassword("P@ss123");
        createCourierModel.setFirstName("Ivan");
        //String json = "{\"login\": \"nini48954\",\"password\": \"12345\",\"firstName\": \"ninini\"}";
        // метод given() помогает сформировать запрос
        // Вызываем степ и сохраняем результат в переменную response
        Response response = CourierSteps.createCourierStep(createCourierModel);
        response.then()
                //.log().all() // Лог запроса
                .assertThat().body("ok", notNullValue())
                .and()
                .statusCode(201);
        System.out.println(response.body().asString());
    }
}
