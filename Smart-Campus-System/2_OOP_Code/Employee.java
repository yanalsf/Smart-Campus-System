package OOP;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;



public class Employee extends User{

	public Employee(String name, int id) {
		super(name, id);
	}

	public void showMenu(Scanner scanner, ArrayList<Workshop> allWorkshops) {

		while (true) {
			System.out.println("1. Workshops Report" + "\n2. Participants" + "\n0. Exit");
			System.out.print("Enter your choice: ");
			
			int choice = -1;
			try {
				choice = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid choice! Input must be a number.");
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
				System.out.println("Workshop ID ->( 1-" + allWorkshops.size() + " )");
				
				try {
					System.out.print("Enter Workshop ID:");
					int id = scanner.nextInt() - 1;
					Workshop w = allWorkshops.get(id);  

					w.sortPartecipants();
					
					if (w.getParticipants().isEmpty())
						System.out.println("No registered Students");
					else {
						System.out.println("====================================");
						System.out.println("Participants in " + w.getTitle() + ":");
						for (Student s : w.getParticipants())
							System.out.println("-> ID: " + s.getId() + " | Name: " + s.getName());
					}
				} catch (InputMismatchException e) {
					System.out.println("Error: Input must be a number!");
					scanner.next();
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Error: Workshop ID out of range!");
				}
				System.out.println("====================================");
			}
		}
	}
}
