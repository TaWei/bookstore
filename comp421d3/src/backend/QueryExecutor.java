package backend;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryExecutor implements Queries {
	private Connection conn;
	private Statement stm;
	
	public QueryExecutor(Connection conn){
		this.conn = conn;
		try{
			this.stm = this.conn.createStatement();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public String addUser(String userName, String firstName, String lastName,
			String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBook(String isbn, String title, String summary,
			String genre, int stock) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBooksGreaterThan4Stars(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBestCustomers(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMostPopularGenres(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String activateOrUpdateCoupons(String couponCode, String validUntil) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void createTestTable(){
	    String createSQL = "CREATE TABLE test (id INTEGER, name VARCHAR (25)) ";
	    try {
			this.stm.execute(createSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void dropTestTable(){
		String dropSQL = "DROP TABLE test";
		try {
			this.stm.execute(dropSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		DB2Connection db2Conn = new DB2Connection("jdbc:db2://db2.cs.mcgill.ca:50000/cs421", "cs421g18", "bs2014[$");
		Connection conn = db2Conn.getConnection();
		QueryExecutor qe = new QueryExecutor(conn);
		
		//To test connection - If working from home, you need to be VPN'd
		//qe.createTestTable();
		qe.dropTestTable();
	}
}
