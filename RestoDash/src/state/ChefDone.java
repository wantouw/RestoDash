package state;

import models.Chef;
import models.Customer;

public class ChefDone extends ChefState {

	private Customer customer;
	public ChefDone(Chef chef, Customer customer) {
		super(chef);
		this.setCustomer(customer);
		this.setReserved(false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void changeState(Customer customer) {
		this.getChef().setPhase(new ChefCook(this.getChef(), customer));
	}
	
	public void toIdle() {
		this.getChef().setPhase(new ChefIdle(this.getChef()));
	}

	@Override
	public void printState() {
		// TODO Auto-generated method stub
		System.out.printf("%-2s,         done(%-2s)|\n", this.getChef().getName(), this.customer.getName()); 

	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	

}
