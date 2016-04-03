/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.appium.java_client.android;

import io.appium.java_client.MobileBy;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class AndroidSearchingTest {

    private static AppiumDriverLocalService service;
    private static AndroidDriver<AndroidElement> driver;

    @BeforeClass public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning())
            throw new RuntimeException("An appium server node is not started!");

        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "ApiDemos-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new AndroidDriver<>(service.getUrl(), capabilities);
    }

    @AfterClass public static void afterClass() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null)
            service.stop();
    }

    @Test  public void findByAccessibilityIdTest() {
        Assert.assertNotEquals(driver.findElement(MobileBy.AccessibilityId("Graphics")).getText(), null);
        Assert.assertEquals(driver.findElements(MobileBy.AccessibilityId("Graphics")).size(), 1);
    }

    @Test  public void findByAndroidUIAutomatorTest() {
        Assert.assertNotEquals(driver.
            findElement(MobileBy.AndroidUIAutomator("new UiSelector().clickable(true)")).getText(), null);
        Assert.assertNotEquals(driver.
            findElements(MobileBy.AndroidUIAutomator("new UiSelector().clickable(true)")).size(), 0);
        Assert.assertNotEquals(driver.
            findElements(MobileBy.AndroidUIAutomator("new UiSelector().clickable(true)")).size(), 1);
    }

    @Test public void findByXPathTest()
    {
        String byXPath = "//android.widget.TextView[contains(@text, 'Animat')]";
        Assert.assertNotNull(driver.findElementByXPath(byXPath).getText());
        Assert.assertEquals(driver.findElementsByXPath(byXPath).size(), 1);
    }
}
