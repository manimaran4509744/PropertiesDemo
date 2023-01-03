package org.prop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PropertyFile1 {
	public WebDriver driver;
	Properties prop;
	FileInputStream stream;

	@BeforeMethod
	private void setUp() {
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		prop = new Properties();
		System.out.println(System.getProperty("user.dir"));

		File f = new File(System.getProperty("user.dir") + "\\new.properties");
		try {
			stream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.out.println("file is missing");
		}
		try {
			prop.load(stream);
		} catch (IOException e) {
			System.out.println("file is not able to load");
			e.printStackTrace();
		}
		driver.get(prop.getProperty("url"));

	}

	@Test
	private void login() {
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(prop.getProperty("username"));
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(prop.getProperty("password"));
		driver.findElement(By.xpath("//div[@class='oxd-form-actions orangehrm-login-action']//button")).click();

	}

	@AfterMethod
	private void tearDown() {
		driver.close();
	}

}
