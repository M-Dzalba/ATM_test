import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<String, Card> cardsMap;
    protected static ATM atm;
    static Scanner scanner = new Scanner(System.in);
    static boolean isRunning = true;

    public static void main(String[] args) {
        cardsMap = CardStorage.loadCards();
        atm = new ATM(cardsMap);

        while (isRunning) {
            System.out.print("\nEnter card number (XXXX-XXXX-XXXX-XXXX): ");
            String cardNumber = scanner.nextLine();
            if (!cardsMap.containsKey(cardNumber)) {
                System.out.println("Invalid card number. Try again.");
                continue;
            }
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();
            atm.authorize(cardNumber, pin);
        }

        CardStorage.saveCards(cardsMap);
        System.out.println("The program is completed. Data saved.");
        scanner.close();

    }
}
