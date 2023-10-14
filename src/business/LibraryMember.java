package business;

import java.io.Serializable;
import java.time.LocalDate;


import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

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
	}
	
	
	public String getMemberId() {
		return memberId;
	}

	
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
