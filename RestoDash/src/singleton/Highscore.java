package singleton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import main.General;
import main.User;

public class Highscore {
	private ArrayList<User> scores;
	Scanner scan = new Scanner(System.in);
	private static void readFile() {
		File fp = new File("src/files/save.txt");
		try {
			fp.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner scanf = null;
		try {
			scanf = new Scanner(fp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(scanf.hasNextLine()) {
			String temp=scanf.nextLine();
			String data[]=temp.split("#");
			scoreInstance.scores.add(new User(data[0], Integer.parseInt(data[1])));
		}
	}
	
	private static Highscore scoreInstance;
	public static Highscore getInstance() {
		if(scoreInstance == null) {
			synchronized (Highscore.class) {
				scoreInstance = new Highscore();
			}
			scoreInstance.scores = new ArrayList<>();
			readFile();
		}
		return scoreInstance;
	}
	
	public void view() {
		sort();
		General.cls();
		System.out.println("----------High Score----------");
		int i=0;
		for (User user : scores) {
			if(i==10) break;
			System.out.printf("%-20s %-10d\n", user.getName(), user.getScore());
			i++;
		}
		General.getchar();
	}
	
	public void sort() {
		for(int i=0;i<scores.size()-1;i++) {
			if(scores.get(i).getScore()<scores.get(i+1).getScore()) {
				User temp = scores.get(i);
				scores.set(i, scores.get(i+1));
				scores.set(i+1, temp);
			}
		}
	}
	
	public void writeFile() {
		sort();
		FileWriter fw = null;
		try {
			fw = new FileWriter("src/files/save.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (User user : scores) {
			try {
				fw.write(String.format("%s#%d\n", user.getName(), user.getScore()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Highscore() {
		
	}

	public ArrayList<User> getScores() {
		return scores;
	}

	public void setScores(ArrayList<User> scores) {
		this.scores = scores;
	}
}
