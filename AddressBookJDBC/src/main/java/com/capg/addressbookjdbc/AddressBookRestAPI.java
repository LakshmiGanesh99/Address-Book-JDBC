package com.capg.addressbookjdbc;

import java.util.ArrayList;
import java.util.List;

public class AddressBookRestAPI {
	
	List<Contacts> contactsList;
	
    public AddressBookRestAPI(List<Contacts> contactList) {
		contactsList=new ArrayList<>(contactList);
	}
	public long countREST_IOEntries() {
		return contactsList.size();
	}
}