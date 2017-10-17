package shared;

public class SharedData {
	private static String devSite;
	private static String site;
	private static String driver;
	private static String driverLocation;
	
	public SharedData() {
		site = "http://www.uat.akdmc.com/";
		devSite = "http://akdmc-dev.green-river-media.com/";
		driver = "webdriver.gecko.driver";
		driverLocation = "\\drivers\\geckodriver.exe";
	}

	public String getSite() {
		return site;
	}
	public String getDevSite() {
		return devSite;
	}
	public String getDriver() {
		return driver;
	}
	public String getDriverLocation() {
		return driverLocation;
	}
}
