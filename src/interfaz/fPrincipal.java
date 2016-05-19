package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import buscador.Interpretador;
import codigoFuente.Clase;
import metricas.Metricas;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.ListModel;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JFileChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.Color;


public class fPrincipal extends JFrame {

	private JPanel contentPane;
	private JLabel lbl1;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnArchivo = new JMenu("Archivo");
	private JMenuItem mntmAbrirCarpeta = new JMenuItem("Abrir carpeta");
	private JFileChooser fc = new JFileChooser();
	private JList lClases = new JList();
	private JList lMetodos = new JList();
	private JList lArchivos = new JList();
	private JLabel lblSeleccionarArchivoA = new JLabel("Seleccionar archivo:");
	private JLabel lblElegirClase = new JLabel("Elegir clase:");
	private JLabel lblElegirMtodo = new JLabel("Elegir m\u00E9todo:");
	private JLabel lblCdigoMtodo = new JLabel("C\u00F3digo del m\u00E9todo:");
	private JTextArea txtaCodigo = new JTextArea();
	private JPanel panel = new JPanel();

	private	JLabel lblLCodTotales = new JLabel("");
	private	JLabel lblLComentarios = new JLabel("");
	private	JLabel lblPComentarios = new JLabel("");
	private	JLabel lblComplejidadCiclomatica = new JLabel("");
	private	JLabel lblFanIn = new JLabel("");
	private	JLabel lblFanOut = new JLabel("");
	private	JLabel lblHalsteadLongitud = new JLabel("");
	private	JLabel lblHalsteadVolumen = new JLabel("");

