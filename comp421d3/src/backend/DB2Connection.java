package backend;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB2Connection {
	private String url;
	private String userName;
	private String password;
	private Connection conn;
	
	public DB2Connection(String url, String userName, String password){
		this.url = url;
		this.userName = userName;
		this.password = password;
		try{
			DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
			this.conn = DriverManager.getConnection(this.url, this.userName, this.password);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return this.conn;
	}
}
