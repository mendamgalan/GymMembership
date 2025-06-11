package membership;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import testutil.*;
public class MemberTest {

    private Member member;
    private Card card;

    @BeforeEach
    void setUp() {
        member = new Member("Bold", "99119911", "bold@example.com");
        card = new Card("A123", false, 6);
    }

    @Test
    void testMemberCreation() {
        assertEquals("Bold", member.getName());
        assertEquals("99119911", member.getPhone());
        assertEquals("bold@example.com", member.getEmail());
        assertNull(member.getCard());
    }

    @Test
    void testInvalidNameThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Member("", "123", "test@example.com");
        });
        assertEquals("Name cannot be null or empty.", ex.getMessage());
    }

    @Test
    void testNullNameThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Member(null, "123", "test@example.com");
        });
        assertEquals("Name cannot be null or empty.", ex.getMessage());
    }

    @Test
    void testInvalidPhoneThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Member("Test", "", "test@example.com");
        });
        assertEquals("Phone number cannot be null or empty.", ex.getMessage());
    }

    @Test
    void testInvalidEmailThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Member("Test", "123", "");
        });
        assertEquals("Email cannot be null or empty.", ex.getMessage());
    }

    @Test
    void testRegisterMembership() {
        member.registerMembership(card);
        assertEquals(card, member.getCard());
        assertEquals("A123", member.getCard().getNumber());
    }

    @Test
    void testRegisterNullCardThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            member.registerMembership(null);
        });
        assertEquals("Card cannot be null.", ex.getMessage());
    }

    @Test
    void testChangePlan() {
        member.registerMembership(card);
        member.changePlan(true);
        assertTrue(member.getCard().isVip());
        member.changePlan(false);
        assertFalse(member.getCard().isVip());
    }

    @Test
    void testChangePlanWithoutCardThrowsException() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            member.changePlan(true);
        });
        assertEquals("No membership card registered.", ex.getMessage());
    }

    @Test
    void testRenewMembership() {
        member.registerMembership(card);
        member.renewMembership(3);
        assertEquals(9, member.getCard().getDuration());
        assertEquals(card.getIssueDate().plusMonths(9), member.getCard().getExpiryDate());
    }

    @Test
    void testRenewWithoutCardThrowsException() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            member.renewMembership(3);
        });
        assertEquals("No membership card registered.", ex.getMessage());
    }

    @Test
    void testRenewWithInvalidMonthsThrowsException() {
        member.registerMembership(card);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            member.renewMembership(0);
        });
        assertEquals("Renewal months must be greater than zero.", ex.getMessage());
    }

    @Test
    void testReportLostCard() {
        member.registerMembership(card);
        member.reportLostCard();
        assertFalse(member.getCard().isActive());
    }

    @Test
    void testReportLostCardWithoutCardThrowsException() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            member.reportLostCard();
        });
        assertEquals("No card to report as lost.", ex.getMessage());
    }

    @Test
    void testReplaceCardAfterLoss() {
        member.registerMembership(card);
        member.reportLostCard();
        member.replaceCard("B456");
        assertEquals("B456", member.getCard().getNumber());
        assertTrue(member.getCard().isActive());
        assertEquals(6, member.getCard().getDuration());
        assertFalse(member.getCard().isVip());
    }

    @Test
    void testReplaceCardWithoutLossThrowsException() {
        member.registerMembership(card);
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            member.replaceCard("B456");
        });
        assertEquals("Cannot replace an active card. Please deactivate it first.", ex.getMessage());
    }

    @Test
    void testReplaceCardWithoutCardThrowsException() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            member.replaceCard("B456");
        });
        assertEquals("No card to replace.", ex.getMessage());
    }

    @Test
    void testReplaceCardWithInvalidNumberThrowsException() {
        member.registerMembership(card);
        member.reportLostCard();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            member.replaceCard("");
        });
        assertEquals("New card number cannot be null or empty.", ex.getMessage());
    }

    @Test
    void testMemberCreationLogging() {
        try (LogTestUtil logs = new LogTestUtil(Member.class)) {
            logs.clear();
            new Member("Alice", "99887766", "alice@example.com");
            List<String> messages = logs.getMessagesContaining("New member registered");
            if (messages.isEmpty()) {
                System.err.println("testMemberCreationLogging: No 'New member registered' logs found. All logs: " + logs.getAllMessages());
            }
            assertFalse(messages.isEmpty(), "No log messages found for member creation");
            assertTrue(messages.get(0).contains("Alice"), "Log message does not contain member name Alice");
        }
    }

    @Test
    void testRegisterMembershipLogging() {
        try (LogTestUtil logs = new LogTestUtil(Member.class)) {
            logs.clear();
            Member m = new Member("Bob", "44556677", "bob@example.com");
            m.registerMembership(new Card("Z999", false, 6));
            List<String> messages = logs.getMessagesContaining("Member Bob registered card Z999");
            if (messages.isEmpty()) {
                System.err.println("testRegisterMembershipLogging: No 'registered card' logs found. All logs: " + logs.getAllMessages());
            }
            assertFalse(messages.isEmpty(), "No log messages found for card registration");
            assertTrue(messages.get(0).contains("Z999"), "Log message does not contain card number Z999");
        }
    }

    @Test
    void testChangePlanLogging() {
        try (LogTestUtil logs = new LogTestUtil(Member.class)) {
            logs.clear();
            member.registerMembership(card);
            member.changePlan(true);
            List<String> messages = logs.getMessagesContaining("Member Bold changed plan to VIP: true");
            if (messages.isEmpty()) {
                System.err.println("testChangePlanLogging: No 'changed plan to VIP' logs found. All logs: " + logs.getAllMessages());
            }
            assertFalse(messages.isEmpty(), "No log messages found for plan change");
            assertTrue(messages.get(0).contains("Bold"), "Log message does not contain member name Bold");
        }
    }

    @Test
    void testRenewMembershipLogging() {
        try (LogTestUtil logs = new LogTestUtil(Member.class)) {
            logs.clear();
            member.registerMembership(card);
            member.renewMembership(3);
            List<String> messages = logs.getMessagesContaining("Member Bold renewed membership for 3 months");
            if (messages.isEmpty()) {
                System.err.println("testRenewMembershipLogging: No 'renewed membership' logs found. All logs: " + logs.getAllMessages());
            }
            assertFalse(messages.isEmpty(), "No log messages found for membership renewal");
            assertTrue(messages.get(0).contains("3"), "Log message does not contain renewal months 3");
        }
    }

    @Test
    void testReportLostCardLogging() {
        try (LogTestUtil logs = new LogTestUtil(Member.class)) {
            logs.clear();
            member.registerMembership(card);
            member.reportLostCard();
            List<String> messages = logs.getMessagesContaining("Member Bold reported card A123 as lost");
            if (messages.isEmpty()) {
                System.err.println("testReportLostCardLogging: No 'reported card as lost' logs found. All logs: " + logs.getAllMessages());
            }
            assertFalse(messages.isEmpty(), "No log messages found for reporting lost card");
            assertTrue(messages.get(0).contains("A123"), "Log message does not contain card number A123");
        }
    }

    @Test
    void testReplaceCardLogging() {
        try (LogTestUtil logs = new LogTestUtil(Member.class)) {
            logs.clear();
            member.registerMembership(card);
            member.reportLostCard();
            member.replaceCard("B456");
            List<String> messages = logs.getMessagesContaining("Member Bold replaced lost card with new card B456");
            if (messages.isEmpty()) {
                System.err.println("testReplaceCardLogging: No 'replaced lost card' logs found. All logs: " + logs.getAllMessages());
            }
            assertFalse(messages.isEmpty(), "No log messages found for card replacement");
            assertTrue(messages.get(0).contains("B456"), "Log message does not contain new card number B456");
        }
    }
}