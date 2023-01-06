//Gal Ashkenazi 315566752 - Final Test
package model;

/**
 * Holder for TOP 10 players
 * @author GalAs
 *
 */
public class TopPlayers {
	private int score;
	private String playerName;
	private String timeDate;
	
	public static final String db_table = "vw_top_players";
	public static final String db_table_score = "tb_score";
	public static enum db_columns {
		playerName 	{ public String toString() 	{return "nickname";}}
		, score 	{ public String toString() 	{return "point";}}
		, timeDate 	{ public String toString() 	{return "date";}}
	};
	
	/**
	 * c'tor
	 * @param _score
	 * @param _playerName
	 */
	public <T extends Comparable<T>>TopPlayers(T _score, String _playerName, String _timeDate) {
		setScore(_score);
		this.playerName = _playerName;
		this.timeDate = _timeDate;
	}
	
	// getters and setters
	
	public int getScore() {
		return score;
	}

	public <T extends Comparable<T>> void setScore(T _score) {
		if(_score instanceof String) 
			this.score = Integer.parseInt((String)_score);
		else {
			this.score = (int)_score;
		}
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(String timeDate) {
		this.timeDate = timeDate;
	}
	
	
}
