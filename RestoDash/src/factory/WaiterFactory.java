package factory;

import models.Character;
import models.Waiter;

public class WaiterFactory extends CharacterFactory{

	@Override
	public Character createStaff() {
		// TODO Auto-generated method stub
		Waiter newWaiter = new Waiter();
		newWaiter.setName(uniqueName().getName());
		return newWaiter;
	}
}
