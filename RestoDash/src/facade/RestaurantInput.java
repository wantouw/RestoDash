package facade;

import java.util.ArrayList;
import java.util.Scanner;

import main.General;
import models.Chef;
import models.Waiter;
import singleton.Restaurant;

public class RestaurantInput implements Runnable{

	private Scanner scan = new Scanner(System.in);
	private Restaurant resto;
	RestaurantFacade restoF = new RestaurantFacade();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(resto.isPlaying()) {
			try {
	            scan.nextLine();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			pauses();
		}
	}
	
	public void mainMenu() {
		System.out.println("");
		System.out.println("1. Continue Business");
		System.out.println("2. Upgrade Restaurant");
		System.out.println("3. Close Business");
	}
	
	public void upgradeMenu() {
		System.out.println("");
		System.out.println("1. Increase Restaurant's Seat (Rp. 400)");
		System.out.println("2. Hire New Employee");
		System.out.println("3. Upgrade Waiter");
		System.out.println("4. Upgrade Cook");
		System.out.println("5. Back to pause menu");
	}
	
	public void enterUpgrade() {
		while(true) {
			restoF.header();
			upgradeMenu();
			System.out.print("Input [1..5] : ");
			String temp = scan.nextLine();
			Integer choice = General.toInt(temp);
			switch(choice) {
				case 1:
					increaseSeat();
					break;
				case 2:
					hireEmployee();
					break;
				case 3:
					upgradeWaiter();
					break;
				case 4:
					upgradeCook();
					break;
				case 5:
					return;
			}
		}
	}
	
	public void pauses() {
		// TODO Auto-generated method stub
		restoF.pauseAll();
		while(true) {
			restoF.header();
			mainMenu();
			System.out.print("Input [1..3] : ");
			String temp = scan.nextLine();
			Integer choice = General.toInt(temp);
			switch(choice) {
				case 1:
					restoF.continueAll();
					return;
				case 2:
					enterUpgrade();
					break;
				case 3:
					restoF.stopAll();
					return;
					
			}
		}
	}

	private void upgradeCook() {
		// TODO Auto-generated method stub
		ArrayList<Chef> chefs = resto.getChefs();
		System.out.println("UPGRADE COOK (Rp. 150)");
		int start=1;
		System.out.println("---------------------------------");
		System.out.println("| No. | Initial | Speed | Skill |");
		System.out.println("---------------------------------");
		for (Chef chef : chefs) {
			System.out.printf("| %-2d. | %-7s | %-5d | %-5d |\n", start++, chef.getName(), chef.getSpeed(), chef.getSkill());
		}
		System.out.println("---------------------------------");
		while(true) {
			System.out.print("Input employee's number to upgrade [0 to exit] : ");
			String temp = scan.nextLine();
			Integer choice = General.toInt(temp);
			if(choice==0) return;
			if(choice<=chefs.size()) {
				if(resto.getMoney()>=150) {
				while(true) {
						System.out.print("Upgrade speed/skill? [0 to exit] : ");
						String temp1 = scan.nextLine();
						if(temp1.equalsIgnoreCase("0")) {
							return;
						}
						else if(temp1.equalsIgnoreCase("speed")) {
							if(chefs.get(choice-1).getSpeed()<5) {
								chefs.get(choice-1).addSpeed();
								System.out.println("Chef speed upgraded!");
								General.getchar();
								return;
							}
							System.out.println("At max speed already!");
						}
						else if(temp1.equalsIgnoreCase("skill")) {
							if(chefs.get(choice-1).getSkill()<5) {
								chefs.get(choice-1).addSkill();
								System.out.println("Chef skill upgraded!");
								General.getchar();
								return;
							}
							System.out.println("At max skill already!");
						}
					}
				}
				else {
					System.out.println("Not enough money!");
				}
			}
		}
	}

	private void upgradeWaiter() {
		// TODO Auto-generated method stub
		ArrayList<Waiter> waiters = resto.getWaiters();
		System.out.println("UPGRADE WAITER (Rp. 150)");
		int start=1;
		System.out.println("-------------------------");
		System.out.println("| No. | Initial | Speed |");
		System.out.println("-------------------------");
		for (Waiter waiter : waiters) {
			System.out.printf("| %-2d. | %-7s | %-5d |\n", start++, waiter.getName(), waiter.getSpeed());
		}
		System.out.println("-------------------------");
		while(true) {
			System.out.print("Input employee's number to upgrade [0 to exit] : ");
			String temp = scan.nextLine();
			Integer choice = General.toInt(temp);
			if(choice==0) return;
			if(choice<=waiters.size()) {
				if(resto.getMoney()>=150&&waiters.get(choice-1).getSpeed()<5) {
					waiters.get(choice-1).addSpeed();
					System.out.println("Waiter upgraded!");
					General.getchar();
					break;
				}
				System.out.println("Not enough money or at max speed already!");
			}
		}
	}

	private void hireEmployee() {
		// TODO Auto-generated method stub
		while(true) {
			restoF.header();
			System.out.println("HIRE NEW EMPLOYEE");
			System.out.printf("1. Hire New Waiter (Rp. %d)\n", resto.getWaiters().size()*150);
			System.out.printf("2. Hire New Cook (Rp. %d)\n", resto.getChefs().size()*200);
			System.out.printf("3. Back to Upgrade Menu\n");
			System.out.print("Input [1..3] : ");
			String temp = scan.nextLine();
			Integer choice = General.toInt(temp);
			switch(choice) {
				case 1:
					hireWaiter();
					break;
				case 2:
					hireCook();
					break;
				case 3:
					return;
			}
		}
	}

	private void hireCook() {
		// TODO Auto-generated method stub
		if(resto.getChefs().size()<7&&resto.getMoney()>=resto.getChefs().size()*200) {
			resto.hireChef();
			System.out.println("Chef hired!");
		} else {
			System.out.println("Your money is not enough or already at maximum cap!");
		}
		General.getchar();
	}

	private void increaseSeat() {
		// TODO Auto-generated method stub
		int chairs = resto.getChairs();
		if(resto.getMoney()>=100*chairs&&chairs<13) {
			resto.addChair();
			System.out.println("Chair Added!");
		} else {
			System.out.println("Your money is not enough or already at maximum cap!");
		}
		General.getchar();
	}
	
	public void hireWaiter() {
		if(resto.getWaiters().size()<7&&resto.getMoney()>=resto.getWaiters().size()*150) {
			resto.hireWaiter();
			System.out.println("Waiter hired!");
		} else {
			System.out.println("Your money is not enough or already at maximum cap!");
		}
		General.getchar();
	}

	/**
	 * @param resto
	 */
	public RestaurantInput() {
		super();
		this.resto = Restaurant.getInstance();
		new Thread(this).start();
	}

}
