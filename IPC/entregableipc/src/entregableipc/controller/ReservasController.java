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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import modelo.Pelicula;
import modelo.Proyeccion;
import modelo.Reserva;

/**
 * FXML Controller class
 *
 * @author marcosesteve
 */
public class ReservasController implements Initializable {
    Stage primaryStage;
    Proyeccion seleccionada;
    String tituloPelicula, horaPelicula;
    LocalDate fechaActual;
    AccesoaBD db;
    int libre;
    
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
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         db = new AccesoaBD();
        List<String> eleccionDiaArray = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            eleccionDiaArray.add(String.valueOf(i)+ " de Abril de 2017"); 
        }
        choiceSelDia.setItems(FXCollections.observableList(eleccionDiaArray));
        choiceSelDia.getSelectionModel().selectFirst();
        fechaActual = LocalDate.of(2017,Month.APRIL,Integer.parseInt(choiceSelDia.getSelectionModel().getSelectedItem().substring(0, 1)));
        choiceSelDia.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
             @Override
             public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 botonReservar.setVisible(false);
                 localidadesLibres.setVisible(false);   
                 System.out.println(newValue.substring(0, 1));
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
        
   
       
        
    }    

    void initStage(Stage estageActual) {
        primaryStage = estageActual;
        primaryStage.setTitle("Reservas");
    }

    @FXML
    private void reservaClicked(ActionEvent event) {
        /*-
        Muestra un dialogo para reservar 
        */
        Dialog<Reserva> dialog = new Dialog<>();
        dialog.setTitle("Añadir reserva");
        dialog.setHeaderText("Agregar reserva para "+
                seleccionada.getPelicula().getTitulo()+
                " a las: "+seleccionada.getHoraInicio()+ " El dia: " + seleccionada.getDia().toString() );
        dialog.setResizable(false);

        Label textName = new Label("Nombre ");
        Label textPhone = new Label("Teléfono ");
        Label textNLocalidades = new Label("Número de localidades ");
        TextField inputName = new TextField();
        TextField inputPhone = new TextField();
        TextField inputNLocalidades  = new TextField();

        GridPane grid = new GridPane();
        grid.add(textName, 1, 1);
        grid.add(inputName, 2, 1);
        grid.add(textPhone, 1, 2);
        grid.add(inputPhone, 2, 2);
        grid.add(textNLocalidades,1,3);
        grid.add(inputNLocalidades,2,3);
        dialog.getDialogPane().setContent(grid);
        grid.setHgap(10);
        grid.setVgap(10);
        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Reserva>() {
            @Override
            public Reserva call(ButtonType param) {
                if (!inputName.getText().equals("") &&
                        !inputPhone.getText().equals("") &&
                        !inputNLocalidades.getText().equals("")) {
                    try{
                       long n = Long.parseLong(inputPhone.getText());
                    return new Reserva(inputName.getText(),
                        inputPhone.getText(),
                        Integer.parseInt(inputNLocalidades.getText()));
                    }catch(NumberFormatException e){
                        Alert alerta = new Alert(Alert.AlertType.ERROR, "El número introducido no es correcto", ButtonType.OK);
                        alerta.showAndWait();
                    }
                }else{
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Rellena todos los campos para realizar una reserva correctamente.", ButtonType.OK);
                        alerta.showAndWait(); 
                }
                return null;
                
            }
        });
    
        Optional<Reserva> result = dialog.showAndWait();

        if (result.isPresent()) {
            //Compruebo si existen localidades suficientes
                if (libre-Integer.parseInt(inputNLocalidades.getText())<0) {
                  Alert alerta = new Alert(Alert.AlertType.ERROR, "No quedan suficientes localidades.", ButtonType.OK);
                        alerta.showAndWait();  
            
            }else{
                    boolean contiene = false;
                    ArrayList<Reserva> reservas = seleccionada.getReservas();
                    for (int i = 0; i < reservas.size() && !contiene; i++) {
                        if (reservas.get(i).getNombre().equals(result.get().getNombre()) 
                                ||reservas.get(i).getTelefono().equals(result.get().getTelefono())) {
                            contiene = true;
                        }
                    }
                    if (contiene) {
                        Alert alerta = new Alert(Alert.AlertType.ERROR, "Ya existe una reserva para este usuario.", ButtonType.OK);
                        alerta.showAndWait();
                        primaryStage.close();
                    }else{
                        seleccionada.addReserva(result.get());
                        db.salvarProyeccion(seleccionada);
                        Alert alerta = new Alert(Alert.AlertType.INFORMATION, "Reserva guardada correctamente.", ButtonType.OK);
                                alerta.showAndWait();
                                primaryStage.close();
                    }
                }
            
        }

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

    
    
}
