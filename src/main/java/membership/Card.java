package membership;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

/**
 * Represents a membership card in the gym system.
 * It includes information about card number, membership type (VIP or regular),
 * duration in months, and its active status.
 */
public class Card {
    private static final Logger logger = LogManager.getLogger(Card.class);

    private String number;
    private boolean isVip;
    private int duration;
    private boolean isActive;
    private LocalDate issueDate;
    private LocalDate expiryDate;

    /**
     * Constructs a new Card with the given number, VIP status, and duration.
     *
     * @param number   the card number
     * @param isVip    whether the card is a VIP membership
     * @param duration duration in months
     * @throws IllegalArgumentException if card number is invalid or duration is not positive
     */
    public Card(String number, boolean isVip, int duration) {
        if (number == null || number.trim().isEmpty()) {
            logger.error("Attempted to create card with null or empty number.");
            throw new IllegalArgumentException("Card number cannot be null or empty.");
        }
        if (duration <= 0) {
            logger.error("Attempted to create card with invalid duration: {}", duration);
            throw new IllegalArgumentException("Duration must be greater than zero.");
        }

        this.number = number;
        this.isVip = isVip;
        this.duration = duration;
        this.isActive = true;
        this.issueDate = LocalDate.now();
        this.expiryDate = issueDate.plusMonths(duration);

        logger.info("Created new card: number={}, isVip={}, duration={} months", number, isVip, duration);
    }

    public String getNumber() {
        return number;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        this.isVip = vip;
        logger.info("Card {} updated VIP status to: {}", number, vip);
    }

    public int getDuration() {
        return duration;
    }

    /**
     * Adds months to the membership duration.
     *
     * @param months the number of months to extend
     * @throws IllegalArgumentException if months is not positive
     */
    public void extendDuration(int months) {
        if (months <= 0) {
            logger.error("Invalid duration extension for card {}: {}", number, months);
            throw new IllegalArgumentException("Extension months must be greater than zero.");
        }
        this.duration += months;
        this.expiryDate = this.expiryDate.plusMonths(months);
        logger.info("Extended duration of card {} by {} months. New duration: {}", number, months, duration);
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * Deactivates the card.
     */
    public void deactivate() {
        this.isActive = false;
        logger.info("Card {} has been deactivated.", number);
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the duration manually and recalculates expiry date.
     *
     * @param duration new duration in months
     * @throws IllegalArgumentException if duration is not positive
     */
    public void setDuration(int duration) {
        if (duration <= 0) {
            logger.error("Attempted to set invalid duration for card {}: {}", number, duration);
            throw new IllegalArgumentException("Duration must be greater than zero.");
        }
        this.duration = duration;
        this.expiryDate = issueDate.plusMonths(duration);
        logger.info("Card {} duration manually set to {} months.", number, duration);
    }
}
