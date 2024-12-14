package state;

import mediator.WaiterMediator;
import models.Customer;
import models.Waiter;
import singleton.Restaurant;

public class WaiterTakeOrder extends WaiterState implements Runnable{

	private Customer customer;
	public WaiterTakeOrder(Waiter waiter, Customer customer) {
		super(waiter);
		this.customer=customer;
		new Thread(this).start();
	}

	@Override
	public void changeState(Customer customer) {
		this.getWaiter().setPhase(new WaiterWaitCook(this.getWaiter(), this.customer));
	}

	@Override
	public void run() {
		int time = 0;
		while(time<6000-(this.getWaiter().getSpeed()*1000)&&Restaurant.getInstance().isPlaying()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(isPaused()) continue;
			time+=100;
		}
		if(Restaurant.getInstance().isPlaying()==false) {
			return;
		}
		changeState(this.customer);
		WaiterMediator waiterM = new WaiterMediator();
		waiterM.orderTaken(this.getWaiter(), this.customer);
		
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public void printState() {
		// TODO Auto-generated method stub
		System.out.printf("%-2s,   take order(%-2s)|", this.getWaiter().getName(), this.customer.getName()); 

	}

}
