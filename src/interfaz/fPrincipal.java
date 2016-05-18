package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
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

public class fPrincipal extends JFrame {

	private JPanel contentPane;
	private JLabel lbl1;

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
		setBounds(100, 100, 727, 611);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenuItem mntmAbrirCarpeta = new JMenuItem("Abrir carpeta");
		mntmAbrirCarpeta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mnArchivo.add(mntmAbrirCarpeta);
		
		JMenu mnAcercaDe = new JMenu("Acerca de");
		menuBar.add(mnAcercaDe);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList lClases = new JList();
		lClases.setBounds(10, 145, 133, 165);
		contentPane.add(lClases);
		
		JList lMetodos = new JList();
		lMetodos.setBounds(153, 145, 311, 165);
		contentPane.add(lMetodos);
		
		JList lArchivos = new JList();
		lArchivos.setBounds(10, 32, 454, 77);
		contentPane.add(lArchivos);
		
		JLabel lblSeleccionarArchivoA = new JLabel("Seleccionar archivo:");
		lblSeleccionarArchivoA.setBounds(10, 11, 168, 14);
		contentPane.add(lblSeleccionarArchivoA);
		
		JLabel lblElegirClase = new JLabel("Elegir clase:");
		lblElegirClase.setBounds(10, 125, 133, 14);
		contentPane.add(lblElegirClase);
		
		JLabel lblElegirMtodo = new JLabel("Elegir m\u00E9todo:");
		lblElegirMtodo.setBounds(153, 125, 133, 14);
		contentPane.add(lblElegirMtodo);
		
		JLabel lblCdigoMtodo = new JLabel("C\u00F3digo del m\u00E9todo:");
		lblCdigoMtodo.setBounds(10, 321, 133, 14);
		contentPane.add(lblCdigoMtodo);
		
		JTextArea txtaCodigo = new JTextArea();
		txtaCodigo.setEditable(false);
		txtaCodigo.setBounds(10, 339, 701, 211);
		contentPane.add(txtaCodigo);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.text);
		panel.setBounds(475, 32, 236, 278);
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
		
		JLabel lblLCodTotales = new JLabel("999999");
		lblLCodTotales.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLCodTotales.setForeground(SystemColor.activeCaption);
		lblLCodTotales.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLCodTotales.setBounds(145, 7, 81, 28);
		panel.add(lblLCodTotales);
		
		JLabel lblLComentarios = new JLabel("999999");
		lblLComentarios.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLComentarios.setForeground(SystemColor.activeCaption);
		lblLComentarios.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLComentarios.setBounds(145, 44, 81, 28);
		panel.add(lblLComentarios);
		
		JLabel lblPComentarios = new JLabel("999999");
		lblPComentarios.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPComentarios.setForeground(SystemColor.activeCaption);
		lblPComentarios.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPComentarios.setBounds(145, 76, 81, 28);
		panel.add(lblPComentarios);
		
		JLabel lblComplejidadCiclomatica = new JLabel("999999");
		lblComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.RIGHT);
		lblComplejidadCiclomatica.setForeground(SystemColor.activeCaption);
		lblComplejidadCiclomatica.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblComplejidadCiclomatica.setBounds(145, 109, 81, 28);
		panel.add(lblComplejidadCiclomatica);
		
		JLabel lblFanIn = new JLabel("999999");
		lblFanIn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFanIn.setForeground(SystemColor.activeCaption);
		lblFanIn.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFanIn.setBounds(145, 142, 81, 28);
		panel.add(lblFanIn);
		
		JLabel lblFanOut = new JLabel("999999");
		lblFanOut.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFanOut.setForeground(SystemColor.activeCaption);
		lblFanOut.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFanOut.setBounds(145, 175, 81, 28);
		panel.add(lblFanOut);
		
		JLabel lblHalsteadLongitud = new JLabel("999999");
		lblHalsteadLongitud.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHalsteadLongitud.setForeground(SystemColor.activeCaption);
		lblHalsteadLongitud.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHalsteadLongitud.setBounds(145, 208, 81, 28);
		panel.add(lblHalsteadLongitud);
		
		JLabel lblHalsteadVolumen = new JLabel("999999");
		lblHalsteadVolumen.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHalsteadVolumen.setForeground(SystemColor.activeCaption);
		lblHalsteadVolumen.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHalsteadVolumen.setBounds(145, 241, 81, 28);
		panel.add(lblHalsteadVolumen);
		
		JLabel lblMtricasDelMtodo = new JLabel("M\u00E9tricas del m\u00E9todo:");
		lblMtricasDelMtodo.setBounds(475, 11, 168, 14);
		contentPane.add(lblMtricasDelMtodo);
	}
}
