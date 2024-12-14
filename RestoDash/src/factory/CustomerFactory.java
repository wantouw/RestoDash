package factory;

import models.Customer;
import models.Character;

public class CustomerFactory extends CharacterFactory{
	

	@Override
	public Character createStaff() {
		// TODO Auto-generated method stub
		Customer newCustomer = new Customer();
		newCustomer.setName(uniqueName().getName());
		return newCustomer;
	}
}
