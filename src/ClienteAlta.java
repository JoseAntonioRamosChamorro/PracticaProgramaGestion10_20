

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class ClienteAlta extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	Label lblDNICli = new Label("DNI:");
	TextField txtDNICli = new TextField(20);
	Label lbldireccion  = new Label("Direccion:");
	TextField txtdireccion = new TextField(20);
	Label lbltelefono  = new Label("Telefono:");
	TextField txttelefono = new TextField(20);
	Label lblnombre  = new Label("Nombre:");
	TextField txtnombre = new TextField(20);

	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	ClienteAlta()
	{
		setTitle("ALTA de empleado");
		setLayout(new FlowLayout());
		add(lblDNICli);
		add(txtDNICli);

		add(lbldireccion);
		add(txtdireccion);

		add(lbltelefono);
		add(txttelefono);

		add(lblnombre);
		add(txtnombre);

		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(200,300);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		Object objetoPulsado = e.getSource();
		if(objetoPulsado.equals(btnLimpiar))
		{
			txtDNICli.selectAll();
			txtDNICli.setText("");
			txtDNICli.requestFocus();

			txtdireccion.selectAll();
			txtdireccion.setText("");
			txtdireccion.requestFocus();

			txttelefono.selectAll();
			txttelefono.setText("");
			txttelefono.requestFocus();

			txtnombre.selectAll();
			txtnombre.setText("");
			txtnombre.requestFocus();

		}
		else if(objetoPulsado.equals(btnAceptar))
		{
			// Conectar BD
			Connection con = conectar();
			// Hacer el INSERT
			int respuesta = insertar(con, "clientes", txtDNICli.getText(),txtdireccion.getText(),txttelefono.getText(), txtnombre.getText() );
			// Mostramos resultado
			if(respuesta == 0)
			{
				Log4JCoreJava objLog4JCoreJavaSample = new Log4JCoreJava();
				  objLog4JCoreJavaSample.callMeInAppWarn("Se agrego un campo en Clientes");
				JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
				System.out.println("Cliente agregado");
			}
			else
			{
				System.out.println("Error en ALTA");
			}
			// Desconectar de la base
			desconectar(con);
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

	public int insertar(Connection con, String tabla, String DNI, String direcc, String telefono, String nombre) 
	{
		int respuesta = 0;
		try 
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			String cadenaSQL = "INSERT INTO " + tabla + " (DNICliente,direccionCliente,telefonoCliente,nombreCliente) "
					+ "VALUES ('" + DNI + "','"+ direcc +"','"+ telefono +"','"+ nombre +"')";
			System.out.println(cadenaSQL);
			sta.executeUpdate(cadenaSQL);
			sta.close();
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al hacer un Insert");
			ex.printStackTrace();
			respuesta = 1;
		}
		return respuesta;
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
