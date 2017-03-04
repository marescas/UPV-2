package aplicaciones.paginasWeb;

/**
*Text genereted by Simple GUI Extension for BlueJ
*/
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.border.Border;
import javax.swing.*;
import java.util.Arrays;


public class GUIRecomendacion extends JFrame {

	private JMenuBar menuBar;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JTextArea textarea1;
	private JTextField textfield1, textfield2;
    private Recomendacion apw;
    
	//Constructor 
	public GUIRecomendacion(String nomFile){

		this.setTitle("Top referencias");
		this.setSize(500,350);
		this.setResizable(false);

		//pane with null layout
		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(500,350));
		contentPane.setBackground(new Color(192,192,192));
        
		if (nomFile == "") { apw = new Recomendacion(); }
		else { apw = new Recomendacion(nomFile); }
        
		button1 = new JButton();
		button1.setBounds(11,188,140,35);
		button1.setBackground(new Color(204,255,255));
		button1.setForeground(new Color(0,0,0));
		button1.setEnabled(true);
		button1.setFont(new Font("sansserif",0,12));
		button1.setText("Analizar Pag web");
		button1.setVisible(true);
		
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				anyadir(evt);
			}
		});


		button2 = new JButton();
		button2.setBounds(11,225,140,35);
		button2.setBackground(new Color(255,255,204));
		button2.setForeground(new Color(0,0,0));
		button2.setEnabled(true);
		button2.setFont(new Font("sansserif",0,12));
		button2.setText("Top");
		button2.setVisible(true);
		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				top(evt);
			}
		});

        button3 = new JButton();
		button3.setBounds(11,262,140,35);
		button3.setBackground(new Color(143,237,143));
		button3.setForeground(new Color(0,0,0));
		button3.setEnabled(true);
		button3.setFont(new Font("sansserif",0,12));
		button3.setText("Top k");
		button3.setVisible(true);
		
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				topK(evt);
			}
		});
		
		button4 = new JButton();
		button4.setBounds(11,299,140,35);
		button4.setBackground(new Color(255,181,194));
		button4.setForeground(new Color(0,0,0));
		button4.setEnabled(true);
		button4.setFont(new Font("sansserif",0,12));
		button4.setText("Listar páginas");
		button4.setVisible(true);
		
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				listarPags(evt);
			}
		});
		
		textarea1 = new JTextArea();
		textarea1.setBounds(9,9,480,167);
		textarea1.setBackground(new Color(255,255,204));
		textarea1.setForeground(new Color(0,0,0));
		textarea1.setEnabled(true);
		textarea1.setFont(new Font("sansserif",0,12));
		textarea1.setText("");
		textarea1.setBorder(BorderFactory.createBevelBorder(1));
		textarea1.setVisible(true);

		textfield1 = new JTextField();
		textfield1.setBounds(155,187,310,35);
		textfield1.setBackground(new Color(204,255,255));
		textfield1.setForeground(new Color(0,0,0));
		textfield1.setEnabled(true);
		textfield1.setFont(new Font("sansserif",0,12));
		textfield1.setText("url ...");
		textfield1.setVisible(true);

		textfield2 = new JTextField();
		textfield2.setBounds(155,261,100,35);
		textfield2.setBackground(new Color(143,237,143));
		textfield2.setForeground(new Color(0,0,0));
		textfield2.setEnabled(true);
		textfield2.setFont(new Font("sansserif",0,12));
		textfield2.setText("k ...");
		textfield2.setVisible(true);
		
		//adding components to contentPane panel
		contentPane.add(button1);
		contentPane.add(button2);
		contentPane.add(button3);
		contentPane.add(button4);
		contentPane.add(textarea1);
		contentPane.add(textfield1);
		contentPane.add(textfield2);

		//adding panel to JFrame and seting of window position and close operation
		this.add(contentPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	//Method actionPerformed for button1
	private void anyadir(ActionEvent evt) {
	    String nomurl = textfield1.getText();
	    ReferenciasWeb pw = apw.anyadirPagina(nomurl);
	    textarea1.setText("Anyadiendo ... \n");
	    textarea1.append(pw.toString());
	}

	//Method actionPerformed for button2
	private void top(ActionEvent evt) {
	    if (apw.noPags > 0) {
            ReferenciasWeb pw = apw.maxReferencias();
	        textarea1.setText(pw.toString());
	    } else {
	        textarea1.setText("No hay páginas para recomendar ... \n");   
	    }
	}
	
	//Method actionPerformed for button3
	private void topK(ActionEvent evt) {
	    if (apw.noPags > 0) {
	        String strk = textfield2.getText();
	        int k = 1;
	        try { 
	            k = Integer.parseInt(strk);
	        } catch (NumberFormatException e) {
	            textarea1.setText("Top con k = 1 ... \n");
	        } finally {
	            ReferenciasWeb[] pwk = apw.maxKReferencias(k);
//         String str = "";
//         for (int i = 0; i < pwk.length; i++) {
//             str.append(pwk[i]); str.append("\n");
//         }
                String str = Arrays.toString(pwk);
	            textarea1.setText(str);
	        }
	    } else {
	        textarea1.setText("No hay páginas para recomendar ... \n");   
	    }
	}
	
	//Method actionPerformed for button4
	private void listarPags(ActionEvent evt) {
	    textarea1.setText(apw.toString());
	}



	 public static void main(String[] args){
		System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			    String nomFile = "";
			    if (args.length != 0) { nomFile = args[0]; }
				new GUIRecomendacion(nomFile);
			}
		});
	}

}