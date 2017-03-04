/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author marcosesteve
 */
public class FXMLcalculadoraController implements Initializable {
    
    private Label label;
    @FXML
    private TextField TextEscribir;
    @FXML
    private Button boton;
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void pulsado(ActionEvent event) {
      TextEscribir.setText(boton.getText());
    }
    
}
