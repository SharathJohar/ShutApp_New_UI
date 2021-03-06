package com.shutapp.newui.loginflow;

import io.appium.java_client.MobileElement;

import io.appium.java_client.android.AndroidDriver;

import io.appium.java_client.android.AndroidElement;

import io.appium.java_client.remote.AndroidMobileCapabilityType;

import io.appium.java_client.remote.AutomationName;

import io.appium.java_client.remote.MobileCapabilityType;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

import org.openqa.selenium.By;

import org.openqa.selenium.TimeoutException;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.BeforeSuite;

import org.testng.annotations.BeforeTest;

import org.testng.annotations.Test;

import files.resources;

import java.io.IOException;

import java.net.MalformedURLException;

import java.net.URL;

import java.util.NoSuchElementException;

import java.util.concurrent.TimeUnit;

/**
 * @author Created By SHARATH 
 */
public class AppiumServer {

	public AndroidDriver<AndroidElement> driver;
	static Logger Log = LogManager.getLogger(AppiumServer.class);
	public WebElement el;

	/** 
	 * this method start Appium server through command prompt
	 * @param
	 */
	@BeforeSuite
	public void startServer() {

		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec(
					"cmd.exe /c start cmd.exe /k \"appium -a 127.0.0.1 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"false\"\"}\"\"");
			Thread.sleep(10000);
			Log.info("Appium server started successfully");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * this method Stop Appium server through command prompt
	 * @param
	 */
	// @AfterSuite
	public void stopServer() {
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec("taskkill /F /IM node.exe");
			runtime.exec("taskkill /F /IM cmd.exe");
			Log.info("Appium server stopped successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * this method creates the android driver
	 * @param
	 * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred
	 */
	@BeforeTest
	public void Capabilities() throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "2628f10a7cf6");// 4da52af
		cap.setCapability("deviceName", "Galaxy J7 Prime");// Mi A2
		cap.setCapability("platformVersion", "8.1.0");
		cap.setCapability("platformName", "Android");
		cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "in.dbst.shutappv1.dev");
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
				"in.dbst.shutappv1.ui.components.launcher.ActivityLauncher");
		driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Log.info("Joynt App Launched");
	}

	/** 
	 * this method swipe the on OnBoardingScreen
	 * @param
	 * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, 
	 * or otherwise occupied,and the thread is interrupted, either before 
	 * or during the activity
	 */
	@Test(priority = 1)
	public void OnBoardingScreen() throws MalformedURLException, InterruptedException {
		for (int i = 0; i < 2; i++) {
			resources.SwipeScreen(el, driver);

		}
		driver.findElementById("in.dbst.shutappv1.dev:id/sign_up").click();
		Log.info("Signing up!");
	}

	/** 
	 * this Method will select country code and enter phone number.
	 * @param
	 * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, 
	 * or otherwise occupied,and the thread is interrupted, either before 
	 * or during the activity
	 */
	@Test(priority = 2)
	public void MobileNumber() throws MalformedURLException, InterruptedException {
		if (driver == null) {
			Capabilities();
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElementById("in.dbst.shutappv1.dev:id/country_code").click();
		driver.hideKeyboard();
		MobileElement scrollIntoView = driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(text(\"India (IN)\"));");
		MobileElement IndSelect = driver.findElementByXPath("//android.widget.TextView[@text='India (IN)']");
		MobileElement search = driver.findElementByXPath(
				"//android.widget.EditText[@resource-id='in.dbst.shutappv1.dev:id/editText_search']");

		try {
			scrollIntoView.isDisplayed();
			IndSelect.click();
			Log.info("Selecting Country Code");
		} catch (NoSuchElementException e) {
			search.click();
			search.sendKeys("India");
			IndSelect.click();
			Log.warn("Zimbabwe");
		}

		driver.findElementById("in.dbst.shutappv1.dev:id/input_number").sendKeys("4444666666");
		driver.findElementById("in.dbst.shutappv1.dev:id/send_otp_btn").click();
		Log.info("Phone Number Entred");

	}
	
	/** 
	 * this method will enter OTP.
	 * @param
	 * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, 
	 * or otherwise occupied,and the thread is interrupted, either before 
	 * or during the activity
	 */
	@Test(priority = 3)
	public void OTP() throws MalformedURLException, InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			new WebDriverWait(driver, 5).until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='OTP Verification']")));
			Log.debug("OTP Page Launched");
			Log.info(driver.findElementById("in.dbst.shutappv1.dev:id/otp_phone_number").getText());
			driver.findElementById("in.dbst.shutappv1.dev:id/otp_edit_text").sendKeys("123456");
			Log.info("OTP Entered");
			driver.findElementById("in.dbst.shutappv1.dev:id/send_otp_btn").click();
		} catch (TimeoutException e) {
			Log.fatal("Page not uploaded in 60 sec");
		}
	}
	
	/** 
	 * 	this method is used to Add profile picture, select gender and enter user name & submit
	 * @param
	 * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, 
	 * or otherwise occupied,and the thread is interrupted, either before 
	 * or during the activity
	 */
	@Test(priority = 4)
	public void EnterUserName() throws MalformedURLException, InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions
					.invisibilityOfElementLocated(By.id("in.dbst.shutappv1.dev:id/tb_progress_view_profile")));
			Log.debug("Profile Page loaded");
		} catch (TimeoutException e) {
			Log.fatal("Page not uploaded in 60 sec");
		}
		WebElement FillYourProfile = driver.findElementByXPath("//android.widget.TextView[@text='Fill Your Profile']");
		String Fill_Your_Profile = FillYourProfile.getText();
		Log.info(Fill_Your_Profile + " Page is Loaded");
		resources.FillYourProfileGallery(driver);
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions
					.invisibilityOfElementLocated(By.id("in.dbst.shutappv1.dev:id/dp_image_progressbar")));
			Log.info("Image uploaded");
		} catch (TimeoutException e) {
			Log.fatal("Image not uploaded in 60 sec");
		}
		driver.findElementById("in.dbst.shutappv1.dev:id/male_gender_parent_layout").click();
		driver.findElementById("in.dbst.shutappv1.dev:id/female_gender").click();
		driver.findElementById("in.dbst.shutappv1.dev:id/other_gender").click();
		driver.findElementById("in.dbst.shutappv1.dev:id/male_gender_parent_layout").click();
		Log.info("Gender selected");
		driver.findElementById("in.dbst.shutappv1.dev:id/user_name_text").sendKeys("Sharath");
		Log.info("User Name Entered");
		driver.findElementById("in.dbst.shutappv1.dev:id/action_next").click();
		driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
		Log.info("Welcome");
	}

	/**
	 * @return
	 */
	public AndroidDriver<AndroidElement> fetch_driver() {
		return driver;
	}

}
