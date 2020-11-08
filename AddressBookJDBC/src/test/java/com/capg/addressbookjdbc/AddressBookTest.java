package com.capg.addressbookjdbc;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
	@Ignore
	@Test
	public void givenAddressBookDB_WhenRetrieved_ShouldMatchContactsCount() throws DBServiceException{
		contactsList = serviceObj.viewAddressBook();
		assertEquals(7, contactsList.size());
	}
	@Ignore
	@Test
	public void givenUpdatedContacts_WhenRetrieved_ShouldBeSyncedWithDB() throws DBServiceException{
		serviceObj.updateContactDetails("West Bengal" , "822234" , "Sumit");
		boolean isSynced = serviceObj.isAddressBookSyncedWithDB("Sumit");
		assertTrue(isSynced);
	}
	@Ignore
	@Test
	public void givenDateRange_WhenRetrieved_ShouldMatchContactsCount() throws DBServiceException{
		contactsList = serviceObj.viewContactsByDateRange(LocalDate.of(2018,02,01), LocalDate.now() );
		assertEquals(3, contactsList.size());
	}
	@Ignore
	@Test
	public void givenAddressDB_WhenRetrievedCountByState_ShouldReturnCountGroupedByState() throws DBServiceException {
		contactsCount = serviceObj.countContactsByCityOrState("state");
		assertEquals(3, contactsCount.get("Jharkhand") , 0);
		assertEquals(1, contactsCount.get("West Bengal"), 0);
		assertEquals(1, contactsCount.get("Uttar Pradesh"), 0);
		assertEquals(1, contactsCount.get("Bihar"), 0);
	}
	@Ignore
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
	@Ignore
	@Test
	public void givenContactData_WhenAddedToDB_ShouldSyncWithDB() throws DBServiceException {
		serviceObj.insertNewContactToDB("Raj","Kishore","Friend_Book","Friend","68/1 Srishti Complex","Patna",
				"Bihar","897654","8734120000","kishoreji@gmail.com","2018-08-08");
		boolean isSynced = serviceObj.isAddressBookSyncedWithDB("Raj");
		assertTrue(isSynced);
	}
	@Test
	public void givenMultipleContact_WhenAdded_ShouldSyncWithDB() throws DBServiceException {
		Contacts[] contactArr= {
								new Contacts("Pankaj","Gupta","Family_Book","Family","Obra",
										"Aurangabad","Bihar","887622","8465216975", "pkg@gmail.com","2018-05-12"),
								new Contacts("Sandeep","Soni","Friend_Book","Friend","Garhwa","Garhwa",
										"Jharkhand","882002","9976549999","pm@gmail.com","2017-08-29"),
								new Contacts("Gaurav","Mridul","Professional_Book","Professional","Dumka","Sampak",
										"Jharkhand", "820056","9648515621","rkboi@yahoo.com","2020-05-15"),
		};
		serviceObj.addMultipleContactsToDBUsingThreads(Arrays.asList(contactArr));
		boolean isSynced1 = serviceObj.isAddressBookSyncedWithDB("Pankaj");
		boolean isSynced2 = serviceObj.isAddressBookSyncedWithDB("Sandeep");
		boolean isSynced3 = serviceObj.isAddressBookSyncedWithDB("Gaurav");
		assertTrue(isSynced1);
		assertTrue(isSynced2);
		assertTrue(isSynced3);
	}
}