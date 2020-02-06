package tk.codedojo.food;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "summary"},
    strict = true,
    features = "src/fn-test/java/tk/codedojo/food/features",
    glue = "tk.codedojo.food.steps",
    tags = "not @skip")
public class RunCucumberTest{
}
