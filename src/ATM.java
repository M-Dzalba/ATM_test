import java.util.Map;

public class ATM {
    private final Map<String, Card> cardsMap;
    private int attempts;

    public ATM(Map<String, Card> cardsMap) {
        this.cardsMap = cardsMap;
        this.attempts = 3;
    }

    public void authorize(String cardNumber, String enteredPIN) {
        cardsMap.get(cardNumber).setBlocked(!canUnblock(cardNumber));
        if (cardsMap.containsKey(cardNumber) && cardsMap.get(cardNumber).isBlocked()) {
            System.out.println("The card is blocked. Time until unlocking: "
                    + (System.currentTimeMillis() - cardsMap.get(cardNumber).getBlockedUntil()) / 3600000 + " ÷");
            return;
        }

        if (!cardsMap.containsKey(cardNumber)) {
            System.out.println("Invalid card number.");
            return;
        }

        Card card = cardsMap.get(cardNumber);

        if (card.checkPIN(enteredPIN)) {
            System.out.println("Authorization successful.");
            showMenu(card);
        } else {
            attempts--;
            if (attempts > 0) {
                System.out.println("Invalid PIN. Attempts left: " + attempts);
            } else {
                System.out.println("The card is blocked for 24 hours. Please use another card.");
                blockCard(cardNumber);
            }
        }
    }

    private void showMenu(Card card) {
        int choice = 0;
        while (choice != 4) {
            System.out.println("\nMenu:");
            System.out.println("1. Check balance");
            System.out.println("2. Withdraw money");
            System.out.println("3. Top up your account");
            System.out.println("4. Quit");

            System.out.print("Choose an action: ");
            choice = Main.scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Current balance: " + card.getBalance());
                    break;
                case 2:
                    System.out.print("Enter the amount to withdraw: ");
                    double withdrawAmount = Main.scanner.nextDouble();
                    withdraw(card, withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter the amount to top up: ");
                    double depositAmount = Main.scanner.nextDouble();
                    deposit(card, depositAmount);
                    break;
                case 4:
                    System.out.println("Exit the program.");
                    Main.isRunning = false;
                    break;
                default:
                    System.out.println("Wrong choice.");
            }
        }
    }

    public void withdraw(Card card, double amount) {
        double balance = card.getBalance();
        if (amount <= balance) {
            card.setBalance(balance - amount);
            System.out.println("Withdrawal successful. New balance: " + card.getBalance());
        } else {
            System.out.println("Insufficient funds or limit exceeded.");
        }
    }

    public void deposit(Card card, double amount) {
        if (amount <= 1000000) {
            double balance = card.getBalance();
            card.setBalance(balance += amount);
            CardStorage.saveCards(cardsMap);
            System.out.println("Replenishment successful. New balance: " + card.getBalance());
        } else {
            System.out.println("The replenishment amount exceeds the limit.");
        }
    }

    private void blockCard(String cardNumber) {
        cardsMap.get(cardNumber).setBlocked(true);
        cardsMap.get(cardNumber).setBlockedUntil(System.currentTimeMillis() + 86400000);
        CardStorage.saveCards(cardsMap);
    }

    public boolean canUnblock(String cardNumber) {
        if (System.currentTimeMillis() > cardsMap.get(cardNumber).getBlockedUntil()) {
            cardsMap.get(cardNumber).setBlocked(false);
            cardsMap.get(cardNumber).setBlockedUntil(0);
            return true;
        }
        return false;
    }

}
