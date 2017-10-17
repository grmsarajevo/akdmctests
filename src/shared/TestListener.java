package shared;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {

	Charset utf8 = StandardCharsets.UTF_8;
	
    @Override
    public void onTestFailure(ITestResult tr) {
        Throwable th = tr.getThrowable();
        if (th != null) {
            System.out.println(th.getMessage());
            Reporter.log(th.getMessage());
            tr.setThrowable(null);
        }
    }
    
}
