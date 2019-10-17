/**
 * 
 */
package files;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

/**
 * @author SHARATH
 *
 * 
 */
public class resources {

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
