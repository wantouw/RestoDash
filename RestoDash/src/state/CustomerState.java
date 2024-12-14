package state;

import mediator.CustomerMediator;
import models.Customer;
import models.Waiter;

public abstract class CustomerState {
	public abstract void changeState(Waiter waiter);
	public abstract void printState();
	private boolean isPaused;
	public void checkTolerance() {
		if(this.customer.getTolerance()==0) {
			CustomerMediator customerM = new CustomerMediator();
			customerM.customerSad(this.customer);
		}
	}
	private Customer customer;
	public Customer getCustomer() {
		return customer;
	}
	public CustomerState(Customer customer) {
		super();
		this.setPaused(false);
		this.customer = customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public boolean isPaused() {
		return isPaused;
	}
	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}
}
