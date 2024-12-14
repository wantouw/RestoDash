package facade;

import java.util.ArrayList;
import java.util.Scanner;

import main.General;
import main.User;
import mediator.CustomerMediator;
import mediator.WaiterMediator;
import models.Chef;
import models.Customer;
import models.Waiter;
import observer.CustomerGenerator;
import singleton.Highscore;
import singleton.Restaurant;
import state.ChefDone;
import state.ChefIdle;
import state.CustomerIdle;
import state.WaiterIdle;
import state.WaiterWaitCook;

public class RestaurantFacade implements Runnable{

	private Restaurant resto;
	
	
	/**
	 * @param resto
	 */
	private boolean isPaused;


	public String checkName() {
		String name;
		while(true) {
			Scanner scan = new Scanner(System.in);
			System.out.print("Input name: ");
			name = scan.nextLine();
			if(name.length()>=3&&name.length()<=15) {
				break;
			}
		}
		return name;
	}
	
	public void header() {
		General.cls();
		System.out.printf("Restaurant '%s' is on Business!\n", resto.getName());
		System.out.println("      Status");
		System.out.printf("Money	: Rp. %d\n", resto.getMoney());
		System.out.printf("Score	: %d Points\n", resto.getPoints());
		System.out.printf("Size 	: %d seats\n", resto.getChairs());
	}
	
	
	
	public void status() {
		System.out.println("================================================================");
		System.out.println("|      Customer      |       Waiter       |        Cook        |");
		System.out.println("================================================================");
		int biggest = resto.getCustomers().size();
		if(resto.getWaiters().size()>biggest) biggest = resto.getWaiters().size();
		if(resto.getChefs().size()>biggest) biggest = resto.getChefs().size();
		for(int i=0;i<biggest;i++) {
			if(i>=resto.getCustomers().size()) {
				System.out.print("|                    |");
			}
			else {
				resto.getCustomers().get(i).getPhase().printState();
			}
			if(i>=resto.getWaiters().size()) {
				System.out.print("                    |");
			}
			else {
				resto.getWaiters().get(i).getPhase().printState();
			}
			if(i>=resto.getChefs().size()) {
				System.out.print("                    |\n");
			}
			else {
				resto.getChefs().get(i).getPhase().printState();
			}
		}
	}
	public RestaurantFacade() {
		super();
		this.resto = Restaurant.getInstance();
		this.isPaused = false;
//		new Thread(this).start();
	}
	
	public void facadeStart() {
		new Thread(this).start();
	}
	
	public void printDisplay() {
		synchronized(resto.getWaiters()) {
			synchronized (resto.getCustomers()) {
				synchronized (resto.getChefs()) {
					General.cls();
					header();
					status();
					System.out.println("	Press Enter to go to pause menu");
				}
			}
		}
	}


	@Override
	public void run() {
		while(resto.isPlaying()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(this.isPaused) {
				continue;
			}
			printDisplay();
		}		
	}



	public void pauseAll() {
		// TODO Auto-generated method stub
		resto.getRestoFacade().setPaused(true);
		resto.setPaused(true);
		CustomerGenerator customerG = resto.getCustomerG();
		customerG.setPaused(true);
		ArrayList<Customer> customers = resto.getCustomers();
		ArrayList<Chef> chefs = resto.getChefs();
		ArrayList<Waiter> waiters = resto.getWaiters();
		for (Waiter waiter : waiters) {
			waiter.getPhase().setPaused(true);
		}
		for (Chef chef : chefs) {
			chef.getPhase().setPaused(true);
		}
		for (Customer customer : customers) {
			customer.getPhase().setPaused(true);
		}
	}



	public boolean isPaused() {
		return isPaused;
	}



	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}



	public void continueAll() {
		System.out.println("Continuing restaurant...");
		resto.getRestoFacade().setPaused(false);
		resto.setPaused(false);
		CustomerGenerator customerG = resto.getCustomerG();
		customerG.setPaused(false);
		ArrayList<Customer> customers = resto.getCustomers();
		ArrayList<Chef> chefs = resto.getChefs();
		ArrayList<Waiter> waiters = resto.getWaiters();
		for (Waiter waiter : waiters) {
			waiter.getPhase().setPaused(false);
		}
		for (Chef chef : chefs) {
			chef.getPhase().setPaused(false);
		}
		for (Customer customer : customers) {
			customer.getPhase().setPaused(false);
		}
	}
	
	public void stopAll() {
		resto.setPlaying(false);
		Highscore highscore = Highscore.getInstance();
		highscore.getScores().add(new User(resto.getName(), resto.getPoints()));
		highscore.writeFile();
	}
	
	public void processOrder(ArrayList<Customer> customers, ArrayList<Waiter> waiters, ArrayList<Chef> chefs) {
		CustomerMediator customerM = new CustomerMediator();
		WaiterMediator waiterM = new WaiterMediator();
		synchronized(waiters) {
			synchronized(customers) {
				synchronized(chefs) {
					for (Waiter waiter : waiters) {
						if(waiter.getPhase() instanceof WaiterWaitCook) {
							for (Chef chef : chefs) {
								if((chef.getPhase() instanceof ChefIdle && !((ChefIdle) chef.getPhase()).isReserved())||(chef.getPhase() instanceof ChefDone && !((ChefDone) chef.getPhase()).isReserved())) {
									waiterM.waiterWaitToChef(waiter, chef);
									break;
								}
							}
						}
						}
					for (Waiter waiter : waiters) {
						if(waiter.getPhase() instanceof WaiterIdle) {
							for (Chef chef : chefs) {
								if(chef.getPhase() instanceof ChefDone && !((ChefDone) chef.getPhase()).isReserved()) {
									waiterM.waiterIdleToChef(waiter, chef);
									break;
								}
//								}8
							}
						}
						}
					
					for (Customer customer : customers) {
							if(customer.getPhase() instanceof CustomerIdle) {
								for (Waiter waiter : waiters) {
									if(waiter.getPhase() instanceof WaiterIdle) {
										customerM.waiterReceive(waiter, customer);
										break;
									}
								}
							}
//						}
					}
				}
			}
		}
	}
	
}
