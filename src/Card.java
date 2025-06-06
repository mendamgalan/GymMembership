package src;
import java.time.LocalDate;

/**
 * Represents a membership card in the gym system.
 * It includes information about card number, membership type (VIP or regular),
 * duration in months, and its active status.
 */
public class Card {
    private String number;
    private boolean isVip;
    private int duration;
    private boolean isActive;
    private LocalDate issueDate;
    private LocalDate expiryDate;

    /**
     * Constructs a new Card with the given number, VIP status, and duration.
     *
     * @param number  the card number
     * @param isVip   whether the card is a VIP membership
     * @param duration duration in months
     */
    public Card(String number, boolean isVip, int duration) {
        this.number = number;
        this.isVip = isVip;
        this.duration = duration;
        this.isActive = true;
        this.issueDate = LocalDate.now();
        this.expiryDate = issueDate.plusMonths(duration);
    }

    /**
     * Returns the card number.
     *
     * @return the card number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Returns whether the card is for a VIP member.
     *
     * @return true if VIP, false otherwise
     */
    public boolean isVip() {
        return isVip;
    }

    /**
     * Sets the VIP status of the card.
     *
     * @param vip the new VIP status
     */
    public void setVip(boolean vip) {
        isVip = vip;
    }

    /**
     * Returns the duration of the card in months.
     *
     * @return the duration in months
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Adds months to the membership duration.
     *
     * @param months the number of months to extend
     */
    public void extendDuration(int months) {
        this.duration += months;
        this.expiryDate = this.expiryDate.plusMonths(months);
    }

    /**
     * Returns whether the card is active.
     *
     * @return true if the card is active, false otherwise
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Deactivates the card.
     */
    public void deactivate() {
        isActive = false;
    }

    /**
     * Returns the issue date of the card.
     *
     * @return the issue date
     */
    public LocalDate getIssueDate() {
        return issueDate;
    }

    /**
     * Returns the expiry date of the card.
     *
     * @return the expiry date
     */
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }
}
