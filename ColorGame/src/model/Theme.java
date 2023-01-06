//Gal Ashkenazi 315566752 - Final Test
package model;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Theme {
	public static final String db_table = "tb_theme";
	public static final String db_table_color = "tb_colors";
	public static final String db_table_collection = "tb_collection";
	public static enum db_columns {
		id 			{ public String toString() 	{return "TID";}}
		, title 	{ public String toString() 	{return "title";}}
		, color 	{ public String toString() 	{return "rgb";}}
		, color_id 	{ public String toString() 	{return "CID";}}
	};
	
	private ArrayList<Color> colors;
	private String title;
	private int id;
	
	public Theme(int _id, String _title, Color _first_color) {
		this.id = _id;
		this.title = _title;
		
		this.colors = new ArrayList<>();
		this.colors.add(_first_color);
	}

	public ArrayList<Color> getColors() {
		return colors;
	}

	public void setColors(ArrayList<Color> colors) {
		this.colors = colors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}