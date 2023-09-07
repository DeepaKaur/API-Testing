package petclinic.pets;

import org.apache.http.client.methods.HttpPost;
import org.junit.Test;
import petclinic.model.PetTypes;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PetApiTests {
    @Test
    public void getPetTypes() {
        String endpoint = "http://localhost:9966/petclinic/api/pettypes";
        var response = given()
                .when()
                .get(endpoint)
                .then()
                .log()
                .headers()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body("JSONArray.size()", greaterThan(0)) //check that at least one pettype is returned
                .body("id", everyItem(notNullValue())) //check that every item has value
                .body("name", everyItem(notNullValue())) //check that every item has value
                ;

        // response.log().body();
    }

    @Test
    public void getSerialisedPetType() {
        String endpoint = "http://localhost:9966/petclinic/api/pettypes/4";
        //id:4, name:coockatoo
        PetTypes expectedPetType = new PetTypes(4, "coockatoo");
        PetTypes actualPetType =
                given()
                .when()
                .get(endpoint)
                .as(PetTypes.class);

        assertThat(actualPetType, samePropertyValuesAs(expectedPetType));
    }

    @Test
    public void createNewPetType() {
        String endpoint = "http://localhost:9966/petclinic/api/pettypes";
        String body = "{\n" +
                "                         \"id\":11,\n" +
                "                         \"name\":\"test11\"\n" +
                "                        }";

        var response = given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .assertThat()
                .statusCode(201)
                .body("name", equalTo("test11"));

        response.log().body();
    }

    @Test
    public void updateExistingPetType() {
        String endpoint = "http://localhost:9966/petclinic/api/pettypes/5";
        String body = "{\n" +
                "  \"id\": 5,\n" +
                "  \"name\": \"snakjjje\"\n" +
                "}";
        var response = given()
                .contentType("application/json")
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .assertThat()
                .statusCode(204);
        response.log().body();
    }

    @Test
    public void deleteExistingPetType() {
        String endpoint = "http://localhost:9966/petclinic/api/pettypes/3";
        var response = given()
                .when()
                .delete(endpoint)
                .then();
        response.log().body();
    }

    @Test
    public void createSerialisedPetTypes() {
        String endpoint = "http://localhost:9966/petclinic/api/pettypes";
        PetTypes pettype = new PetTypes(
                "mynewpettype"
        );
        var response = given()
                .contentType("application/json")
                .body(pettype)
                .when()
                .post(endpoint)
                .then();
        response.log().body();
    }

    @Test
    public void updatePetTypeWithConstructor() {
        String endpoint = "http://localhost:9966/petclinic/api/pettypes/10";

        PetTypes pettype = new PetTypes(
                "myupdatedpettype"
        );
        var response = given()
                .contentType("application/json")
                .body(pettype)
                .when()
                .put(endpoint)
                .then();
        response.log().body();
    }
}
