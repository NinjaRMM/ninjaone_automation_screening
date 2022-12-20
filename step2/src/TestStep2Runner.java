import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
        features = "step2/step-two-filter-movies.feature",
        glue ="com.step2")
public class TestStep2Runner
{
// This class will be empty
}
