package io.github.travisdeshotels.food.fntest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "summary"},
    features = "src/test/java/io/github/travisdeshotels/food/fntest/features",
    glue = "io.github.travisdeshotels.food.fntest.steps",
    tags = "not @functional")
public class RunCucumberTest{
}
