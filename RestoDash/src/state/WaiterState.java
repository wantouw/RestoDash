package state;

import models.Customer;
import models.Waiter;

public abstract class WaiterState {
	public abstract void changeState(Customer customer);
	public abstract void printState();
	private boolean isPaused;
	public void toIdle() {
		this.waiter.setPhase(new WaiterIdle(this.waiter));
	}
	private Waiter waiter;
	public Waiter getWaiter() {
		return waiter;
	}
	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}
	public WaiterState(Waiter waiter) {
		super();
		this.waiter = waiter;
	}
//	protected abstract void changeState(Customer customer);
	public boolean isPaused() {
		return isPaused;
	}
	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}
}
