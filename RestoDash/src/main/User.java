package main;

public class User {
	private String name;
	private Integer score;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param name
	 * @param score
	 */
	public User(String name, Integer score) {
		super();
		this.name = name;
		this.score = score;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
}
