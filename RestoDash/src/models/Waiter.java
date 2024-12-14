package models;

import java.util.Random;

import singleton.Restaurant;
import state.WaiterIdle;
import state.WaiterState;

public class Waiter extends Character{
	private Integer speed;
	private WaiterState phase;
	public Waiter() {
		super();
		this.speed = 1;
		this.phase = new WaiterIdle(this);
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public WaiterState getPhase() {
		return phase;
	}
	public void setPhase(WaiterState phase) {
		this.phase = phase;
	}
	public void addSpeed() {
		// TODO Auto-generated method stub
		this.speed++;
		Restaurant.getInstance().reduceMoney(150);
	}
}
