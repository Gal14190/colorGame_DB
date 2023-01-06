//Gal Ashkenazi 315566752 - Final Test
package controller;
import java.sql.*;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import model.Icon;
import model.Medal;
import model.Model;
import model.Theme;
import model.TopPlayers;
import model.User;

/**
 * Handle Database
 *
 */
public class Database {
	//connection string
	private final String con_path = "jdbc:mysql://localhost:3306/db_colorGame";
	private final String con_userName = "java_user", con_password = "123asdASD!@#";
	
	private Model model;
	
	Connection con;
	
	/**
	 * c'tor
	 */
	public Database(Model _model) {
		this.model = _model;
		
		// test communication
		try {
			this.connect();
			this.close();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	}
	
	/**
	 * Connection to a database
	 */
	private void connect() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC 8.0 driver
		con = DriverManager.getConnection(con_path, con_userName, con_password);
	}
	
	/**
	 * Set a new score into a db
	 * @param uid
	 * @param score
	 * @param level
	 */
	public void insert_newScore(int uid, int score, int level) {
		try {
			this.connect();
			String query = "INSERT INTO " + TopPlayers.db_table_score
					+ " (`UID`, `date`, `point`, `level`)"
					+ " VALUES (?, CURRENT_DATE(), ?, ?)";
			
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setInt(1, uid);	// user id
			stmt.setInt(2, score);	// point
			stmt.setInt(3, level);	// level
			stmt.execute();		// run insert query
			
			this.close();
			
			int average = fetch_avg(uid);
			model.getUser().setAverage(average);
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Update data a new user icon
	 * @param uid
	 * @param iid
	 */
	public void update_userIcon(int uid, int iid) {
		try {
			this.connect();
			String query = "UPDATE " + User.db_table_front 
					+ " SET " + User.db_columns.icon_id + " = ?"
					+ " WHERE " + User.db_columns.id + " = ?";
			
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setInt(1, iid);	// icon id
			stmt.setInt(2, uid);	// user id
			stmt.executeUpdate();	// run update query
			
			this.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Update data a new user theme
	 * @param uid
	 * @param tid
	 */
	public void update_userTheme(int uid, int tid) {
		try {
			this.connect();
			String query = "UPDATE " + User.db_table_front 
					+ " SET " + User.db_columns.theme_id + " = ?"
					+ " WHERE " + User.db_columns.id + " = ?";
			
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setInt(1, tid);	// theme id
			stmt.setInt(2, uid);	// user id
			stmt.executeUpdate();	// run update query
			
			this.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Fetch all achievement user medals
	 * @param uid
	 * @return
	 */
	public ArrayList<Medal> fetch_Medals(int uid) {
		ArrayList<Medal> medals = new ArrayList<>();
		
		try {
			this.connect();
			
			String query = "SELECT *"
					+ " FROM " + Medal.db_table
					+ " JOIN " + Medal.db_table_rel_user
					+ " ON " + Medal.db_table + "." + Medal.db_columns.id + " = " + Medal.db_table_rel_user + "." + Medal.db_columns.id
					+ " AND " + Medal.db_table_rel_user + "." + User.db_columns.id + " = ?";
			
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setInt(1, uid); // user id
			ResultSet rs = stmt.executeQuery();	// run select query
			
			// read all medals
			while(rs.next()) {
				int id 			= rs.getInt(Medal.db_columns.id.toString());		// read medal id
				String title 	= rs.getString(Medal.db_columns.title.toString());	// read title
				String details 	= rs.getString(Medal.db_columns.details.toString());// read details
				String path 	= rs.getString(Medal.db_columns.path.toString());	// read path
				
				medals.add(new Medal(id, title, details, path));
			}
			
			this.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return medals;
	}
	
	/**
	 * Fetch a player data
	 * @param nickname
	 * @return
	 */
	public User fetch_User(String nickname) {
		User user = new User();
		
		String query = "SELECT *"
						+ " FROM " + User.db_table
						+ " JOIN " + User.db_table_front
						+ " ON " + User.db_table + "." + User.db_columns.id + " = " + User.db_table_front + "." + User.db_columns.id
						+ " JOIN " + Icon.db_table
						+ " ON " + User.db_table_front + "." + User.db_columns.icon_id + " = " + Icon.db_table + "." + Icon.db_columns.id
						+ " AND " + User.db_table + "." + User.db_columns.playerName + " = ?";
		
		try {
			this.connect();
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1, nickname);	// player name
			
			ResultSet rs = stmt.executeQuery();	// run select query
			
			if(rs.next()) { // if user exist
				int id 			= rs.getInt(User.db_columns.id.toString());				// read user id
				String date 	= rs.getString(User.db_columns.date_join.toString());	// read join data
				int theme_id	= rs.getInt(User.db_columns.theme_id.toString());		// read theme id
				int icon_id 	= rs.getInt(User.db_columns.icon_id.toString());		// read icon id
				String icon_path = rs.getString(Icon.db_columns.path.toString());		// read icon path
				Theme theme 	= new Theme(1, "", null);	// set clear theme
				int average 	= 0;						// set default average
				
				// validation
				if(theme_id == 0) theme_id = 1;
				if(icon_path == null) icon_path = "1.png";
				
				String queryTheme = "SELECT *"
						+ " FROM " + Theme.db_table
						+ " JOIN " + Theme.db_table_collection + " ON " + Theme.db_table + "." + Theme.db_columns.id + " = " + Theme.db_table_collection + "." + Theme.db_columns.id + " AND " + Theme.db_table + "." + Theme.db_columns.id + " = " + Integer.toString(theme_id)
						+ " JOIN " + Theme.db_table_color + " ON "  + Theme.db_table_collection + "." + Theme.db_columns.color_id + " = " + Theme.db_table_color + "." + Theme.db_columns.color_id;
				
				Statement stmt2 = con.createStatement();
				
				rs = stmt2.executeQuery(queryTheme);	// run select query
				
				boolean firstRow = true;
				while (rs.next()) {
					String title = rs.getString(Theme.db_columns.title.toString());
					int rgb = rs.getInt(Theme.db_columns.color.toString());
					
					int r = (rgb >>> 16) & 0xFF;
				    int g = (rgb >>> 8) & 0xFF;
				    int b = rgb & 0xFF;
					
					Color color =  Color.rgb(r, g, b);
					
					if(firstRow) {
						theme = new Theme(theme_id, title, color);
						firstRow = false;
						
					} else {
						theme.getColors().add(color);
					}
				}
				
				average = fetch_avg(id);
				
				if(rs.next()) {
					average = rs.getInt("avg");
				}
				
				// create object player
				user = new User(id
						, nickname
						, date
						, theme
						, new Icon(icon_id, icon_path)
						, average);
				
				System.out.println("WELCOM BACK!");
			} else { // create new user if not exist
				String queryInsert = "INSERT INTO " + User.db_table
									+ " (" + User.db_columns.playerName + ", " + User.db_columns.date_join + ")"
									+ " VALUES (?, CURRENT_DATE())";
				
				stmt = con.prepareStatement(queryInsert);
				stmt.setString(1, nickname); 	// set player name
				stmt.execute();					// run insert query
				
				user = fetch_User(nickname);
				System.out.println("Created new player!");
			}
			
			this.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return user;
	}
	
	/**
	 * Fetch the top 10 players
	 * @return
	 */
	public ArrayList<model.TopPlayers> fetch_TopPlayers() {
		ArrayList<model.TopPlayers> topPlayers = new ArrayList<>();
		
		try {
			this.connect();
			String query = "SELECT * FROM " + TopPlayers.db_table;
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);	// run select query
			
			while (rs.next()) {
				String 	playerName 	=  rs.getString(TopPlayers.db_columns.playerName.toString()); 	// read player name
			    int playerScore 	= rs.getInt(TopPlayers.db_columns.score.toString()); 			// read the score
			    String 	timeDate 	= 	  rs.getString(TopPlayers.db_columns.timeDate.toString());	// read the date
			        
			    topPlayers.add(new TopPlayers(playerScore, playerName, timeDate));
			}
			this.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return topPlayers;
	}
	
	/**
	 * Fetch the all icons exist
	 */
	public void fetch_Icons() {
		try {
			ArrayList<Icon> icons = new ArrayList<>();
			
			this.connect();
			String query = "SELECT *"
					+ " FROM " + Icon.db_table;
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query); // run select query
			
			while (rs.next()) {
				int id 		= 	rs.getInt(Icon.db_columns.id.toString());		// read icon id
				String path = 	rs.getString(Icon.db_columns.path.toString());	// read path

				icons.add(new Icon(id, path));
			}
			
			model.setIcon(icons);
			this.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Fetch all themes exist
	 */
	public void fetch_Themes() {
		try {
			ArrayList<Theme> themes = new ArrayList<>();
			
			this.connect();
			String query = "SELECT *"
					+ " FROM " + Theme.db_table
					+ " JOIN " + Theme.db_table_collection + " ON " + Theme.db_table + "." + Theme.db_columns.id + " = " + Theme.db_table_collection + "." + Theme.db_columns.id
					+ " JOIN " + Theme.db_table_color + " ON " + Theme.db_table_collection + "." + Theme.db_columns.color_id + " = " + Theme.db_table_color + "." + Theme.db_columns.color_id;
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery(query); // run select query
			while (rs.next()) {
				int id 			= rs.getInt(Theme.db_columns.id.toString());		// read theme id
				String title 	= rs.getString(Theme.db_columns.title.toString());	// read title
				int rgb 		= rs.getInt(Theme.db_columns.color.toString());		// read rgb color
				
				int r = (rgb >>> 16) & 0xFF;
			    int g = (rgb >>> 8) & 0xFF;
			    int b = rgb & 0xFF;
				
				Color color =  Color.rgb(r, g, b);
				
				if(themes.size() < id) {
					themes.add(new Theme(id, title, color));
				} else {
					themes.get(id - 1).getColors().add(color);
				}
			}
			
			model.setThemes(themes);
			
			this.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Fetch the average score of player
	 * @param uid
	 * @return
	 */
	public int fetch_avg(int uid) {
		int average = 0;
		
		try {
			this.connect();
			
			String queryAVG = "SELECT AVG(point) AS avg"
					+ " FROM " + TopPlayers.db_table_score
					+ " WHERE " + User.db_columns.id + " = " + uid;
			
			Statement stmt3 = con.createStatement();
			ResultSet rs = stmt3.executeQuery(queryAVG); // run select query
			
			if(rs.next()) {
				average = rs.getInt("avg");
			}
			
			this.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		
		return average;
	}
	
	/**
	 * Close the communication with the database
	 */
	public void close() {
		try {
			
			con.close(); 
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

