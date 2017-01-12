package fx;

import controller.ClientController;
import controller.MovieController;
import controller.RentController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Client;
import model.Movie;
import model.Rent;
import repository.ClientRepository;
import repository.MovieRepository;
import repository.RentRepository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class FullAppView extends BaseApplication implements Initializable {


    @FXML
    private ListView<Client> clientList;
    @FXML
    private TextField clientIdInput;
    @FXML
    private TextField clientAddressInput;
    @FXML
    private TextField clientNameInput;

    @FXML
    private ListView<Movie> movieList;
    @FXML
    private TextField movieIdInput;
    @FXML
    private TextField movieTypeInput;
    @FXML
    private TextField movieDirectorInput;

    @FXML
    private ListView<Rent> rentList;
    @FXML
    private TextField rentIdInput;
    @FXML
    private TextField rentClientId;
    @FXML
    private TextField rentMovieId;

    @FXML
    private TextField clientNameSearch;
    @FXML
    private TextField movieDirectorSearch;

    private ClientController clientController;
    private MovieController movieController;
    private RentController rentController;

    public FullAppView() {
        clientController = new ClientController(new ClientRepository());
        movieController = new MovieController(new MovieRepository());
        rentController = new RentController(new RentRepository());
        rentController.setClientController(clientController);
        rentController.setMovieController(movieController);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
        primaryStage.setTitle("Great app");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void updateSelectedClient() {
        Client client = clientList.getSelectionModel().getSelectedItem();
        if (client == null) {
            return;
        }
        clientController.updateClient(client.getId(), clientNameInput.getText(), clientAddressInput.getText());
        refreshClients();
    }

    public void deleteSelectedClient() {
        Client client = clientList.getSelectionModel().getSelectedItem();
        if (client == null) {
            return;
        }
        clientController.deleteClient(client.getId());
        refreshClients();
    }

    public void addNewClient() {
        clientController.addClient(clientNameInput.getText(), clientAddressInput.getText());
        refreshClients();
    }

    public void updateSelectedMovie() {
        Movie movie = movieList.getSelectionModel().getSelectedItem();
        if (movie == null) {
            return;
        }
        movieController.updateMovie(movie.getId(), movieDirectorInput.getText(), movieTypeInput.getText());
        refreshMovies();
    }

    public void deleteSelectedMovie() {
        Movie movie = movieList.getSelectionModel().getSelectedItem();
        if (movie == null) {
            return;
        }
        movieController.deleteMovie(movie.getId());
        refreshMovies();
    }

    public void addNewMovie() {
        movieController.addMovie(movieDirectorInput.getText(), movieTypeInput.getText());
        refreshMovies();
    }

    public void addNewRent() {
        Movie movie = movieList.getSelectionModel().getSelectedItem();
        Client client = clientList.getSelectionModel().getSelectedItem();
        if (movie == null || client == null) {
            showMessage("Movie or client was not selected!");
            return;
        }
        rentController.addRent(movie.getId(), client.getId());
        refreshRents();
    }

    public void deleteSelectedRent() {
        Rent rent = rentList.getSelectionModel().getSelectedItem();
        if (rent == null) {
            return;
        }
        rentController.deleteRent(rent.getId());
        refreshRents();
    }

    private void selectedItem(Client newValue) {
        if (newValue == null) {
            clientIdInput.setText(null);
            clientNameInput.setText(null);
            clientAddressInput.setText(null);
            return;
        }
        clientIdInput.setText(newValue.getId() + "");
        clientNameInput.setText(newValue.getName());
        clientAddressInput.setText(newValue.getAddress());
        Utils.saveXml(newValue, "f.xml");
    }

    private void selectedItem(Rent newValue) {
        if (newValue == null) {
            rentIdInput.setText(null);
            rentClientId.setText(null);
            rentMovieId.setText(null);
            return;
        }
        clientIdInput.setText(newValue.getId() + "");
        rentClientId.setText(newValue.getClientId() + "");
        rentMovieId.setText(newValue.getMovieId() + "");
    }

    private void selectedItem(Movie newValue) {
        if (newValue == null) {
            movieIdInput.setText(null);
            movieDirectorInput.setText(null);
            movieTypeInput.setText(null);
            return;
        }
        movieIdInput.setText(newValue.getId() + "");
        movieDirectorInput.setText(newValue.getDirector());
        movieTypeInput.setText(newValue.getType());
    }

    private void refreshClients() {
        ObservableList data =
                FXCollections.observableArrayList();
        data.addAll(clientController.getClients());
        clientList.setItems(data);
    }

    private void refreshMovies() {
        ObservableList data =
                FXCollections.observableArrayList();
        data.addAll(movieController.getMovies());
        movieList.setItems(data);
    }

    private void refreshRents() {
        ObservableList data =
                FXCollections.observableArrayList();
        data.addAll(rentController.getRents());
        rentList.setItems(data);
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialized called");
        refreshClients();
        refreshMovies();
        refreshRents();

        searchClientEvent();
        searchMovieEvent();

        clientList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedItem(newValue);
        });
        movieList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedItem(newValue);
        });
        rentList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedItem(newValue);
        });
    }

    private void searchMovieEvent() {
        movieDirectorSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) {
                refreshMovies();
            } else {
                ObservableList data =
                        FXCollections.observableArrayList();
                data.addAll(movieController.getMoviesByDirector(newValue));
                movieList.setItems(data);
            }
        });
    }

    private void searchClientEvent() {
        clientNameSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) {
                refreshClients();
            } else {
                ObservableList data =
                        FXCollections.observableArrayList();
                data.addAll(clientController.getClientsByName(newValue));
                clientList.setItems(data);
            }
        });
    }
}
