package state;

import models.Chef;
import models.Customer;
import models.Waiter;

public abstract class ChefState {
	public abstract void changeState(Customer customer);
	public abstract void printState();
	private boolean isReserved;
	private boolean isPaused;
	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}
	public void toIdle() {
		this.chef.setPhase(new ChefIdle(this.chef));
	}
	private Chef chef;
	public Chef getChef() {
		return chef;
	}
	public void setChef(Chef chef) {
		this.chef = chef;
	}
	/**
	 * @param chef
	 */
	public ChefState(Chef chef) {
		super();
		this.setPaused(false);
		this.chef = chef;
		this.setReserved(false);
	}
	public boolean isReserved() {
		return isReserved;
	}
	public boolean isPaused() {
		return isPaused;
	}
	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}
	
}
