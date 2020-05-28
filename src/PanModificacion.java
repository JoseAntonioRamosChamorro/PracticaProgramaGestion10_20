

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

public class PanModificacion extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	Label lblPan = new Label("Pan a Modificar:");
	Choice choPan = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Button btncompl = new Button("Completar");
	Dialog seguro;
	Button btnSi;
	Button btnNo;

	Label lblPrecio = new Label("Precio Pan:");
	TextField txtPrecio = new TextField(20);
	Label hueco = new Label("                                     ");
	Label lblTipo = new Label("Tipo Pan:");
	TextField txtTipo = new TextField(20);

	PanModificacion()
	{
		setTitle("Modificar Pan");
		setLayout(new FlowLayout());
		// Montar el Choice
		choPan.add("Seleccionar uno...");
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM Pan";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) 
			{
				choPan.add(rs.getInt("idPan")+
						"-"+rs.getString("precioPan")+
						", "+rs.getString("tipoPan"));
			}
			//terminar de montar

			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		//rellenar
	
		//desconectar
		desconectar(con);
		add(choPan);
		add(lblPrecio);
		add(txtPrecio);
		add(hueco);
		add(lblTipo);
		add(txtTipo);
		add(btnAceptar);
		add(btnLimpiar);
		add(btnAceptar);
		add(btnLimpiar);
		add(btncompl);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(200,300);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		btncompl.addActionListener(new ActionListener() {
			Connection con = conectar();
			@Override
			public void actionPerformed(ActionEvent e) {				
				txtPrecio.selectAll();
				txtPrecio.setText("");
				txtPrecio.requestFocus();
				txtTipo.selectAll();
				txtTipo.setText("");
				txtTipo.requestFocus();
				String[] Pan=choPan.getSelectedItem().split("-");
				 CompletarPrecio(con, Integer.parseInt(Pan[0]),txtPrecio);
				 Completartipo(con, Integer.parseInt(Pan[0]),txtTipo);
			}

			private void Completartipo(Connection con2, int idPan, TextField txtTipo) {
				String sqlSelect = "SELECT * FROM pan WHERE idPan =" + idPan;
				try {
					// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
					Statement stmt = con2.createStatement();
					ResultSet rs = stmt.executeQuery(sqlSelect);
					while (rs.next()) 
					{
						if(txtTipo.getText().length()==0)
						{
							txtTipo.setText(rs.getString("tipoPan"));
						}
						else
						{
							txtTipo.setText(txtPrecio.getText() + "\n" 
									+rs.getString("tipoPan"));
						}
					}
					rs.close();
					stmt.close();
				} catch (SQLException ex) {
					System.out.println("ERROR:al consultar");
					ex.printStackTrace();
				}
				
			}

			private void CompletarPrecio(Connection con, int idPan, TextField txtPrecio) {
				String sqlSelect = "SELECT * FROM pan WHERE idPan =" + idPan;
				try {
					// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sqlSelect);
					while (rs.next()) 
					{
						if(txtPrecio.getText().length()==0)
						{
							txtPrecio.setText(rs.getString("precioPan")+"€");
						}
						else
						{
							txtPrecio.setText(txtPrecio.getText() + "\n" 
									+rs.getString("precioPan")+"€");
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
	}



	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		Object objetoPulsado = e.getSource();
		if(objetoPulsado.equals(btnLimpiar))
		{
			txtPrecio.selectAll();
			txtPrecio.setText("");
			txtPrecio.requestFocus();
			txtTipo.selectAll();
			txtTipo.setText("");
			txtTipo.requestFocus();
			choPan.select(0);
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
			String[] Pan=choPan.getSelectedItem().split("-");
			int respuesta = modificar(con, Integer.parseInt(Pan[0]),txtPrecio.getText(), txtTipo.getText());
			// Mostramos resultado
			if(respuesta == 0)
			{
				System.out.println("Pan Modificado");
			}
			else
			{
				System.out.println("Error en la modificación");
			}
			// Actualizar el Choice
			choPan.removeAll();
			choPan.add("Seleccionar uno...");
			String sqlSelect = "SELECT * FROM Pan";
			try {
				// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sqlSelect);
				while (rs.next()) 
				{
					choPan.add(rs.getInt("idPan")+
							"-"+rs.getString("precioPan")+
							", "+rs.getString("tipoPan"));

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
	public int modificar(Connection con, int idPan, String precio, String tipo)
	{
		int respuesta = 0;
		String sql = "update Pan set precioPan='"+precio+"',tipoPan='"+tipo+"' where idPan='"+idPan+"'";
		System.out.println(sql);
		try 
		{
			Log4JCoreJava objLog4JCoreJavaSample = new Log4JCoreJava();
			  objLog4JCoreJavaSample.callMeInAppWarn("Se hizo una modificacion en Pan");
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
