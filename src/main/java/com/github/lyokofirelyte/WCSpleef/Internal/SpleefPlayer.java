package com.github.lyokofirelyte.WCSpleef.Internal;

public class SpleefPlayer {

	String name;

	public SpleefPlayer(String name) {
	this.name = name;
	}
	
	int score = 0;
	Boolean disq = false;
	
	public int getScore(){
		return score;
	}
	
	public Boolean getDisq(){
		return disq;
	}
	
	
	
	public void setScore(int a){
		score = a;
	}
	
	public void setDisq(Boolean a){
		disq = a;
	}
}