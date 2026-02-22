import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierSteps {
    public static Response createCourierStep(Object body) {

        return given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("lih2TK06FLsu5HQ32PR6XzCsmg0GVPrL2seXkQVprx5FhRzNK8ArtT7u42RhqegQ")
                        .and()
                        .body(body)
                        .when()
                        .post("/api/v1/courier");
    }
    }
