package shared;

public class NewsletterFormGenerator {
	String time;
	String firstName;
	String lastName;
	String email;
	String agency;
	String consortia;
	
	public NewsletterFormGenerator() {
		time = String.valueOf(System.currentTimeMillis());
		
		firstName = "test" + time;
		lastName = "test";
		email = "test@test.test";
		agency = "test";
		consortia = "test";
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	public String getAgency() {
		return agency;
	}
	public String getConsortia() {
		return consortia;
	}
	
}
