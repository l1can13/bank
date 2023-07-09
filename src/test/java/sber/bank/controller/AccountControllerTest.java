package sber.bank.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sber.bank.domain.Account;
import sber.bank.domain.User;
import sber.bank.exceptions.BadArgumentException;
import sber.bank.exceptions.NotFoundException;
import sber.bank.service.implementation.AccountService;
import sber.bank.validation.Validation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountControllerTest {
    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccount_ValidAccount_ReturnsTrue() {
        // Arrange
        Account account = new Account(1234567890L, new User(), "USD", 1000.0);
        when(accountService.create(account)).thenReturn(account);

        // Act
        boolean result = accountController.createAccount(account);

        // Assert
        assertTrue(result);
        verify(accountService, times(1)).create(account);
    }

    @Test
    void createAccount_InvalidAccount_ThrowsBadArgumentException() {
        // Arrange
        Account account = new Account();

        // Act & Assert
        assertThrows(BadArgumentException.class, () -> accountController.createAccount(account));
        verifyNoInteractions(accountService);
    }

    @Test
    void deleteAccount_ValidNumber_NoExceptionThrown() {
        // Arrange
        Long number = 1001001001001001L;

        // Act & Assert
        assertDoesNotThrow(() -> accountController.deleteAccount(number));
        verify(accountService, times(1)).delete(number);
    }

    @Test
    void deleteAccount_InvalidNumber_ThrowsBadArgumentException() {
        // Arrange
        Long number = null;

        // Act & Assert
        assertThrows(BadArgumentException.class, () -> accountController.deleteAccount(number));
        verifyNoInteractions(accountService);
    }

    @Test
    void updateAccount_ValidNumberAndAccount_NoExceptionThrown() {
        // Arrange
        Long number = 1001001001001001L;
        Account account = new Account(1234567890L, new User(), "USD", 1000.0);;

        // Act & Assert
        assertDoesNotThrow(() -> accountController.updateAccount(number, account));
        verify(accountService, times(1)).update(number, account);
    }

    @Test
    void updateAccount_InvalidNumber_ThrowsBadArgumentException() {
        // Arrange
        Long number = null;
        Account account = new Account();

        // Act & Assert
        assertThrows(BadArgumentException.class, () -> accountController.updateAccount(number, account));
        verifyNoInteractions(accountService);
    }

    @Test
    void updateAccount_InvalidAccount_ThrowsBadArgumentException() {
        // Arrange
        Long number = 123L;
        Account account = new Account();

        // Act & Assert
        assertThrows(BadArgumentException.class, () -> accountController.updateAccount(number, account));
        verifyNoInteractions(accountService);
    }
}