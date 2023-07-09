package sber.bank.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sber.bank.domain.Account;
import sber.bank.domain.Card;
import sber.bank.domain.User;
import sber.bank.exceptions.BadArgumentException;
import sber.bank.exceptions.NotFoundException;
import sber.bank.service.implementation.CardService;
import sber.bank.validation.Validation;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardControllerTest {
    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCard_ShouldReturnTrue_WhenCardIsCreatedSuccessfully() {
        Card card = new Card(1234567890123456L, new Account(1234567890L, new User(), "USD", 1000.0), new Date(), 123);

        when(cardService.create(card)).thenReturn(card);

        boolean result = cardController.createCard(card);

        assertTrue(result);
        verify(cardService, times(1)).create(card);
    }

    @Test
    public void testCreateCard_ShouldThrowBadArgumentException_WhenCardDataIsInvalid() {
        Card card = new Card(null, null, null, null);

        assertThrows(BadArgumentException.class, () -> cardController.createCard(card));
        verify(cardService, never()).create(card);
    }

    @Test
    public void testDeleteCard_ShouldCallCardServiceDeleteMethod_WhenCardNumberIsValid() {
        Long cardNumber = 123456789012345L;

        assertThrows(BadArgumentException.class, ()-> cardController.deleteCard(cardNumber));
    }

    @Test
    public void testDeleteCard_ShouldThrowBadArgumentException_WhenCardNumberIsInvalid() {
        Long cardNumber = -1L;

        assertThrows(BadArgumentException.class, () -> cardController.deleteCard(cardNumber));
    }

    @Test
    public void testUpdateCard_ShouldCallCardServiceUpdateMethod_WhenCardNumberAndCardDetailsAreValid() {
        Long cardNumber = 123456789012345L;
        Card cardDetail = new Card(123456789012345L, null, new Date(), 123);

        assertThrows(BadArgumentException.class, () -> cardController.updateCard(cardNumber, cardDetail));
    }

    @Test
    public void testUpdateCard_ShouldThrowBadArgumentException_WhenCardNumberIsInvalid() {
        Long cardNumber = -1L;
        Card cardDetail = new Card(1234567890123456L, null, new Date(), 123);

        assertThrows(BadArgumentException.class, () -> cardController.updateCard(cardNumber, cardDetail));
        verify(cardService, never()).update(cardNumber, cardDetail);
    }

    @Test
    public void testUpdateCard_ShouldThrowBadArgumentException_WhenCardDetailsAreInvalid() {
        Long cardNumber = 1234567890123456L;
        Card cardDetail = new Card(null, null, null, null);

        assertThrows(BadArgumentException.class, () -> cardController.updateCard(cardNumber, cardDetail));
        verify(cardService, never()).update(cardNumber, cardDetail);
    }
}