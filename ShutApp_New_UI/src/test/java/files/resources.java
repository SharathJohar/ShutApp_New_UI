/**
 * 
 */
package files;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.shutapp.newui.loginflow.AppiumServer;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

/**
 * @author SHARATH
 *
 * 
 */
public class resources extends AppiumServer {

	static Logger Log = LogManager.getLogger(resources.class);

	public static void FillYourProfilecamera(AndroidDriver<AndroidElement> driver) throws MalformedURLException, InterruptedException {

		driver.findElementById("in.dbst.shutappv1.dev:id/profile_pic").click();
		Log.info("Clicked on Avatar");
		driver.findElementById("in.dbst.shutappv1.dev:id/chat_attachment_dialog_btn_camera").click();
		Log.info("Camera selected");
		driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
		driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
		driver.findElementByXPath("//GLButton[@text='Shutter']").click(); // --Samsung--//
		Log.info("Image captured");
		driver.findElementById("com.sec.android.app.camera:id/okay").click(); // --Samsung--//
		Log.info("click on Done");

	}

	public static void FillYourProfileGallery(AndroidDriver<AndroidElement> driver) throws InterruptedException {
		driver.findElementById("in.dbst.shutappv1.dev:id/profile_pic").click();
		Log.info("Clicked on Avatar");
		driver.findElementById("in.dbst.shutappv1.dev:id/chat_attachment_dialog_btn_gallery").click();
		Log.info("Gallery selected");
		driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
		List<AndroidElement> list = driver.findElements(By.id("in.dbst.shutappv1.dev:id/imageView"));
		list.get(0).click();
		Log.info("Gallery accessed");
	}

	public static void SwipeScreen(WebElement el, WebDriver driver) throws InterruptedException {

		el = driver.findElement(By.id("in.dbst.shutappv1.dev:id/onboard_view_pager"));
		WebElement Panel = el;
		Dimension dimension = Panel.getSize();

		int Anchor = Panel.getSize().getHeight() / 2;

		Double ScreenWidthStart = dimension.getWidth() * 0.9;
		int scrollStart = ScreenWidthStart.intValue();

		Double ScreenWidthEnd = dimension.getWidth() * 0.2;
		int scrollEnd = ScreenWidthEnd.intValue();

		new TouchAction((PerformsTouchActions) driver).press(PointOption.point(scrollStart, Anchor))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(scrollEnd, Anchor))
				.release().perform();
		Thread.sleep(3000);
	}

}
