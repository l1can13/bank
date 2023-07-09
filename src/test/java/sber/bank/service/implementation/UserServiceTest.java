package sber.bank.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sber.bank.domain.Account;
import sber.bank.domain.Card;
import sber.bank.domain.User;
import sber.bank.exceptions.NotFoundException;
import sber.bank.repos.AccountRepository;
import sber.bank.repos.CardRepository;
import sber.bank.repos.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<User> expectedUsers = getAllUsers();
        when(userRepository.findAll()).thenReturn(expectedUsers);

        Iterable<User> actualUsers = userService.getAll();

        assertIterableEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById_ValidId_ReturnsUser() {
        Long userId = 1L;
        User expectedUser = new User(getAllUsers().get(0));
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.getByPk(userId);

        assertEquals(expectedUser, actualUser);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserById_InvalidId_ThrowsNotFoundException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getByPk(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void create_ShouldCreateUser() {
        User user = new User(1L, "John Smith", null, "123 Main St, City");

        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.create(user);

        assertEquals(user, createdUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void delete_ShouldDeleteUser_WhenUserExists() {
        User user = new User(1L, "John Smith", null, "123 Main St, City");

        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));

        assertDoesNotThrow(() -> userService.delete(user.getId()));
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void delete_ShouldThrowNotFoundException_WhenUserDoesNotExist() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.delete(userId));
        verify(userRepository, never()).delete(any());
    }

    @Test
    void update_ShouldUpdateUser_WhenUserExists() {
        User existingUser = new User(1L, "John Smith", null, "123 Main St, City");
        User updatedUser = new User(1L, "John Doe", null, "456 Elm St, Town");

        when(userRepository.findById(existingUser.getId())).thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        assertDoesNotThrow(() -> userService.update(existingUser.getId(), updatedUser));
        assertEquals(updatedUser.getName(), existingUser.getName());
        verify(userRepository, times(1)).findById(existingUser.getId());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void update_ShouldThrowNotFoundException_WhenUserDoesNotExist() {
        Long userId = 1L;
        User updatedUser = new User(1L, "John Doe", null, "456 Elm St, Town");

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.update(userId, updatedUser));
        verify(userRepository, never()).save(any());
    }

    private List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            Date birthdate1 = dateFormat.parse("1989-12-31T21:00:00");
            Date birthdate2 = dateFormat.parse("1995-02-01T20:00:00");
            Date birthdate3 = dateFormat.parse("1988-05-09T19:00:00");

            User user1 = new User(1L, "John Smith", birthdate1, "123 Main St, City");
            User user2 = new User(2L, "Jane Doe", birthdate2, "456 Elm St, Town");
            User user3 = new User(3L, "Michael Johnson", birthdate3, "789 Oak St, Village");

            userList.add(user1);
            userList.add(user2);
            userList.add(user3);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return userList;
    }
}