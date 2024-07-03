package com.harsha.db.mysql;

import java.sql.SQLException;
 
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World");
		
		var props = Profile.getProperties("db");
		
		var db = Database.instance();

		try {
			db.connect(props);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot connect to database.");
			return;
		}

		System.out.println("Connected!");

		UserDao userDao = new UserDaoImpl();

//		userDao.save(new User("Mars"));
//		userDao.save(new User("Mercury"));

		var users = userDao.getAll();

		users.forEach(System.out::println);

		var userOpt = userDao.findOne(4);

		if (userOpt.isPresent()) {
			User user=userOpt.get();
			
			System.out.println("Retrieved " + userOpt);
			var oldName = user.getName();
			var oldId = user.getId();
			user.setName("Snoppy");
			userDao.update(user);
			
			System.out.println("Updated user with id "+ oldId + " from " + oldName + " to "+ user.getName());
		} else
			System.out.println("No user retrieved.");

		int deleteId=5; 
		userDao.delete(new User(deleteId,null));
		System.out.println("Deleted user with id " + deleteId);
		users = userDao.getAll();
		users.forEach(System.out::println);
		try {
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot close database connection.");
		}
	}
}
