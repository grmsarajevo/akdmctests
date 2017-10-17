package shared;
import java.util.Map;
import org.openqa.selenium.chrome.ChromeDriver;


public class MyChromeDriver extends ChromeDriver {

	@Override
	protected org.openqa.selenium.remote.Response execute(String driverCommand, Map<String, ?> parameters) {
	    try {
	        // wait 500 millis and after that run command
	        Thread.sleep(200);
	    } catch (InterruptedException ex) {}
	    return super.execute(driverCommand, parameters);
	}

}
