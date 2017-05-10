/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableipc.controller;

import accesoaBD.AccesoaBD;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.Proyeccion;

/**
 * FXML Controller class
 *
 * @author marcosesteve
 */
public class RecaudacionPeliculaController implements Initializable {
    Stage primaryStage;
    Map<String,Integer> tablaHash = new HashMap<String,Integer>();
    AccesoaBD db;
    String[] festivo = {"1","2","8","9"};
    String[] normal = {"3","4","6","7"};
    String diaSpectador = "5";
    int diaInicial, diaFinal;
    @FXML
    private ChoiceBox<String> choiceInicial;
    @FXML
    private ChoiceBox<String> choiceFinal;
    @FXML
    private Button salirClicked;
    @FXML
    private TableColumn<film, String> titulo;
    @FXML
    private TableColumn<film, String> recaudacion;
    @FXML
    private TableView<film> tableView;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         db= new AccesoaBD();
         tableView.setPlaceholder(new Label("Selecciona los días para ver las estadísticas"));
        List<String> listaDias = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            listaDias.add(String.valueOf(i) +" de Abril de 2017");
        }
        choiceInicial.setItems(FXCollections.observableList(listaDias));
       choiceFinal.setItems(FXCollections.observableList(listaDias));
        titulo.setCellValueFactory(new PropertyValueFactory<film,String>("Titulo"));
        recaudacion.setCellValueFactory(new PropertyValueFactory<film,String>("Recaudacion"));
        choiceInicial.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
             @Override
             public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 int dia = Integer.parseInt(newValue.substring(0,1));
                 choiceFinal.setDisable(false);
                 diaInicial = dia;
                 tablaHash.clear();
                 //choiceFinal.getSelectionModel().clearSelection();
             }
         });
        choiceFinal.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tablaHash.clear();
                int dia = Integer.parseInt(newValue.substring(0,1));
                int ratio = 0;
                diaFinal = dia;
                System.out.println(dia);
                if (diaFinal<diaInicial) {
                   Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Dias incorrectos");
                    alert.setHeaderText("El rango está mal especificado.");
                   

                    alert.showAndWait();
                }else{
                    
                
                List<film> films = new ArrayList<>();
                for(int  j = diaInicial; j<=diaFinal; j++){
                        List<Proyeccion> proyecciones = db.getProyeccionesDia(LocalDate.of(2017, Month.APRIL, j));
                        if (Arrays.asList(festivo).contains(
                    String.valueOf(j))) {
                            ratio = 8;
                    }else if (Arrays.asList(normal).contains(
                    String.valueOf(j))) {
                            ratio = 6;
                    }else{
                        ratio = 5;
                    }
                       for (int i = 0; i < proyecciones.size(); i++) {
                           String tituloPeli = proyecciones.get(i).getPelicula().getTitulo();
                           int recaudado = proyecciones.get(i).getSala().getEntradasVendidas()*ratio;

                           if (tablaHash.get(tituloPeli)!=null) {
                               tablaHash.put(tituloPeli, recaudado+tablaHash.get(tituloPeli));
                           }else{
                               tablaHash.put(tituloPeli, recaudado);
                           }   
                       }
                }
                 
                    for (Map.Entry<String, Integer> entry : tablaHash.entrySet()) {
                            String key = entry.getKey();
                            Integer value = entry.getValue();
                            
                            films.add(new film(key, value+"€"));

                    
                    }   
                    

                    
               tableView.setItems(FXCollections.observableList(films));
                
                }   
            }
        });
    }    

    void initStage(Stage estageActual) {
        primaryStage = estageActual;
    }

    @FXML
    private void salirClicked(ActionEvent event) {
        primaryStage.close();
    }
    public class film {
        private StringProperty Titulo = new SimpleStringProperty();
        private StringProperty Recaudacion = new SimpleStringProperty();
       
        public film(String titulo, String recaudacion ) {
            this.Titulo.setValue(titulo);
            this.Recaudacion.setValue(recaudacion);
           
        }
         
          public String getTitulo(){
          return this.Titulo.getValue();
          }
         
       public String getRecaudacion(){
           return Recaudacion.getValue();
       }
    }
    
}
