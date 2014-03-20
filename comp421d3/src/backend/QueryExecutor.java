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
}
