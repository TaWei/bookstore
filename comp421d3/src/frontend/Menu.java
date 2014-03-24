package frontend;

import java.util.Scanner;

import backend.QueryExecutor;

public class Menu {
	
	public void showMenu(){
		System.out.println();
		System.out.println("Please choose one of the following options:");
		System.out.println("(1)-Add user");
		System.out.println("(2)-Add book");
		System.out.println("(3)-Get books ordered by user that are greater than N stars");
		System.out.println("(4)-Get top N customers for a given year");
		System.out.println("(5)-Get top N genres for a given year");
		System.out.println("(6)-Extend coupon expiration date");
		System.out.println("(7)-Change destination address of active order");
		System.out.println("(8)-Quit");
	}
	
	public static void main(String[] args){
		Menu menu = new Menu();
		QueryExecutor qe = new QueryExecutor();
		
		System.out.println("Successfully connected to comp421 db as user cs421g18...");
		
		Scanner keyboard = new Scanner(System.in);
		int choice = 0;
		
		do{
			choice = 0;
			
			while(choice < 1 || choice > 8){
			  menu.showMenu();
			  choice = keyboard.nextInt();
			}
			
			if(choice == 1){
				qe.addUser();
			}else if(choice == 2){
				qe.addBook();
			}else if(choice == 3){
				qe.getBooksGreaterThanNStarsForUser();
			}else if(choice == 4){
				qe.getNBestCustomersForYear();
			}else if(choice == 5){
				qe.getNMostPopularGenresForYear();
			}else if(choice == 6){
				qe.changeCouponExpirationDate();
			}else if(choice == 7){
				qe.changeDestAddressOfActiveOrder();
			}else if(choice == 8){
				System.out.println("Come back soon!");
			}
		}while(choice != 8);
	}
}
