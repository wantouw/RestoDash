package state;

import models.Customer;
import models.Waiter;

public class WaiterIdle extends WaiterState{

	public WaiterIdle(Waiter waiter) {
		super(waiter);
	}

//	@Override
	public void changeState(Customer customer) {
		this.getWaiter().setPhase(new WaiterTakeOrder(this.getWaiter(), customer));
	}

	public void sendFood(Customer customer) {
		this.getWaiter().setPhase(new WaiterBringFood(this.getWaiter(), customer));
	}

	@Override
	public void printState() {
		System.out.printf("%-2s,             idle|", this.getWaiter().getName()); 
	}
	
}
