package Procedural;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RegisterationSystemProcedural {

	static String[] workshopTitles = { "Fundementals of C language", "Advanced C", "Fundementals of Java language",
			"Advanced Java", "Robotics 1", "Robotics 2" };
	static String[] workshopeTime = { "10am", "12pm", "10am", "12pm", "9am", "12pm" };
	static String[] workshopLocation = { "S-204", "S-204", "S-205", "S-205", "N-203", "N-203" };
	static String[] workshopOutcome = { "learn c", "adv c", "learn java", "adv java", "build basic robot",
			"build complex robot" };
	static int[] workshopCapacity = { 25, 25, 25, 25, 15, 15 };
	static int[] registeredCount = { 0, 0, 0, 0, 0, 0 };

	static String[][] participantsNames = new String[6][30];
	static int[][] participantsIds = new int[6][30];

	static int[] globalIds = new int[30];

	static int globalIdsCount = 0;

	static String currentStudentName;
	static int currentStudentId;
	static String[] currentStudentSchedule = new String[10];
	static int currentStudentRegCount = 0;

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("==========================");
			System.out.println("Welcom to my system");
			System.out.print("Name: ");
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

			if (isRegistered(id)) {
				System.out.println("This id for other person");
				continue;
			}

			if (id >= 1 && id <= 100) {
				System.out.print("Password: ");
				String pass = scanner.next();
				if (pass.contains("admin123")) {
					globalIds[globalIdsCount++] = id;
					employeeMenu(scanner);
				} else
					System.out.println("Wrong password");
			}

			else if (id > 100 && id < 26000000) {
				globalIds[globalIdsCount++] = id;
				currentStudentId = id;
				currentStudentName = name;
				studentMenu(scanner);
			} else
				System.out.println("ID out of range");

			resetSession();
		}
	}

	public static void studentMenu(Scanner scanner) {

		while (true) {
			System.out.println(
					"1. Show Workshops" + "\n2. Register" + "\n3. Unregister" + "\n4. Show Schedule" + "\n0. Exit");
			System.out.print("Enter Choice: ");

			int choice = -1;
			try {
				choice = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid choice! Input must be a number.");
				scanner.next();
				continue;
			}

			if (choice == 0) {
				System.out.println("logging out");
				break;
			}

			if (choice == 1) {
				System.out.println("==================================");
				for (int i = 0; i < workshopTitles.length; i++) {
					System.out.println((i + 1) + ". " + workshopTitles[i] + " | " + workshopeTime[i] + " | "
							+ workshopLocation[i]);
					if (i != workshopTitles.length - 1)
						System.out.println("--");
				}
				System.out.println("==================================");

			} else if (choice == 2) {
				register(scanner);
			} else if (choice == 3) {
				unregister(scanner);
			} else if (choice == 4) {
				System.out.println("\n--Schedule--");
				for (int i = 0; i < currentStudentRegCount; i++) {
					System.out.println("- " + currentStudentSchedule[i] + "\n");
				}
			}
		}
	}

	public static void register(Scanner scanner) {
		System.out.println("====================================");
		for (int i = 0; i < workshopTitles.length; i++) {
			System.out.println((i + 1) + ". " + workshopTitles[i] + " | Time: " + workshopeTime[i] + "| Outcomes: "
					+ workshopOutcome[i] + " | Seats remaining : "
					+ (workshopCapacity[i] - registeredCount[i]));
			if (i < workshopTitles.length - 1)
				System.out.println("--");
		}
		System.out.println("====================================");

		try {
			System.out.print("Enter workshop ID: ");
			int id = scanner.nextInt() - 1;

			if (id < 0 || id >= workshopTitles.length) {
				System.out.println("Invalid Workshop ID");
				return;
			}

			for (int i = 0; i < currentStudentRegCount; i++) {
				if (workshopeTime[id].equals(currentStudentSchedule[i])) {
					System.out.println("Time conflict! You already have a workshop at " + workshopeTime[id]);
					return;
				}
			}

			for (int i = 0; i < registeredCount[id]; i++) {
				if (participantsIds[id][i] == currentStudentId) {
					System.out.println("You are already registered in this workshop!");
					return;
				}
			}

			if (registeredCount[id] <= workshopCapacity[id]) {
				participantsIds[id][registeredCount[id]] = currentStudentId;
				participantsNames[id][registeredCount[id]] = currentStudentName;

				currentStudentSchedule[currentStudentRegCount] = workshopeTime[id];
				currentStudentRegCount++;

				registeredCount[id]++;
				System.out.println("Success");
			} else {
				System.out.println("Workshop is full!");
			}
		} catch (InputMismatchException e) {
			System.out.println("Error: Input must be a valid number!");
			scanner.next();
		}
	}

	public static void unregister(Scanner scanner) {
		try {
			System.out.println("====================================");

			System.out.print("Enter workshop ID: ");
			int id = scanner.nextInt() - 1;

			if (id < 0 || id >= workshopTitles.length) {
				System.out.println("Invalid Workshop ID");
				return;
			}

			boolean found = false;

			for (int i = 0; i < registeredCount[id]; i++) {
				if (participantsIds[id][i] == currentStudentId) {
					for (int j = i; j < registeredCount[id] - 1; j++) {
						participantsIds[id][j] = participantsIds[id][j + 1];
						participantsNames[id][j] = participantsNames[id][j + 1];
					}
					registeredCount[id]--;
					found = true;
					break;
				}
			}

			if (found) {
				for (int i = 0; i < currentStudentRegCount; i++) {
					if (workshopeTime[id].equals(currentStudentSchedule[i])) {
						for (int j = i; j < currentStudentRegCount - 1; j++) {
							currentStudentSchedule[j] = currentStudentSchedule[j + 1];
						}
						currentStudentSchedule[currentStudentRegCount - 1] = null;
						currentStudentRegCount--;
						System.out.println("Removed");
						System.out.println("====================================");

						return;
					}
				}
			} else {
				System.out.println("You are not registered in this workshop.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Error: Input must be a valid number!");
			scanner.next();
		}
	}

	public static void employeeMenu(Scanner scanner) {

		while (true) {
			System.out.println("====================================");
			System.out.println("1. Print Report" + "\n2. Participants" + "\n0. Exit");
			System.out.print("Enter Choice: ");

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
				System.out.println("==================================");
				for (int i = 0; i < workshopTitles.length; i++) {
					System.out.println((i + 1) + ". " + workshopTitles[i] + " | " + workshopeTime[i] + " | "
							+ workshopLocation[i]);
					if (i != workshopTitles.length - 1)
						System.out.println("--");
				}
			}

			else if (choice == 2) {
				System.out.println("======================================");
				try {
					System.out.print("Enter workshop ID: ");
					int id = scanner.nextInt() - 1;

					if (id < 0 || id >= workshopTitles.length) {
						System.out.println("Invalid Workshop ID");
						return;
					}

					sortParticipants(participantsIds[id], participantsNames[id], registeredCount[id]);

					System.out.println("Participants in -> " + workshopTitles[id]);
					for (int i = 0; i < registeredCount[id]; i++) {
						System.out.println("-" + participantsIds[id][i] + " -> " + participantsNames[id][i]);
					}
				} catch (InputMismatchException e) {
					System.out.println("Error: Input must be a valid number!");
					scanner.next();
				}
			}
		}
	}

	public static boolean isRegistered(int id) {
		for (int i = 0; i < globalIdsCount; i++) {
			if (id == globalIds[i])
				return true;
		}
		return false;
	}

	public static void heapify(int[] ids, String[] names, int n, int i) {

		int max = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;

		if (l < n && ids[l] > ids[max])
			max = l;
		if (r < n && ids[r] > ids[max])
			max = r;
		if (max != i) {

			int swapId = ids[i];
			ids[i] = ids[max];
			ids[max] = swapId;

			String swapName = names[i];
			names[i] = names[max];
			names[max] = swapName;

			heapify(ids, names, n, max);
		}
	}

	public static void sortParticipants(int[] ids, String[] names, int n) {

		for (int i = n / 2 - 1; i >= 0; i--) {
			heapify(ids, names, n, i);
		}

		for (int i = n - 1; i > 0; i--) {

			int swapId = ids[i];
			ids[i] = ids[0];
			ids[0] = swapId;

			String swapName = names[i];
			names[i] = names[0];
			names[0] = swapName;

			heapify(ids, names, i, 0);
		}
	}

	public static void resetSession() {
		currentStudentId = 0;
		currentStudentName = "";
		currentStudentRegCount = 0;
		Arrays.fill(currentStudentSchedule, "");
	}
}