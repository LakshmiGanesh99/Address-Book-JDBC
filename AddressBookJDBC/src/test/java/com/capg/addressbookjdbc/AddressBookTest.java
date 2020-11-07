package com.capg.addressbookjdbc;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class AddressBookTest{
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
		Assert.assertEquals(6, contactsList.size());
	}
	
	@Test
	public void givenUpdatedContacts_WhenRetrieved_ShouldBeSyncedWithDB() throws DBServiceException{
		serviceObj.updateContactDetails("West Bengal" , "822234" , "Sumit");
		boolean isSynced = serviceObj.isAddressBookSyncedWithDB("Sumit");
		Assert.assertTrue(isSynced);
	}
	
	@Test
	public void givenDateRange_WhenRetrieved_ShouldMatchContactsCount() throws DBServiceException{
		contactsList = serviceObj.viewContactsByDateRange(LocalDate.of(2018,02,01), LocalDate.now() );
		Assert.assertEquals(3, contactsList.size());
	}
	
	@Test
	public void givenAddressDB_WhenRetrievedCountByState_ShouldReturnCountGroupedByState() throws DBServiceException {
		contactsCount = serviceObj.countContactsByCityOrState("state");
		Assert.assertEquals(3, contactsCount.get("Telangana") , 0);
		Assert.assertEquals(1, contactsCount.get("Goa"), 0);
		Assert.assertEquals(1, contactsCount.get("AP"), 0);
		Assert.assertEquals(1, contactsCount.get("Maharashtra"), 0);
	}
	
	@Test
	public void givenAddressDB_WhenRetrievedCountByCity_ShouldReturnCountGroupedByCity() throws DBServiceException {
		contactsCount = serviceObj.countContactsByCityOrState("city");
		Assert.assertEquals(2, contactsCount.get("Pune") , 0);
		Assert.assertEquals(2, contactsCount.get("Hyderabad"), 0);
		Assert.assertEquals(1, contactsCount.get("Mumbai"), 0);
		Assert.assertEquals(1, contactsCount.get("Vzag"), 0);
		
	}
	
	@Test
	public void givenContactData_WhenAddedToDB_ShouldSyncWithDB() throws DBServiceException {
		serviceObj.insertNewContactToDB("Koushik","Kiran","BookA","Friend","Hyderabad","Telangana",
				"telangana","520120","2232123212","abc@gmail.com","2020-07-18");
		boolean isSynced = serviceObj.isAddressBookSyncedWithDB("Raj");
		Assert.assertTrue(isSynced);
	}
	
	@Test
	public void givenMultipleContact_WhenAdded_ShouldSyncWithDB() throws DBServiceException {
		Contacts[] contactArr= {
								new Contacts("Bhargav","MLN","BookB","Family","Home",
										"Hyderabad","Telangana","887622","1231231231", "mln@gmail.com","2018-05-12"),
								new Contacts("Koushik","MLN","BookA","Friend","Home","Hyderabad",
										"Telangana","011544","91456321782","xyz@gmail.com","2017-08-29"),
								new Contacts("Gnaesh","Karanam","BookC","Professional","Vizag","AP",
										"AP", "820056","9154263987","def@yahoo.com","2020-05-15"),
		};
		serviceObj.addMultipleContactsToDBUsingThreads(Arrays.asList(contactArr));
		boolean isSynced1 = serviceObj.isAddressBookSyncedWithDB("Bhargav");
		boolean isSynced2 = serviceObj.isAddressBookSyncedWithDB("Koushik");
		boolean isSynced3 = serviceObj.isAddressBookSyncedWithDB("Ganesh");
		Assert.assertTrue(isSynced1);
		Assert.assertTrue(isSynced2);
		Assert.assertTrue(isSynced3);
	}
}