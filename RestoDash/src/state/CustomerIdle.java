package state;

import mediator.CustomerMediator;
import models.Customer;
import models.Waiter;
import singleton.Restaurant;

public class CustomerIdle extends CustomerState implements Runnable{

	private boolean isRunning;
	public CustomerIdle(Customer customer) {
		super(customer);
		new Thread(this).start();
		isRunning = true;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRunning&&Restaurant.getInstance().isPlaying()) {
			int time=0;
			while(time<2000) {
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

	@Override
	public void changeState(Waiter waiter) {
			this.getCustomer().setPhase(new CustomerOrder(this.getCustomer(), waiter));
			this.isRunning = false;
	}

	@Override
	public void printState() {
		// TODO Auto-generated method stub
		System.out.printf("|%-2s (%2d),        idle|", this.getCustomer().getName(), this.getCustomer().getTolerance()); 
	}
	
}
