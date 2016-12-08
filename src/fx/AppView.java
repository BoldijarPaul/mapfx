package fx;

import controller.ClientController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Client;
import repository.ClientRepository;

public class AppView extends Application {

    private ListView<Client> listView;
    private TextField idTextField;
    private TextField addressTextField;
    private TextField nameTextField;
    private ClientController clientController;

    public AppView() {
        clientController = new ClientController(new ClientRepository());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);

        listView = new ListView<>();
        listView.setPrefHeight(250);

        idTextField = new TextField();
        idTextField.setPromptText("ID");
        idTextField.setDisable(true);
        nameTextField = new TextField();
        nameTextField.setPromptText("Name");
        addressTextField = new TextField();
        addressTextField.setPromptText("address");

        HBox buttonsHBox = new HBox();
        Button addButton = new Button("Add");
        addButton.setPrefWidth(166.666667);
        Button updateButton = new Button("Update");
        updateButton.setPrefWidth(166.666667);
        Button deleteButton = new Button("Delete");
        deleteButton.setPrefWidth(166.666667);
        buttonsHBox.getChildren().addAll(addButton, updateButton, deleteButton);

        root.getChildren().addAll(listView, idTextField, nameTextField, addressTextField, buttonsHBox);
        primaryStage.show();
        refreshClients();

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedItem(newValue);
        });

        addButton.setOnAction(event -> addNewItem());
        deleteButton.setOnAction(event -> deleteSelectedItem());
        updateButton.setOnAction(event -> updateSelectedItem());
    }

    private void updateSelectedItem() {
        Client client = listView.getSelectionModel().getSelectedItem();
        if (client == null) {
            return;
        }
        clientController.updateClient(client.getId(), nameTextField.getText(), addressTextField.getText());
        refreshClients();
    }

    private void deleteSelectedItem() {
        Client client = listView.getSelectionModel().getSelectedItem();
        if (client == null) {
            return;
        }
        clientController.deleteClient(client.getId());
        refreshClients();
    }

    private void addNewItem() {
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
}
