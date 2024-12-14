package state;

import models.Character;

public interface CharacterState {
	public abstract void printState();
	public abstract void changeState(Character people);
}
