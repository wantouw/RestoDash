package state;

import mediator.WaiterMediator;
import models.Customer;
import models.Waiter;
import singleton.Restaurant;

public class WaiterBringFood extends WaiterState implements Runnable{

	private Customer customer;
	public WaiterBringFood(Waiter waiter, Customer customer) {
		super(waiter);
		this.setCustomer(customer);
		new Thread(this).start();
	}

	@Override
	public void changeState(Customer customer) {
		this.getWaiter().setPhase(new WaiterIdle(this.getWaiter()));
	}

	@Override
	public void printState() {
		// TODO Auto-generated method stub
		System.out.printf("%-2s,  servingfood(%-2s)|", this.getWaiter().getName(), this.customer.getName()); 
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int time=0;
		while(time<1000&&Restaurant.getInstance().isPlaying()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(Restaurant.getInstance().isPlaying()==false) {
				return;
			}
			if(isPaused()) continue;
			time+=100;
		}
		if(Restaurant.getInstance().isPlaying()==false) {
			return;
		}
		changeState(this.customer);
		WaiterMediator waiterM = new WaiterMediator();
		waiterM.orderServed(this.getWaiter(), this.customer);
	}

}
