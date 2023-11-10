package com.github.sebastianp265.ordermanager.services;

import com.github.sebastianp265.ordermanager.entities.Client;
import com.github.sebastianp265.ordermanager.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @ParameterizedTest
    @ValueSource(strings = {"John", "Jane", "Jack"})
    void getClient_shouldReturnClient_whenClientExists_(String name) {
        // arrange
        Long id = 1L;
        Client client = Client.builder()
                .id(id)
                .name(name)
                .surname("Doe")
                .email(name.toLowerCase() + "@example.com")
                .build();
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        // act
        Client result = clientService.findClientById(id);
        // assert
        assertEquals(result, client);
    }

    @Test
    void getClient_shouldThrowException_whenClientDoesNotExist() {
        // arrange
        Long id = 1L;
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());
        // act
        // assert
        assertThrows(Exception.class, () -> clientService.findClientById(id));
    }

    @Test
    void deleteClientById_shouldCallRepositoryDeleteById() {
        // arrange
        Long id = 1L;
        // act
        clientService.deleteClientById(id);
        // assert
        verify(clientRepository, times(1)).deleteById(id);
    }

    @Test
    void createClient_shouldCallRepositorySave() {
        // arrange
        Client client = Client.builder().build();
        // act
        clientService.createClient(client);
        // assert
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void updateClient_shouldCallRepositorySave() {
        // arrange
        Client client = Client.builder().build();
        // act
        clientService.updateClient(client);
        // assert
        verify(clientRepository, times(1)).save(client);
    }

}