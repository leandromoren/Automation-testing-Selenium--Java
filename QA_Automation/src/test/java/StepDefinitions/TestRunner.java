package StepDefinitions;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions (
        features = "classpath:Features",
        glue = "classpath:StepDefinitions",
        tags = "@SmokeTest"
)
public class TestRunner
{
}
