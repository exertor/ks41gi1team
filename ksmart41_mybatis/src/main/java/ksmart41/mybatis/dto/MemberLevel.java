package ksmart41.mybatis.dto;

public class MemberLevel {
	private int levelNum;
	private String levelName;
	public int getLevelNum() {
		return levelNum;
	}
	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MemberLevel [levelNum=");
		builder.append(levelNum);
		builder.append(", levelName=");
		builder.append(levelName);
		builder.append("]");
		return builder.toString();
	}
	
}
