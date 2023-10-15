package business;

import java.util.HashMap;
import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	boolean memberIdExist(String id);
//	void saveNewMember(LibraryMember libraryMember) throws LibrarySystemException;
	void saveNewMember(String fname, String lname,
					   String id, String tel, String street,
					   String city, String zip, String state
	) throws LibrarySystemException;
	List<LibraryMember> getAllMembers();

	public List<Book> getAllBooks();

	public boolean bookIdExists(String ISBN);
	public Book getBook(String ISBN);

	public void saveBook(Book book);
	public Book copyBook(String ISBN);
}
