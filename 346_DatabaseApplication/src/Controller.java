import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    TextArea outputArea;
    @FXML
    TextField searchByTextField;
    @FXML
    ComboBox<String> searchByComboBox, dateMissingComboBox;

    SQLManager sql = new SQLManager();
    List<String> datesMissing = Arrays.asList("1995 - 2000", "2001 - 2006", "2007 - 2011", "2012 - 2017");
    List<String> searchBy = Arrays.asList("Child First Name", "Child Last Name", "Suspect First Name", "Suspect Last Name", "Case ID", "State", "City");


    @FXML
    public void calculateAge() throws SQLException, ClassNotFoundException {
        String input = ("select fname, lname, (to_number(to_char(SYSDATE, 'YYYY')) - to_number(to_char(BDATE, 'YYYY'))), gender, MCity, MState from child");
        ResultSet result = sql.retrieveQuery(input);
        int count = sql.columnCount(result);
        populateTextArea(result, count);
    }

    @FXML
    public void search() throws SQLException, ClassNotFoundException {
        String constraint = searchByComboBox.getSelectionModel().getSelectedItem().toString();
        String searchField = searchByTextField.getText();
        ResultSet result;
        if (constraint.equals("Child First Name") || constraint.equals("Child Last Name") || constraint.equals("City") || constraint.equals("State") || constraint.equals("Case ID")) {
            if (constraint.contains("First Name")) {
                result = sql.retrieveQuery("select * from child where FNAME = '" + searchField +"'");
                int count = sql.columnCount(result);
                populateTextArea(result, count);
            }
            if (constraint.contains("Last Name")) {
                result = sql.retrieveQuery("select * from child where LNAME = '" + searchField +"'");
                int count = sql.columnCount(result);
                populateTextArea(result, count);
            }
            if (constraint.contains("City")) {
                result = sql.retrieveQuery("select * from child where MCity = '" + searchField +"'");
                int count = sql.columnCount(result);
                populateTextArea(result, count);
            }
            if (constraint.contains("State")) {
                result = sql.retrieveQuery("select * from child where MState =  '" + searchField +"'");
                int count = sql.columnCount(result);
                populateTextArea(result, count);
            } else {
                result = sql.retrieveQuery("select * from child where CID =  '" + searchField +"'");
                int count = sql.columnCount(result);
                populateTextArea(result, count);
            }
        } else {
            if(constraint.contains("Last Name")){
                result = sql.retrieveQuery("select * from suspect where LNAME =  '" + searchField +"'");
                int count = sql.columnCount(result);
                populateTextArea(result, count);
            }
            if(constraint.contains("First Name")){
                result = sql.retrieveQuery("select * from suspect where FNAME =  '" + searchField +"'");
                int count = sql.columnCount(result);
                populateTextArea(result, count);
            }
            if(constraint.contains("Case ID")){
                result = sql.retrieveQuery("select * from suspect where CID =  '" + searchField +"'");
                int count = sql.columnCount(result);
                populateTextArea(result, count);
            }
        }
    }

    @FXML
    public void childrenMissingBetween() throws SQLException, ClassNotFoundException {
        String constraint = dateMissingComboBox.getSelectionModel().getSelectedItem().toString();
        ResultSet result;
        if(constraint.equals("1995 - 2000")){
            result = sql.retrieveQuery("select * from child where ((to_char(MDATE, 'YYYY')) BETWEEN 1995 AND 2000)");
            int count = sql.columnCount(result);
            populateTextArea(result, count);
        }
        if(constraint.equals("2001 - 2006")){
            result = sql.retrieveQuery("select * from child where ((to_char(MDATE, 'YYYY')) BETWEEN 2001 AND 2006)");
            int count = sql.columnCount(result);
            populateTextArea(result, count);
        }
        if(constraint.equals("2007 - 2011")){
            result = sql.retrieveQuery("select * from child where ((to_char(MDATE, 'YYYY')) BETWEEN 2007 AND 2011)");
            int count = sql.columnCount(result);
            populateTextArea(result, count);
        }
        if(constraint.equals("2012 - 2017")){
            result = sql.retrieveQuery("select * from child where ((to_char(MDATE, 'YYYY')) BETWEEN 2012 AND 2017)");
            int count = sql.columnCount(result);
            populateTextArea(result, count);
        }
    }

    @FXML
    public void populateTextArea(ResultSet rst, int count) throws SQLException {
        String toTextArea = "";
        while (rst.next()) {
            for (int i = 1; i < count; i++) {
                toTextArea += (rst.getString(i) + " ");
            }
            toTextArea += "\n\n";
            outputArea.setText(toTextArea);
        }
    }

    @FXML
    public void populateSearchByComboBox(){
        ObservableList<String> searchByList = FXCollections.observableList(searchBy);
        searchByComboBox.setItems(searchByList);
    }

    @FXML
    public void populateDatesComboBox(){
        ObservableList<String> searchByDateList = FXCollections.observableList(datesMissing);
        dateMissingComboBox.setItems(searchByDateList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateSearchByComboBox();
        populateDatesComboBox();

    }
}