package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QueryExecutor{
	private Statement stm;
	
	public QueryExecutor(){
		DB2Connection db2Conn = new DB2Connection("jdbc:db2://db2.cs.mcgill.ca:50000/cs421", "cs421g18", "bs2014[$");
		Connection conn = db2Conn.getConnection();
		try{
			this.stm = conn.createStatement();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void addUser() {
		String userName, firstName, lastName, email, password;
		Scanner keyboard = new Scanner(System.in);
		String output = "";
		
		//Get the input from the user for all the attributes in the User table
		System.out.print("Enter the user name: ");
		userName = keyboard.nextLine();
		System.out.print("Enter the first name: " );
		firstName = keyboard.nextLine();
		System.out.print("Enter the last name: ");
		lastName = keyboard.nextLine();
		System.out.print("Enter the email: ");
		email = keyboard.nextLine();
		System.out.print("Enter the password: ");
		password = keyboard.nextLine();
		
		try{
			//Execute the insert
			String sqlInsert = "insert into User values('"+userName+"','"+firstName+"','"+lastName+"','"+email+"','"+password+"')";
			stm.execute(sqlInsert);
			output = "Insert was successfull";
			System.out.println();
			System.out.println(output);
		}catch(SQLException e){
			output = "An error occured while attempting to insert -- "+e.toString();
			System.out.println(output);
		}
	}

	public void addBook() {
		Scanner keyboard = new Scanner(System.in);
		String isbn, title, summary, genre;
		int stock;
		String output = "";

		//Get the input from the user for all the attributes in the Book table
		System.out.print("Enter the isbn: ");
		isbn = keyboard.nextLine();
		System.out.print("Enter the title: " );
		title = keyboard.nextLine();
		System.out.print("Enter the summary: ");
		summary = keyboard.nextLine();
		System.out.print("Enter genre: ");
		genre = keyboard.nextLine();
		System.out.print("Enter the stock: ");
		stock = keyboard.nextInt();
		
		try{
			//Execute the insert
			String sqlInsert = "insert into Book values('"+isbn+"','"+title+"','"+summary+"','"+genre+"',"+stock+")";
			stm.execute(sqlInsert);
			output = "Insert was successfull";
			System.out.println();
			System.out.println(output);
		}catch(SQLException e){
			output = "An error occured while attempting to insert -- "+e.toString();
			System.out.println(output);
		}
	}

	public void getBooksGreaterThanNStarsForUser() {
		Scanner keyboard = new Scanner(System.in);
		int choice;
		String userName;
		double stars;
		ResultSet result;
		String output=""; 
		
		try{
		  //Display the available users first
		  output = "";
		  String sqlGetUsers = "select username, firstname, lastname from User";
		  result = stm.executeQuery(sqlGetUsers);
		  output += "Choice\tUsername\n";
		  output += "------\t--------\n";
		  Map<Integer, String>choiceToUsername = new HashMap<Integer, String>();
		  int count = 1;
		  while(result.next()){
			  output += count+"\t"+result.getString("username")+"\n";
			  choiceToUsername.put(count, result.getString("username"));
			  count++;
		  }
		  System.out.println(output);
		  
		  //Get the user and stars
		  System.out.print("Choose a user: ");
		  choice = keyboard.nextInt();
		  userName = choiceToUsername.get(choice);
		  System.out.print("Enter the minimum avgerage stars the ordered book should have: ");
		  stars = keyboard.nextDouble();
		  
		  //Execute the query based on the user
		  String sqlSelect  = "select b.title, avg(r.stars) as avg_stars "
					+ "from Book as b, Reviews as r, reviewFor as rF "
					+ "where b.isbn = rf.isbn and r.reviewId = rF.reviewId "
					+ "and b.isbn in (select c.isbn from contains as c, makes as m where c.orderId = m.orderId and m.userName = '"+userName+"') "
					+ "group by b.isbn, b.title "
					+ "having avg(r.stars) >= "+stars;		
		  output="";
		  result = stm.executeQuery(sqlSelect);
		  output += "Title\t\tAvg Stars\n";
		  output += "------\t\t--------\n";
		  while(result.next()){
			  output += result.getString("title")+"\t"+result.getString("avg_stars")+"\n";
		  }
		  System.out.println();
		  System.out.println(output);
		}catch(SQLException e){
			output = "An error occured while executing the query: "+e.toString();
			System.out.print(output);
		}		
	}

	
	public void getNBestCustomersForYear() {
		Scanner keyboard = new Scanner(System.in);
		int choice;
		int noUsers, year;
		ResultSet result;
		String output="";
	
		try{
			//Get the number of users to include as well as the year to query
			System.out.println("Enter the number of top users to include: ");
			noUsers = keyboard.nextInt();
			System.out.println("Enter the year to query: ");
			year = keyboard.nextInt();
			
			//Execute the query
			String sqlSelect = "select m.username, count(*) as orders_made "
					+ "from makes as m, orders as o "
					+ "where m.orderId = o.orderId and o.orderDate >= '"+year+"-01-01 00:00:00' and o.orderDate < '"+(year+1)+"-01-01 00:00:00' "
					+ "group by m.username "
					+ "order by count(*) desc "
					+ "fetch first "+noUsers+" rows only";
			output = "";
			result = stm.executeQuery(sqlSelect);
			output += "Username\tOrders_Made\n";
			output += "--------\t-----------\n";
			while(result.next()){
				output += result.getString("username")+"\t\t"+result.getString("orders_made")+"\n";
			}
			System.out.println();
			System.out.println(output);
		}catch(SQLException e){
			output = "An error occured while executing the query: "+e.toString();
			System.out.println(output);
		}
	}

	public void getNMostPopularGenresForYear() {
		Scanner keyboard = new Scanner(System.in);
		int choice;
		int noGenres, year;
		ResultSet result;
		String output = "";

		try{
			//Get the number of top results to include as well as the year to query
			System.out.print("Enter the number of top genres to include: ");
			noGenres = keyboard.nextInt();
			System.out.print("Enter the year to query: ");
			year = keyboard.nextInt();
			
			//Execute the query
			String sqlSelect = "select b.genre, count(*) as orders_made "
					+ "from book as b, contains as c, orders as o "
					+ "where c.isbn = b.isbn and c.orderId = o.orderId and o.orderDate >= '"+year+"-01-01 00:00:00' and o.orderDate < '"+(year+1)+"-01-01 00:00:00' "
					+ "group by b.genre "
					+ "order by count(*) desc "
					+ "fetch first "+noGenres+" rows only";
			output = "";
			result = stm.executeQuery(sqlSelect);
			output += "Genre\t\tOrders_Made\n";
			output += "--------\t-----------\n";
			while(result.next()){
				output += result.getString("genre")+"\t\t"+result.getString("orders_made")+"\n";
			}
			System.out.println();
			System.out.println(output);
		}catch(SQLException e){
			output = "An error occured while executing the query: "+e.toString();
			System.out.println(output);
		}
	}

	public void changeCouponExpirationDate() {
		Scanner keyboard = new Scanner(System.in);
		int choice;
		String couponCode;
		int yearEnd, monthEnd, dayEnd;
		String timeEnd;
		ResultSet result;
		String output = "";

		try{
			//Display the coupon codes
			output = "";
			String sqlGetCoupons = "select couponcode from Coupons";
			result = stm.executeQuery(sqlGetCoupons);
			output += "Choice\tCouponcode\n";
			output += "------\t----------\n";
			Map<Integer, String>choiceToCoupon = new HashMap<Integer, String>();
			int count = 1;
			while(result.next()){
		      output += count+"\t"+result.getString("couponcode")+"\n";
		      choiceToCoupon.put(count, result.getString("couponcode"));
			  count++;
			}
			System.out.println(output);
			
			//Get the coupon code and the date to extend until.
			System.out.print("Enter choice of coupon: ");
			choice = keyboard.nextInt();
			couponCode = choiceToCoupon.get(choice);
			System.out.print("Enter the year the coupon is valid until: ");
			yearEnd = keyboard.nextInt();
			System.out.print("Enter the month the coupon is valid until: ");
			monthEnd = keyboard.nextInt();
			System.out.print("Ener the day the coupon is valid until: ");
			dayEnd = keyboard.nextInt();
			System.out.print("Enter the time (hh:mm:ss) the coupon is valid until: ");
			timeEnd = keyboard.next();
			
			//Execute the update 
			String sqlUpdate = "update Coupons "
					+ "set validFrom = CURRENT_TIMESTAMP, validUntil='"+yearEnd+"-"+monthEnd+"-"+dayEnd+" "+timeEnd+"'"
					+ "where couponCode='"+couponCode+"'";
			stm.execute(sqlUpdate);
			output = "Update was successfull";
			System.out.println();
			System.out.println(output);
		}catch(SQLException e){
			output = "An error occured while attempting to update -- "+e.toString();
			System.out.println(output);
		}

	}
	
	public void changeDestAddressOfActiveOrder() {
		Scanner keyboard = new Scanner(System.in);
		int choice;
		int orderId;
		int addrId;
		String userName;
		ResultSet result;
		String output=""; 
		
		try{
			//Display the available users first
			output = "";
			String sqlGetUsers = "select username, firstname, lastname from User";
			result = stm.executeQuery(sqlGetUsers);
			output += "Choice\tUsername\n";
			output += "------\t--------\n";
			Map<Integer, String>choiceToUsername = new HashMap<Integer, String>();
			int count = 1;
			while(result.next()){
				output += count+"\t"+result.getString("username")+"\n";
				choiceToUsername.put(count, result.getString("username"));
				count++;
			}
			System.out.println(output);
			  
			//Prompt the user the choose a user.
			System.out.print("Choose a user: ");
			choice = keyboard.nextInt();
			userName = choiceToUsername.get(choice);
			
			//Display the active orders for this user
			output="";
			String sqlGetActiveOrderForUser = "select o.orderId, o.estArrivalDate "
												+ "from orders as o, makes as m "
												+ "where o.orderId = m.orderId and o.estArrivalDate <= CURRENT_TIMESTAMP and m.username ='"+userName+"'";
			result = stm.executeQuery(sqlGetActiveOrderForUser);
			output += "Choice\tOrder_Id\tEst. Arrival date\n";
			output += "------\t--------\t-----------\n";
			Map<Integer, Integer>choiceToOrder = new HashMap<Integer, Integer>();
			count = 1;
			while(result.next()){
				output += count+"\t"+result.getString("orderId")+"\t\t"+result.getString("estArrivalDate")+"\n";
				choiceToOrder.put(count, result.getInt("orderId"));
				count++;
			}
			System.out.println();
			System.out.println(output);
			
			//Prompt the user to pick an order
			System.out.print("Choose an order: ");
			choice = keyboard.nextInt();
			orderId = choiceToOrder.get(choice);
			
			//Display the addresses
			output = "";
			String sqlGetAddr = "select a.addrId, a.streetAddr from Address as a, livesAt as lA where a.addrId = lA.addrId and lA.username = '"+userName+"'";
			result = stm.executeQuery(sqlGetAddr);
			output += "Choice\tAddr_Id\tStreet\n";
			output += "------\t-------\t------\n";
			Map<Integer, Integer>choiceToAddr = new HashMap<Integer, Integer>();
			count = 1;
			while(result.next()){
				output += count+"\t"+result.getString("addrId")+"\t"+result.getString("streetAddr")+"\n";
				choiceToAddr.put(count, result.getInt("addrId"));
				count++;
			}
			System.out.println();
			System.out.println(output);
			
			//Prompt the user to pick an addr
			System.out.print("Choose an alternative address for this user: ");
			choice = keyboard.nextInt();
			addrId = choiceToAddr.get(choice);
			
			String sqlUpdate = "update Orders "
					+ "set addrId = "+addrId
					+" where orderId = "+orderId;
			stm.execute(sqlUpdate);
			output = "The update was successfull";
			System.out.println();
			System.out.println(output);
		}catch(SQLException e){
			output = "An error occured while attempting to update -- "+e.toString();
			System.out.println(output);
		}
		
	}
}
