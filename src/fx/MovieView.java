package fx;

import controller.ClientController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Client;
import repository.ClientRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieView extends Application implements Initializable {


    @FXML
    private ListView<Client> listView;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField nameTextField;

    private ClientController clientController;

    public MovieView() {
        clientController = new ClientController(new ClientRepository());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();

    }

    public void updateSelectedItem() {
        Client client = listView.getSelectionModel().getSelectedItem();
        if (client == null) {
            return;
        }
        clientController.updateClient(client.getId(), nameTextField.getText(), addressTextField.getText());
        refreshClients();
    }

    public void deleteSelectedItem() {
        Client client = listView.getSelectionModel().getSelectedItem();
        if (client == null) {
            return;
        }
        clientController.deleteClient(client.getId());
        refreshClients();
    }

    public void addNewItem() {
        clientController.addClient(nameTextField.getText(), addressTextField.getText());
        refreshClients();
    }

    private void selectedItem(Client newValue) {
        if (newValue == null) {
            idTextField.setText(null);
            nameTextField.setText(null);
            addressTextField.setText(null);
            return;
        }
        idTextField.setText(newValue.getId() + "");
        nameTextField.setText(newValue.getName());
        addressTextField.setText(newValue.getAddress());
    }

    private void refreshClients() {
        ObservableList data =
                FXCollections.observableArrayList();
        data.addAll(clientController.getClients());
        listView.setItems(data);
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialized called");
        refreshClients();

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedItem(newValue);
        });
//
//        addButton.setOnAction(event -> addNewItem());
//        deleteButton.setOnAction(event -> deleteSelectedItem());
//        updateButton.setOnAction(event -> updateSelectedItem());
    }
}
