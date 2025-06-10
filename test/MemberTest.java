package test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import srcCode.Member;
import src.Card;

public class MemberTest {

    private Member member;
    private Card card;

    @BeforeEach
    public void setup() {
        member = new Member("Bold", "99119911", "bold@example.com");
        card = new Card("A123", false, 6); // Regular
        member.registerMembership(card);
    }

    @Test
    public void testRegisterMembership() {
        assertEquals("Active", member.getCard().isActive() ? "Active" : "Inactive");
        assertEquals("A123", member.getCard().getNumber());
    }

    @Test
    public void testChangePlanToVip() {
        member.changePlan(true); // make VIP
        assertTrue(member.getCard().isVip());
    }

    @Test
    public void testRenewMembership() {
        int originalDuration = member.getCard().getDuration();
        member.renewMembership(3);
        assertEquals(originalDuration + 3, member.getCard().getDuration());
    }

    @Test
    public void testReportLostCard() {
        member.reportLostCard();
        assertFalse(member.getCard().isActive());
    }

    @Test
    public void testReplaceCardAfterLoss() {
        member.reportLostCard();
        member.replaceCard("B456");

        assertEquals("B456", member.getCard().getNumber());
        assertTrue(member.getCard().isActive());
        assertEquals(6, member.getCard().getDuration());  //same duration
    }

   @Test
    public void testReplaceCardWithoutReportingLost() {
    member.registerMembership(new Card("A123", false, 6));

    Exception exception = assertThrows(IllegalStateException.class, () -> {
        member.replaceCard("C789");  // should fail, card is still active
    });

    assertEquals("Cannot replace an active card. Please deactivate it first.", exception.getMessage());
}

    @Test
    public void testRenewMembershipExtendsDuration() {
        // Create a new Card with 3 months
        Card card = new Card("12345", false, 3);

        // Create a Member with the card
        Member member = new Member("John Doe", "99112233", "john@example.com");
        member.registerMembership(card);

        // Renew for 2 more months
        member.renewMembership(2);

        // Expected duration = 3 + 2 = 5
        assertEquals(5, member.getCard().getDuration());
    }
     @Test
    public void testInvalidMemberNameThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Member("", "99112233", "email@example.com");
        });
        assertEquals("Name cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void testRegisterNullCardThrowsException() {
        Member member = new Member("Test", "123", "test@email.com");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            member.registerMembership(null);
        });
        assertEquals("Card cannot be null.", exception.getMessage());
    }

    @Test
    public void testChangePlanWithoutCardThrowsException() {
        Member member = new Member("Test", "123", "test@email.com");
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            member.changePlan(true);
        });
        assertEquals("No membership card registered.", exception.getMessage());
    }

    @Test
    public void testRenewWithoutCardThrowsException() {
        Member member = new Member("Test", "123", "test@email.com");
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            member.renewMembership(1);
        });
        assertEquals("No membership card registered.", exception.getMessage());
    }

    @Test
    public void testRenewWithInvalidMonthsThrowsException() {
        Member member = new Member("Test", "123", "test@email.com");
        member.registerMembership(new Card("123", false, 3));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            member.renewMembership(0);
        });
        assertEquals("Renewal months must be greater than zero.", exception.getMessage());
    }

    @Test
    public void testReportLostCardWhenNoneThrowsException() {
        Member member = new Member("Test", "123", "test@email.com");
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            member.reportLostCard();
        });
        assertEquals("No card to report as lost.", exception.getMessage());
    }

    @Test
    public void testReplaceCardWhenNoneThrowsException() {
        Member member = new Member("Test", "123", "test@email.com");
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            member.replaceCard("999");
        });
        assertEquals("No card to replace.", exception.getMessage());
    }

    @Test
    public void testReplaceActiveCardThrowsException() {
        Member member = new Member("Test", "123", "test@email.com");
        member.registerMembership(new Card("123", true, 6));
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            member.replaceCard("999");
        });
        assertEquals("Cannot replace an active card. Please deactivate it first.", exception.getMessage());
    }

    @Test
    public void testReplaceCardWithNullNumberThrowsException() {
        Member member = new Member("Test", "123", "test@email.com");
        Card card = new Card("123", true, 6);
        card.deactivate();
        member.registerMembership(card);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            member.replaceCard("");
        });
        assertEquals("New card number cannot be null or empty.", exception.getMessage());
    }
}
