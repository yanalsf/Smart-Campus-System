package OOP;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;



public class Student extends User{
	private ArrayList<Workshop> myWorkshops = new ArrayList<Workshop>();

	public Student(String name, int id) {
		super(name, id);
	}

	public void showMenu(Scanner scanner, ArrayList<Workshop> allWorkshops) {

		while (true) {
			System.out.println("====================================");
			System.out.println(
					"1. Show all workshops" + "\n2. Register" + "\n3. Unregister" 
			         + "\n4. Schedual" + "\n0. Exit System");
			System.out.println("====================================");
			System.out.print("Enter your choice: ");
			
			int choice = -1;
			try {
				choice = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input! Please enter a number.");
				scanner.next(); 
				continue;
			}

			if (choice == 0)
				break;

			if (choice == 1) {
				System.out.println("====================================");
				for (int i = 0; i < allWorkshops.size(); i++) {
					System.out.println((i + 1) + ". " + allWorkshops.get(i));
					System.out.println("--");
				}
			}

			else if (choice == 2) {
				System.out.println("====================================");
				for (int i = 0; i < allWorkshops.size(); i++) {
					System.out.println((i + 1) + ". " + allWorkshops.get(i));
					System.out.println("--");
				}

				try {
					System.out.print("Enter Workshop ID: ");
					int id = scanner.nextInt() - 1;
					Workshop selected = allWorkshops.get(id); 

					boolean conflict = false;
					for (Workshop w : myWorkshops)
						if (w.getTime().equals(selected.getTime())) 
							conflict = true;

					if (!conflict && selected.addStudent(this)) {
						myWorkshops.add(selected);
						System.out.println("Registered successfully");
					} else
						System.out.println("Time conflict or workshop is full");
						
				} catch (InputMismatchException e) {
					System.out.println("Error: Please enter a valid number!");
					scanner.next();
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Error: Workshop ID does not exist!");
				}

			}

			else if (choice == 3) {
				if (myWorkshops.isEmpty()) {
					System.out.println("You are not registered in any workshop.");
					continue;
				}

				System.out.println("====================================");
				for (int i = 0; i < myWorkshops.size(); i++) { 
					System.out.println((i + 1) + ". " + myWorkshops.get(i));
					System.out.println("--");
				}

				try {
					System.out.print("Enter registered Workshop ID to remove: ");
					int id = scanner.nextInt() - 1;
					Workshop w = myWorkshops.remove(id);
					w.removeStudent(getId());
					System.out.println("Removed successfully");
				} catch (InputMismatchException e) {
					System.out.println("Error: Please enter a valid number!");
					scanner.next();
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Error: Invalid ID! Out of your schedule range.");
				}
				
			} else if (choice == 4) {
				System.out.println("====================================");
				System.out.println("--- My Schedule ---");
				for (Workshop w : myWorkshops)
					System.out.println("- " + w.getTitle() + " (" + w.getTime() + ")");
			}
		}
	}
}
