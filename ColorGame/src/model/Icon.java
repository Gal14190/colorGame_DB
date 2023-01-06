package model;

public class Icon {
	public static final String db_table = "tb_icons";
	public static enum db_columns {
		id 		{ public String toString() 	{return "IID";}}
		, path 	{ public String toString() 	{return "path";}}
	};
	
	private int id;
	private String path;
	
	public Icon(int _id, String _path) {
		this.id = _id;
		this.path = _path;
	}

	
	public int getId() {
		return id;
	}
	
	void setId(int id) {
		this.id = id;
	}



	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
