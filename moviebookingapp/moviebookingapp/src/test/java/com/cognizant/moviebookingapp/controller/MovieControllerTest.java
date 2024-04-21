package com.cognizant.moviebookingapp.controller;

import com.cognizant.moviebookingapp.exception.AuthorizationException;
import com.cognizant.moviebookingapp.model.Movie;
//import com.cognizant.moviebookingapp.controller.AuthService;
import com.cognizant.moviebookingapp.service.MovieService;
import com.cognizant.moviebookingapp.service.TicketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @Mock
    private TicketService ticketService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private MovieController movieController;

    @Test
    public void testGetAllMovies_AdminRole_Success() throws AuthorizationException {
        // Mock the authentication token
        String token = "admin-token";
        when(authService.validateToken(token)).thenReturn(Collections.singletonMap("admin", "admin"));

        // Mock the movie service response
        List<Movie> movies = Arrays.asList(new Movie(), new Movie());
        ResponseEntity<List<Movie>> expectedResponse = ResponseEntity.ok(movies);
        when(movieService.getAllMovies()).thenReturn(expectedResponse);

        // Perform the request
        ResponseEntity<List<Movie>> response = movieController.getAllMovies(token);

        // Verify the interactions and assertions
        verify(authService).validateToken(token);
        verify(movieService).getAllMovies();
        assertEquals(expectedResponse, response);
    }

    @Test
    public void testGetAllMovies_NonAdminRole_AuthorizationException() throws AuthorizationException{
        // Mock the authentication token
        String token = "customer-token";
        when(authService.validateToken(token)).thenReturn(Collections.singletonMap("customer", "customer"));

        // Perform the request (expecting an AuthorizationException)
        movieController.getAllMovies(token);
    }

    @Test
    public void testDeleteMovieById_AdminRole_Success() throws AuthorizationException{
        // Mock the authentication token
        String token = "admin-token";
        when(authService.validateToken(token)).thenReturn(Collections.singletonMap("admin", "admin"));

        // Mock the movie ID to be deleted
        Long movieId = 123L;

        // Perform the request
        ResponseEntity<?> response = movieController.deleteMovieById(movieId, token);

        // Verify the interactions and assertions
        verify(authService).validateToken(token);
        verify(movieService).deleteMovie(movieId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully deleted movie", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test(expected = AuthorizationException.class)
    public void testDeleteMovieById_NonAdminRole_AuthorizationException() throws AuthorizationException {
        // Mock the authentication token
        String token = "customer-token";
        when(authService.validateToken(token)).thenReturn(Collections.singletonMap("customer", "customer"));

        // Mock the movie ID to be deleted
        Long movieId = 123L;

        // Perform the request (expecting an AuthorizationException)
        movieController.deleteMovieById(movieId, token);
    }

    // Additional test cases can be added for other methods in MovieController

}
