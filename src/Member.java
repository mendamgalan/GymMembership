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
     */
    public Member(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Registers a membership card for the member.
     *
     * @param card the membership card
     */
    public void registerMembership(Card card) {
        this.card = card;
    }

    /**
     * Returns the member's name.
     *
     * @return the member's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the member's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the member's phone number.
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the member's membership card.
     *
     * @return the card
     */
    public Card getCard() {
        return card;
    }

    /**
     * Changes the membership plan (VIP or regular).
     *
     * @param toVip true to upgrade to VIP, false to downgrade to regular
     */
    public void changePlan(boolean toVip) {
        if (card != null) {
            card.setVip(toVip);
        }
    }

    /**
     * Renews the membership by extending the duration.
     *
     * @param months the number of months to extend
     */
    public void renewMembership(int months) {
        if (card != null) {
            card.extendDuration(months);
        }
    }

    /**
     * Reports the current membership card as lost.
     * The card will be deactivated.
     */
    public void reportLostCard() {
        if (card != null) {
            card.deactivate();
        }
    }

    /**
     * Replaces the lost card with a new card if the old one is deactivated.
     *
     * @param newCardNumber the number of the new card
     */
    public void replaceCard(String newCardNumber) {
        if (card != null && !card.isActive()) {
            this.card = new Card(newCardNumber, card.isVip(), card.getDuration());
        }
    }
}
