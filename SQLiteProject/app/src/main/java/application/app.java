package application;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class app {

	private static final Logger logger = LoggerFactory.getLogger(app.class);
			
	public static void main(String[] args) throws ClassNotFoundException {
		
		try {
			Class.forName("org.sqlite.JDBC");
			logger.info("SQLite JDBC driver loaded successfully.");
			
			String dbUrl = "jdbc:sqlite:people.db";
			var conn = DriverManager.getConnection(dbUrl);
			
			var stmt = conn.createStatement();
			
			var sql = "create table if not exists user (id interger primary key, name text not null)";
			System.out.println("User table successfully created");
			stmt.execute(sql);
			
			var insertData = "insert into user(id, name) values(0,'bob'),(1,'Mary')";
			
			stmt.execute(insertData);
			
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