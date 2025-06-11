package membership;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a gym member.
 * Contains personal details and the associated membership card.
 */
public class Member {
    private static final Logger logger = LogManager.getLogger(Member.class);

    private String name;
    private String phone;
    private String email;
    private Card card;

    /**
     * Constructs a new Member with the given details.
     *
     * @param name  the member's full name
     * @param phone the member's phone number
     * @param email the member's email address
     * @throws IllegalArgumentException if any argument is null or empty
     */
    public Member(String name, String phone, String email) {
        if (name == null || name.isEmpty()) {
            logger.error("Invalid name provided during member creation.");
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (phone == null || phone.isEmpty()) {
            logger.error("Invalid phone number provided during member creation.");
            throw new IllegalArgumentException("Phone number cannot be null or empty.");
        }
        if (email == null || email.isEmpty()) {
            logger.error("Invalid email provided during member creation.");
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        this.name = name;
        this.phone = phone;
        this.email = email;

        logger.info("New member registered: name={}, phone={}, email={}", name, phone, email);
    }

    /**
     * Registers a membership card for the member.
     *
     * @param card the membership card
     * @throws IllegalArgumentException if card is null
     */
    public void registerMembership(Card card) {
        if (card == null) {
            logger.error("Attempted to register a null card for member {}", name);
            throw new IllegalArgumentException("Card cannot be null.");
        }
        this.card = card;
        logger.info("Member {} registered card {}", name, card.getNumber());
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Card getCard() {
        return card;
    }

    /**
     * Changes the membership plan (VIP or regular).
     *
     * @param toVip true to upgrade to VIP, false to downgrade to regular
     * @throws IllegalStateException if card is not registered
     */
    public void changePlan(boolean toVip) {
        if (card == null) {
            logger.error("Member {} tried to change plan without a registered card.", name);
            throw new IllegalStateException("No membership card registered.");
        }
        card.setVip(toVip);
        logger.info("Member {} changed plan to VIP: {}", name, toVip);
    }

    /**
     * Renews the membership by extending the duration.
     *
     * @param months the number of months to extend
     * @throws IllegalStateException if no card is registered
     * @throws IllegalArgumentException if months is not positive
     */
    public void renewMembership(int months) {
        if (card == null) {
            logger.error("Member {} tried to renew without a registered card.", name);
            throw new IllegalStateException("No membership card registered.");
        }
        if (months <= 0) {
            logger.error("Member {} tried to renew with invalid months: {}", name, months);
            throw new IllegalArgumentException("Renewal months must be greater than zero.");
        }
        card.extendDuration(months);
        logger.info("Member {} renewed membership for {} months.", name, months);
    }

    /**
     * Reports the current membership card as lost.
     * The card will be deactivated.
     *
     * @throws IllegalStateException if no card is registered
     */
    public void reportLostCard() {
        if (card == null) {
            logger.error("Member {} attempted to report lost card with no card registered.", name);
            throw new IllegalStateException("No card to report as lost.");
        }
        card.deactivate();
        logger.warn("Member {} reported card {} as lost.", name, card.getNumber());
    }

    /**
     * Replaces the lost card with a new card if the old one is deactivated.
     *
     * @param newCardNumber the number of the new card
     * @throws IllegalStateException if card is not deactivated or not registered
     * @throws IllegalArgumentException if newCardNumber is invalid
     */
    public void replaceCard(String newCardNumber) {
        if (card == null) {
            logger.error("Member {} attempted to replace card, but none is registered.", name);
            throw new IllegalStateException("No card to replace.");
        }
        if (card.isActive()) {
            logger.error("Member {} attempted to replace an active card.", name);
            throw new IllegalStateException("Cannot replace an active card. Please deactivate it first.");
        }
        if (newCardNumber == null || newCardNumber.isEmpty()) {
            logger.error("Member {} provided invalid new card number.", name);
            throw new IllegalArgumentException("New card number cannot be null or empty.");
        }
        this.card = new Card(newCardNumber, card.isVip(), card.getDuration());
        logger.info("Member {} replaced lost card with new card {}", name, newCardNumber);
    }
}
