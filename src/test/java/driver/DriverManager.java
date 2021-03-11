package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.HashMap;
import java.util.Map;

public class DriverManager {

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public void createInstance(String browser, String headlessMode) {
        switch (browser) {
            case "CHROME":
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver(setHeadlessModeChrome(Boolean.parseBoolean(headlessMode))));
                break;
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver(setHeadlessModeFirefox(Boolean.parseBoolean(headlessMode))));
                break;
            case "EDGE":
                WebDriverManager.edgedriver().setup();
                driver.set(new EdgeDriver());
                break;
            case "IEXPLORER":
                WebDriverManager.iedriver().setup();
                driver.set(new EdgeDriver());
                break;
            case "SAFARI":
                WebDriverManager.getInstance(DriverManagerType.SAFARI).setup();
                driver.set(new SafariDriver());
                break;
        }
    }

    public ChromeOptions setHeadlessModeChrome(boolean headlessMode) {
        ChromeOptions options = new ChromeOptions().setHeadless(Boolean.parseBoolean(String.valueOf(headlessMode)));
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 375);
        deviceMetrics.put("height", 812);
        deviceMetrics.put("pixelRatio", 3.0);
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 8.0.0;" +
                "iPhone X Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/67.0.3396.99 Mobile Safari/537.36");
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        return options;
}

    public FirefoxOptions setHeadlessModeFirefox(boolean headlessMode) {
        return new FirefoxOptions().setHeadless(Boolean.parseBoolean(String.valueOf(headlessMode)));
    }

    public SafariOptions setOptionsSafari() {
        SafariOptions opt = new SafariOptions();
        new SafariOptions().setCapability("browserstack.safari.allowAllCookies", "true");
        return opt;
    }

}

/*
private static void resolveLocal(String validBrowsers, String arg) {
  log.info("Using WebDriverManager to resolve {}", arg);
  try {
    DriverManagerType driverManagerType = DriverManagerType
        .valueOf(arg.toUpperCase());
    WebDriverManager wdm = WebDriverManager
        .getInstance(driverManagerType).avoidExport()
        .targetPath(".").forceDownload();
    if (arg.equalsIgnoreCase("edge")
        || arg.equalsIgnoreCase("iexplorer")) {
      wdm.operatingSystem(WIN);
    }
    wdm.avoidOutputTree().setup();
  } catch (Exception e) {
    log.error("Driver for {} not found (valid browsers {})", arg,
        validBrowsers);
  }
}
 */


