package src.test;
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
}
