package com.shutapp.loginflow;

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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

/**
 * @author SHARATH
 */
public class AppiumServer {

	public AndroidDriver<AndroidElement> driver;
	static Logger log = LogManager.getLogger(AppiumServer.class);

	// This method will start Appium server through command prompt
	@BeforeSuite
	public void startServer() {

		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec(
					"cmd.exe /c start cmd.exe /k \"appium -a 127.0.0.1 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"false\"\"}\"\"");
			Thread.sleep(10000);
			log.info("Appium server started successfully");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	// This method will Stop Appium server through command prompt
//	@AfterSuite
	public void stopServer() {
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec("taskkill /F /IM node.exe");
			runtime.exec("taskkill /F /IM cmd.exe");
			log.info("Appium server stopped successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
		log.info("ShutApp Launched");
	}

	@Test(priority = 1)
	public void OnBoardingScreenOne() throws MalformedURLException, InterruptedException {
		System.out.println("Page 1: " + driver.findElementById("in.dbst.shutappv1.dev:id/header").getText());
		System.out.println(driver.findElementById("in.dbst.shutappv1.dev:id/subtext").getText());
		driver.findElementById("in.dbst.shutappv1.dev:id/next_btn").click();

		System.out.println("Page 2: " + driver.findElementById("in.dbst.shutappv1.dev:id/header").getText());
		System.out.println(driver.findElementById("in.dbst.shutappv1.dev:id/subtext").getText());
		driver.findElementById("in.dbst.shutappv1.dev:id/next_btn").click();

		System.out.println("Page 3: " + driver.findElementById("in.dbst.shutappv1.dev:id/header").getText());
		System.out.println(driver.findElementById("in.dbst.shutappv1.dev:id/subtext").getText());
		driver.findElementById("in.dbst.shutappv1.dev:id/next_btn").click();
	}

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
			log.info("Country Selected");
		} catch (NoSuchElementException e) {
			search.click();
			search.sendKeys("India");
			IndSelect.click();
			log.warn("Zimbabwe");
		}

		driver.findElementById("in.dbst.shutappv1.dev:id/input").sendKeys("4444666666");
		WebElement next = driver.findElementById("in.dbst.shutappv1.dev:id/action_next");

		if (next.isEnabled()) {
			log.info("Next Button is Enabled");
			next.click();
		} else {
			log.info("Invalid Phone Number");
			log.info("Shutting Down App");
			driver.quit();
		}
	}

	@Test(priority = 3)
	public void OTP() throws MalformedURLException, InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions
					.invisibilityOfElementLocated(By.id("in.dbst.shutappv1.dev:id/tb_progress_view_profile")));
			log.info("Page uploaded");
		} catch (TimeoutException e) {
			log.info("Page not uploaded in 60 sec");
		}
		driver.findElementById("in.dbst.shutappv1.dev:id/onboard_otp_fragment_input").sendKeys("123456");
		driver.findElementById("in.dbst.shutappv1.dev:id/tb_dialpad_done").click();
	}

	@Test(priority = 4)
	public void EnterUserName() throws MalformedURLException, InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions
					.invisibilityOfElementLocated(By.id("in.dbst.shutappv1.dev:id/tb_progress_view_profile")));
			log.info("Page uploaded");
		} catch (TimeoutException e) {
			log.info("Page not uploaded in 60 sec");
		}
		WebElement FillYourProfile = driver.findElementByXPath("//android.widget.TextView[@text='Fill Your Profile']");
		String Fill_Your_Profile = FillYourProfile.getText();
		System.out.println(Fill_Your_Profile + " Page is Loaded");
		driver.findElementById("in.dbst.shutappv1.dev:id/profile_pic").click();
		log.info("Clicked on Avatar");
		driver.findElementById("in.dbst.shutappv1.dev:id/chat_attachment_dialog_btn_camera").click();
		log.info("Camera selected");
//		driver.findElementById("in.dbst.shutappv1.dev:id/chat_attachment_dialog_btn_gallery").click();
//		driver.findElementByXPath("//android.widget.LinearLayout[@index='3']").click();
		driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
		log.info("Camera accessed");
		driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
		log.info("Photos and media accessesd");
//		driver.findElementByXPath("//android.widget.LinearLayout[@index='6']").click(); //MI A2
//		log.info("Image uploading"); //MI A2
//		driver.findElementByAccessibilityId("Shutter button").click();//MI A2
		driver.findElementByXPath("//GLButton[@text='Shutter']").click(); // --Samsung--//
		log.info("Image captured");
