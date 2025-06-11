package membership;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import testutil.*;
import java.time.LocalDate;
import java.util.List;

public class CardTest {

    @Test
    void testCardCreation() {
        Card card = new Card("12345", true, 6);
        assertEquals("12345", card.getNumber());
        assertTrue(card.isVip());
        assertEquals(6, card.getDuration());
        assertTrue(card.isActive());
        assertEquals(LocalDate.now(), card.getIssueDate());
        assertEquals(LocalDate.now().plusMonths(6), card.getExpiryDate());
    }

    @Test
    void testInvalidCardNumberThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Card("", true, 3);
        });
        assertEquals("Card number cannot be null or empty.", ex.getMessage());
    }

    @Test
    void testNullCardNumberThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Card(null, true, 3);
        });
        assertEquals("Card number cannot be null or empty.", ex.getMessage());
    }

    @Test
    void testInvalidDurationThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Card("123", false, 0);
        });
        assertEquals("Duration must be greater than zero.", ex.getMessage());
    }

    @Test
    void testSetVip() {
        Card card = new Card("123", false, 3);
        card.setVip(true);
        assertTrue(card.isVip());
        card.setVip(false);
        assertFalse(card.isVip());
    }

    @Test
    void testExtendDuration() {
        Card card = new Card("123", false, 3);
        card.extendDuration(2);
        assertEquals(5, card.getDuration());
        assertEquals(card.getIssueDate().plusMonths(5), card.getExpiryDate());
    }

    @Test
    void testExtendWithNegativeDurationThrowsException() {
        Card card = new Card("123", false, 3);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            card.extendDuration(-1);
        });
        assertEquals("Extension months must be greater than zero.", ex.getMessage());
    }

    @Test
    void testDeactivate() {
        Card card = new Card("123", false, 3);
        card.deactivate();
        assertFalse(card.isActive());
    }

    @Test
    void testSetDuration() {
        Card card = new Card("123", false, 3);
        card.setDuration(6);
        assertEquals(6, card.getDuration());
        assertEquals(card.getIssueDate().plusMonths(6), card.getExpiryDate());
    }

    @Test
    void testSetInvalidDurationThrowsException() {
        Card card = new Card("123", false, 3);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            card.setDuration(0);
        });
        assertEquals("Duration must be greater than zero.", ex.getMessage());
    }

    @Test
    void testCardCreationLogging() {
        try (LogTestUtil logs = new LogTestUtil(Card.class)) {
            logs.clear();
            Card card = new Card("LOG1", false, 2);
            List<String> messages = logs.getMessagesContaining("Created new card");
            if (messages.isEmpty()) {
                System.err.println("testCardCreationLogging: No 'Created new card' logs found. All logs: " + logs.getAllMessages());
                card.setVip(true);
                System.err.println("testCardCreationLogging: After setVip, all logs: " + logs.getAllMessages());
            }
            assertFalse(messages.isEmpty(), "No log messages found for card creation");
            assertTrue(messages.get(0).contains("LOG1"), "Log message does not contain card number LOG1");
        }
    }

    @Test
    void testDeactivateLogging() {
        try (LogTestUtil logs = new LogTestUtil(Card.class)) {
            logs.clear();
            Card card = new Card("LOG2", false, 2);
            card.deactivate();
            List<String> messages = logs.getMessagesContaining("Card LOG2 has been deactivated");
            if (messages.isEmpty()) {
                System.err.println("testDeactivateLogging: No 'Card deactivated' logs found. All logs: " + logs.getAllMessages());
            }
            assertFalse(messages.isEmpty(), "No log messages found for card deactivation");
            assertTrue(messages.get(0).contains("LOG2"), "Log message does not contain card number LOG2");
        }
    }

    @Test
    void testSetVipLogging() {
        try (LogTestUtil logs = new LogTestUtil(Card.class)) {
            logs.clear();
            Card card = new Card("LOG3", false, 2);
            card.setVip(true);
            List<String> messages = logs.getMessagesContaining("Card LOG3 updated VIP status to: true");
            if (messages.isEmpty()) {
                System.err.println("testSetVipLogging: No 'updated VIP status' logs found. All logs: " + logs.getAllMessages());
            }
            assertFalse(messages.isEmpty(), "No log messages found for VIP status update");
            assertTrue(messages.get(0).contains("LOG3"), "Log message does not contain card number LOG3");
        }
    }

    @Test
    void testExtendDurationLogging() {
        try (LogTestUtil logs = new LogTestUtil(Card.class)) {
            logs.clear();
            Card card = new Card("LOG4", false, 2);
            card.extendDuration(3);
            List<String> messages = logs.getMessagesContaining("Extended duration of card LOG4 by 3 months");
            if (messages.isEmpty()) {
                System.err.println("testExtendDurationLogging: No 'Extended duration' logs found. All logs: " + logs.getAllMessages());
            }
            assertFalse(messages.isEmpty(), "No log messages found for duration extension");
            assertTrue(messages.get(0).contains("LOG4"), "Log message does not contain card number LOG4");
        }
    }

    @Test
    void testSetDurationLogging() {
        try (LogTestUtil logs = new LogTestUtil(Card.class)) {
            logs.clear();
            Card card = new Card("LOG5", false, 2);
            card.setDuration(4);
            List<String> messages = logs.getMessagesContaining("Card LOG5 duration manually set to 4 months");
            if (messages.isEmpty()) {
                System.err.println("testSetDurationLogging: No 'duration manually set' logs found. All logs: " + logs.getAllMessages());
            }
            assertFalse(messages.isEmpty(), "No log messages found for duration set");
            assertTrue(messages.get(0).contains("LOG5"), "Log message does not contain card number LOG5");
        }
    }
}