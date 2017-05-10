/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entregableipc.controller;

import accesoaBD.AccesoaBD;
import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.Proyeccion;
import modelo.Reserva;
import modelo.Sala;

/**
 * FXML Controller class
 *
 * @author marcosesteve
 */
public class ImprimirEntradaController implements Initializable {
private Printer printer;
    String[] festivo = {"1","2","8","9"};
    String[] normal = {"3","4","6","7"};
    String diaSpectador = "5";
AccesoaBD bd;
int numEntradas;
int precios;
    @FXML
    private Label titulo;
    @FXML
    private Label dia;
    @FXML
    private Label sala;
    @FXML
    private Label hora;
    @FXML
    private Label localidad;
    @FXML
    private Button SelecImpresora;
    @FXML
    private Button SelecImprimir;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private VBox vbox;
    @FXML
    private Label precio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        printer =Printer.getDefaultPrinter();
        bd = new AccesoaBD();
        numEntradas = 0;
        // TODO
    }    

    void initStage(Stage estageActual, Proyeccion actual,boolean[][] elegidas ) {
        
        titulo.setText("Película: " +actual.getPelicula().getTitulo());
        dia.setText("Fecha: "+ actual.getDia().toString());
        sala.setText("SALA "+actual.getSala().getNombresala());
        hora.setText("Hora de inicio: "+actual.getHoraInicio());
        String s = "";
        for (int col =0 ; col < 12; col++) {
            for (int row = 0; row < 18; row++) {
                if (elegidas[row][col]) {
                    numEntradas++;
                    actual.getSala().updateLocalidad(row, col, Sala.localidad.vendida);
                    actual.getSala().setEntradasVendidas(actual.getSala().getEntradasVendidas()+1);
                    s+="Fila: "+ (row+1) +" Columna: "+(col+1)+ "\n";
                }
            }
            }
        localidad.setText(s);
        bd.salvarProyeccion(actual);
        if (Arrays.asList(festivo).contains(
                String.valueOf(actual.getDia().getDayOfMonth()))) {
            precios = numEntradas*8;
        }else if (Arrays.asList(normal).contains(
                String.valueOf(actual.getDia().getDayOfMonth()))) {
            precios = numEntradas*6;
        }else{
            precios = numEntradas*5;
        }
        precio.setText("PRECIO: " +precios + "€");
    }

    @FXML
    private void selectClicked(ActionEvent event) {
        ChoiceDialog dialog = new ChoiceDialog(Printer.getDefaultPrinter(),
        Printer.getAllPrinters());
        dialog.setHeaderText("Seleccionar la impresora!");
        dialog.setContentText("Seleccionar una impresora de las disponibles");
        dialog.setTitle("Selección Impresora");
        Optional<Printer> opt = dialog.showAndWait();
        if (opt.isPresent()) {
        printer = opt.get();
        
        } 
    }

    @FXML
    private void imprimirClicked(ActionEvent event) {
        print(vbox);
    }

   
    private void print(Node node) {
        PrinterJob job = PrinterJob.createPrinterJob(printer);
        if (job != null)
        {
        boolean printed = job.printPage(node);
        if (printed)
        {
        job.endJob();
        }
        else
        {
        System.out.println("Fallo al imprimir");
        }
        }
        else
        {
        System.out.println("No puede crearse el job de impresión.");
        }
}
}
