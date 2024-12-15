package state;

import mediator.CustomerMediator;
import mediator.WaiterMediator;
import models.Chef;
import models.Customer;
import models.Waiter;
import singleton.Restaurant;

public class WaiterBringOrder extends WaiterState implements Runnable{

	private Customer customer;
	private Chef chef;
	public WaiterBringOrder(Waiter waiter, Customer customer, Chef chef) {
		super(waiter);
		this.customer = customer;
		this.chef = chef;
		new Thread(this).start();
	}

	@Override
	public void changeState(Customer customer) {
		this.getWaiter().setPhase(new WaiterBringFood(this.getWaiter(), customer));
	}

	@Override
	public void printState() {
		System.out.printf("%-2s,  bring order(%-2s)|", this.getWaiter().getName(), this.chef.getName()); 

	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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
		WaiterMediator waiterM = new WaiterMediator();
		CustomerMediator customerM = new CustomerMediator();
		if(this.chef.getPhase() instanceof ChefIdle) {
			customerM.sendOrder(this.customer, this.chef);
			toIdle();
		} else if(this.chef.getPhase() instanceof ChefDone) {
			Customer currCust = this.customer;
			changeState(((ChefDone)this.chef.getPhase()).getCustomer());
			waiterM.orderDone(this.getWaiter(), currCust, this.chef);
		}
	}
	
}
