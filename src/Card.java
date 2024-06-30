public class Card {
    private final String cardNumber;
    private final String pin;
    private double balance;
    private boolean blocked;
    private long blockedUntil;

    public Card(String cardNumber, String pin, double balance, boolean blocked, long blockUntil) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
        this.blocked = blocked;
        this.blockedUntil = blockUntil;
    }

    public String getCardNumber() {
        return cardNumber;
    }


    public String getPin() {
        return pin;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean checkPIN(String enteredPIN) {
        return this.pin.equals(enteredPIN);
    }

    public double getBalance() {
        return balance;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public long getBlockedUntil() {
        return blockedUntil;
    }

    public void setBlockedUntil(long blockedUntil) {
        this.blockedUntil = blockedUntil;
    }

}



