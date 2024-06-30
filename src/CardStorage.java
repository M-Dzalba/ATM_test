import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CardStorage {
    private static final String FILENAME = "cards.txt";
    protected static Map<String, Card> cardsMap = new HashMap<>();

    public static Map<String, Card> loadCards() {

        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String cardNumber = parts[0];
                String pin = parts[1];
                double balance = Double.parseDouble(parts[2]);
                boolean blocked = Boolean.parseBoolean(parts[3]);
                long blockUntil = Long.parseLong(parts[4]);
                Card card = new Card(cardNumber, pin, balance, blocked, blockUntil);
                cardsMap.put(cardNumber, card);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cardsMap;
    }

    public static void saveCards(Map<String, Card> cardsMap) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Card creditCard : cardsMap.values()) {
                bw.write(creditCard.getCardNumber() + " " + creditCard.getPin() + " " + creditCard.getBalance() + " "
                        + creditCard.isBlocked() + " " + creditCard.getBlockedUntil() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
