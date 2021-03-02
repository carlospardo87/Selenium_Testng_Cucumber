package helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.masterthought.cucumber.*;
import net.masterthought.cucumber.sorting.SortingMethod;


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

        configuration.addClassifications("Platform", System.getProperty("os.name"));
        configuration.addClassifications("Browser", "Chrome");
        configuration.addClassifications("Branch", "release/1.0");

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