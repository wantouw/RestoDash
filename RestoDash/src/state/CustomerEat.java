package state;

import mediator.CustomerMediator;
import models.Chef;
import models.Customer;
import models.Waiter;
import singleton.Restaurant;

public class CustomerEat extends CustomerState implements Runnable{

	private Chef chef;
	private boolean isPause;
	public CustomerEat(Customer customer, Chef chef) {
		super(customer);
		this.chef = chef;
		this.setPause(false);
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int start=0;
		while(start<6000&&Restaurant.getInstance().isPlaying()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(Restaurant.getInstance().isPlaying()==false) {
				return;
			}
			if(isPause==true) {
				continue;
			}
			start+=100;
		}
		if(Restaurant.getInstance().isPlaying()==false) {
			return;
		}
		CustomerMediator customerM = new CustomerMediator();
		customerM.customerLeave(this.getCustomer(), this.chef);
	}

	@Override
	public void changeState(Waiter waiter) {
		
	}
	

	@Override
	public void printState() {
		// TODO Auto-generated method stub
		System.out.printf("|%-2s (%2d),      eating|", this.getCustomer().getName(), this.getCustomer().getTolerance()); 

	}

	public boolean isPause() {
		return isPause;
	}

	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}

	

}
