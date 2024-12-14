package state;

import models.Chef;
import models.Customer;
import models.Waiter;
import singleton.Restaurant;

public class ChefCook extends ChefState implements Runnable{

	private Customer customer;
	public ChefCook(Chef chef, Customer customer) {
		super(chef);
		this.customer = customer;
		new Thread(this).start();
	}

	@Override
	public void changeState(Customer customer) {
		this.getChef().setPhase(new ChefDone(this.getChef(), customer));
	}

	@Override
	public void printState() {
		// TODO Auto-generated method stub
		System.out.printf("%-2s,         cook(%-2s)|\n", this.getChef().getName(), this.customer.getName()); 

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int time = 0;
		while(time<6000-(this.getChef().getSpeed()*1000)&&Restaurant.getInstance().isPlaying()) {
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
	}

}
