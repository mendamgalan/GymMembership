package test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import src.Card;

import java.time.LocalDate;

public class CardTest {

    @Test
    public void testCardCreation() {
        Card card = new Card("12345", true, 6);
        assertEquals("12345", card.getNumber());
        assertTrue(card.isVip());
        assertEquals(6, card.getDuration());
        assertTrue(card.isActive());
    }

    @Test
    public void testExpiryDateCalculation() {
        Card card = new Card("99999", false, 3);
        LocalDate expectedExpiry = LocalDate.now().plusMonths(3);
        assertEquals(expectedExpiry, card.getExpiryDate());
    }

    @Test
    public void testDeactivateCard() {
        Card card = new Card("11111", false, 12);
        card.deactivate();
        assertFalse(card.isActive());
    }
    @Test
    public void testCardSetDuration() {
        Card card = new Card("67890", true, 6);
        card.setDuration(12); // replaces current duration
        assertEquals(12, card.getDuration());
    }
     @Test
    public void testInvalidCardNumberThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Card("", true, 3);
        });
        assertEquals("Card number cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void testInvalidCardDurationThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Card("123", false, 0);
        });
        assertEquals("Duration must be greater than zero.", exception.getMessage());
    }

    @Test
    public void testExtendWithNegativeDurationThrowsException() {
        Card card = new Card("123", false, 3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            card.extendDuration(-5);
        });
        assertEquals("Extension months must be greater than zero.", exception.getMessage());
    }

    @Test
    public void testSetInvalidDurationThrowsException() {
        Card card = new Card("123", false, 3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            card.setDuration(0);
        });
        assertEquals("Duration must be greater than zero.", exception.getMessage());
    }

}
