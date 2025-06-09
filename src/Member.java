package src;

/**
 * Represents a gym member.
 * Contains personal details and the associated membership card.
 */
public class Member {
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
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty.");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Registers a membership card for the member.
     *
     * @param card the membership card
     * @throws IllegalArgumentException if card is null
     */
    public void registerMembership(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null.");
        }
        this.card = card;
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
            throw new IllegalStateException("No membership card registered.");
        }
        card.setVip(toVip);
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
            throw new IllegalStateException("No membership card registered.");
        }
        if (months <= 0) {
            throw new IllegalArgumentException("Renewal months must be greater than zero.");
        }
        card.extendDuration(months);
    }

    /**
     * Reports the current membership card as lost.
     * The card will be deactivated.
     *
     * @throws IllegalStateException if no card is registered
     */
    public void reportLostCard() {
        if (card == null) {
            throw new IllegalStateException("No card to report as lost.");
        }
        card.deactivate();
    }

    /*
     * Replaces the lost card with a new card if the old one is deactivated.
     *
     * @param newCardNumber the number of the new card
     * @throws IllegalStateException if card is not deactivated or not registered
     * @throws IllegalArgumentException if newCardNumber is invalid
     */
    public void replaceCard(String newCardNumber) {
        if (card == null) {
            throw new IllegalStateException("No card to replace.");
        }
        if (card.isActive()) {
            throw new IllegalStateException("Cannot replace an active card. Please deactivate it first.");
        }
        if (newCardNumber == null || newCardNumber.isEmpty()) {
            throw new IllegalArgumentException("New card number cannot be null or empty.");
        }
        this.card = new Card(newCardNumber, card.isVip(), card.getDuration());
    }
}
