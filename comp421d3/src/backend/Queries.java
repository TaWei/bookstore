package backend;

public interface Queries {
	public String addUser(String userName, String firstName, String lastName, String email, String password);
	public String addBook(String isbn, String title, String summary, String genre, int stock);	
	public String getBooksGreaterThan4Stars(String userName);
	public String getBestCustomers(int number);
	public String getMostPopularGenres(int number);
	public String activateOrUpdateCoupons(String couponCode, String validUntil);
}