	private String[] ficheros;
	private Interpretador interprete;
	private JFileChooser chooser;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fPrincipal frame = new fPrincipal();
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
	public fPrincipal() {
		setTitle("Teja");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 741, 611);
		
		
		setJMenuBar(menuBar);
		
		
		menuBar.add(mnArchivo);
		mntmAbrirCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listarArchivos();
			}
		});
		
		mntmAbrirCarpeta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UNDEFINED, 0));
		mnArchivo.add(mntmAbrirCarpeta);
		
		JMenu mnAcercaDe = new JMenu("Acerca de");
		menuBar.add(mnAcercaDe);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		lblSeleccionarArchivoA.setBounds(10, 11, 168, 14);
		contentPane.add(lblSeleccionarArchivoA);
		
		
		lblElegirClase.setBounds(213, 11, 133, 14);
		contentPane.add(lblElegirClase);
		
		
		lblElegirMtodo.setBounds(213, 125, 133, 14);
		contentPane.add(lblElegirMtodo);
		
		
		lblCdigoMtodo.setBounds(10, 321, 133, 14);
		contentPane.add(lblCdigoMtodo);
		
		
		panel.setBackground(SystemColor.text);
		panel.setBounds(475, 32, 251, 278);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lbl1 = new JLabel("L\u00EDneas de c\u00F3digo totales:");
		lbl1.setBounds(10, 11, 159, 22);
		panel.add(lbl1);
		
		JLabel lbl2 = new JLabel("L\u00EDneas de comentarios:");
		lbl2.setBounds(10, 47, 136, 22);
		panel.add(lbl2);
		
		JLabel lbl3 = new JLabel("Porcetaje de comentarios:");
		lbl3.setBounds(10, 80, 172, 22);
		panel.add(lbl3);
		
		JLabel lbl4 = new JLabel("Complejidad ciclom\u00E1tica:");
		lbl4.setBounds(10, 113, 172, 22);
		panel.add(lbl4);
		
		JLabel lbl5 = new JLabel("Fan in:");
		lbl5.setBounds(10, 146, 136, 22);
		panel.add(lbl5);
		
		JLabel lbl6 = new JLabel("Fan out:");
		lbl6.setBounds(10, 179, 136, 22);
		panel.add(lbl6);
		
		JLabel lbl7 = new JLabel("Halstead longitud");
		lbl7.setBounds(10, 212, 136, 22);
		panel.add(lbl7);
		
		JLabel lbl8 = new JLabel("Halstead volumen:");
		lbl8.setBounds(10, 245, 136, 22);
		panel.add(lbl8);
		
		
		lblLCodTotales.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLCodTotales.setForeground(Color.RED);
		lblLCodTotales.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLCodTotales.setBounds(145, 7, 101, 28);
		panel.add(lblLCodTotales);
		
		
		lblLComentarios.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLComentarios.setForeground(Color.RED);
		lblLComentarios.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLComentarios.setBounds(145, 44, 101, 28);
		panel.add(lblLComentarios);
		
		
		lblPComentarios.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPComentarios.setForeground(Color.RED);
		lblPComentarios.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPComentarios.setBounds(145, 76, 101, 28);
		panel.add(lblPComentarios);
		
		
		lblComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.RIGHT);
		lblComplejidadCiclomatica.setForeground(Color.RED);
		lblComplejidadCiclomatica.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblComplejidadCiclomatica.setBounds(145, 109, 101, 28);
		panel.add(lblComplejidadCiclomatica);
		
		
		lblFanIn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFanIn.setForeground(Color.RED);
		lblFanIn.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFanIn.setBounds(145, 142, 101, 28);
		panel.add(lblFanIn);
		
		
		lblFanOut.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFanOut.setForeground(Color.RED);
		lblFanOut.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFanOut.setBounds(145, 175, 101, 28);
		panel.add(lblFanOut);
		
		
		lblHalsteadLongitud.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHalsteadLongitud.setForeground(Color.RED);
		lblHalsteadLongitud.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHalsteadLongitud.setBounds(145, 208, 101, 28);
		panel.add(lblHalsteadLongitud);
		
		
		lblHalsteadVolumen.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHalsteadVolumen.setForeground(Color.RED);
		lblHalsteadVolumen.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHalsteadVolumen.setBounds(145, 241, 101, 28);
		panel.add(lblHalsteadVolumen);
		
		JLabel lblMtricasDelMtodo = new JLabel("M\u00E9tricas del m\u00E9todo:");
		lblMtricasDelMtodo.setBounds(475, 11, 168, 14);
		contentPane.add(lblMtricasDelMtodo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 32, 193, 278);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(lArchivos);
		lArchivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(213, 32, 251, 86);
		contentPane.add(scrollPane_1);
		scrollPane_1.setViewportView(lClases);
		lClases.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(213, 150, 251, 159);
		contentPane.add(scrollPane_2);
		lMetodos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mostrarCodigoDelMetodoSeleccionado();
			}
		});
		scrollPane_2.setViewportView(lMetodos);
		lMetodos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 344, 716, 206);
		contentPane.add(scrollPane_3);
		scrollPane_3.setViewportView(txtaCodigo);
		
		
		txtaCodigo.setEditable(false);
		lClases.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mostrarMetodosDeLaClaseSeleccionada();
			}

			
		});
		lArchivos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				buscarClasesEnArchivo();
			}

		});
	}
  
	protected void mostrarCodigoDelMetodoSeleccionado() {
		int iClase = this.lClases.getSelectedIndex();
		int iMetodo = this.lMetodos.getSelectedIndex();
		List<String> codigoFuente = this.interprete.getClases().get(iClase).getMetodos().get(iMetodo).getCodigoFuente();
		String textoCodigoFuente = "";
		for (String string : codigoFuente) {
			textoCodigoFuente += string + "\n";
		}
		this.txtaCodigo.setText(textoCodigoFuente);
		
		//Ejecutar metricas
		ejecutarMetricas();
	}

	private void ejecutarMetricas() {
		// TODO Auto-generated method stub
		int iClase = this.lClases.getSelectedIndex();
		int iMetodo = this.lMetodos.getSelectedIndex();
		
		Clase c = this.interprete.getClases().get(iClase);
		Metricas m = new Metricas(c);
		m.calcumarMetricas(iMetodo);
		
		this.lblLComentarios.setText(m.getLComentarios() + "");
		this.lblLCodTotales.setText(m.getLCodigo() + "");
		double porcentaje = Math.round((double) m.getLComentarios() * 100 / m.getLCodigo() );
		this.lblPComentarios.setText("%" + porcentaje);
		this.lblComplejidadCiclomatica.setText(m.getComplejidadCiclomatica() + "");
		this.lblFanIn.setText(m.getFanIn() + "");
		this.lblFanOut.setText(m.getFanOut() + "");
	}

	private void listarArchivos(){
		chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("choosertitle");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	      File dir = new File(chooser.getSelectedFile()+ "");
	      ficheros = dir.list();
	      if (ficheros != null)
	    	  this.lArchivos.setListData(ficheros);
	    } 
	    
	    //vaciamos las listas
		String[] vacio = new String[0];
		this.lClases.setListData(vacio);
		this.lMetodos.setListData(vacio);
		this.txtaCodigo.setText("");
	}
	

	private void buscarClasesEnArchivo() {
		int i = this.lArchivos.getSelectedIndex();
		String ruta = chooser.getSelectedFile() +"\\" + ficheros[i];
		interprete = new Interpretador(ruta);
		this.lClases.setListData( interprete.getNombreClases().toArray());
		
		//vaciamos las listas
		String[] vacio = new String[0];
		this.lMetodos.setListData(vacio);
		this.txtaCodigo.setText("");
	}
	
	private void mostrarMetodosDeLaClaseSeleccionada() {
		int iClase = this.lClases.getSelectedIndex();
		this.lMetodos.setListData(this.interprete.getClases().get(iClase).getNombresMetodos().toArray());
		
		//vaciamos las listas
		this.txtaCodigo.setText("");
	}
}
