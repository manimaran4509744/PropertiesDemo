package org.prop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PropertyFile1 {
	public WebDriver driver;
	Properties prop;
	FileInputStream stream;
	Logger logger;

	@BeforeSuite
	private void logCreation() {
		logger = LogManager.getLogger(PropertyFile1.class.getName());
		logger.info("logger object is created");
		
		
	}

	@BeforeMethod
	private void setUp() {
		WebDriverManager.firefoxdriver().setup();
		logger.info("Firefox jar are downloaded and setup");
		driver = new FirefoxDriver();
		logger.info("Firefox object is created and browser is launched");
		driver.manage().window().maximize();
		prop = new Properties();
		logger.info("Properties object is created");
		System.out.println(System.getProperty("user.dir"));

		File f = new File(System.getProperty("user.dir") + "\\new.properties");
		logger.info("File object is created and location of properties is provided");
		try {
			stream = new FileInputStream(f);
			logger.info("FileInputStream object is created to read the properties file");
		} catch (FileNotFoundException e) {
			System.out.println("file is missing");
		}
		try {
			prop.load(stream);
			logger.info("InputStream is loaded");
		} catch (IOException e) {
			System.out.println("file is not able to load");
			e.printStackTrace();
		}
		driver.get(prop.getProperty("url"));
		logger.info("Url is launched");

	}

	@Test
	private void login() {
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(prop.getProperty("username"));
		logger.info("Username is given to field from properties file");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(prop.getProperty("password"));
		logger.info("Password is given to field from properties file");
		driver.findElement(By.xpath("//div[@class='oxd-form-actions orangehrm-login-action']//button")).click();
		logger.info("Login button is clicked");

	}

	@AfterMethod
	private void tearDown() {
		logger.info("Browser is closed");
		driver.close();
	}

}
