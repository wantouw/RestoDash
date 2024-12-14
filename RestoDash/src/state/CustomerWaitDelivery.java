package state;

import models.Chef;
import models.Customer;
import models.Waiter;
import singleton.Restaurant;

public class CustomerWaitDelivery extends CustomerState implements Runnable{

	private Waiter waiter;
	private Chef chef;
	public Waiter getWaiter() {
		return waiter;
	}

	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	private boolean isRunning;
	public CustomerWaitDelivery(Customer customer, Chef chef, Waiter waiter) {
		super(customer);
		this.setWaiter(waiter);
		this.setChef(chef);
		isRunning = true;
		new Thread(this).start();
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

	@Override
	public void changeState(Waiter waiter) {
		// TODO Auto-generated method stub
		this.isRunning = false;
		this.getCustomer().setPhase(new CustomerEat(this.getCustomer(), this.chef));
	}

	@Override
	public void printState() {
		// TODO Auto-generated method stub
		System.out.printf("|%-2s (%2d),waitfood(%-2s)|", this.getCustomer().getName(), this.getCustomer().getTolerance(), this.waiter.getName()); 

	}

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

}
