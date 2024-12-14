package main;

import java.util.Scanner;

import facade.RestaurantFacade;
import singleton.Highscore;
import singleton.Restaurant;

public class Main {
	Scanner scan = new Scanner(System.in);
	Highscore scores = Highscore.getInstance();
	RestaurantFacade restoF = new RestaurantFacade();
	String name;
	Restaurant resto;
	
	public void mainMenu() {
		System.out.println("Main Menu\n");
		System.out.println("1. Play New Restaurant");
		System.out.println("2. High Score");
		System.out.println("3. Exit");
		System.out.print("Input [1..3] : ");
	}
	
	public void play() {
		name = restoF.checkName();
		resto = Restaurant.getInstance();
		resto.startResto(name);
		while(true) {
			if(Restaurant.getInstance().isPlaying()==false) {
				return;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Main() {
		while(true) {
			General.cls();
			mainMenu();
			String temp = scan.nextLine();
			Integer choice = General.toInt(temp);
			switch(choice) {
				case 1:
					play();
					break;
				case 2:
					scores.view();
					break;
				case 3:
					System.out.println("Exit...");
					return;
			}
			
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

}
