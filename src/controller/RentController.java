package controller;

import model.Rent;
import repository.BaseRepository;
import util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Paul on 10/8/2016.
 */
public class RentController {

    private ClientController clientController;
    private MovieController movieController;

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public void setMovieController(MovieController movieController) {
        this.movieController = movieController;
    }

    private final BaseRepository<Rent> rentRepository;

    public RentController(BaseRepository<Rent> RentRepository) {
        this.rentRepository = RentRepository;
    }

    public void addRent(int movieId, int clientId) {
        rentRepository.add(new Rent(Utils.randomInt(), clientId, movieId));
    }

    public void deleteRent(int id) {
        rentRepository.remove(id);
    }

    public List<Rent> getRents() {
        List<Rent> list = rentRepository.listAll();
        for (Rent rent : list) {
            rent.setClient(clientController.getClient(rent.getClientId()));
            rent.setMovie(movieController.getMovie(rent.getMovieId()));
        }
        return list;
    }
}
