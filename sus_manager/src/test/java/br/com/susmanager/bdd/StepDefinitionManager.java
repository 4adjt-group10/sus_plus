package br.com.susmanager.bdd;

import br.com.susmanager.controller.dto.professional.ProfessionalCreateForm;
import br.com.susmanager.helper.ProfessionalHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class StepDefinitionManager {

    private Response response;

    private ProfessionalHelper helper = new ProfessionalHelper();

    private ProfessionalCreateForm professionalValid;


    private final String ENDPOINT_API_REGISTER = "http://localhost:8081/manager/professional/create";
    private final String ENDPOINT_API_FIND_BY_ID = "http://localhost:8081/manager/professional/find/{professionalId}";
    private final String ENDPOINT_API_LIST_ALL = "http://localhost:8081/manager/professional/findall";
    private final String ENDPOINT_API_DELETE = "http://localhost:8081/manager/professional/delete/{professionalId}";
    private final String ENDPOINT_API_REGISTER_ERROR = "http://localhost:8081/manager/professional";

    @Given("that I create a professional with {string} and {string}")
    public void thatICreateAProfessionalWithAnd(String name, String document) {

        var professionalCreated = helper.createProfessionalForm(document, name);
        professionalValid = professionalCreated;
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(professionalCreated)
                .when()
                .post(ENDPOINT_API_REGISTER);

    }

    @When("I look for all professionals")
    public void iLookForAllProfessionals() {
        response = when().get(ENDPOINT_API_LIST_ALL);

    }

    @Then("the response status code should be {string}")
    public void theResponseStatusCodeShouldBe(String status) {
        response.then().statusCode(Integer.parseInt(status))
                .log().all();
        
    }

    @When("I look for a client by id")
    public void iLookForAClientById() {
        response = when().get(ENDPOINT_API_FIND_BY_ID, response.jsonPath().getString("id"));
        
    }


    @When("I delete the client")
    public void iDeleteTheClient() {
        response = when().delete(ENDPOINT_API_DELETE, response.jsonPath().getString("id"));

    }


    @Given("that I create a professional with {string} and {string} error")
    public void thatICreateAProfessionalWithAndError(String document, String name) {
        var professionalCreated = helper.createProfessionalForm(document, name);
        professionalValid = professionalCreated;
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(professionalCreated)
                .when()
                .post(ENDPOINT_API_REGISTER_ERROR);

    }
}
