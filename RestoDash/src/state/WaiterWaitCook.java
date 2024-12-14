package state;

import models.Chef;
import models.Customer;
import models.Waiter;

public class WaiterWaitCook extends WaiterState{

	private Customer customer;
	public WaiterWaitCook(Waiter waiter, Customer customer) {
		super(waiter);
		this.setCustomer(customer);
	}
	@Override
	public void printState() {
		System.out.printf("%-2s,    wait cook(%-2s)|", this.getWaiter().getName(), this.customer.getName()); 
	}
	
	public void changeState(Customer customer, Chef chef) {
		this.getWaiter().setPhase(new WaiterBringOrder(this.getWaiter(), customer, chef));
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@Override
	public void changeState(Customer customer) {
		// TODO Auto-generated method stub
	}

}
