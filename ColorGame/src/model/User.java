//Gal Ashkenazi 315566752 - Final Test
package model;

public class User {
	public static final String db_table = "tb_users";
	public static final String db_table_front = "tb_front";
	public static enum db_columns {
		id 				{ public String toString() 	{return "uid";}}
		, playerName 	{ public String toString() 	{return "nickname";}}
		, date_join 	{ public String toString() 	{return "date_join";}}
		, theme_id 		{ public String toString() 	{return "TID";}}
		, icon_id 		{ public String toString() 	{return "IID";}}
	};
	
	private int id;
	private String playerName, date_join;
	private Icon icon;
	private Theme theme;
	private int average;
	
	public User(int _id, String _playerName, String _date_join, Theme _theme, Icon _icon, int _average) {
		this.id = _id;
		this.playerName = _playerName;
		this.date_join = _date_join;
		this.theme = _theme;
		this.icon = _icon;
		this.average = _average;
	}
	
	public User() { }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getDate_join() {
		return date_join;
	}
	public void setDate_join(String date_join) {
		this.date_join = date_join;
	}
	public Icon getIcon() {
		return icon;
	}
	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public int getAverage() {
		return average;
	}

	public void setAverage(int average) {
		this.average = average;
	}
}

