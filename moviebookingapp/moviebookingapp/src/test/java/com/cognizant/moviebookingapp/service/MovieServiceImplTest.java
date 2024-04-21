package com.cognizant.moviebookingapp.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cognizant.moviebookingapp.exception.InvalidInputException;
import com.cognizant.moviebookingapp.model.Movie;
import com.cognizant.moviebookingapp.repository.MovieRepository;
import com.cognizant.moviebookingapp.service.Impl.MovieServiceImpl;
import com.cognizant.moviebookingapp.model.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Before
    public void setup() {
        // Mocking repository methods
        Movie movie = new Movie();
        movie.setMovieId(1L);
        movie.setMovieName("Avengers: Endgame");
        movie.setTotalTickets(100);
        movie.setTicketStatus("BOOK ASAP");
        movie.setTickets(new ArrayList<>());

        when(movieRepository.findByMovieId(1L)).thenReturn(Optional.of(movie));
        when(movieRepository.findByMovieId(2L)).thenReturn(Optional.empty());
        when(movieRepository.findByMovieName("Non-existent Movie")).thenReturn(Optional.empty());
    }

    @Test
    public void testAddMovie_Success() {
        Movie movie = new Movie();
        movie.setMovieName("Inception");
        movie.setTotalTickets(50);

        ResponseEntity<?> response = movieService.addMovie(movie);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(movie, response.getBody());
    }

    @Test
    public void testAddMovie_MovieAlreadyExists() {
        // Create a mock movie
        Movie movie = new Movie();
        movie.setMovieName("Avengers: Endgame");
        movie.setTotalTickets(50);

        // Mock the behavior of the movie repository
        when(movieRepository.existsByMovieName(movie.getMovieName())).thenReturn(true);

        // Call the service method
        ResponseEntity<?> response = movieService.addMovie(movie);

        // Assert the response
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("movie already exists", response.getBody());
    }


    @Test
    public void testGetAllMovies_Success() {
        List<Movie> movieList = new ArrayList<>();
        Movie movie = new Movie();
        movie.setMovieName("Avengers: Endgame");
        movie.setTotalTickets(100);
        movie.setTicketStatus("BOOK ASAP");
        movie.setTickets(new ArrayList<>());
        movieList.add(movie);

        when(movieRepository.findAll()).thenReturn(movieList);

        ResponseEntity<List<Movie>> response = movieService.getAllMovies();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(movieList, response.getBody());
    }

    @Test
    public void testSearchMovieById_Success() {
        ResponseEntity<?> response = movieService.searchMovieById(1L);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, ((Movie) response.getBody()).getMovieId().longValue());
    }

    @Test
    public void testSearchMovieById_MovieNotFound() {
        ResponseEntity<?> response = movieService.searchMovieById(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("movie not found", response.getBody());
    }

    @Test
    public void testDeleteMovie_Success() {
        ResponseEntity<?> response = movieService.deleteMovie(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("movie deleted successfully!", response.getBody());
    }

    @Test
    public void testDeleteMovie_MovieNotFound() {
        ResponseEntity<?> response = movieService.deleteMovie(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("movie not found", response.getBody());
    }

    @Test
    public void testUpdateMovie_MovieNotFound() {
        ResponseEntity<?> response = movieService.updateMovie("Non-existent Movie", 100);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof InvalidInputException);
    }


    @Test
    public void testGetBookedTicketList_MovieNotFound() {
        String movieName = "Non-existent Movie";
        when(movieRepository.findByMovieName(movieName)).thenReturn(Optional.empty());

        ResponseEntity<?> response = movieService.getBookedTicketList(movieName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("movie not found", response.getBody());
    }
}
