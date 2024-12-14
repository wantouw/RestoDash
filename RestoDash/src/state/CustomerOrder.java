package state;

import models.Customer;
import models.Waiter;

public class CustomerOrder extends CustomerState{

	boolean isRunning;
	private Waiter waiter;
	public CustomerOrder(Customer customer, Waiter waiter) {
		super(customer);
		this.waiter = waiter;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void changeState(Waiter waiter) {

		this.getCustomer().setPhase(new CustomerWaitFood(this.getCustomer(), waiter));
	}
	@Override
	public void printState() {
		// TODO Auto-generated method stub
		System.out.printf("|%-2s (%2d),   order(%-2s)|", this.getCustomer().getName(), this.getCustomer().getTolerance(), this.waiter.getName()); 

	}

}


