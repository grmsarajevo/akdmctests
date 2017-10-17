package shared;

public class PaymentFormGenerator {
	String time;
	
	String bookingRef;
	String guestLastName;
	String agencyName;
	String iata;
	String agentName;
	String agentEmail;
	
	public PaymentFormGenerator() {
		time = String.valueOf(System.currentTimeMillis());
		
		bookingRef = "test" + time;
		guestLastName = "test";
		agencyName = "test" + time;
		iata = "test";
		agentName = "test" + time;
		agentEmail = "test@test.test";
	}
	
	public String getTime() {
		return time;
	}
	public String getBookingRef() {
		return bookingRef;
	}
	public String getGuestLastName() {
		return guestLastName;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public String getIata() {
		return iata;
	}
	public String getAgentName() {
		return agentName;
	}
	public String getAgentEmail() {
		return agentEmail;
	}	
}
