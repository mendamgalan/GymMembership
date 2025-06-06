package src.test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import src.Member;
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
        assertEquals(6, member.getCard().getDuration());  // same duration
    }

    @Test
    public void testReplaceCardWithoutReportingLost() {
        member.replaceCard("C789");  // should not replace because not reported lost
        assertEquals("A123", member.getCard().getNumber());  // old card remains
    }
}
