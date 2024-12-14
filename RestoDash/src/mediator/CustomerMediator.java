package mediator;

import java.util.ArrayList;

import factory.ChefFactory;
import factory.CustomerFactory;
import models.Chef;
import models.Customer;
import models.Waiter;
import singleton.Restaurant;
import state.CustomerIdle;
import state.CustomerState;
import state.CustomerWaitChef;
import state.CustomerWaitDelivery;
import state.CustomerWaitFood;
import state.WaiterBringOrder;
import state.WaiterIdle;
import state.WaiterState;

public class CustomerMediator {

	
	public void customerLeave(Customer customer, Chef chef) {
		Restaurant resto = Restaurant.getInstance();
		ArrayList<Customer> customers = resto.getCustomers();
		synchronized(customers) {
			resto.getNames().remove(customer.getName());
			customers.remove(customer);
		}
		synchronized(resto) {
			synchronized(chef) {
				resto.addPoints(chef.getSkill()*30);
				resto.addMoney(chef.getSkill()*30);
			}
		}
	}
	
	public void customerSad(Customer customer) {
		Restaurant resto = Restaurant.getInstance();
		ArrayList<Customer> customers = resto.getCustomers();
		synchronized(customers) {
			CustomerState customerS = customer.getPhase();
			WaiterState customerWaiter = ((CustomerWaitFood) customerS).getWaiter().getPhase();
			if(customerS instanceof CustomerWaitFood || customerS instanceof CustomerWaitDelivery) {
				if(customerWaiter instanceof WaiterBringOrder) {
					((WaiterBringOrder) customerWaiter).getChef().getPhase().setReserved(false);
				}
				customerWaiter.toIdle();
			} else if(customerS instanceof CustomerWaitChef) {
				((CustomerWaitChef) customerS).getChef().getPhase().toIdle();
			} 
			resto.getNames().remove(customer.getName());
			customers.remove(customer);
		}
		synchronized(resto) {
			
			resto.reducePoint();
		}
	}

	public void waiterReceive(Waiter waiter, Customer customer) {
		// TODO Auto-generated method stub
		customer.getPhase().changeState(waiter);
		waiter.getPhase().changeState(customer);
	}
	
}
