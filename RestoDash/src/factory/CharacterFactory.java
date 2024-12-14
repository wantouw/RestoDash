package factory;

import java.util.ArrayList;

import models.Character;
import singleton.Restaurant;

public abstract class CharacterFactory {
	public abstract Character createStaff();
	public Character uniqueName() {
		ArrayList<String> names = Restaurant.getInstance().getNames();
		Character newPeople;
		while(true) {
			newPeople = new Character();
			if(names.size()==0) break;
			if(!names.contains(newPeople.getName())) {
				break;
			}
		}
		return newPeople;
	}
}
