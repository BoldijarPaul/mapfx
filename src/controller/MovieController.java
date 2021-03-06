package controller;

import model.Movie;
import model.Rent;
import repository.BaseRepository;
import util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Paul on 11/18/2016.
 */
public class MovieController implements IMovieController {

    private final BaseRepository<Movie> movieRepository;

    public MovieController(BaseRepository<Movie> movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void addMovie(String director, String type) {
        movieRepository.add(new Movie(Utils.randomInt(), director, type));
    }

    @Override
    public void deleteMovie(int id) {
        movieRepository.remove(id);
    }

    @Override
    public void updateMovie(int id, String director, String type) {
        movieRepository.update(new Movie(id, director, type));
    }

    @Override
    public List<Movie> getMoviesByDirector(String director) {
        List<Movie> clients = movieRepository.listAll();
        return clients.stream().filter(client -> client.getDirector().contains(director)).collect(Collectors.toList());
    }

    @Override
    public List<Movie> getMoviesByType(String type) {
        List<Movie> clients = movieRepository.listAll();
        return clients.stream().filter(client -> client.getType().equals(type)).collect(Collectors.toList());
    }

    @Override
    public List<Movie> getMovies() {
        return movieRepository.listAll();
    }

    @Override
    public Movie getMostRentedMovie(List<Rent> rents) {
        List<Movie> movies = movieRepository.listAll();
        if (rents.size() == 0 || movies.size() == 0) {
            return null;
        }
        int[] rentedTimes = new int[movies.size()];

        // calculam de cate ori a fost inchiriat fiecare film
        rents.forEach(rent -> {
            for (int i = 0; i < movies.size(); i++) {
                if (rent.getMovieId() == movies.get(i).getId()) {
                    rentedTimes[i]++;
                }
            }
        });
        // cautam filmul cu nr maxim de inchirieri
        int maxRentedTimes = -1;
        int maxRentedMovieIndex = -1;
        for (int i = 0; i < movies.size(); i++) {
            if (rentedTimes[i] > maxRentedTimes) {
                maxRentedTimes = rentedTimes[i];
                maxRentedMovieIndex = i;
            }
        }
        if (maxRentedMovieIndex == -1) {
            return null;
        }
        return movies.get(maxRentedMovieIndex);
    }

    @Override
    public List<Movie> getMoviesSortedBy(boolean byDirector, boolean byType, boolean ascending) {
        List<Movie> movies = getMovies();
        Stream<Movie> stream = movies.stream();
        if (byDirector) {
            stream.sorted((o1, o2) -> o1.getDirector().compareTo(o2.getDirector()));
        }
        if (byType) {
            stream.sorted((o1, o2) -> o1.getType().compareTo(o2.getType()));
        }
        movies = stream.collect(Collectors.toList());
        if (!ascending) {
            Collections.reverse(movies);
        }
        return movies;
    }

    public Movie getMovie(int id) {
        return movieRepository.getFromId(id);
    }

}
