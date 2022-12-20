import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
        features = "step3/step-three-six-degrees.feature",
        glue ="com.step3")
public class TestStep3Runner
{
// This class will be empty
}