//		driver.findElementByAccessibilityId("Done").click();
		driver.findElementById("com.sec.android.app.camera:id/okay").click(); // --Samsung--//
		log.info("click on Done");
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions
					.invisibilityOfElementLocated(By.id("in.dbst.shutappv1.dev:id/dp_image_progressbar")));
			log.info("Image uploaded");
		} catch (TimeoutException e) {
			log.info("Image not uploaded in 60 sec");
		}
		driver.findElementById("in.dbst.shutappv1.dev:id/male_gender_parent_layout").click();
		driver.findElementById("in.dbst.shutappv1.dev:id/female_gender").click();
		driver.findElementById("in.dbst.shutappv1.dev:id/other_gender").click();
		driver.findElementById("in.dbst.shutappv1.dev:id/male_gender_parent_layout").click();
		log.info("Gender selected");
		driver.findElementById("in.dbst.shutappv1.dev:id/user_name_text").sendKeys("Sharath");
		driver.findElementById("in.dbst.shutappv1.dev:id/action_next").click();
	}

	@Test(priority = 5)
	public void OnBoardingScreenThree() throws MalformedURLException, InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("Page 1: " + driver.findElementById("in.dbst.shutappv1.dev:id/hide_chat_header").getText());
		System.out.println(driver.findElementById("in.dbst.shutappv1.dev:id/hide_chat_sub_text").getText());
		driver.findElementById("in.dbst.shutappv1.dev:id/next_btn").click();

		System.out
				.println("Page 2: " + driver.findElementById("in.dbst.shutappv1.dev:id/on_board_one_header").getText());
		System.out.println(driver.findElementById("in.dbst.shutappv1.dev:id/on_board_one_subtext").getText());
		driver.findElementById("in.dbst.shutappv1.dev:id/next_btn").click();

		System.out
				.println("Page 3: " + driver.findElementById("in.dbst.shutappv1.dev:id/un_hide_chat_header").getText());
		System.out.println(driver.findElementById("in.dbst.shutappv1.dev:id/un_hide_chat_subtext").getText());
		driver.findElementById("in.dbst.shutappv1.dev:id/next_btn").click();
	}

	/**
	 * @return
	 */

	// @Test(priority = 3)
	public void WelCome() throws MalformedURLException, InterruptedException {

		MobileElement welcome = driver.findElementByXPath("//android.widget.TextView[@text='WELCOME']");
		String isDisplayed = welcome.getText();
		System.out.println("PageName is: " + isDisplayed);
		MobileElement UserName = driver.findElementByXPath("//android.widget.TextView[@index='1']");
		String isDisplayed1 = UserName.getText();
		System.out.println("UserName is: " + isDisplayed1);
		MobileElement dp = driver.findElementById("in.dbst.shutappv1.dev:id/dp_img");
		boolean isDisplayed2 = dp.isDisplayed();
		System.out.println("DP is " + isDisplayed2);
		driver.findElementByXPath("//android.widget.Button[@text='Shut Modes']").click();
		System.out.println("Clicked on ShutMode");

	}

	@Test(priority = 6)
	public void ShutModes() throws MalformedURLException, InterruptedException {

		MobileElement ShutMode = driver.findElementByXPath("//android.widget.TextView[@text='Shut Mode']");
		String Shut = ShutMode.getText();
		MobileElement PersonnelSapce = driver
				.findElementByXPath("//android.widget.TextView[@text='For your personal space']");
		String PerSpace = PersonnelSapce.getText();
		System.out.println("PageName is: " + Shut + PerSpace + "  is Launched");
		driver.findElementById("in.dbst.shutappv1.dev:id/welcome_modes_fragment_normal_mode_btn").click();
		MobileElement NormalMode = driver.findElementByXPath("//android.widget.TextView[@text='Normal Mode']");
		System.out.println("Clicked on NormalMode");
		String Normal = NormalMode.getText();
		MobileElement BetterExperience = driver
				.findElementByXPath("//android.widget.TextView[@text='Better Experience']");
		String BetterExp = BetterExperience.getText();
		System.out.println("PageName is: " + Normal + BetterExp + "  is Launched");
		driver.findElementByXPath("//android.widget.Button[@text='Shut Modes']").click();
		System.out.println("Clicked on ShutMode");
		driver.findElementByXPath(
				"//android.widget.Button[@resource-id='in.dbst.shutappv1.dev:id/welcome_modes_fragment_btn_setlock']")
				.click();
		System.out.println("Clicked on Set Lock");

		driver.findElementById("in.dbst.shutappv1.dev:id/shut_mode_activity_pin_input_show").sendKeys("1111");
		driver.hideKeyboard();
		driver.findElementById("in.dbst.shutappv1.dev:id/tb_dialpad_done").click();
		driver.findElementById("in.dbst.shutappv1.dev:id/shut_mode_activity_pin_input_show").sendKeys("1111");
		driver.hideKeyboard();
		driver.findElementById("in.dbst.shutappv1.dev:id/tb_dialpad_done").click();
//		WebElement toastView = driver.findElementByXPath("//android.widget.Toast[1]");
//		String text = toastView.getAttribute("name");
//		System.out.println("Toast Message is : " + text);
		driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
		System.out.println("Welcome to ShutApp Recent Chats");

	}

	public AndroidDriver<AndroidElement> fetch_driver() {
//		AndroidDriver<AndroidElement> mydriver = driver;
		return driver;
	}

}
