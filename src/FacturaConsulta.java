

import java.awt.Button;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class FacturaConsulta extends Frame implements WindowListener, ActionListener 
{
	private static final long serialVersionUID = 1L;
	TextArea consulta = new TextArea(5,30);
	Button btnAceptar = new Button("Aceptar");
	Button btnPdf = new Button("Exportar a PDF");
	// Se crea el documento
	Document documento = new Document();
	File path = new File ("fichero.pdf");
	FacturaConsulta()
	{
		setTitle("Lista de Facturas");
		setLayout(new FlowLayout());
		// Conectar a la base de datos
		Connection con = conectar();
		// Seleccionar de la tabla edificios
		// Sacar la información
		rellenarTextArea(con, consulta);
		// Cerrar la conexión
		desconectar(con);
		consulta.setEditable(false);
		add(consulta);
		add(btnAceptar);
		add(btnPdf);
		btnAceptar.addActionListener(this);
		btnPdf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try
				{
					// Se crea el OutputStream para el fichero donde queremos dejar el pdf.
					FileOutputStream ficheroPdf = new FileOutputStream("fichero.pdf");
					// Se asocia el documento al OutputStream y se indica que el espaciado entre
					// lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
					PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
					// Se abre el documento.
					documento.open();
					documento.add(new Paragraph(consulta.getText()));
					documento.close();
					// Y ahora abrimos el fichero para mostrarlo
					Desktop.getDesktop().open(path);
				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}

			}
		});
		addWindowListener(this);
		setSize(250,300);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		Object objetoPulsado = e.getSource();
		if(objetoPulsado.equals(btnAceptar))
		{
			setVisible(false);
		}
		else
		{
			System.out.println("Exportando a PDF...");
		}
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		// TODO Auto-generated method stub
		setVisible(false);
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	public Connection conectar()
	{
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/panaderiaprog?autoReconnect=true&useSSL=false";
		String user = "root2";
		String password = "12345678A";
		Connection con = null;
		try {
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexión con la BD empresa
			con = DriverManager.getConnection(url, user, password);
			if (con != null) {
				System.out.println("Conectado a la base de datos");
			}
		} catch (SQLException ex) {
			System.out.println("ERROR:La dirección no es válida o el usuario y clave");
			ex.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error 1-" + cnfe.getMessage());
		}
		return con;
	}
	public void rellenarTextArea(Connection con, TextArea t)
	{
		String sqlSelect = "SELECT * FROM facturas";
		try {
			Log4JCoreJava objLog4JCoreJavaSample = new Log4JCoreJava();
			  objLog4JCoreJavaSample.callMeInAppWarn("Se entro a Consulta de Facturas");
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) 
			{
				if(t.getText().length()==0)
				{
					t.setText(rs.getInt("idFacturas")+
							"-"+rs.getString("fechaFactura")+
							", "+rs.getString("importeTotalFactura")+"€, DNI del Cliente"+
							": "+rs.getString("idClienteFK"));
				}
				else
				{
					t.setText(t.getText() + "\n" +
							rs.getInt("idFacturas")+
							"-"+rs.getString("fechaFactura")+
							", "+rs.getString("importeTotalFactura")+"€, DNI del Cliente"+
							": "+rs.getString("idClienteFK"));
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
	}
	public void desconectar(Connection con)
	{
		try
		{
			con.close();
		}
		catch(Exception e) {}
	}

}
