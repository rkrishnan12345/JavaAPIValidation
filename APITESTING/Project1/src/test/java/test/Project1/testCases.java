package test.SampleProj;

import org.hamcrest.core.IsEqual;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
//import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.util.HashMap;

public class testCases {
	ResponseSpecification res;
	@BeforeClass
	public void setSpecification( ) {
		res = RestAssured.expect();
		res.statusLine("HTTP/1.1 200 OK");
		res.contentType(ContentType.JSON);
		
		ExtentReportManager.createReport();

		
	}
	
	@Test
	public void testFetchUser() {
		
		ExtentReportManager.test = ExtentReportManager.reports.startTest("testFetchUser", "Fetch User");
		RestAssured.baseURI = "https://reqres.in/";
		ExtentReportManager.test.log(LogStatus.PASS, "Test Case Status", "Passed");
		ExtentReportManager.test.log(LogStatus.INFO, "Base URI","https://reqres.in");
		ExtentReportManager.test.log(LogStatus.INFO, "API call","GET");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource route","api/users?page=2");
		given().when().get("api/users?page=2").then().statusCode(200);
	}
	
	@Test
	public void testCreateUser() {
		RestAssured.baseURI = "https://reqres.in/";
		JSONObject params = new JSONObject();
		params.put("name", "Aditya");
		params.put("job", "QA");

		/*HashMap<String,String> params = new HashMap();
		params.put("name", "Aditya");
		params.put("job", "QA"); */
		given().when().contentType("application/json").post("api/users").then().assertThat().body("name", IsEqual.equalTo("Aditya"));
	}
	
	@Test
	public void testUserDetail() {
		
		ExtentReportManager.test = ExtentReportManager.reports.startTest("testUserDetail", "User Details");
		ExtentReportManager.test.log(LogStatus.PASS, "Test Case Status", "Passed");
		ExtentReportManager.test.log(LogStatus.INFO, "Base URI","https://reqres.in");
		ExtentReportManager.test.log(LogStatus.INFO, "API call","GET");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource route","api/users/2");
		
		RestAssured.baseURI = "https://reqres.in/";
		given().when().get("api/users/2").then().assertThat().body("data.id", IsEqual.equalTo(2));
	}
	
	@Test
	public void testRegisterUser() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("testRegisterUser","Registering the user");
		ExtentReportManager.test.log(LogStatus.INFO, "Base URI","https://reqres.in");
		ExtentReportManager.test.log(LogStatus.INFO, "API call","POST");
		ExtentReportManager.test.log(LogStatus.INFO, "Body passed","email=eve.holt@reqres.in and password=pistol");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource route","api/register");
		ExtentReportManager.test.log(LogStatus.INFO, "Value compared","id");
		
		RestAssured.baseURI = "https://reqres.in";
		
		HashMap<String,String> params = new HashMap<>();
		params.put("email", "eve.holt@reqres.in");
		params.put("password", "pistol");
		
		given().when().contentType("application/json").body(params).post("api/register").then().assertThat().body("id", IsEqual.equalTo(4));
	}
	
	@Test
	public void testUserNotFound(){
		ExtentReportManager.test = ExtentReportManager.reports.startTest("testUserNotFound", "User not found.");
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI", "https://reqres.in");
		ExtentReportManager.test.log(LogStatus.INFO, "API Call", "GET");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource url", "api/users/23");
		ExtentReportManager.test.log(LogStatus.INFO, "Field Comparison", "data");
		ResponseSpecification resp;
		resp = RestAssured.expect();
		resp.statusLine("HTTP/1.1 404 Not Found");
		resp.contentType(ContentType.JSON);
		
		RestAssured.baseURI = "https://reqres.in";
		given().when().get("api/users/23").then().spec(resp).assertThat().body("data", IsEqual.equalTo(null));
		
	}
	
	@Test
	public void testRegisterUserSuccessful(){
		ExtentReportManager.test = ExtentReportManager.reports.startTest("testRegisterUserSuccessful", "User register successful");
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI", "https://reqres.in");
		ExtentReportManager.test.log(LogStatus.INFO, "API Call", "POST");
		ExtentReportManager.test.log(LogStatus.INFO, "Payload", "{email: \"eve.holt@reqres.in\", password: \"123456\"}");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource url", "api/register");
		ExtentReportManager.test.log(LogStatus.INFO, "Field Comparison", "id");
		RestAssured.baseURI = "https://reqres.in";
		HashMap<String,String> params = new HashMap<>();
		params.put("email", "eve.holt@reqres.in");
		params.put("password", "123456");
		given().when().contentType("application/json").body(params).post("api/register").then().assertThat().body("id", IsEqual.equalTo(4));
		
	}
	
	@Test
	public void testRegisterUserUnsuccessful(){
		ExtentReportManager.test = ExtentReportManager.reports.startTest("testRegisterUserUnsuccessful", "User register unsuccessful");
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI", "https://reqres.in");
		ExtentReportManager.test.log(LogStatus.INFO, "API Call", "POST");
		ExtentReportManager.test.log(LogStatus.INFO, "Payload", "{email: \"eve.holt@reqres.in\"}");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource url", "api/register");
		ExtentReportManager.test.log(LogStatus.INFO, "Field Comparison", "error");
		RestAssured.baseURI = "https://reqres.in";
		HashMap<String,String> params = new HashMap<>();
		params.put("email", "eve.holt@reqres.in");
		given().when().contentType("application/json").body(params).post("api/register").then().assertThat().body("error", IsEqual.equalTo("Missing password"));
		
	}
	
	
	@Test
	public void ListSingleUser() {
		ExtentReportManager.test = ExtentReportManager.reports.startTest("ListSingleUser","Fetching single user details");
		ExtentReportManager.test.log(LogStatus.INFO, "Base URI","https://reqres.in");
		ExtentReportManager.test.log(LogStatus.INFO, "API call","GET");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource route","api/unknown/2");
		ExtentReportManager.test.log(LogStatus.INFO, "Value compared","data.name and data.id");
		
		RestAssured.baseURI = "https://reqres.in";
		given().when().get("api/unknown/2").then().assertThat().body("data.name", IsEqual.equalTo("fuchsia rose")).body("data.id", IsEqual.equalTo(2));
	}
	
	@AfterClass
    public void endReport()
    {
        ExtentReportManager.reports.flush();
    }

}
