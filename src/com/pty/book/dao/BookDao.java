package com.pty.book.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pty.book.pojo.*;
import com.google.gson.Gson;

public class BookDao {

	// JDBC 驱动名及数据库 URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/BooksDB?useUnicode=true&characterEncoding=utf8";
	// 数据库的用户名与密码，需要根据自己的设置
	private static final String USER = "root";
	private static final String PASSWORD = "1";
	// 数据库的连接对象
	private static Connection connection = null;
	// GOOGLE 序列化和反序列化 JSON 插件
	private static Gson gson = new Gson();

	private static void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
	}

	private static void closeAll(Connection connection, PreparedStatement pStatement, Statement statement, ResultSet reSet)
			throws ClassNotFoundException, SQLException {
		if (reSet != null) {
			reSet.close();
		}
		if (pStatement != null) {
			pStatement.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (connection != null) {
			connection.close();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		System.out.println(DELETE("1,2"));
		System.out.println(INSERT("{\"Bid\":1,\"Bname\":\"机器zzz学习\",\"Bpress\":\"清华大学出版社\",\"Bdate\":\"2016-10-07\"}"));
		System.out.println(UPDATE("{\"Bid\":1,\"Bname\":\"机器学习\",\"Bpress\":\"AAA出版社\",\"Bdate\":\"2016-10-07\"}"));
		System.out.println(QUERY());
	}
	
	public static String INSERT(String json) throws ClassNotFoundException, SQLException {
		PreparedStatement pStatement = null;
		getConnection();
		Book book = gson.fromJson(json, Book.class);
		String sql = "INSERT INTO `books` (`Bid`, `Bname`, `Bpress`, `Bdate`) VALUES (?, ?, ?, ?);";
		System.out.println(sql);
		pStatement = connection.prepareStatement(sql);
		pStatement.setInt(1, book.getBid());
		pStatement.setString(2, book.getBname());
		pStatement.setString(3, book.getBpress());
		pStatement.setString(4, book.getBdate());
		pStatement.executeUpdate();
		closeAll(connection, pStatement, null, null);
		return "true";
	}

	public static String DELETE(String Bid) throws ClassNotFoundException, SQLException {
		Statement statement = null;
		getConnection();
		
		statement = connection.createStatement();
		String sql = "DELETE FROM `books` WHERE `books`.`Bid` in (" + Bid + ");";
		System.out.println(sql);
		statement.executeUpdate(sql);
		
		closeAll(connection, null, statement, null);
		return "true";
	}

	public static String UPDATE(String json) throws ClassNotFoundException, SQLException {
		PreparedStatement pStatement = null;
		getConnection();
		Book book = gson.fromJson(json, Book.class);
		String sql = "UPDATE `books` SET `Bname`=?,`Bpress`=?,`Bdate`=? WHERE `books`.`Bid` = ?;";
		System.out.println(sql);
		pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, book.getBname());
		pStatement.setString(2, book.getBpress());
		pStatement.setString(3, book.getBdate());
		pStatement.setInt(4, book.getBid());
		pStatement.executeUpdate();
		closeAll(connection, pStatement, null, null);
		return "true";
	}

	public static String QUERY() throws ClassNotFoundException, SQLException {
		PreparedStatement pStatement = null;
		ResultSet reSet;
		List<Book> booksList = new ArrayList<>();
		getConnection();
		String sql = "SELECT * FROM books;";
		System.out.println(sql);
		pStatement = connection.prepareStatement(sql);
		reSet = pStatement.executeQuery();
		while (reSet.next()) {
			Book book = new Book();
			book.setBid(reSet.getInt("Bid"));
			book.setBname(reSet.getString("Bname"));
			book.setBpress(reSet.getString("Bpress"));
			book.setBdate(reSet.getString("Bdate"));
			booksList.add(book);
		}
		BookBean bookBean = new BookBean();
		bookBean.setRows(booksList);
		bookBean.setTotal(String.valueOf(booksList.size()));
		closeAll(connection, pStatement, null, reSet);
		return gson.toJson(bookBean);
	}

}
