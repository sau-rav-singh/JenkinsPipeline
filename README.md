# JenkinsPipeline Project

This project is a Java-based automated testing framework utilizing Maven, TestNG, and Rest-Assured. It is designed to be integrated into a CI/CD pipeline using Jenkins.

## Technologies Used
* **Java 21**
* **Maven**: Build and dependency management.
* **TestNG**: Testing framework.
* **Rest-Assured**: API testing library.
* **Jenkins**: CI/CD automation.

## Jenkins Pipeline Integration

The project includes a `Jenkinsfile` that defines a declarative pipeline for automated builds and testing.

### Pipeline Stages
1. **Init**: Loads external Groovy scripts (`script.groovy`).
2. **Build**: Compiles the project and installs dependencies using `mvn clean install`.
3. **Test**: Executes the test suite if the `RUN_SMOKE` parameter is set to `true`.
4. **Deploy**: An optional stage that requires manual approval and branch validation (`main` branch only).

### Pipeline Parameters
When triggering a build in Jenkins, you can configure the following parameters:
* `TARGET_ENV`: Specify the target environment (e.g., staging, dev, prod).
* `RUN_SMOKE`: Boolean flag to determine if the Test stage should execute.
* `BROWSER`: Choose the browser for testing (chrome, firefox, edge).
* `DEPLOY`: Boolean flag to enable the deployment stage.

## How to Configure Which Tests to Run

The selection of tests executed during the pipeline is managed through two main layers:

### 1. Jenkins Parameter Level
The `RUN_SMOKE` parameter in the `Jenkinsfile` acts as a high-level toggle.
* If `RUN_SMOKE` is `true`, the `Test` stage runs `mvn -B test`.
* If `RUN_SMOKE` is `false`, the `Test` stage is skipped entirely.

### 2. TestNG Configuration Level (`testng.xml`)
The specific tests that `mvn test` executes are defined in the `testng.xml` file. The `maven-surefire-plugin` in `pom.xml` is configured to look at this file:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.2.5</version>
    <configuration>
        <suiteXmlFiles>
            <suiteXmlFile>testng.xml</suiteXmlFile>
        </suiteXmlFiles>
    </configuration>
</plugin>
```

To change which tests run on the pipeline:
1. Open `testng.xml`.
2. Add or remove `<class>` or `<methods>` tags within the `<test>` block.
3. For example, to add a new test class, uncomment or add:
   ```xml
   <class name="tests.ComplexJsonPojoTest"/>
   ```
4. Commit and push the changes to your repository. The next Jenkins run will pick up the updated `testng.xml` and execute the newly defined suite.

## Reports and Artifacts
* **Test Results**: JUnit reports are automatically captured from `**/target/surefire-reports/*.xml` and displayed in Jenkins.
* **Artifacts**: Upon a successful build, the resulting JAR files in `**/target/*.jar` are archived.
