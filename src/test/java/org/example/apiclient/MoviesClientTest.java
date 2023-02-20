package org.example.apiclient;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.example.util.CommonUtil.startTimer;
import static org.example.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/18/2023
 */
class MoviesClientTest
{
    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/movies").build();
    MoviesClient moviesClient = new MoviesClient(webClient);

    @Test
    //@RepeatedTest(10)
    void retrieveMovie()
    {
        startTimer();
        var movieInfoId=1L;
        var movie =moviesClient.retrieveMovie(movieInfoId);
        timeTaken();
        System.out.println("movie: "+movie);

        assertNotNull(movie);
        assertEquals("Batman Begins",movie.getMovieInfo().getName());
        assert movie.getReviewList().size() ==1;
    }
    @Test
    //@RepeatedTest(10)
    void retrieveMovies()
    {
        startTimer();
        var movieInfoIds= List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L);
        var movies =moviesClient.retrieveMovies(movieInfoIds);
        timeTaken();
        System.out.println("movie: "+movies);

        assert movies != null;
        assert  movies.size() == 7;
    }
    @Test
    //@RepeatedTest(10)
    void retrieveMovieList_Cf_async()
    {
        startTimer();
        var movieInfoIds= List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L);
        var movies = moviesClient.retrieveMovieList_Cf_async(movieInfoIds);
        timeTaken();
        System.out.println("movie: "+movies);

        assert movies != null;
        assert  movies.size() == 7;
    }
    @Test
    //@RepeatedTest(10)
    void retrieveMovieList_Cf_async_allOf()
    {
        startTimer();
        var movieInfoIds= List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L);
        var movies = moviesClient.retrieveMovieList_Cf_async_allOf(movieInfoIds);
        timeTaken();
        System.out.println("movie: "+movies);

        assert movies != null;
        assert  movies.size() == 7;
    }

    @Test
    //@RepeatedTest(10)
    void retrieveMovie_Cf_async()
    {
        startTimer();
        var movieInfoId=1L;
        var movie =moviesClient.retrieveMovie_Cf_async(movieInfoId)
                .join();
        timeTaken();
        System.out.println("movie: "+movie);

        assertNotNull(movie);
        assertEquals("Batman Begins",movie.getMovieInfo().getName());
        assert movie.getReviewList().size() ==1;
    }
}