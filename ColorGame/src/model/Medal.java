//Gal Ashkenazi 315566752 - Final Test
package model;

public class Medal {
	public static final String db_table = "tb_medal";
	public static final String db_table_rel_user = "tb_achievement";
	public static enum db_columns {
		id 				{ public String toString() 	{return "mid";}}
		, title 		{ public String toString() 	{return "title";}}
		, details 		{ public String toString() 	{return "details";}}
		, path	 		{ public String toString() 	{return "path";}}
	};
	
	int id;
	String title, details, path;
	
	public Medal(int _id, String _title, String _details, String _path) {
		this.id = _id;
		this.title = _title;
		this.details = _details;
		this.path = _path;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
