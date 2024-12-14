package state;

import mediator.CustomerMediator;
import models.Chef;
import models.Customer;
import models.Waiter;
import singleton.Restaurant;

public class CustomerWaitChef extends CustomerState implements Runnable{

	private Chef chef;
	private boolean isRunning;
	public CustomerWaitChef(Customer customer, Chef chef) {
		super(customer);
		this.setChef(chef);
		isRunning = true;
		new Thread(this).start();
	}

	@Override
	public void changeState(Waiter waiter) {
		this.isRunning = false; 
		this.getCustomer().setPhase(new CustomerWaitDelivery(this.getCustomer(), this.chef, waiter));
	}

	@Override
	public void printState() {
		// TODO Auto-generated method stub
		System.out.printf("|%-2s (%2d),waitfood(%-2s)|", this.getCustomer().getName(), this.getCustomer().getTolerance(), this.chef.getName()); 
	}

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRunning&&Restaurant.getInstance().isPlaying()) {
			int time=0;
			while(time<4000) {
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
			if(!isRunning) break;
			this.getCustomer().reduceTolerance();
			checkTolerance();
		}
	}

}
