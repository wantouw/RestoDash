package singleton;

import java.util.ArrayList;

import facade.RestaurantFacade;
import facade.RestaurantInput;
import factory.ChefFactory;
import factory.CustomerFactory;
import factory.WaiterFactory;
import mediator.CustomerMediator;
import mediator.WaiterMediator;
import models.Chef;
import models.Customer;
import models.Waiter;
import observer.CustomerGenerator;

public class Restaurant implements Runnable{
	private Integer chairs;
	private String name;
	private Integer points;
	private Integer money;
	private boolean isPlaying;
	private boolean isPaused;
	private RestaurantInput restoI;
	private ArrayList<Waiter> waiters;
	private ArrayList<Chef> chefs;
	private ArrayList<String> names;
	private ArrayList<Customer> customers;
	private static Restaurant resto;
	private CustomerGenerator customerG;
	private RestaurantFacade restoFacade;
	private WaiterMediator waiterM;
	private ChefFactory chefF;
	private WaiterFactory waiterF;
	
	public static Restaurant getInstance() {
		if(resto==null) {
			resto = new Restaurant();
		}
		return resto;
	}
	
	public CustomerGenerator getCustomerG() {
		return customerG;
	}
	
	public void setCustomerG(CustomerGenerator customerG) {
		this.customerG = customerG;
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}
	public void startResto(String name) {
		System.out.println("Starting the restaurant...");
		this.name = name;
		this.isPaused = false;
		
		
		chefF = new ChefFactory();
		waiterF = new WaiterFactory();
		waiterM = new WaiterMediator();
		this.money = 1300;
		this.points = 0;
		this.waiters = new ArrayList<>();
		this.chefs = new ArrayList<>();
		this.customers = new ArrayList<>();
		this.names = new ArrayList<>();
		this.setChairs(4);
		this.isPlaying = true;
		
		
		Chef newChef = (Chef) chefF.createStaff();
		this.chefs.add(newChef);
		this.names.add(newChef.getName());
		
		newChef = (Chef) chefF.createStaff();
		this.chefs.add(newChef);
		this.names.add(newChef.getName());
		
		Waiter newWaiter = (Waiter) waiterF.createStaff();
		this.waiters.add(newWaiter);
		this.names.add(newWaiter.getName());
		
		newWaiter = (Waiter) waiterF.createStaff();
		this.waiters.add(newWaiter);
		this.names.add(newWaiter.getName());
		
		this.customerG = new CustomerGenerator();
		this.restoFacade = new RestaurantFacade();
		restoFacade.facadeStart();
		customerG.startGenerate();
		new Thread(this).start();
		
	}
	
	public RestaurantFacade getRestoFacade() {
		return restoFacade;
	}

	public void setRestoFacade(RestaurantFacade restoFacade) {
		this.restoFacade = restoFacade;
	}

	private Restaurant() {
		
	}
	
	public void header() {
		System.out.printf("Restaurant '%s' is on Business!\n", this.name);
		System.out.println("      Status");
		System.out.printf("Money	: Rp. %d\n", this.money);
		System.out.printf("Score	: %d Points\n", this.points);
		System.out.printf("Size 	: %d seats\n", this.chairs);
	}
	@Override
	public void run() {
		restoI = new RestaurantInput();
		while(this.isPlaying) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(isPaused) {
				continue;
			}
			restoFacade.processOrder(customers, waiters, chefs);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
	
	public void addPoints(Integer points) {
		this.points+=points;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}
	
	public void addMoney(Integer money) {
		this.money+=money;
	}

	public ArrayList<Waiter> getWaiters() {
		return waiters;
	}

	public void setWaiters(ArrayList<Waiter> waiters) {
		this.waiters = waiters;
	}

	public ArrayList<Chef> getChefs() {
		return chefs;
	}

	public void setChefs(ArrayList<Chef> chefs) {
		this.chefs = chefs;
	}

	public ArrayList<String> getNames() {
		return names;
	}

	public void setNames(ArrayList<String> names) {
		this.names = names;
	}

	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}

	public Integer getChairs() {
		return chairs;
	}

	public void setChairs(Integer chairs) {
		this.chairs = chairs;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public void reducePoint() {
		// TODO Auto-generated method stub
		this.points-=300;
	}

	public void addChair() {
		// TODO Auto-generated method stub
		this.money-=100*this.chairs;
		this.chairs++;
	}

	public void hireWaiter() {
		// TODO Auto-generated method stub
		Waiter newWaiter = (Waiter) waiterF.createStaff();
		this.money-=this.waiters.size()*150;
		this.waiters.add(newWaiter);
		newWaiter.getPhase().setPaused(true);
		this.names.add(newWaiter.getName());
	}

	public void hireChef() {
		// TODO Auto-generated method stub
		Chef newChef = (Chef) chefF.createStaff();
		this.money-=this.chefs.size()*200;
		this.chefs.add(newChef);
		newChef.getPhase().setPaused(true);
		this.names.add(newChef.getName());
	}
	
	public void reduceMoney(Integer amount) {
		this.money-=amount;
	}
	
}
