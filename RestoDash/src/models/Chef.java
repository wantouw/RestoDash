package models;

import java.util.Random;

import singleton.Restaurant;
import state.ChefIdle;
import state.ChefState;

public class Chef extends Character{

	private Integer speed;
	private Integer skill;
	private ChefState phase;
	public Chef() {
		super();
		this.speed = 1;
		this.skill = 1;
		this.setPhase(new ChefIdle(this));
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public Integer getSkill() {
		return skill;
	}
	public void setSkill(Integer skill) {
		this.skill = skill;
	}
	public ChefState getPhase() {
		return phase;
	}
	public void setPhase(ChefState phase) {
		this.phase = phase;
	}
	public void addSpeed() {
		// TODO Auto-generated method stub
		this.speed++;
		Restaurant.getInstance().reduceMoney(150);
	}
	public void addSkill() {
		this.skill++;
		Restaurant.getInstance().reduceMoney(150);
	}
}
