package mediator;

import java.util.ArrayList;

import factory.WaiterFactory;
import models.Chef;
import models.Customer;
import models.Waiter;
import state.ChefDone;
import state.ChefIdle;
import state.ChefState;
import state.CustomerIdle;
import state.CustomerWaitFood;
import state.WaiterIdle;
import state.WaiterState;
import state.WaiterWaitCook;

public class WaiterMediator {
	
	public void orderTaken(Waiter waiter, Customer customer) {
		customer.getPhase().changeState(waiter);
	}
	
	public void sendOrder(Customer customer, Chef chef) {
		chef.getPhase().changeState(customer);
		((CustomerWaitFood)customer.getPhase()).changeState(chef);
	}
	
	public void orderDone(	Waiter waiter, Customer customer, Chef chef) {
		((ChefDone)chef.getPhase()).getCustomer().getPhase().changeState(waiter);
		chef.getPhase().changeState(customer);
		((CustomerWaitFood)customer.getPhase()).changeState(chef);
	}
	
	public void orderServed(Waiter waiter, Customer customer) {
		customer.getPhase().changeState(waiter);
	}

	public void waiterWaitToChef(Waiter waiter, Chef chef) {
		// TODO Auto-generated method stub
		WaiterWaitCook currPhase = (WaiterWaitCook) waiter.getPhase();
		currPhase.changeState(currPhase.getCustomer(), chef);
		chef.getPhase().setReserved(true);
	}
	
	public void waiterIdleToChef(Waiter waiter, Chef chef) {
		WaiterIdle currPhase = (WaiterIdle) waiter.getPhase();
		currPhase.sendFood(((ChefDone)chef.getPhase()).getCustomer());
		((ChefDone)chef.getPhase()).getCustomer().getPhase().changeState(waiter);
		((ChefDone)chef.getPhase()).toIdle();
	}
	
}
