package com.capg.addressbookjdbc;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;


public class AddressBookTest {
	static AddressBookDB serviceObj;
	static List<Contacts> contactsList;
	static Map<String, Integer> contactsCount;

	@BeforeClass
	public static void setUp()  {
		serviceObj = new AddressBookDB();
		contactsList = new ArrayList<>();
		contactsCount = new HashMap<>();
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
	
	@Test
	public void givenDateRange_WhenRetrieved_ShouldMatchContactsCount() throws DBServiceException{
		contactsList = serviceObj.viewContactsByDateRange(LocalDate.of(2018,02,01), LocalDate.now() );
		assertEquals(3, contactsList.size());
	}
	
	@Test
	public void givenAddressDB_WhenRetrievedCountByState_ShouldReturnCountGroupedByState() throws DBServiceException {
		contactsCount = serviceObj.countContactsByCityOrState("state");
		assertEquals(3, contactsCount.get("Jharkhand") , 0);
		assertEquals(1, contactsCount.get("West Bengal"), 0);
		assertEquals(1, contactsCount.get("Uttar Pradesh"), 0);
		assertEquals(1, contactsCount.get("Bihar"), 0);
	}
	
	@Test
	public void givenAddressDB_WhenRetrievedCountByCity_ShouldReturnCountGroupedByCity() throws DBServiceException {
		contactsCount = serviceObj.countContactsByCityOrState("city");
		assertEquals(1, contactsCount.get("Panki") , 0);
		assertEquals(1, contactsCount.get("Ranchi"), 0);
		assertEquals(1, contactsCount.get("Etawah"), 0);
		assertEquals(1, contactsCount.get("Garhwa"), 0);
		assertEquals(1, contactsCount.get("Aurangabad"), 0);
		assertEquals(1, contactsCount.get("Dumka"), 0);
	}
}
