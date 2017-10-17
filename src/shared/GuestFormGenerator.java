package shared;

public class GuestFormGenerator {
	
		String jordan;					// DMC
		String brn;						// Booking reference number  
	  String agency; 					// Agency name
	  String agent;						// Agent name
	  String agentEmail;				// Agent e-mail
	  String iata;						// IATA number
	  
	  String firstName;					// First name
	  String lastName;					// Last name
	  String adress;					// Adress
	  String passEmail;					// e-mail
	  String telephone; 				// Telephone
	  String mobile; 					// Mobile
	  String nationality;				// Nationality
	  String dateOfBirth;				// Date of birth
	  String passNum;					// Passport number
	  String dateOfIssue;				// Passport date of issue
	  String passExpiry;				// Passport expiry
	  String passPlace;					// Passport place of issue
	  
	  String name;						// Name
	  String relationship; 				// Relationship
	  String contactEmail;				// e-mail
	  String contactPhone; 				// Telephone
	  String contactMobile; 			// Mobile
	  
	  String flight;
	String req;
	  String spec;
	  String occasions;
	  String lists;
			
	public GuestFormGenerator() {
		
		String time = String.valueOf(System.currentTimeMillis());
		
		  jordan = "Jordan";							// DMC
		  brn = "123" + time;							// Booking reference number
		  
		  agency = "test"; 								// Agency name
		  agent = "test";								// Agent name
		  agentEmail = "test@test.test";				// Agent e-mail
		  iata = "456";									// IATA number
		  
		  firstName = "test";							// First name
		  lastName = "test";							// Last name
		  adress = "test";								// Adress
		  passEmail = "test@test.test";					// e-mail
		  telephone = "111 222 333"; 					// Telephone
		  mobile = "444 555 666" ; 						// Mobile
		  nationality = "test";							// Nationality
		  dateOfBirth = "19/09/1999";					// Date of birth
		  passNum = "test123";							// Passport number
		  dateOfIssue = "19/09/1999";					// Passport date of issue
		  passExpiry = "19/09/1999";					// Passport expiry
		  passPlace = "test";
		  
		  name = "test";								// Name
		  relationship = "test"; 						// Relationship
		  contactEmail = "test@test.test";				// e-mail
		  contactPhone = "123 456 789"; 				// Telephone
		  contactMobile = "987 654 321"; 				// Mobile
		  
		  flight = "test";
		  req = "test";
		  spec = "test";
		  occasions = "test";
		  lists = "test";
	}
	
	public String getJordan() {
		return jordan;
	}

	public String getBrn() {
		return brn;
	}

	public String getAgency() {
		return agency;
	}

	public String getAgent() {
		return agent;
	}

	public String getAgentEmail() {
		return agentEmail;
	}

	public String getIata() {
		return iata;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAdress() {
		return adress;
	}

	public String getPassEmail() {
		return passEmail;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public String getNationality() {
		return nationality;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getPassNum() {
		return passNum;
	}

	public String getDateOfIssue() {
		return dateOfIssue;
	}

	public String getPassExpiry() {
		return passExpiry;
	}

	public String getName() {
		return name;
	}

	public String getRelationship() {
		return relationship;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public String getContactMobile() {
		return contactMobile;
	}
	
	public String getFlight() {
		return flight;
	}

	public String getReq() {
		return req;
	}

	public String getSpec() {
		return spec;
	}

	public String getOccasions() {
		return occasions;
	}

	public String getLists() {
		return lists;
	}
	public String getPassPlace() {
		return passPlace;
	}
}
