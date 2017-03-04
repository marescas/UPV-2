/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica31;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author marcosesteve
 */
public class FXMLDocumentController implements Initializable {
    int x;
    @FXML
    private Label valor;
    @FXML
    private TextField textoSumar;
    @FXML
    private CheckBox restarcheck;
    @FXML
    private Label textoRestando;
    
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //no se si sera correcto pero inicializo aqui el valor de x
         x= 0;
        valor.setText( x+"");
    }    

    @FXML
    private void boton1(ActionEvent event) {
        if (restarcheck.isSelected()) {
            x-=1;
        }else{
            x+=1;
        }
        valor.setText(x+"");
    }

    @FXML
    private void boton5(ActionEvent event) {
         if (restarcheck.isSelected()) {
            x-=5;
        }else{
            x+=5;
        }
        valor.setText(x+"");
    }

    @FXML
    private void boton10(ActionEvent event) {
         if (restarcheck.isSelected()) {
            x-=10;
        }else{
            x+=10;
        }
        valor.setText(x+"");
    }

    @FXML
    private void restar(ActionEvent event) {
        if (restarcheck.isSelected()) {
            textoRestando.setVisible(true);
        }else{
            textoRestando.setVisible(false);
        }
    }

    @FXML
    private void sumar(ActionEvent event) {
        int t = 0;
        try{
            t = Integer.parseInt(textoSumar.getText());
            if (restarcheck.isSelected()) {
                x-=t;
            }else{
                x+=t;
            }
            valor.setText(x+"");
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eres tonto");
            alert.setContentText("Introduce un número correcto, informático de pacotilla.");
            alert.showAndWait();
        }
        
        
    }
    
}
