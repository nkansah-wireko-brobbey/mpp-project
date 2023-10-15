package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;

	private DataAccess da = new DataAccessFacade();
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	@Override
	public boolean memberIdExist(String id){
		List<String> memberList = allMemberIds();
		for (String anId: memberList
			 ) {

			if (anId.equals(id)){
				System.out.println("Duplicate Id");
				return false;
			}

		}
		return true;
	}

	public void saveNewMember(String fname, String lname,
							  String id, String tel, String street,
							  String city, String zip, String state
							  ) throws LibrarySystemException {

		try{
			Address newMemberAddress = new Address(street,city, state,zip);
			LibraryMember newLibraryMember = new LibraryMember(id,fname,lname,tel,newMemberAddress);
			da.saveNewMember(newLibraryMember);
		}catch (Exception e){
			throw new LibrarySystemException("Cannot Save");
		}

	}

	public Book getBookByISBN(String isbn) throws LibrarySystemException{
		HashMap<String, Book> booksMap = da.readBooksMap();
		if(booksMap.get(isbn)==(null)){
			throw new LibrarySystemException("ISBN not found");
		}
		return booksMap.get(isbn);
	}

	public List<Book> getAllBooks(){
		HashMap<String, Book> booksMap = da.readBooksMap();
		List<Book> books = new ArrayList<>(booksMap.values());
		return books;
	}

	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	public boolean bookIdExists(String ISBN){
		HashMap<String, Book> map = da.readBooksMap();
		return map.containsKey(ISBN);
	}

	@Override
	public Book getBook(String ISBN){
		HashMap<String, Book> map = da.readBooksMap();
		return map.get(ISBN);
	}

	public void saveBook(Book book){
		da.saveBook(book);
	}
	public Book copyBook(String ISBN){
		Book book = getBook(ISBN);
		book.addCopy(); // duplicate the book
		saveBook(book);
		return book;
	}

//	public
	public List<LibraryMember> getAllMembers(){

		HashMap<String, LibraryMember> allMembers = da.readMemberMap();

		List<LibraryMember> allMembersList = new ArrayList<>(allMembers.values());
		return allMembersList;
	}
	
}
