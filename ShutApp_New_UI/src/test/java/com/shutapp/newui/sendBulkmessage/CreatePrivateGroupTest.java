/**
 * 
 */
package com.shutapp.newui.sendBulkmessage;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.shutapp.newui.loginflow.AppiumServer;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * @author SHARATH
 *
 * 
 */
public class CreatePrivateGroupTest extends AppiumServer {

	public AndroidDriver<AndroidElement> mydriver;
	static Logger Log = LogManager.getLogger(CreatePrivateGroupTest.class);

	@Test(priority = 7)
	// This method will create the private group
	public void CreateGroup() throws MalformedURLException, InterruptedException {

		AndroidDriver<AndroidElement> driver = fetch_driver();

		driver.findElementById("in.dbst.shutappv1.dev:id/new_chat_btn").click();
		Thread.sleep(3000);
		try {
			WebElement toastView = driver.findElementByXPath("//android.widget.Toast[1]");
			String text = toastView.getAttribute("name");
			Log.info("Tost Message is : " + text);
			Log.info("Contatcs Updated");

		} catch (Exception e) {
			Log.info("Contatcs Not Updated");
		}
		driver.findElementByXPath(
				"//android.widget.LinearLayout[@resource-id='in.dbst.shutappv1.dev:id/private_group_create']").click();
//		driver.findElementByXPath(
//				"//android.widget.LinearLayout[@resource-id='in.dbst.shutappv1.dev:id/open_group_create']").click();
		Thread.sleep(5000);
		driver.findElementByXPath("//android.widget.TextView[@text='A Sharath'] ").click();
		driver.findElementByXPath("//android.widget.TextView[@text='A ShutApp 3'] ").click();
		driver.findElementByXPath("//android.widget.TextView[@text='Ajay ShutApp'] ").click();
		driver.findElementByXPath("//android.widget.TextView[@text='Alok ShutApp'] ").click();
		driver.findElementByXPath("//android.widget.TextView[@text='Amit ShutApp'] ").click();
		driver.findElementByXPath("//android.widget.TextView[@text='Neeraj ShutApp'] ").click();
		driver.findElementByXPath("//android.widget.TextView[@text='Ravi ShutApp'] ").click();
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Vivek Shutapp\").instance(0))");	
		driver.findElementByXPath("//android.widget.TextView[@text='Sagar ShutApp'] ").click();
		driver.findElementByXPath("//android.widget.TextView[@text='Sindhu'] ").click();
		driver.findElementByXPath("//android.widget.TextView[@text='ShutApp 4'] ").click();
		driver.findElementByXPath("//android.widget.TextView[@text='Vijay Goel'] ").click();
		driver.findElementByXPath("//android.widget.TextView[@text='Vivek ShutApp'] ").click();
		Log.info("Memebers addedd successfully");
		driver.findElementById("in.dbst.shutappv1.dev:id/create_group_next").click();
		driver.findElementById("in.dbst.shutappv1.dev:id/input_number").sendKeys("Notify!");
		driver.findElementById("in.dbst.shutappv1.dev:id/group_name_next").click();
		driver.findElementById("in.dbst.shutappv1.dev:id/add_image").click();
////	driver.findElementById("in.dbst.shutappv1.dev:id/chat_attachment_dialog_btn_camera").click();
////	Log.info("Camera selected");
////	driver.findElementByAccessibilityId("Shutter button").click();
////	Log.info("Image captured");
////	driver.findElementByAccessibilityId("Done").click();
////	Log.info("click on Done");
		driver.findElementById("in.dbst.shutappv1.dev:id/chat_attachment_dialog_btn_gallery").click();
		Log.info("Gallery selected");
		driver.findElementByXPath("//android.widget.LinearLayout[@index='5']").click();
		Log.info("Image uploading");
		Thread.sleep(7000);
		driver.findElementById("in.dbst.shutappv1.dev:id/create_group").click();
		Log.info("Group successfully created");
	}

	@Test(priority = 8)
	// This method will send TTL messages and verify Auto delete
	public void TTL() throws MalformedURLException, InterruptedException {

		driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_ttl_image_checkable").click();
		driver.findElementById("in.dbst.shutappv1.dev:id/btn_ok").click();
		WebElement Text = driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_text_box_input");
		WebElement Send = driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_send_fab");
		for (int i = 0; i < 20; i++) {
			Text.sendKeys("Abcdefghdfdefghijklmnopqrstuvwxyz!");
			Send.click();

		}
		Thread.sleep(25000);

		try {
			driver.findElementById("in.dbst.shutappv1.dev:id/chat_bubble_message_text").isDisplayed();
			Log.fatal("AutoDelete Not Working as Expected");

		} catch (Exception e) {

			Log.info("AutoDelete Working as Expected");
		}
		driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_action_mode_ttl").click();
		driver.findElementByXPath("//android.widget.Button[@text='off']").click();
		driver.findElementById("in.dbst.shutappv1.dev:id/btn_ok").click();
	}

	@Test(priority = 9)
	// This method will send text messages in Bulk
	public void TextMessage() throws MalformedURLException, InterruptedException {

		WebElement Text = driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_text_box_input");
		WebElement Send = driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_send_fab");
		for (int i = 0; i < 50; i++) {
			Text.sendKeys(
					"You have brains in your head. You have feet in your shoes. You can steer yourself any direction you choose. You're on your own. And you know what you know. And YOU are the one who'll decide where to go...");
			Send.click();

		}
		Log.info("Text Messages sent Successfully");
	}

	@Test(priority = 10)
	// This method will send Anonymous text messages in Bulk
	public void AnonymousTextMessage() throws MalformedURLException, InterruptedException {

		driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_anonymous_image_checkable").click();
		WebElement Text = driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_text_box_input");
		WebElement Send = driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_send_fab");
		for (int i = 0; i < 20; i++) {
			Text.sendKeys("Change the world by being yourself.1");
			Send.click();
		}
		driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_anonymous_image_checkable").click();
		Log.info("Anonymous Text Messages sent Successfully");
	}

	@Test(priority = 11)
	// This method will send Images in Bulk
	public void Images() throws MalformedURLException, InterruptedException {

		for (int i = 0; i < 2; i++) {
			driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_action_attachments").click();
			driver.findElementById("in.dbst.shutappv1.dev:id/chat_attachment_dialog_btn_gallery").click();
			List<AndroidElement> list = driver.findElements(By.id("in.dbst.shutappv1.dev:id/imageView"));
			list.get(0).click();
			list.get(1).click();
			list.get(2).click();
			list.get(3).click();
			list.get(4).click();
			list.get(5).click();
			list.get(6).click();
			list.get(7).click();
			list.get(8).click();
			list.get(9).click();
			list.get(10).click();
			list.get(11).click();
			list.get(12).click();
			list.get(13).click();
			list.get(14).click();
			driver.findElementById("in.dbst.shutappv1.dev:id/image_ok").click();
			driver.findElementById("in.dbst.shutappv1.dev:id/ok").click();
		}
		Log.info("Bulk Images sent Successfully");
		Thread.sleep(2000);
//			driver.pressKey(new KeyEvent(AndroidKey.BACK));
//			driver.findElementById("in.dbst.shutappv1.dev:id/back_btn").click();
	}

	@Test(priority = 12)
	// This method will send Anonymous Images in Bulk
	public void AnonymousImages() throws MalformedURLException, InterruptedException {

		driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_anonymous_image_checkable").click();
		for (int i = 0; i < 2; i++) {
			driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_action_attachments").click();
			driver.findElementById("in.dbst.shutappv1.dev:id/chat_attachment_dialog_btn_gallery").click();
			List<AndroidElement> list = driver.findElements(By.id("in.dbst.shutappv1.dev:id/imageView"));
			list.get(0).click();
			list.get(1).click();
			list.get(2).click();
			list.get(3).click();
			list.get(4).click();
			list.get(5).click();
			list.get(6).click();
			list.get(7).click();
			list.get(8).click();
			list.get(9).click();
			list.get(10).click();
			list.get(11).click();
			list.get(12).click();
			list.get(13).click();
			list.get(14).click();
			driver.findElementById("in.dbst.shutappv1.dev:id/image_ok").click();
			driver.findElementById("in.dbst.shutappv1.dev:id/ok").click();
		}
		driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_anonymous_image_checkable").click();
		Log.info("Anonymous Images sent Successfully");
	}

	@Test(priority = 13)
	// This method will send Videos in Bulk
	public void Videos() throws MalformedURLException, InterruptedException {

		for (int i = 0; i < 2; i++) {
			driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_action_attachments").click();
			driver.findElementById("in.dbst.shutappv1.dev:id/chat_attachment_dialog_btn_video").click();
			List<AndroidElement> list = driver.findElements(By.id("in.dbst.shutappv1.dev:id/imageView"));
			list.get(0).click();
			list.get(1).click();
			list.get(2).click();
			list.get(3).click();
			list.get(4).click();
			list.get(5).click();
			list.get(6).click();
			list.get(7).click();
			list.get(8).click();
			list.get(9).click();
			list.get(10).click();
			list.get(11).click();
			list.get(12).click();
			list.get(13).click();
			list.get(14).click();
			driver.findElementById("in.dbst.shutappv1.dev:id/video_ok").click();
			driver.findElementById("in.dbst.shutappv1.dev:id/ok_video").click();
		}
		Log.info("Bulk Videos sent Successfully");
	}

	@Test(priority = 14)
	// This method will send Anonymous Videos in Bulk
	public void AnonymousVideos() throws MalformedURLException, InterruptedException {
		driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_anonymous_image_checkable").click();
		for (int i = 0; i < 2; i++) {
			driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_action_attachments").click();
			driver.findElementById("in.dbst.shutappv1.dev:id/chat_attachment_dialog_btn_video").click();
			List<AndroidElement> list = driver.findElements(By.id("in.dbst.shutappv1.dev:id/imageView"));
			list.get(0).click();
			list.get(1).click();
			list.get(2).click();
			list.get(3).click();
			list.get(4).click();
			list.get(5).click();
			list.get(6).click();
			list.get(7).click();
			list.get(8).click();
			list.get(9).click();
			list.get(10).click();
			list.get(11).click();
			list.get(12).click();
			list.get(13).click();
			list.get(14).click();
			driver.findElementById("in.dbst.shutappv1.dev:id/video_ok").click();
			driver.findElementById("in.dbst.shutappv1.dev:id/ok_video").click();
		}
		driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_anonymous_image_checkable").click();
		Log.info("Anonymous Bulk Videos sent Successfully");
	}

	@Test(priority = 15)
	// This method will send Audio Files in Bulk
	public void BulkAudios() throws MalformedURLException, InterruptedException {

		for (int i = 0; i < 2; i++) {
			driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_action_attachments").click();
			driver.findElementById("in.dbst.shutappv1.dev:id/chat_attachment_dialog_btn_audio").click();
			driver.findElementByAndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"[iSongs.info] 10 - Climax Theme Song.mp3\").instance(0))");
			List<AndroidElement> list = driver.findElements(By.id("in.dbst.shutappv1.dev:id/songname"));
			list.get(0).click();
			list.get(1).click();
			list.get(2).click();
			list.get(3).click();
			list.get(4).click();
			list.get(5).click();
			list.get(6).click();
			list.get(7).click();
			driver.findElementById("in.dbst.shutappv1.dev:id/okBtn").click();
			driver.findElementById("in.dbst.shutappv1.dev:id/ok").click();
		}
		Log.info("Bulk Audios sent Successfully");
	}

	@Test(priority = 16)
	// This method will send Anonymous Audio Files in Bulk
	public void AnonymousAudios() throws MalformedURLException, InterruptedException {

		driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_anonymous_image_checkable").click();
		for (int i = 0; i < 2; i++) {
			driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_action_attachments").click();
			driver.findElementById("in.dbst.shutappv1.dev:id/chat_attachment_dialog_btn_audio").click();
			driver.findElementByAndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"[iSongs.info] 10 - Climax Theme Song.mp3\").instance(0))");
			List<AndroidElement> list = driver.findElements(By.id("in.dbst.shutappv1.dev:id/songname"));
			list.get(0).click();
			list.get(1).click();
			list.get(2).click();
			list.get(3).click();
			list.get(4).click();
			list.get(5).click();
			list.get(6).click();
			list.get(7).click();
			driver.findElementById("in.dbst.shutappv1.dev:id/okBtn").click();
			driver.findElementById("in.dbst.shutappv1.dev:id/ok").click();
		}
		driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_anonymous_image_checkable").click();
		Log.info("Anonymous Bulk Audios sent Successfully");
	}

	// @Test(priority = 14)
	// This method will send Document Files in Bulk
	public void BulkDocument() throws MalformedURLException, InterruptedException {

		for (int i = 0; i < 3; i++) {
			driver.findElementById("in.dbst.shutappv1.dev:id/chat_message_box_action_attachments").click();
			driver.findElementById("in.dbst.shutappv1.dev:id/chat_attachment_dialog_btn_document").click();
//			driver.findElementByAndroidUIAutomator(
//					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"09 Rise of Sultan (Shekhar Ravjiani) 320Kbps.mp3\").instance(0))");
			List<AndroidElement> list = driver.findElements(By.id("in.dbst.shutappv1.dev:id/fileName"));
			list.get(1).click();
			list.get(2).click();
			list.get(3).click();
			list.get(4).click();
			list.get(5).click();
			list.get(6).click();
			list.get(7).click();
			list.get(8).click();
			driver.findElementById("in.dbst.shutappv1.dev:id/okBtn").click();
			driver.findElementById("in.dbst.shutappv1.dev:id/ok").click();
		}
	}
}
