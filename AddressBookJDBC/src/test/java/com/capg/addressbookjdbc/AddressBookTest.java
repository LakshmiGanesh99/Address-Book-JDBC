package com.capg.addressbookjdbc;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class AddressBookTest{
	static AddressBookDB serviceObj;
	static List<Contacts> contactsList;

	@BeforeClass
	public static void setUp()  {
		serviceObj = new AddressBookDB();
		contactsList = new ArrayList<>();	
	}
	
	@Test
	public void givenAddressBookDB_WhenRetrieved_ShouldMatchContactsCount() throws DBServiceException{
		contactsList = serviceObj.viewAddressBook();
		Assert.assertEquals(6, contactsList.size());
	}
	
}
