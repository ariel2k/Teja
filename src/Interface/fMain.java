package Interface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class fMain extends JFrame {

	private JPanel contentPane;
	private JLabel lblLblpath = new JLabel("");
	private final JTextArea textArea = new JTextArea();
	private JLabel lblLineasCod = new JLabel("Cantidad lineas c\u00F3digo:");
	private JLabel lblLineasComentarios = new JLabel("Cantidad lineas comentarios:");
	private JLabel lblLineasBlancas = new JLabel("Cantidad lineas blancas:");


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fMain frame = new fMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public fMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 619, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblLblpath.setBounds(10, 11, 467, 14);
		contentPane.add(lblLblpath);
		
		JButton btnBtnabrir = new JButton("Abrir");
		btnBtnabrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirArchivoApi();
			}
		});
		btnBtnabrir.setBounds(504, 11, 89, 23);
		contentPane.add(btnBtnabrir);
		textArea.setBounds(10, 65, 294, 353);
		
		contentPane.add(textArea);
		
		lblLineasCod.setBounds(314, 72, 220, 23);
		contentPane.add(lblLineasCod);
		
		lblLineasComentarios.setBounds(314, 110, 220, 23);
		contentPane.add(lblLineasComentarios);
		
		lblLineasBlancas.setBounds(314, 151, 220, 23);
		contentPane.add(lblLineasBlancas);
	}
	
	private void abrirArchivoApi(){
		// muestra el cuadro de diálogo de archivos, para que el usuario pueda elegir el archivo a abrir
		JFileChooser selectorArchivos = new JFileChooser();
		selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// indica cual fue la accion de usuario sobre el jfilechooser
		int resultado = selectorArchivos.showOpenDialog(this);
		
		File archivo = selectorArchivos.getSelectedFile(); // obtiene el archivo seleccionado

		// muestra error si es inválido
		/*
		if ((archivo == null) || (archivo.getName().equals(""))) {
			textPane .showMessageDialog(this, "Nombre de archivo inválido", "Nombre de archivo inválido");
		} // fin de if
		*/
		
		int nroComentario = 0, nroCodigo = 0, nroBlanco=0;
		
		lblLblpath.setText(archivo.getAbsolutePath());
		try {
			Scanner scn = new Scanner(archivo);
			while (scn.hasNext()) {
				textArea.insert(scn.nextLine() + "\n" , textArea.getText().length());
				//guardamos la linea en un string y le sacamos los espacios en blanco
			}	
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			//lblLineasComentarios.setText("Cantidad de lineas comentario: " + nroComentario);
		}
		
		String texto = textArea.getText();
		
	}
}
