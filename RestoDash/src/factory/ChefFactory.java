package factory;


import models.Chef;
import models.Character;

public class ChefFactory extends CharacterFactory{
	@Override
	public Character createStaff() {
		Chef newChef = new Chef();
		newChef.setName(uniqueName().getName());
		return newChef;
	}
}
