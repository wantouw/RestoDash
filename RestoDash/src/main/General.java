package main;

import java.util.Scanner;

public class General {
	public static void cls() {
		for(int i=0;i<100;i++) {
			System.out.println("");
		}
	}
	
	public static int toInt(String temp) {
		for(int i=0;i<temp.length();i++) {
			if(Character.isDigit(temp.charAt(i))==false) {
				return -1;
			}
		}
		if(temp.length()==0) {
			return -1;
		}
		return Integer.parseInt(temp);
	}
	
	public static void getchar() {
		Scanner scan = new Scanner(System.in);
		System.out.print("\nPress Enter to continue...");
		scan.nextLine();
	}
	
}
