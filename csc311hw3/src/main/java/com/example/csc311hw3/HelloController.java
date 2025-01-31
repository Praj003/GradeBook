package com.example.csc311hw3;

import com.google.gson.*;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import java.io.*;
import java.util.*;

import java.nio.charset.StandardCharsets;
import java.io.InputStreamReader;


/**
 * Praj CSC311 HW3
*/

public class HelloController {

    // variables for javaFX
    @FXML
    private TextField qtext;
    @FXML
    private TextField latext;
    @FXML
    private TextField nametext;
    @FXML
    private TextField cattext;
    @FXML
    private TextField scoretext;
    @FXML
    private TableView<Grade> tableview;
    @FXML
    private TableColumn<Grade, String> namecol;
    @FXML
    private TableColumn<Grade, String> catcol;
    @FXML
    private TableColumn<Grade, Integer> scorecol;


////////////////////////////////////////////////////////////////

    //private ObservableList<Grade> datalist = FXCollections.observableArrayList();
    // Initializing noramlqueue using LL for Grade
    private Queue<Grade> datalist = new LinkedList<>();
    // Initializes the gradeComparator to the class for pq
    //private Comparator<Grade> gradeComparator = new GradeComparator();
    // For loading queues // gradecomp for the order
    //private Queue<Grade> Pqueue = new PriorityQueue<>(gradeComparator);
////////////////////////////////////////////////////////////////

    /**
     * Method to initialize the table view's columns
     */
    @FXML
    protected void initialize() {
        namecol.setCellValueFactory
                (new PropertyValueFactory<>("name"));
        catcol.setCellValueFactory
                (new PropertyValueFactory<>("category"));
        scorecol.setCellValueFactory
                (new PropertyValueFactory<>("score"));
    }

    @FXML
    protected void normalQ() { // Method for when normal queue is clicked, choose file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // Set initial directory to project directory
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            loadGrades(file);
            qtext.setText(" Normal Queue ");
            latext.setText(" Loaded Normal Queue from a JSON file ");
        }
    }

    @FXML
    private void loadGrades(File file) { // actually loads the data
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            Grade[] grades = gson.fromJson(reader, Grade[].class);
            if (grades != null) {
                datalist.clear();
                for (Grade grade : grades) {
                    datalist.add(grade);
                }
                printData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printData() { //prints out into table voew
        ObservableList<Grade> items = tableview.getItems();
        items.clear();
        for (Grade grade : datalist) {
            items.add(grade);
        }
    }
////////////////////////////////////////////////////////////////

    /**
     * Method for when priorityQ is clicked, choose file
     */
    @FXML
    protected void priorityQ() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // Set initial directory to project directory
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            loadGrades(file);
            qtext.setText(" Priority Queue ");
            latext.setText(" Loaded Priority Queue from a JSON file ");
        }
    }

////////////////////////////////////////////////////////////////

    /**
     * Method for manually adding data into the Table View
     */
    @FXML
    protected void addGrade() {
        String name = nametext.getText();
        String category = cattext.getText();
        int score = Integer.parseInt(scoretext.getText());

        Grade grade = new Grade(name, category, score);
        datalist.add(grade);
        addToTableView(grade); //calling
        clearFields(); //calling
        latext.setText(" Adding Data ");
    }

    private void addToTableView(Grade grade) { // retrives data
        ObservableList<Grade> items = tableview.getItems();
        items.add(grade);
    }

    private void clearFields() { // clears all text fields
        nametext.clear();
        cattext.clear();
        scoretext.clear();
    }

////////////////////////////////////////////////////////////////

    /**
     * Method for removing last added grade in Table View
     */
    @FXML
    protected void removeLastGrade() {
        ObservableList<Grade> items = tableview.getItems();
        int lastIndex = items.size() - 1;
        if (lastIndex >= 0) {
            Grade removedGrade = items.remove(lastIndex);
            datalist.remove(removedGrade);
            latext.setText(" Removing Data ");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(" Grade Removed ");
        alert.setHeaderText(null);
        alert.setContentText(" Last grade was removed from tableview ");
        alert.showAndWait();
    }

////////////////////////////////////////////////////////////////

    /**
     * Method for saving data into a json file (replace the 2nd json file)
     */
    @FXML
    protected void savedata() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // Set initial directory to project directory
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            saveDataToJSON(file);
        }
        latext.setText(" Successfully Saved ");
    }

    private void saveDataToJSON(File file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            gson.toJson(datalist, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

////////////////////////////////////////////////////////////////

    /**
     * Method for clearning all data from TableView Columns
     */
    @FXML
    protected void clearTV() {
        ObservableList<Grade> items = tableview.getItems();
        items.clear();
    }

    @FXML
    protected void clear() {
        clearTV();
        qtext.clear();
        latext.setText(" Cleared Data ");
    }

////////////////////////////////////////////////////////////////

    /**
     * Method for close
     */
    public void close() {
        throw new RuntimeException("Closing the application.");
    }
}
////////////////////////////////////////////////////////////////