package cinema;

import java.util.Scanner;

public class Cinema {

    private static int rows;
    private static int seats;
    private static String[][] seatsArray;
    private static Scanner scanner;
    private static int purchasedTickets = 0;
    private static int currentIncome = 0;
    private static int totalIncome;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();
        seatsArray = new String[rows + 1][seats + 1];
        fillCinemaHall();
        totalIncome = calculateCosts();
        showMenu();
    }

    private static void showMenu() {
        System.out.println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
        int selectedMenuItem = scanner.nextInt();
        switch (selectedMenuItem) {
            case 1:
                printSeats();
                break;
            case 2:
                buyTicket();
                break;
            case 3:
                showStatistics();
                break;
            default:
                break;
        }
    }

    private static void showStatistics() {
        System.out.printf("\nNumber of purchased tickets: %d\n", purchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", 100 / ((double) rows * (double) seats) * purchasedTickets);
        System.out.printf("Current income: $%d\n", currentIncome);
        System.out.printf("Total income: $%d\n", totalIncome);
        showMenu();
    }

    private static void buyTicket() {
        System.out.println("\nEnter a row number:");
        int selectedRow = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int selectedSeat = scanner.nextInt();
        if (isBookingValid(selectedRow, selectedSeat)) {
            bookSeat(selectedRow, selectedSeat);
            int ticketPrice = getTicketPrice(selectedRow);
            System.out.println("\nTicket price: $" + ticketPrice);
            currentIncome += ticketPrice;
            purchasedTickets++;
        } else {
            buyTicket();
        }
        showMenu();
    }

    private static boolean isBookingValid(int selectedRow, int selectedSeat) {
        if (selectedRow > rows || selectedRow < 1 || selectedSeat > seats || selectedSeat < 1) {
            System.out.println("\nWrong input!");
            return false;
        } else if ("B".equals(seatsArray[selectedRow][selectedSeat])) {
            System.out.println("\nThat ticket has already been purchased!");
            return false;
        }
        return true;
    }

    private static void fillCinemaHall() {
        for (int i = 0; i < rows + 1; i++) {
            for (int j = 0; j < seats + 1; j++) {
                if (i == 0 && j == 0) {
                    seatsArray[i][j] = " ";
                } else if (i == 0) {
                    seatsArray[i][j] = Integer.toString(j);
                } else if (j == 0) {
                    seatsArray[i][j] = Integer.toString(i);
                } else {
                    seatsArray[i][j] = "S";
                }
            }
        }
    }

    private static int getTicketPrice(int selectedRow) {
        int totalSeats = rows * seats;
        if (totalSeats <= 60) {
            return 10;
        } else {
            return (rows - rows % 2) / 2 >= selectedRow ? 10 : 8;
        }
    }

    private static void printSeats() {
        System.out.println("\nCinema:");
        for (int i = 0; i < rows + 1; i++) {
            for (int j = 0; j < seats + 1; j++) {
                System.out.print(seatsArray[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        showMenu();
    }

    private static void bookSeat(int selectedRow, int selectedSeat) {
        seatsArray[selectedRow][selectedSeat] = "B";
    }

    private static int calculateCosts() {
        int totalSeats = rows * seats;
        if (totalSeats <= 60) {
            return totalSeats * 10;
        } else {
            int firstPart = (rows - rows % 2) / 2 * seats * 10;
            int secondPart = (rows + rows % 2) / 2 * seats * 8;
            return firstPart + secondPart;
        }
    }
}