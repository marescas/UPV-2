/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableipc.controller;

import accesoaBD.AccesoaBD;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import modelo.Pelicula;
import modelo.Proyeccion;
import modelo.Reserva;

/**
 * FXML Controller class
 *
 * @author marcosesteve
 */
public class VentaentradasController implements Initializable {
    Stage primaryStage;
    Proyeccion seleccionada;
    String tituloPelicula, horaPelicula;
    LocalDate fechaActual;
    AccesoaBD db;
    int libre;
    Reserva elegida = null;
    Proyeccion proyeccionelegida;
    
    @FXML
    private Text textSelPeli;
    @FXML
    private ChoiceBox<Pelicula> choiceSelPeli;
    @FXML
    private Text textSelHora;
    @FXML
    private ChoiceBox<Proyeccion> choiceSelHora;
    @FXML
    private Text textSelDia;
    @FXML
    private ChoiceBox<String> choiceSelDia;
    @FXML
    private Label localidadesLibres;
    @FXML
    private Button botonReservar;
    @FXML
    private Button buscar;
    @FXML
    private ImageView imagenPelicula;
  
    @FXML
    private CheckBox tienereserva;
    @FXML
    private Button comprar;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         db = new AccesoaBD();
        List<String> eleccionDiaArray = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            eleccionDiaArray.add(String.valueOf(i)+ " de Abril del 2017"); 
        }
        choiceSelDia.setItems(FXCollections.observableList(eleccionDiaArray));
        choiceSelDia.getSelectionModel().selectFirst();
        fechaActual = LocalDate.of(2017,Month.APRIL,Integer.parseInt(choiceSelDia.getSelectionModel().getSelectedItem().substring(0, 1)));
        choiceSelDia.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
             @Override
             public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 botonReservar.setVisible(false);
                 localidadesLibres.setVisible(false);        
                 fechaActual = LocalDate.of(2017,Month.APRIL,Integer.parseInt(newValue.substring(0, 1)));
             }
         });
        /*-
        Descargo las peliculas de la base de datos.
        Como no existe un toString() en la clase pelicula me creo un StringConverter para mostrar los datos como yo quiero.
        */
        List<Pelicula> peliculas =db.getTodasPeliculas();
        choiceSelPeli.setItems(FXCollections.observableList(peliculas));
        choiceSelPeli.getSelectionModel().selectFirst();
        choiceSelPeli.setConverter(new StringConverter<Pelicula>() {
             @Override
             public String toString(Pelicula object) {
                 
                return object.getTitulo();
             }

             @Override
             public Pelicula fromString(String string) {
                 throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }
         });
        imagenPelicula.setImage(new Image(choiceSelPeli.getSelectionModel().getSelectedItem().getPathImage()));
        tituloPelicula = choiceSelPeli.getSelectionModel().getSelectedItem().getTitulo();
        choiceSelPeli.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pelicula>() {
             @Override
             public void changed(ObservableValue<? extends Pelicula> observable, Pelicula oldValue, Pelicula newValue) {
                 imagenPelicula.setImage(new Image(newValue.getPathImage()));
                 botonReservar.setVisible(false);
                 localidadesLibres.setVisible(false);
                 tituloPelicula = newValue.getTitulo();
                 System.out.println(tituloPelicula);
             }
         });
        
        /*-
        Descargo las proyecciones para ese dia y pelicula
        */
        List<Proyeccion> proyeccPeliDia = db.getProyeccion(tituloPelicula,fechaActual);
        choiceSelHora.setItems(FXCollections.observableList(proyeccPeliDia));
        choiceSelHora.getSelectionModel().selectFirst();
        choiceSelHora.setConverter(new StringConverter<Proyeccion>() {
             @Override
             public String toString(Proyeccion object) {
                return object.getHoraInicio();
             }

             @Override
             public Proyeccion fromString(String string) {
                 throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }
         });
        horaPelicula = choiceSelHora.getSelectionModel().getSelectedItem().getHoraInicio();
        choiceSelHora.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Proyeccion>() {
             @Override
             public void changed(ObservableValue<? extends Proyeccion> observable, Proyeccion oldValue, Proyeccion newValue) {
                 
                botonReservar.setVisible(false);
                localidadesLibres.setVisible(false);
                horaPelicula = newValue.getHoraInicio();
                System.out.println(horaPelicula);
             }
         });
         
      tienereserva.selectedProperty().addListener(new ChangeListener<Boolean>() {
             @Override
             public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                 if (newValue) {
                     
                      TextInputDialog dialog = new TextInputDialog();
                      dialog.setTitle("Recuperar reserva");
                      dialog.setHeaderText("Recuperar reserva para el dia "+choiceSelDia.getSelectionModel().getSelectedItem());
                      dialog.setContentText("Introduce nombre o teléfono del titular");
                      // Traditional way to get the response value.
                      
                       Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()){ 
                            boolean encontrado = false;
                           List<Proyeccion> proyecciones = db.getProyeccionesDia(fechaActual);
                            for (int i = 0; i < proyecciones.size() && !encontrado; i++) {
                                List<Reserva> reservas = proyecciones.get(i).getReservas();
                                for (int j = 0; j < reservas.size(); j++) {
                                    if (reservas.get(j).getNombre().equals(result.get()) 
                                            ||reservas.get(j).getTelefono().equals(result.get()) ) {
                                                encontrado = true;
                                                proyeccionelegida = proyecciones.get(i);
                                                elegida = reservas.get(j);
                                                reservas.remove(j);
                                                db.salvarProyeccion(proyeccionelegida);
                                                break;
                                    }
                                    
                                }
                            }
                            if (encontrado) {
                                comprar.setDisable(false);
                                buscar.setDisable(true);
                               Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Reserva encontrada");
                                alert.setHeaderText("La reserva ha sido encontrada con éxito.");

                                Optional<ButtonType> resultado = alert.showAndWait();
                                if (resultado.isPresent() && elegida!=null) {
                                   choiceSelPeli.getSelectionModel().select(proyeccionelegida.getPelicula());
                                   choiceSelHora.getSelectionModel().select(proyeccionelegida);
                                }
                                
                            }else{
                                comprar.setDisable(true);
                                buscar.setDisable(false);
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Reserva no encontrada");
                                alert.setHeaderText("No existe ninguna reserva asociada a ese nombre o telefono.");

                                alert.showAndWait();
                            
                            }
                        }
                 }
             }
         });
     
   
       
        
    }    

    void initStage(Stage estageActual) {
        primaryStage = estageActual;
        primaryStage.setTitle("Reservas");
    }

    @FXML
    private void reservaClicked(ActionEvent event) {
        comprarClicked(event);

    }

    @FXML
    private void buscarClicked(ActionEvent event) {
        botonReservar.setVisible(true);
        localidadesLibres.setVisible(true);
        seleccionada = db.getProyeccion(tituloPelicula
                         , fechaActual
                         , horaPelicula);
                 int localidadesReservadas = 0;
                 for (int i = 0; i < seleccionada.getReservas().size(); i++) {
                     localidadesReservadas += seleccionada.getReservas().get(i).getNumLocalidades();
                 }
                 libre = seleccionada.getSala().getCapacidad()-seleccionada.getSala().getEntradasVendidas()-localidadesReservadas;
                 if (libre > 0) {
                    //existen localidades libres 
                    localidadesLibres.setText("Quedan "+ libre +" localidades libres.");
                 }else{
                     localidadesLibres.setText("No quedan localidades libres.");
                     botonReservar.setVisible(false);
                 }        
    }

    @FXML
    private void comprarClicked(ActionEvent event) {
         try {
             seleccionada = db.getProyeccion(tituloPelicula
                         , fechaActual
                         , horaPelicula);
            Stage estageActual = new Stage();
            FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/entregableipc/view/seleccionarLocalidad.fxml"));
            Parent root = (Parent) miCargador.load();
            miCargador.<SeleccionarLocalidadController>getController().initStage(estageActual);
            System.out.println(seleccionada.getHoraInicio()+" "+seleccionada.getPelicula().getTitulo());
            SeleccionarLocalidadController controlador = miCargador.<SeleccionarLocalidadController>getController();
            controlador.initProyeccion(seleccionada);
          
            
            Scene scene = new Scene(root,900,750);
            estageActual.setScene(scene);
            estageActual.initModality(Modality.APPLICATION_MODAL);
            estageActual.setMinWidth(900);
            estageActual.setMinHeight(750);
            estageActual.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    

   
    
}
