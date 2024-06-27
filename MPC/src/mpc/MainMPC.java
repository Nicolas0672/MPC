package mpc;
import java.util.Scanner;
public class MainMPC {

	public static void main(String[] args) {
	
		GameControl control = new GameControl();
		Scanner scnr = new Scanner(System.in);
		
		System.out.println("\tWelcome to the world of MPC");
		control.startGame();
		
		while(true)
		{
			
			control.displayMenu();
			System.out.println("\nWould you like to continue?\n\nPress 1 to continue playing\nPress 2 to exit game");
			int num = scnr.nextInt();
			
			while (num != 1 && num != 2) {
		        System.out.println("Invalid choice. Please press 1 to continue playing or 2 to exit game.");
		        num = scnr.nextInt();
		    }
			
			if(num == 2)
			{
				System.out.println("Thank you for playing! Exiting game...");
				break;
			}
		}

	}

}

