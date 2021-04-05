package helpers;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import static driver.DriverManager.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ReportHelper {

    public static void generateCucumberReport() {
        File reportOutputDirectory = new File("target");
        ArrayList<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/cucumber.json");

        String projectName = "testng-cucumber";
        String buildNumber = "1";



        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.setBuildNumber(buildNumber);
        //configuration.setRunWithJenkins(false);
        //configuration.setTagsToExcludeFromChart("@");

        configuration.addClassifications("OS_Platform", System.getProperty("os.name"));
        configuration.addClassifications("OS_Version", System.getProperty("os.version"));
        configuration.addClassifications("Browser_Name", config.getProperty("browser"));
        configuration.addClassifications("URL_Home", config.getProperty("siteUrl"));
        configuration.addClassifications("URL_Hub", config.getProperty("urlHub"));
        configuration.addClassifications("URL_Hub", System.getProperty("systemic.user"));

        //configuration.setSortingMethod(SortingMethod.ALPHABETICAL);


        configuration.setTrendsStatsFile(new File("target/test-classes/demo-trends.json"));



        // optionally add metadata presented on main page via properties file
        List<String> classificationFiles = new ArrayList<>();
        classificationFiles.add("src/test/resources/config/config.properties");
        configuration.addClassificationFiles(classificationFiles);
        configuration.addClassifications("Parallel", "TRUE");


        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
        Reportable result = reportBuilder.generateReports();
    }



}