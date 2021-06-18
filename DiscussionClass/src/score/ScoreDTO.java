package score;

public class ScoreDTO {
	int scoreID;
	String fromID;
	String toID;
	int userScore;
	int groupScore;
	int available;
	
	public int getScoreID() {
		return scoreID;
	}
	public void setScoreID(int scoreID) {
		this.scoreID = scoreID;
	}
	public String getFromID() {
		return fromID;
	}
	public void setFromID(String fromID) {
		this.fromID = fromID;
	}
	public String getToID() {
		return toID;
	}
	public void setToID(String toID) {
		this.toID = toID;
	}
	public int getUserScore() {
		return userScore;
	}
	public void setUserScore(int userScore) {
		this.userScore = userScore;
	}
	public int getGroupScore() {
		return groupScore;
	}
	public void setGroupScore(int groupScore) {
		this.groupScore = groupScore;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
}
