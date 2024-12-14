package state;

import models.Chef;
import models.Customer;

public class ChefIdle extends ChefState{

	public ChefIdle(Chef chef) {
		super(chef);
		this.setReserved(false);
	}

	@Override
	public void changeState(Customer customer) {
		this.getChef().setPhase(new ChefCook(this.getChef(), customer));
	}

	@Override
	public void printState() {
		// TODO Auto-generated method stub
		System.out.printf("%-2s,             idle|\n", this.getChef().getName()); 
	}

	
}
