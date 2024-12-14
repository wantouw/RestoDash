package models;

import state.CustomerIdle;
import state.CustomerState;

public class Customer extends Character{

	private Integer tolerance;
	private CustomerState phase;
//	private Waiter waiter;
	public Integer getTolerance() {
		return tolerance;
	}
	public void reduceTolerance() {
		this.tolerance--;
	}
	public void setTolerance(Integer tolerance) {
		this.tolerance = tolerance;
	}
	public CustomerState getPhase() {
		return phase;
	}
	public void setPhase(CustomerState phase) {
		this.phase = phase;
	}
	/**
	 * 
	 */
	public Customer() {
		super();
		this.tolerance = 15;
		this.phase = new CustomerIdle(this);
		// TODO Auto-generated constructor stub
	}
	
}
