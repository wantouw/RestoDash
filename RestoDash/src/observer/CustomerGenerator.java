package observer;

import java.util.Random;

import factory.CustomerFactory;
import mediator.CustomerMediator;
import models.Customer;
import singleton.Restaurant;

public class CustomerGenerator implements Runnable{

	private Restaurant resto;
	private boolean isPaused;
	private Random rand = new Random();
	public void startGenerate() {
		resto = Restaurant.getInstance();
		new Thread(this).start();
		this.isPaused = false;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(resto.isPlaying()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
			if(isPaused) continue;
			int chance = rand.nextInt(4);
//			System.out.println(chance);
			synchronized(resto.getCustomers()) {
			if(chance==0&&resto.getCustomers().size()<resto.getChairs()) {
					CustomerFactory customerF = new CustomerFactory();
					resto.getCustomers().add((Customer) customerF.createStaff());
				}
			}
		}
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

}