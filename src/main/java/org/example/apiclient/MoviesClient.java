package org.example.apiclient;

import org.example.domain.movie.Movie;
import org.example.domain.movie.MovieInfo;
import org.example.domain.movie.Review;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/18/2023
 */
public class MoviesClient
{
    private final WebClient webClient;

    public MoviesClient(WebClient webClient)
    {
        this.webClient = webClient;
    }
    //Implementing in blocking way
    public Movie retrieveMovie(Long movieInfoId){
        
        var movieInfo = invokeMovieInfo(movieInfoId);
        var reviews =invokeReviewsService(movieInfoId);
        return new Movie(movieInfo, reviews);
    }


    //Implementing in non-blocking way
    public CompletableFuture<Movie> retrieveMovie_Cf_async(Long movieInfoId){

        var movieInfo = CompletableFuture.supplyAsync( () ->invokeMovieInfo(movieInfoId)) ;
        var reviews =CompletableFuture.supplyAsync( () ->invokeReviewsService(movieInfoId)) ;
        return movieInfo
                .thenCombine(reviews, (movieInfoResult, reviewsResult) ->{
                    return new Movie(movieInfoResult, reviewsResult);
    });

    }
    //Calling traditional retrieve movie method to make list of loaded movies
    public List<Movie> retrieveMovies(List<Long> movieInfoIds){
        return movieInfoIds.stream().map(movieInfoId -> retrieveMovie(movieInfoId)).collect(Collectors.toList());
    }

    //Calling completable-future retrieve movie method to make list of loaded movies using retrieveMovie_Cf_async() method
    public List<Movie> retrieveMovieList_Cf_async(List<Long> movieInfoIds){
        var movieFutures = movieInfoIds.stream().map(this::retrieveMovie_Cf_async).
                collect(Collectors.toList());
        return movieFutures.
                stream().
                map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    //Using "allOf()" method to improve performance, this method will wait until all items get processed
    public List<Movie> retrieveMovieList_Cf_async_allOf(List<Long> movieInfoIds){
        var movieFutures = movieInfoIds.stream().map(this::retrieveMovie_Cf_async).
                collect(Collectors.toList());

        var cfAllOf = CompletableFuture.allOf(movieFutures.toArray(new CompletableFuture[movieFutures.size()]));
        return cfAllOf.thenApply(v -> movieFutures
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()))
                .join();
    }

    //Implementing the web client call in blocking way
    private MovieInfo invokeMovieInfo(Long movieInfoId)
    {
        var movieInfoUrlPath = "/v1/reviews?movieInfoId=2";
        return webClient.get().uri(movieInfoUrlPath, movieInfoId).retrieve().bodyToMono(MovieInfo.class).block();
    }

    //Implementing the web client call in blocking way
    private List<Review> invokeReviewsService(Long movieInfoId)
    {
        ///v1/reviews?movieInfoId=2
        var reviewUri = UriComponentsBuilder.fromUriString("/v1/reviews").queryParam("movieInfoId", movieInfoId).buildAndExpand().toString();
        return webClient.get().uri(reviewUri).retrieve().bodyToFlux(Review.class).collectList().block();
    }

}
