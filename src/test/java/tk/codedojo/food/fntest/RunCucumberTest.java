package tk.codedojo.food.fntest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "summary"},
    strict = true,
    features = "src/test/java/tk/codedojo/food/fntest/features",
    glue = "tk.codedojo.food.steps",
    tags = "not @functional")
public class RunCucumberTest{
}
