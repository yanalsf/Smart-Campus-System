package OOP;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Oral.Story;


public class RegisterationSystemOOP {
	static ArrayList<Integer> globalId = new ArrayList<Integer>();

	public static void main(String[] args) {
		
		
		Story str1 = new Story(null, null);
				
		

		Scanner scanner = new Scanner(System.in);
		ArrayList<Workshop> workshops = new ArrayList<Workshop>();
		workshops.add(new Workshop("Fundementals of C language", "10am", "S-204", "Learn the basics of C language", 25));
		workshops.add(new Workshop("Advanced C", "12pm", "S-204", "solve high level of promblems with C", 25));
		workshops.add(new Workshop("Fundementals of Java language", "10am", "S-206", "Learn the basics of Java (OOP),(Event-Driven)", 25));
		workshops.add(new Workshop("Advanced Java", "12pm","S-206" , "Build Systems with java using OOP & Event-Driven", 25));
		workshops.add(new Workshop("Robotics 1", "9am", "N-302","Learning the basics of robots " , 15));
		workshops.add(new Workshop("Robotics 2", "12pm", "N-302", "Build a robot", 15));

		while (true) {
			System.out.println("------------Welcom to my system------------");

			System.out.print("name: ");
			String name = scanner.next();
			
			int id = 0;
			
			try {
				System.out.print("ID: ");
				id = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Error: ID must be a numeric value!");
				scanner.next();
				continue;
			}
			
			if(globalId.contains(id)) {
				System.out.println("this ID for onther person");
				continue;
			}

			if (id >= 1 && id <= 100) {

				System.out.print("Admin password: ");
				String pass = scanner.next();
				if (pass.contains("admin123")) {
					globalId.add(id);
					new Employee(name, id).showMenu(scanner, workshops);
				} else
					System.out.println("password incorrect");

			}

			else if (id > 100 && id < 26000000) {
				globalId.add(id);

				new Student(name, id).showMenu(scanner, workshops);

			}else System.out.println("ID out of range");

		}

	}
}
