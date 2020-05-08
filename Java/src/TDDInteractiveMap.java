import com.google.gson.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TDDInteractiveMap {
    ChromeDriver driver;
    WebElement latitude_element, longtitude_element, distance_from_city_center_btn, distance_from_earth_core_btn, get_location_by_gps_btn, get_location_by_coordinates_btn, label;

    @BeforeClass
    public void Initialize() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Desktop\\Web_Drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("C:/xampp/htdocs/CS458_Project3_TDD/index.html");
    }

    @AfterMethod
    public void Terminate() throws IOException {
        driver.navigate().to(driver.getCurrentUrl());
    }

    @BeforeMethod
    public void BeforeMethod() throws IOException {
        latitude_element = driver.findElement(By.xpath("//input[@id='lat']"));
        longtitude_element = driver.findElement(By.xpath("//input[@id='long']"));
        distance_from_city_center_btn = driver.findElement(By.xpath("//button[id='city-center-btn']"));
        distance_from_earth_core_btn = driver.findElement(By.xpath("//button[id='earth-center-btn']"));
        get_location_by_gps_btn = driver.findElement(By.xpath("//button[id='get-gps-btn']"));
        get_location_by_coordinates_btn = driver.findElement(By.xpath("//button[id='get-coords-btn']"));
    }

    @DataProvider(name = "distanceToCityCenterDataProvider")
    public static Object[] distanceToCityCenterDataProvider() throws IOException {
        String data = readFile("Resources/testDataInputs.json");
        return readCredentialsFromResource(data, "Test1");
    }

    @Test(dataProvider = "distanceToCityCenterDataProvider")
    public void distanceToCityCenterTest(String latitude, String longtitude, String expectedResult) {

        performDistanceToCityCenter(latitude, longtitude, expectedResult);
    }

    @DataProvider(name = "distanceToEarthCoreDataProvider")
    public static Object[] distanceToEarthCoreDataProvider() throws IOException {
        String data = readFile("Resources/testDataInputs.json");
        return readCredentialsFromResource(data, "Test2");
    }

    @Test(dataProvider = "distanceToEarthCoreDataProvider")
    public void distanceToEarthCoreTest(String latitude, String longtitude, String expectedResult) {

        performDistanceToEarthCenter(latitude, longtitude, expectedResult);
    }

    @DataProvider(name = "getLocationUsingCoordinatesDataProvider")
    public static Object[] getLocationUsingCoordinatesDataProvider() throws IOException {
        String data = readFile("Resources/testDataInputs.json");
        return readCredentialsFromResource(data, "Test3");
    }

    @Test(dataProvider = "getLocationUsingCoordinatesDataProvider")
    public void getLocationUsingCoordinatesTest(String latitude, String longtitude, String expectedResult) {

        performFindLocationByCoordinates(latitude, longtitude, expectedResult);
    }

    @Test
    public void findLocationByGps() throws InterruptedException {
        performFindLocationByGps();
    }


    ////METHODS////

    void performDistanceToCityCenter(String latitude, String longtitude, String expectedResult) {
        latitude_element.sendKeys(latitude);
        longtitude_element.sendKeys(longtitude);
        distance_from_city_center_btn.click();
        label = driver.findElement(By.xpath("//p[@class='help-block']//span[@class='Message']"));
        String outcome = label.getText();
        Assert.assertEquals(outcome, expectedResult);
    }

    void performDistanceToEarthCenter(String latitude, String longtitude, String expectedResult) {
        latitude_element.sendKeys(latitude);
        longtitude_element.sendKeys(longtitude);
        distance_from_earth_core_btn.click();
        label = driver.findElement(By.xpath("//p[@class='help-block']//span[@class='Message']"));
        String outcome = label.getText();
        Assert.assertEquals(outcome, expectedResult);
    }

    void performFindLocationByCoordinates(String latitude, String longtitude, String expectedResult) {
        latitude_element.sendKeys(latitude);
        longtitude_element.sendKeys(longtitude);
        get_location_by_coordinates_btn.click();
        label = driver.findElement(By.xpath("//p[@class='help-block']//span[@class='Message']"));
        String outcome = label.getText();
        Assert.assertEquals(outcome, expectedResult);
    }

    void performFindLocationByGps() throws InterruptedException {
        get_location_by_gps_btn = driver.findElement(By.xpath("//button[id='get-gps-btn']"));
        get_location_by_gps_btn.click();
        label = driver.findElement(By.xpath("//p[@class='help-block']//span[@class='Message2']"));
        String outcome = label.getText();
        Assert.assertEquals(outcome, "A new password is sent to your Bilkent Mail.");
    }

    public static Object[][] readCredentialsFromResource(String json, String testName) {
        JsonObject jsonFile = new JsonParser().parse(json).getAsJsonObject();
        JsonArray testJson = jsonFile.getAsJsonArray(testName);
        Object[][] testData = new Object[testJson.size()][3];

        for (int i = 0; i < testJson.size(); i++) {
            JsonObject obj = testJson.get(i).getAsJsonObject();
            testData[i][0] = obj.get("lan").getAsString();
            testData[i][1] = obj.get("long").getAsString();
            testData[i][2] = obj.get("output").getAsString();
        }
        return testData;
    }

    public static String readFile(String fileName) throws IOException {
        String data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

}
