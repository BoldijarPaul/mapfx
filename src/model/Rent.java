package model;

/**
 * Created by Paul on 10/8/2016.
 */
public class Rent extends IndexedModel {
    private int id;
    private int clientId;
    private int movieId;

    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    private Movie movie;


    @Override
    public String toString() {
        String output = "Id: " + id + " client id: " + clientId;
        if (client != null) {
            output += " Client name: " + client.getName();
        }
        output += " movie id: " + movieId;
        if (movie != null) {
            output += " movie director: " + movie.getDirector();
        }
        return output;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Rent(int id, int clientId, int movieId) {

        this.id = id;
        this.clientId = clientId;
        this.movieId = movieId;
    }
}
