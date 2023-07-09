package sber.bank.service.implementation;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sber.bank.domain.Card;
import sber.bank.exceptions.NotFoundException;
import sber.bank.repos.CardRepository;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardServiceTest {
    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    public CardServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetByPk_ValidNumber_ReturnsCard() {
        Long cardNumber = 1111222233334444L;
        Card expectedCard = new Card();
        expectedCard.setNumber(cardNumber);
        expectedCard.setAccount(null);
        expectedCard.setExpirationDate(new Date());
        expectedCard.setCvv(123);

        when(cardRepository.findById(cardNumber)).thenReturn(Optional.of(expectedCard));

        Card actualCard = cardService.getByPk(cardNumber);

        assertEquals(expectedCard, actualCard);
        verify(cardRepository, times(1)).findById(cardNumber);
    }

    @Test
    public void testGetByPk_InvalidNumber_ThrowsNotFoundException() {
        Long cardNumber = 1111222233334444L;

        when(cardRepository.findById(cardNumber)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cardService.getByPk(cardNumber));
        verify(cardRepository, times(1)).findById(cardNumber);
    }

    @Test
    public void testCreate_ShouldCreateCard() {
        Card card = new Card();
        card.setNumber(1111222233334444L);
        card.setAccount(null);
        card.setExpirationDate(new Date());
        card.setCvv(123);

        when(cardRepository.save(card)).thenReturn(card);

        Card createdCard = cardService.create(card);

        assertEquals(card, createdCard);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    public void testDelete_ShouldDeleteCard_WhenCardExists() {
        Long cardNumber = 1111222233334444L;
        Card card = new Card();
        card.setNumber(cardNumber);
        card.setAccount(null);
        card.setExpirationDate(new Date());
        card.setCvv(123);

        when(cardRepository.findById(cardNumber)).thenReturn(Optional.of(card));

        assertDoesNotThrow(() -> cardService.delete(cardNumber));
        verify(cardRepository, times(1)).delete(card);
    }

    @Test
    public void testDelete_ShouldThrowNotFoundException_WhenCardDoesNotExist() {
        Long cardNumber = 1111222233334444L;

        when(cardRepository.findById(cardNumber)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cardService.delete(cardNumber));
        verify(cardRepository, never()).delete(any());
    }

    @Test
    public void testUpdate_ShouldUpdateCard_WhenCardExists() {
        Long cardNumber = 1111222233334444L;
        Card existingCard = new Card();
        existingCard.setNumber(cardNumber);
        existingCard.setAccount(null);
        existingCard.setExpirationDate(new Date());
        existingCard.setCvv(123);

        Card updatedCard = new Card();
        updatedCard.setNumber(cardNumber);
        updatedCard.setAccount(null);
        updatedCard.setExpirationDate(new Date(new Date().getTime() + (365L * 24L * 60L * 60L * 1000L)));
        updatedCard.setCvv(456);

        when(cardRepository.findById(cardNumber)).thenReturn(Optional.of(existingCard));
        when(cardRepository.save(existingCard)).thenReturn(existingCard);

        assertDoesNotThrow(() -> cardService.update(cardNumber, updatedCard));
        assertEquals(updatedCard.getExpirationDate(), existingCard.getExpirationDate());
        assertEquals(updatedCard.getCvv(), existingCard.getCvv());
        verify(cardRepository, times(1)).findById(cardNumber);
        verify(cardRepository, times(1)).save(existingCard);
    }

    @Test
    public void testUpdate_ShouldThrowNotFoundException_WhenCardDoesNotExist() {
        Long cardNumber = 1111222233334444L;
        Card updatedCard = new Card();
        updatedCard.setNumber(cardNumber);
        updatedCard.setAccount(null);
        updatedCard.setExpirationDate(new Date(new Date().getTime() + (365L * 24L * 60L * 60L * 1000L)));
        updatedCard.setCvv(456);

        when(cardRepository.findById(cardNumber)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cardService.update(cardNumber, updatedCard));
        verify(cardRepository, never()).save(any());
    }
}