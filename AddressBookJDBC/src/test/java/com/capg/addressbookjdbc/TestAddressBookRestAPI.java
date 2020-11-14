package com.capg.addressbookjdbc;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestAddressBookRestAPI {
	AddressBookDB serviceObj;
	@Before
	public void setup()
	{
		RestAssured.baseURI="http://localhost";
		RestAssured.port=3000;
		serviceObj = new AddressBookDB();
	}
	
	public Contacts[] getAddressBookContactList()
	{
		Response response=RestAssured.get("/AddressBookFile");
		Contacts gsonToContacts[] = new Gson().fromJson(response.asString(),Contacts[].class);
		return gsonToContacts;
	}
	
    @Test
    public void givenContactsInJsonServer_WhenRetrieved_ShouldMatchTotalCount()
    {
    	Contacts gsonContacts[] = getAddressBookContactList();
    	AddressBookRestAPI restApiObj;
        restApiObj = new AddressBookRestAPI(Arrays.asList(gsonContacts));
        long count = restApiObj.countREST_IOEntries();
        assertEquals(3,count);
    }
    
    @Test
    public void addedNewEmployee_ShouldMatch201ResponseAndTotalCount() throws DBServiceException
    {
    	Contacts gsonContacts[] = getAddressBookContactList();
    	serviceObj.addNewContactsUsingRestAPI(Arrays.asList(gsonContacts));
    	Contacts newContact = new Contacts("Santosh","Kumar","Friend_Book","Friend","Ramgarh",
    										  "Puri","Orrisa","833633","8765432187","djbabu@gmail.com","2019-03-27");
    	Response response = addContactToJsonServer(newContact);
    	int HTTPstatusCode = response.getStatusCode();
    	assertEquals(201,HTTPstatusCode);
    	newContact = new Gson().fromJson(response.asString(),Contacts.class);
    	serviceObj.addNewContactsUsingRestAPI(Arrays.asList(newContact));
    	long count = serviceObj.entryCount();
    	assertEquals(3,count);
    }
    public Response addContactToJsonServer(Contacts newContact) {
		String gsonString = new Gson().toJson(newContact);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type","application/json");
		request.body(gsonString);
		return request.post("/AddressBookFile");
	}
}