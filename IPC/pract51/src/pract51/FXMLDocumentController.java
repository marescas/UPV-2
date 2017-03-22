/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pract51;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import static javafx.application.Platform.exit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;

/**
 *
 * @author marcosesteve
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Menu btnlanzar;
    @FXML
    private CheckMenuItem amazonbtn;
    @FXML
    private CheckMenuItem ebaybtn;
    @FXML
    private ToolBar toolbar;
    @FXML
    private Label texto;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void salirpulsado(ActionEvent event) {
        
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Salir");
            alert.setHeaderText("Vas a salir");
            alert.setContentText("¿Seguro que quieres continuar?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                exit();
            }
    }

    @FXML
    private void lanzarPulsado(ActionEvent event) {
        toolbar.setVisible(true);
    }

    @FXML
    private void amazonpulsado(ActionEvent event) {
        if (amazonbtn.isSelected()) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Compra correcta");
            alert.setContentText("Compra realizada con éxito");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                    
            }
        }else{
        
        }
    }

    @FXML
    private void ebaypulsado(ActionEvent event) {
    }

    @FXML
    private void amazonComprar(ActionEvent event) {
    }

    @FXML
    private void bloggerPulsado(ActionEvent event) {
            List<String> choices = new ArrayList<>();
            choices.add("El blog de Athos");
            choices.add("El blog de Phortos");
            choices.add("El blog de Aramis");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("El blog de Athos", choices);
            dialog.setTitle("Selecciona un blog");
            dialog.setHeaderText("¿Qué blog quieres leer?");
            dialog.setContentText("Elige: ");
            Optional<String> result = dialog.showAndWait();
            texto.setText("Estas leyendo "+result.get());
       }

    @FXML
    private void ebaybtnok(ActionEvent event) {
        if(ebaybtn.isSelected()){
        
        }
    }

    @FXML
    private void facebookbtnok(ActionEvent event) {
            TextInputDialog dialog = new TextInputDialog("Nombre"); // Por defecto
    dialog.setTitle("Introduce tu nombre");
    dialog.setHeaderText("¿Con que usuarios quieres escribir en Facebook?");
    dialog.setContentText("Introduce tu nombre:");
    Optional<String> result = dialog.showAndWait();
    // Obteniendo el resultado (pre Java 8)
    if (result.isPresent()){
    texto.setText(result.get());
}
    }

    @FXML
    private void plusbtnOk(ActionEvent event) {
    }
    
}
