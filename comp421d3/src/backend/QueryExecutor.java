package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryExecutor{
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
	
	public String addUser(String userName, String firstName, String lastName, String email, String password) {
		String sqlInsert = "insert into User values('"+userName+"','"+firstName+"','"+lastName+"','"+email+"','"+password+"')";
		String output = null;
		
		try{
			stm.execute(sqlInsert);
		}catch(SQLException e){
			output = "An error occured while attempting to insert -- "+e.toString();
		}
		
		if(output == null){
			output = "Insert was successfull.";
		}
		
		return output;
	}

	public String addBook(String isbn, String title, String summary, String genre, int stock) {
		String sqlInsert = "insert into Book values('"+isbn+"','"+title+"','"+summary+"','"+genre+"',"+stock+")";
		String output = null;
		
		try{
			stm.execute(sqlInsert);
		}catch(SQLException e){
			output = "An error occured while attempting to insert -- "+e.toString();
		}
		
		if(output == null){
			output = "Insert was successfull.";
		}
		
		return output;
	}

	public String getBooksGreaterThanNStarsForUser(String userName, double stars) {
		String sqlSelect  = "select b.title, avg(r.stars) as avg_stars "
							+ "from Book as b, Reviews as r, reviewFor as rF "
							+ "where b.isbn = rf.isbn and r.reviewId = rF.reviewId "
							+ "and b.isbn in (select c.isbn from contains as c, makes as m where c.orderId = m.orderId and m.userName = '"+userName+"') "
							+ "group by b.isbn, b.title "
							+ "having avg(r.stars) >= "+stars;				
		ResultSet result;
		String output = "";
		
		try{
			result = stm.executeQuery(sqlSelect);
			output += "Title\t\tAvg Stars\n";
			output += "------\t\t--------\n";
			while(result.next()){
			  output += result.getString("title")+"\t"+result.getString("avg_stars")+"\n";
			}
		}catch(SQLException e){
			output = "An error occured while executing the query: "+e.toString();
		}
		System.out.println(output);
		
		return output;
	}

	
	public String getNBestCustomersForYear(int number, int year) {
		String sqlSelect = "select m.username, count(*) as orders_made "
							+ "from makes as m, orders as o "
							+ "where m.orderId = o.orderId and o.orderDate >= '"+year+"-01-01 00:00:00' and o.orderDate < '"+(year+1)+"-01-01 00:00:00' "
							+ "group by m.username "
							+ "order by count(*) desc "
							+ "fetch first "+number+" rows only";
		ResultSet result;
		String output = "";
		
		try{
			result = stm.executeQuery(sqlSelect);
			output += "Username\tOrders_Made\n";
			output += "--------\t-----------\n";
			while(result.next()){
				output += result.getString("username")+"\t\t"+result.getString("orders_made")+"\n";
			}
		}catch(SQLException e){
			output = "An error occured while executing the query: "+e.toString();
		}
		System.out.println(output);
		
		return output;
	}

	public String getNMostPopularGenresForYear(int number, int year) {
		// TODO Auto-generated method stub
		String sqlSelect = "select b.genre, count(*) as orders_made "
							+ "from book as b, contains as c, orders as o "
							+ "where c.isbn = b.isbn and c.orderId = o.orderId and o.orderDate >= '"+year+"-01-01 00:00:00' and o.orderDate < '"+(year+1)+"-01-01 00:00:00' "
							+ "group by b.genre "
							+ "order by count(*) desc "
							+ "fetch first "+number+" rows only";
		ResultSet result;
		String output = "";
		
		try{
			result = stm.executeQuery(sqlSelect);
			output += "Genre\t\tOrders_Made\n";
			output += "--------\t-----------\n";
			while(result.next()){
				output += result.getString("genre")+"\t\t"+result.getString("orders_made")+"\n";
			}
		}catch(SQLException e){
			output = "An error occured while executing the query: "+e.toString();
		}
		System.out.println(output);
		
		return output;
	}

	public String activateOrUpdateCoupons(String couponCode, int yearEnd, int monthEnd, int dayEnd, String timeEnd) {
		String sqlUpdate = "update Coupons "
							+ "set validFrom = CURRENT_TIMESTAMP, validUntil='"+yearEnd+"-"+monthEnd+"-"+dayEnd+" "+timeEnd+"'"
							+ "where couponCode='"+couponCode+"'";
		String output = null;
		
		try{
			stm.execute(sqlUpdate);
		}catch(SQLException e){
			output = "An error occured while attempting to update -- "+e.toString();
		}
		
		if(output == null){
			output = "Update was successfull.";
		}
		
		return output;
	}
	
	public String changeDestAddressOfActiveOrder(int orderId, int addrId) {
		String sqlUpdate = "update Orders "
							+ "set addrId = "+addrId
							+"where orderId = "+orderId;
		String output = null;
		
		try{
			stm.execute(sqlUpdate);
		}catch(SQLException e){
			output = "An error occured while attempting to update -- "+e.toString();
		}
		
		if(output == null){
			output = "Update was successfull.";
		}
		
		return output;
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
		//qe.dropTestTable();
		
		//qe.addUser("rjberfer22", "Robert", "Berger", "rberger222@gmail.com", "blagagaga");
		//qe.addBook("977-0316015844", "Head First Design Patterns", "Design pattern books for dummies", "Computer Science", 100);
		//qe.getBooksGreaterThanNStarsForUser("kpatel", 1);
		//qe.getNBestCustomersForYear(10, 2014);
		//qe.getNMostPopularGenresForYear(10, 2014);
	}
}
