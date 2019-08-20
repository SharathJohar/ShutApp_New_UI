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
	static Logger Log = LogManager.getLogger(AppiumServer.class);

	// This method will start Appium server through command prompt
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

	// This method will Stop Appium server through command prompt
//	@AfterSuite
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
		Log.info("ShutApp Launched");
	}

	@Test(priority = 1)
	public void OnBoardingScreenOne() throws MalformedURLException, InterruptedException {
		Log.info("Page 1: " + driver.findElementById("in.dbst.shutappv1.dev:id/header").getText());
		driver.findElementById("in.dbst.shutappv1.dev:id/next_btn").click();
		Log.info("Page 2: " + driver.findElementById("in.dbst.shutappv1.dev:id/header").getText());
		driver.findElementById("in.dbst.shutappv1.dev:id/next_btn").click();
		Log.info("Page 3: " + driver.findElementById("in.dbst.shutappv1.dev:id/header").getText());
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
			Log.info("Country Selected");
		} catch (NoSuchElementException e) {
			search.click();
			search.sendKeys("India");
			IndSelect.click();
			Log.warn("Zimbabwe");
		}

		driver.findElementById("in.dbst.shutappv1.dev:id/input").sendKeys("4444666666");
		WebElement next = driver.findElementById("in.dbst.shutappv1.dev:id/action_next");

		if (next.isEnabled()) {
			Log.info("Next Button is Enabled");
			next.click();
		} else {
			Log.info("Invalid Phone Number");
			Log.info("Shutting Down App");
			driver.quit();
		}
	}

	@Test(priority = 3)
	public void OTP() throws MalformedURLException, InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions
					.invisibilityOfElementLocated(By.id("in.dbst.shutappv1.dev:id/tb_progress_view_profile")));
			Log.info("OTP Page loaded");
			driver.findElementById("in.dbst.shutappv1.dev:id/onboard_otp_fragment_input").sendKeys("123456");
			driver.findElementById("in.dbst.shutappv1.dev:id/tb_dialpad_done").click();
		} catch (TimeoutException e) {
			Log.fatal("Page not uploaded in 60 sec");
		}
	}

	@Test(priority = 4)
	public void EnterUserName() throws MalformedURLException, InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions
					.invisibilityOfElementLocated(By.id("in.dbst.shutappv1.dev:id/tb_progress_view_profile")));
			Log.info("Profile Page loaded");
		} catch (TimeoutException e) {
			Log.fatal("Page not uploaded in 60 sec");
		}
		WebElement FillYourProfile = driver.findElementByXPath("//android.widget.TextView[@text='Fill Your Profile']");
		String Fill_Your_Profile = FillYourProfile.getText();
		Log.info(Fill_Your_Profile + " Page is Loaded");
		driver.findElementById("in.dbst.shutappv1.dev:id/profile_pic").click();
		Log.info("Clicked on Avatar");
		driver.findElementById("in.dbst.shutappv1.dev:id/chat_attachment_dialog_btn_camera").click();
		Log.info("Camera selected");
//		driver.findElementById("in.dbst.shutappv1.dev:id/chat_attachment_dialog_btn_gallery").click();
//		driver.findElementByXPath("//android.widget.LinearLayout[@index='3']").click();
		driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
		Log.info("Camera accessed");
		driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
		Log.info("Photos and media accessesd");
//		driver.findElementByXPath("//android.widget.LinearLayout[@index='6']").click(); //MI A2
//		log.info("Image uploading"); //MI A2
//		driver.findElementByAccessibilityId("Shutter button").click();//MI A2
		driver.findElementByXPath("//GLButton[@text='Shutter']").click(); // --Samsung--//
		Log.info("Image captured");
//		driver.findElementByAccessibilityId("Done").click();
		driver.findElementById("com.sec.android.app.camera:id/okay").click(); // --Samsung--//
		Log.info("click on Done");
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
		driver.findElementById("in.dbst.shutappv1.dev:id/action_next").click();
	}

	@Test(priority = 5)
	public void OnBoardingScreenThree() throws MalformedURLException, InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Log.info("Page 1: " + driver.findElementById("in.dbst.shutappv1.dev:id/hide_chat_header").getText());
		driver.findElementById("in.dbst.shutappv1.dev:id/next_btn").click();
		Log.info("Page 2: " + driver.findElementById("in.dbst.shutappv1.dev:id/on_board_one_header").getText());
		driver.findElementById("in.dbst.shutappv1.dev:id/next_btn").click();
		Log.info("Page 3: " + driver.findElementById("in.dbst.shutappv1.dev:id/un_hide_chat_header").getText());
		driver.findElementById("in.dbst.shutappv1.dev:id/next_btn").click();
	}

	/**
	 * @return
	 */
	@Test(priority = 6)
	public void ShutMode() throws MalformedURLException, InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		Log.info(driver.findElementById("in.dbst.shutappv1.dev:id/set_pin_header").getText());
		MobileElement EneterNewPin = driver.findElementById("in.dbst.shutappv1.dev:id/shutmode_pin_view");
		EneterNewPin.sendKeys("1111");
		driver.hideKeyboard();
		driver.findElementById("in.dbst.shutappv1.dev:id/set_lock_btn").click();

		Log.info(driver.findElementById("in.dbst.shutappv1.dev:id/set_pin_header").getText());
		MobileElement Reenter_pin = driver.findElementById("in.dbst.shutappv1.dev:id/shutmode_pin_view");
		Reenter_pin.sendKeys("1117");
		driver.hideKeyboard();
		driver.findElementById("in.dbst.shutappv1.dev:id/set_lock_btn").click();

		MobileElement PopUpTetx = driver.findElementById("in.dbst.shutappv1.dev:id/pop_up_text");
		if (PopUpTetx.isDisplayed()) {
			driver.findElementById("in.dbst.shutappv1.dev:id/set_pin_back").click();
			EneterNewPin.sendKeys("1111");
			driver.hideKeyboard();
			driver.findElementById("in.dbst.shutappv1.dev:id/set_lock_btn").click();
			Reenter_pin.sendKeys("1111");
			driver.hideKeyboard();
			driver.findElementById("in.dbst.shutappv1.dev:id/set_lock_btn").click();
			driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
		} else {
			Log.fatal("Unable to set Lock");
		}
		Log.info("Welcome");
	}

	public AndroidDriver<AndroidElement> fetch_driver() {
		return driver;
	}

}
