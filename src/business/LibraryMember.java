package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import static java.time.LocalDate.now;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;

	public String getFname() {
		return fname;
	}

	public String getLname() {
		return lname;
	}

	public String getTel() {
		return tel;
	}

	private String fname;
	private String lname;
	private String tel;

	@Override
	public Address getAddress() {
		return address;
	}

	private Address address;


	
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;
		this.fname = fname;
		this.lname = lname;
		this.tel = tel;
		this.address = add;
		this.checkoutRecords = new ArrayList<>();
	}
	
	
	public String getMemberId() {
		return memberId;
	}

	
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}
	public void checkout(BookCopy copy, LocalDate checkoutDate, LocalDate dueDate) {
		copy.changeAvailability();
		CheckoutEntry entry = new CheckoutEntry(copy, checkoutDate, dueDate);
		Checkout checkoutRecord = new Checkout(this, List.of(entry));
//        checkoutRecord.addEntry(entry);
		this.addCheckoutRecord(checkoutRecord);
	}

	public void checkout(BookCopy copy, int maxCheckoutLength) {
		checkout(copy, now(), now().plusDays(maxCheckoutLength));
	}
	private final List<Checkout> checkoutRecords;
	public void addCheckoutRecord(Checkout record) {
		this.checkoutRecords.add(record);
	}
	public List<Checkout> getCheckoutRecords() {
		return Collections.unmodifiableList(checkoutRecords);
	}
	private static final long serialVersionUID = -2226197306790714013L;
}
