package models;

import java.util.Random;

public class Character {
	private String name;
	Random rand = new Random();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 */
	public Character() {
		super();
		this.name = String.format("%c%c", 'A'+rand.nextInt(26), 'A'+rand.nextInt(26));
	}
}
