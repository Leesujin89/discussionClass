package user;

public class UserDTO {

		String userID;
		String userPWD;
		String userName;
		String userEmail;
		String userProfile;
		int groupNumber;
		int userScore;
		int groupSocre;
		int now;
		
		public String getUserID() {
			return userID;
		}
		public void setUserID(String userID) {
			this.userID = userID;
		}
		public String getUserPWD() {
			return userPWD;
		}
		public void setUserPWD(String userPWD) {
			this.userPWD = userPWD;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getUserEmail() {
			return userEmail;
		}
		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}
		public String getUserProfile() {
			return userProfile;
		}
		public void setUserProfile(String userProfile) {
			this.userProfile = userProfile;
		}
		public int getGroupNumber() {
			return groupNumber;
		}
		public void setGroupNumber(int groupNumber) {
			this.groupNumber = groupNumber;
		}
		public int getUserScore() {
			return userScore;
		}
		public void setUserScore(int userScore) {
			this.userScore = userScore;
		}
		public int getGroupSocre() {
			return groupSocre;
		}
		public void setGroupSocre(int groupSocre) {
			this.groupSocre = groupSocre;
		}
		public int getNow() {
			return now;
		}
		public void setNow(int now) {
			this.now = now;
		}
		
}
