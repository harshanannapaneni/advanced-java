package application;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class preparedstatements {
	private static final Logger logger = LoggerFactory.getLogger(app.class);

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		int[] ids = {10,20,30};
		String[] names = {"John Doe","Willam Smith","Robert McKinney"};
		
		try {
			Class.forName("org.sqlite.JDBC");
			logger.info("SQLite JDBC driver loaded successfully.");
			
			String dbUrl = "jdbc:sqlite:people.db";
			var conn = DriverManager.getConnection(dbUrl);
			
			var stmt = conn.createStatement();
			
			var sql = "create table if not exists user (id interger primary key, name text not null)";
			System.out.println("User table successfully created");
			stmt.execute(sql);
			
			sql = "insert into user (id, name) values(?, ?)";
			
			var insertStmt = conn.prepareStatement(sql);
			
			for(int i=0; i < ids.length; i++) {
				insertStmt.setInt(1, ids[i]);
				insertStmt.setString(2, names[i]);
				
				insertStmt.executeUpdate();
			}
			
			insertStmt.close();
			
			var querySQL = "select * from user";
			var result = stmt.executeQuery(querySQL);
			
			while(result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				
				System.out.println(id + " " + name);
				
			}
			var dropTableSQL = "drop table user";
			
			stmt.execute(dropTableSQL);
			System.out.println("User table successfully deleted.");
			
			stmt.close();
			
			conn.close();
		}
		catch (ClassNotFoundException | SQLException e) {
			// TODO: handle exception
			logger.error("Failed to load SQLite JDBC driver.",e);
		}
	}

}
