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
import sber.bank.service.implementation.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<User> expectedUsers = getAllUsers();
        when(userService.getAll()).thenReturn(expectedUsers);

        Iterable<User> actualUsers = userController.getAllUsers();

        assertIterableEquals(expectedUsers, actualUsers);
        verify(userService, times(1)).getAll();
    }

    @Test
    public void testGetUserAccounts_ValidId_ReturnsListOfAccounts() {
        Long userId = 1L;
        List<Account> expectedAccounts = getAllAccounts();
        when(userService.getAccounts(userId)).thenReturn(expectedAccounts);

        List<Account> actualAccounts = userController.getUserAccounts(userId);

        assertEquals(expectedAccounts, actualAccounts);
        verify(userService, times(1)).getAccounts(userId);
    }

    @Test
    public void testGetUserAccounts_InvalidId_ThrowsBadArgumentException() {
        Long userId = -1L;

        assertThrows(BadArgumentException.class, () -> userController.getUserAccounts(userId));
        verify(userService, never()).getAccounts(userId);
    }

    @Test
    public void testGetUserAccounts_UserNotFound_ThrowsNotFoundException() {
        Long userId = 1L;
        when(userService.getAccounts(userId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> userController.getUserAccounts(userId));
        verify(userService, times(1)).getAccounts(userId);
    }

    @Test
    public void testGetUserCards_InvalidId_ThrowsBadArgumentException() {
        Long userId = -1L;

        assertThrows(BadArgumentException.class, () -> userController.getUserCards(userId));
        verify(userService, never()).getCards(userId);
    }

    @Test
    public void testGetUserCards_UserNotFound_ThrowsNotFoundException() {
        Long userId = 1L;
        when(userService.getCards(userId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> userController.getUserCards(userId));
        verify(userService, times(1)).getCards(userId);
    }

    @Test
    public void testGetOverallBalance_ValidId_ReturnsOverallBalance() {
        Long userId = 1L;
        double expectedOverallBalance = 5000.0;
        when(userService.getOverallBalance(userId)).thenReturn(expectedOverallBalance);

        double actualOverallBalance = userController.getOverallBalance(userId);

        assertEquals(expectedOverallBalance, actualOverallBalance);
        verify(userService, times(1)).getOverallBalance(userId);
    }

    @Test
    public void testGetOverallBalance_InvalidId_ThrowsBadArgumentException() {
        Long userId = -1L;

        assertThrows(BadArgumentException.class, () -> userController.getOverallBalance(userId));
        verify(userService, never()).getOverallBalance(userId);
    }

    @Test
    public void testGetOverallBalance_UserNotFound_ThrowsNotFoundException() {
        Long userId = 1L;
        when(userService.getOverallBalance(userId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> userController.getOverallBalance(userId));
        verify(userService, times(1)).getOverallBalance(userId);
    }

    @Test
    void deleteUser_ShouldCallUserServiceDeleteMethod_WhenUserIdIsValid() {
        Long userId = 1L;

        assertDoesNotThrow(() -> userController.deleteUser(userId));
        verify(userService, times(1)).delete(userId);
    }

    @Test
    void deleteUser_ShouldThrowBadArgumentException_WhenUserIdIsInvalid() {
        Long userId = -1L;

        assertThrows(BadArgumentException.class, () -> userController.deleteUser(userId));
        verify(userService, never()).delete(userId);
    }

    @Test
    void deleteUser_ShouldThrowNotFoundException_WhenUserNotFound() {
        Long userId = 1L;
        doThrow(NotFoundException.class).when(userService).delete(userId);

        assertThrows(NotFoundException.class, () -> userController.deleteUser(userId));
        verify(userService, times(1)).delete(userId);
    }

    @Test
    void updateUser_ShouldThrowBadArgumentException_WhenUserIdIsInvalid() {
        Long userId = -1L;
        User userDetail = new User(1L, "John Doe", null, "456 Elm St, Town");

        assertThrows(BadArgumentException.class, () -> userController.updateUser(userId, userDetail));
        verify(userService, never()).update(userId, userDetail);
    }

    @Test
    void updateUser_ShouldThrowBadArgumentException_WhenUserDetailsAreInvalid() {
        Long userId = 1L;
        User userDetail = new User(1L, null, null, null);

        assertThrows(BadArgumentException.class, () -> userController.updateUser(userId, userDetail));
        verify(userService, never()).update(userId, userDetail);
    }

    private List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        User user1 = new User(1L, "John Smith", null, "123 Main St, City");
        User user2 = new User(2L, "Jane Doe", null, "456 Elm St, Town");

        users.add(user1);
        users.add(user2);

        return users;
    }

    private List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();

        Account account1 = new Account(1001001001001001L, new User(1L, "John Smith", null, "123 Main St, City"), "USD", 5000.0);
        Account account2 = new Account(2002002002002002L, new User(1L, "John Smith", null, "123 Main St, City"), "EUR", 3000.0);

        accounts.add(account1);
        accounts.add(account2);

        return accounts;
    }
}