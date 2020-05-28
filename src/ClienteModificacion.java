

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteModificacion extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	Label lblPan = new Label("Cliente a Modificar:");
	Choice choClient = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Button btncompl = new Button("Completar");
	Dialog seguro;
	Button btnSi;
	Button btnNo;

	Label lblDNICli = new Label("DNI:          ");
	TextField txtDNICli = new TextField(20);
	Label lbldireccion  = new Label("Direccion:");
	TextField txtdireccion = new TextField(20);
	Label lbltelefono  = new Label("Telefono:");
	TextField txttelefono = new TextField(20);
	Label lblnombre  = new Label("Nombre:");
	TextField txtnombre = new TextField(20);

	ClienteModificacion()
	{
		setTitle("Modificar Cliente");
		setLayout(new FlowLayout());
		// Montar el Choice
		choClient.add("Seleccionar uno...");
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM clientes";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) 
			{
				choClient.add(rs.getInt("DNICliente")+
						"-"+rs.getString("direccionCliente")+
						", "+rs.getString("telefonoCliente")+
						", "+rs.getString("nombreCliente"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		// Cerrar la conexión
		desconectar(con);
		add(choClient);
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
		add(btnAceptar);
		add(btnLimpiar);
		add(btncompl);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(300,250);
		setResizable(false);
		setLocationRelativeTo(null);
		btncompl.addActionListener(new ActionListener() {
			Connection con = conectar();
			@Override
			public void actionPerformed(ActionEvent e) {
				
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
				String[] Client=choClient.getSelectedItem().split("-");
				 CompletarDNI(con, Integer.parseInt(Client[0]),txtDNICli);
				 CompletarDireccion(con, Integer.parseInt(Client[0]),txtdireccion);
				 CompletarTelefono(con, Integer.parseInt(Client[0]),txttelefono);
				 CompletarNombre(con, Integer.parseInt(Client[0]),txtnombre);
				
			}
			private void CompletarNombre(Connection con2, int parseInt, TextField txtnombre) {
				String sqlSelect = "SELECT * FROM Clientes WHERE DNICliente =" + parseInt;
				try {
					// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sqlSelect);
					while (rs.next()) 
					{
						if(txtnombre.getText().length()==0)
						{
							txtnombre.setText(rs.getString("nombreCliente"));
						}
						else
						{
							txtnombre.setText(txtnombre.getText() + "\n" 
									+rs.getString("nombreCliente"));
						}
					}
					rs.close();
					stmt.close();
				} catch (SQLException ex) {
					System.out.println("ERROR:al consultar");
					ex.printStackTrace();
				}
			}
			private void CompletarTelefono(Connection con2, int parseInt, TextField txttelefono) {
				String sqlSelect = "SELECT * FROM Clientes WHERE DNICliente =" + parseInt;
				try {
					// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sqlSelect);
					while (rs.next()) 
					{
						if(txttelefono.getText().length()==0)
						{
							txttelefono.setText(rs.getString("telefonoCliente"));
						}
						else
						{
							txttelefono.setText(txttelefono.getText() + "\n" 
									+rs.getString("telefonoCliente"));
						}
					}
					rs.close();
					stmt.close();
				} catch (SQLException ex) {
					System.out.println("ERROR:al consultar");
					ex.printStackTrace();
				}
			}
			private void CompletarDireccion(Connection con2, int parseInt, TextField txtdireccion) {
				String sqlSelect = "SELECT * FROM Clientes WHERE DNICliente =" + parseInt;
				try {
					// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sqlSelect);
					while (rs.next()) 
					{
						if(txtdireccion.getText().length()==0)
						{
							txtdireccion.setText(rs.getString("direccionCliente"));
						}
						else
						{
							txtdireccion.setText(txtdireccion.getText() + "\n" 
									+rs.getString("direccionCliente"));
						}
					}
					rs.close();
					stmt.close();
				} catch (SQLException ex) {
					System.out.println("ERROR:al consultar");
					ex.printStackTrace();
				}
			}
			private void CompletarDNI(Connection con2, int parseInt, TextField txtDNICli) {
				String sqlSelect = "SELECT * FROM Clientes WHERE DNICliente =" + parseInt;
				try {
					// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sqlSelect);
					while (rs.next()) 
					{
						if(txtDNICli.getText().length()==0)
						{
							txtDNICli.setText(rs.getString("DNICliente"));
						}
						else
						{
							txtDNICli.setText(txtDNICli.getText() + "\n" 
									+rs.getString("DNICliente"));
						}
					}
					rs.close();
					stmt.close();
				} catch (SQLException ex) {
					System.out.println("ERROR:al consultar");
					ex.printStackTrace();
				}
			}
		});
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
			choClient.select(0);
		}
		else if(objetoPulsado.equals(btnAceptar))
		{
			seguro = new Dialog(this,"¿Seguro?", true);
			btnSi = new Button("Sí");
			btnNo = new Button("No");
			Label lblEtiqueta = new Label("¿Está seguro de modificar?");
			seguro.setLayout(new FlowLayout());
			seguro.setSize(120,100);
			btnSi.addActionListener(this);
			btnNo.addActionListener(this);
			seguro.add(lblEtiqueta);
			seguro.add(btnSi);
			seguro.add(btnNo);
			seguro.addWindowListener(this);
			seguro.setResizable(false);
			seguro.setLocationRelativeTo(null);
			seguro.setVisible(true);
		}
		else if(objetoPulsado.equals(btnNo))
		{
			seguro.setVisible(false);
		}
		else if(objetoPulsado.equals(btnSi))
		{
			// Conectar a BD
			Connection con = conectar(); 
			// Borrar
			String[] Clin=choClient.getSelectedItem().split("-");
			int respuesta = modificar(con,Integer.parseInt(Clin[0]), txtDNICli.getText(),txtdireccion.getText(),
					txttelefono.getText(), txtnombre.getText() );
			// Mostramos resultado
			if(respuesta == 0)
			{
				System.out.println("Cliente Modificado");
			}
			else
			{
				System.out.println("Error en la modificación");
			}
			// Actualizar el Choice
			choClient.removeAll();
			choClient.add("Seleccionar uno...");
			String sqlSelect = "SELECT * FROM clientes";
			try {
				// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sqlSelect);
				while (rs.next()) 
				{
					choClient.add(rs.getInt("DNICliente")+
							"-"+rs.getString("direccionCliente")+
							", "+rs.getString("telefonoCliente")+
							", "+rs.getString("nombreCliente"));
				}
				rs.close();
				stmt.close();
			} catch (SQLException ex) {
				System.out.println("ERROR:al consultar");
				ex.printStackTrace();
			}
			// Desconectar
			desconectar(con);
			seguro.setVisible(false);
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
		if(this.isActive())
		{
			setVisible(false);
		}
		else
		{
			seguro.setVisible(false);
		}
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
	public int modificar(Connection con, int idPanch, String txtDNICli, String txtdireccion, String txttelefono, String txtnombre)
	{
		int respuesta = 0;
		String sql = "update clientes set DNICliente ='"+txtDNICli+"', direccionCliente = '"+txtdireccion+"'"
				+ ", telefonoCliente = '"+txttelefono+"', nombreCliente = '"+txtnombre+"' where DNICliente = '"+idPanch+"'";
		System.out.println(sql);
		try 
		{
			Log4JCoreJava objLog4JCoreJavaSample = new Log4JCoreJava();
			  objLog4JCoreJavaSample.callMeInAppWarn("Se hizo una modificacion en Clientes");
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			sta.executeUpdate(sql);
			sta.close();
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al hacer un Update");
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
