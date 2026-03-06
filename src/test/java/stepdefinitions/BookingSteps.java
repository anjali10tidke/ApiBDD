package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.TestContext;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class BookingSteps {



        Response response ;
    @Given("user has a valid auth token")
    public void generateToken() {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        response = given()
                .contentType("application/json")
                .body("{\"username\":\"admin\",\"password\":\"password123\"}")
                .post("/auth");

        System.out.println("STATUS: " + response.statusCode());
        System.out.println("BODY: " + response.asString());

        TestContext.token = response.jsonPath().getString("token");

        assertEquals(response.statusCode(), 200);
    }

    @When("user creates a new booking")
    public void createBooking() {

        response = given()
                .contentType("application/json")
                .body("{\"firstname\":\"Anjali\",\"lastname\":\"BDD\",\"totalprice\":111,\"depositpaid\":true,\"bookingdates\":{\"checkin\":\"2026-03-01\",\"checkout\":\"2026-03-10\"},\"additionalneeds\":\"Breakfast\"}")
                .post("/booking");

        System.out.println("CREATE STATUS: " + response.statusCode());
        System.out.println("CREATE BODY: " + response.asString());

        TestContext.bookingId = response.jsonPath().getInt("bookingid");

        assertEquals(response.statusCode(), 200);
    }

        @When("user updates the booking using PATCH")
                public void patchBooking()
        {
            response = given()
                    .contentType("application/json")
                    .header("Cookie","token=" + TestContext.token)
                    .body("{\"additionalneeds\":\"lunch\"}")
                    .when()
                    .patch("/booking/"+ TestContext.bookingId);

            assertEquals(response.statusCode(),200);
        }

        @Then("user fetches the booking by id")
        public void getBooking()
        {
            response = given()
                    .when()
                    .get("/booking/" + TestContext.bookingId);

            assertEquals(response.statusCode(),200);
        }

        @Then("user deletes the booking")

                public void deleteBooking()
        {
            response = given()
                    .header("Cookie","token=" + TestContext.token)
                    .when()
                    .delete("/booking/"+ TestContext.bookingId);

            assertEquals(response.statusCode(),201);
        }

        @Then("booking should not exist anymore")

                public void verifyBookingdeleted()
        {
            response = given()
                    .when()
                    .get("/booking/"+ TestContext.bookingId);

            assertEquals(response.statusCode(),404);
        }

    @When("user creates booking with {string} and {string}")
    public void user_creates_booking_with_and(String firstname, String lastname) {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        String requestBody = "{\n" +
                "\"firstname\": \"" + firstname + "\",\n" +
                "\"lastname\": \"" + lastname + "\",\n" +
                "\"totalprice\": 111,\n" +
                "\"depositpaid\": true,\n" +
                "\"bookingdates\": {\n" +
                "    \"checkin\": \"2026-03-01\",\n" +
                "    \"checkout\": \"2026-03-10\"\n" +
                "},\n" +
                "\"additionalneeds\": \"Breakfast\"\n" +
                "}";

        response = given()
                .contentType("application/json")
                .body(requestBody)
                .post("/booking");

        System.out.println("STATUS: " + response.statusCode());
        System.out.println("BODY: " + response.asString());

        assertEquals(response.statusCode(),200);
    }

        @Then("booking should be created succesfully")
        public void booking_should_be_created_succesfully()
    {

        assertEquals(response.statusCode(), 200);
    }

    }




