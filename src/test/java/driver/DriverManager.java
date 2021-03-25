package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public void createInstance(String browser, String headlessMode) {
      /*  URL url = null;
        try {
            url = new URL("http://localhost:4444/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
        switch (browser) {
            case "CHROME":
                WebDriverManager.chromedriver().setup();
                try {
                    driver.set(new RemoteWebDriver(URI.create("http://localhost:4444/wd/hub").toURL(),setHeadlessModeChrome(Boolean.parseBoolean(headlessMode))));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                try {
                    driver.set(new RemoteWebDriver(URI.create("http://localhost:4444/wd/hub").toURL(),setHeadlessModeFirefox(Boolean.parseBoolean(headlessMode))));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
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
        options.addArguments("--disable-dev-shm-usage");
        Map<String, Object> value = new HashMap<>();
        value.put("enableVNC", true);
        value.put("enableVideo", false);
        options.setCapability("selenoid:options", value);

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
        FirefoxOptions options = new FirefoxOptions().setHeadless(Boolean.parseBoolean(String.valueOf(headlessMode)));
        Map<String, Object> value = new HashMap<>();
        value.put("enableVNC", true);
        value.put("enableVideo", false);
        options.setCapability("selenoid:options", value);
        return options;
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


