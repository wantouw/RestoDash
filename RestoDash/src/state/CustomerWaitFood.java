package state;

import models.Chef;
import models.Customer;
import models.Waiter;
import singleton.Restaurant;

public class CustomerWaitFood extends CustomerState implements Runnable{
	
	private Waiter waiter;
	private boolean isRunning;
	public CustomerWaitFood(Customer customer, Waiter waiter) {
		super(customer);
		this.setWaiter(waiter);
		isRunning = true;
		new Thread(this).start();
	}
	@Override
	public void run() {
		while(isRunning&&Restaurant.getInstance().isPlaying()) {
			int time=0;
			while(time<4000) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
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
//			System.out.println(this.customer.getName()+"waitfood");
			this.getCustomer().reduceTolerance();
			checkTolerance();
		}
		// TODO Auto-generated method stub
		
	}
	@Override
	public void printState() {
		// TODO Auto-generated method stub
		System.out.printf("|%-2s (%2d),waitfood(%-2s)|", this.getCustomer().getName(), this.getCustomer().getTolerance(), this.waiter.getName()); 

	}
	@Override
	public void changeState(Waiter waiter) {
		// TODO Auto-generated method stub
		
	}
	
	public void changeState(Chef chef) {
//		synchronized(this.customer) {

		this.isRunning = false;
		this.getCustomer().setPhase(new CustomerWaitChef(this.getCustomer(), chef));
//		}
	}
	
	public Waiter getWaiter() {
		return waiter;
	}
	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}

}
