package com.bookit.step_definitions;

import com.bookit.pojos.Room;
import com.bookit.utilities.APIUtilities;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class APIStepDefinitions {

    private Response response;
    private String token;
    private JsonPath jsonPath;
    private ContentType contentType;

    @Given("authorization token is provided for {string}")
    public void authorization_token_is_provided_for(String role) {
        token = APIUtilities.getToken(role);
    }

    @Given("user accepts content type as {string}")
    public void user_accepts_content_type_as(String string) {
        if (string.toLowerCase().contains("json")) {
            contentType = ContentType.JSON;
        } else if (string.toLowerCase().contains("xml")) {
            contentType = ContentType.XML;
        }
    }

    @When("user sends GET request to {string}")
    public void user_sends_GET_request_to(String string) {
       response= given().
               accept(contentType).
               auth().oauth2(token).
       when().
               get(string).prettyPeek();
    }
    //Then user should be able to see 18 rooms
    @Then("user should be able to see {int} rooms")
    public void user_should_be_able_to_see_rooms(int expected) {
        List<?> rooms=response.jsonPath().get();
        Assert.assertEquals(expected,rooms.size());
    }
    // And user verifies that response status code is 200
    @Then("user verifies that response status code is {int}")
    public void user_verifies_that_response_status_code_is(int expected) {
        Assert.assertEquals(expected,response.statusCode());
    }

    /**
     * Any number in cucumber test step, becomes step definition (variable)
     * By changing this number, you are not changing a content of test step
     */
    @When("user sends POST request to {string} with following information:")
    public void user_sends_POST_request_to_with_following_information(String path,List<Map<String,String>> students) {
       for(Map<String,String> student:students){

           response=given().
                   auth().oauth2(token).
                   queryParams(student).
                   accept(contentType).
           when().
                   post(path);

           response.then().log().body(true);
             }
    }
}
