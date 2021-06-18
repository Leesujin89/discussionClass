package chat;

public class ChatDTO {

		int chatID;
		String userID;
		int roomID;
		String chatContent;
		String chatTime;
		int chatRead;
		String memberProfile;
		
		public int getChatID() {
			return chatID;
		}
		public void setChatID(int chatID) {
			this.chatID = chatID;
		}
		public String getUserID() {
			return userID;
		}
		public void setUserID(String userID) {
			this.userID = userID;
		}
		public int getRoomID() {
			return roomID;
		}
		public void setRoomID(int roomID) {
			this.roomID = roomID;
		}
		public String getChatContent() {
			return chatContent;
		}
		public void setChatContent(String chatContent) {
			this.chatContent = chatContent;
		}
		public String getChatTime() {
			return chatTime;
		}
		public void setChatTime(String chatTime) {
			this.chatTime = chatTime;
		}
		public int getChatRead() {
			return chatRead;
		}
		public void setChatRead(int chatRead) {
			this.chatRead = chatRead;
		}
		public String getMemberProfile() {
			return memberProfile;
		}
		public void setMemberProfile(String memberProfile) {
			this.memberProfile = memberProfile;
		}
		
}
