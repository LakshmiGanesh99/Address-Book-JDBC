package com.capg.addressbookjdbc;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;



public class AddressBookTest {
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
		assertEquals(6, contactsList.size());
	}
	
	@Test
	public void givenUpdatedContacts_WhenRetrieved_ShouldBeSyncedWithDB() throws DBServiceException{
		serviceObj.updateContactDetails("West Bengal" , "822234" , "Sumit");
		boolean isSynced = serviceObj.isAddressBookSyncedWithDB("Sumit");
		assertTrue(isSynced);
	}
	
}