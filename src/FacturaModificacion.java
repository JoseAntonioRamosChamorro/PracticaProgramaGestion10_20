

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

public class FacturaModificacion extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	Label lblPan = new Label("Factura a Modificar:");
	Choice chofac = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Button btncompl = new Button("Completar");
	Dialog seguro;
	Button btnSi;
	Button btnNo;

	Label lblFecha  = new Label("Fecha          ");
	TextField txtFecha = new TextField("yyyy/mm/dd",20);
	Label lblimporte  = new Label("importeTotal");
	TextField txtimporte = new TextField(20);

	FacturaModificacion()
	{
		setTitle("Modificar Factura");
		setLayout(new FlowLayout());
		// Montar el Choice
		chofac.add("Seleccionar uno...");
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM facturas";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) 
			{
				chofac.add(rs.getInt("idFacturas")+
						"-"+rs.getString("fechaFactura")+
						", "+rs.getString("importeTotalFactura")+"€, DNI del Cliente"+
						": "+rs.getInt("idClienteFK"));
			
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		// Cerrar la conexión
		desconectar(con);
		add(chofac);
		add(lblFecha);
		add(txtFecha);

		add(lblimporte);
		add(txtimporte);

	
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
		setVisible(true);
		
		
		btncompl.addActionListener(new ActionListener() {
			Connection con = conectar();
			
			public void actionPerformed(ActionEvent e) {
				txtFecha.selectAll();
				txtFecha.setText("");
				txtFecha.requestFocus();
				
				txtimporte.selectAll();
				txtimporte.setText("");
				txtimporte.requestFocus();
				String[] Factura=chofac.getSelectedItem().split("-");
				CompletarPrecio(con, Integer.parseInt(Factura[0]),txtFecha);
				 Completartipo(con, Integer.parseInt(Factura[0]),txtimporte);
				
			}

			private void CompletarPrecio(Connection con2, int parseInt, TextField txtFecha) {
				String sqlSelect = "SELECT * FROM facturas WHERE idFacturas =" + parseInt;
				try {
					// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sqlSelect);
					while (rs.next()) 
					{
						if(txtFecha.getText().length()==0)
						{
							txtFecha.setText(rs.getString("fechaFactura"));
						}
						else
						{
							txtFecha.setText(txtFecha.getText() + "\n" 
									+rs.getString("fechaFactura"));
						}
					}
					rs.close();
					stmt.close();
				} catch (SQLException ex) {
					System.out.println("ERROR:al consultar");
					ex.printStackTrace();
				}
			}

			private void Completartipo(Connection con2, int parseInt, TextField txtimporte) {
				String sqlSelect = "SELECT * FROM facturas WHERE idFacturas =" + parseInt;
				try {
					// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sqlSelect);
					while (rs.next()) 
					{
						if(txtimporte.getText().length()==0)
						{
							txtimporte.setText(rs.getString("importeTotalFactura"));
						}
						else
						{
							txtimporte.setText(txtimporte.getText() + "\n" 
									+rs.getString("importeTotalFactura"));
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
			txtFecha.selectAll();
			txtFecha.setText("yyyy/mm/dd");
			txtFecha.requestFocus();
			
			txtimporte.selectAll();
			txtimporte.setText("");
			txtimporte.requestFocus();
			
			chofac.select(0);
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
			String[] fact=chofac.getSelectedItem().split("-");
			int respuesta = modificar(con,Integer.parseInt(fact[0]), txtFecha.getText(),txtimporte.getText());
			// Mostramos resultado
			if(respuesta == 0)
			{
				System.out.println("Factura Modificado");
			}
			else
			{
				System.out.println("Error en la modificación");
			}
			// Actualizar el Choice
			chofac.removeAll();
			chofac.add("Seleccionar uno...");
			String sqlSelect = "SELECT * FROM facturas";
			try {
				// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sqlSelect);
				while (rs.next()) 
				{
					chofac.add(rs.getInt("idFactura")+
							"-"+rs.getString("fechaFactura")+
							", "+rs.getString("importeTotalFactura")+"€, DNI del Cliente"+
							": "+rs.getString("idClienteFK"));
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
	public int modificar(Connection con, int idFac, String txtFecha, String txtimporte)
	{
		int respuesta = 0;
		String sql = "update Facturas set fechaFactura = '"+txtFecha+"'"
				+ ", importeTotalFactura = '"+txtimporte+"' where idFactura = '"+idFac+"'";
		System.out.println(sql);
		try 
		{
			Log4JCoreJava objLog4JCoreJavaSample = new Log4JCoreJava();
			  objLog4JCoreJavaSample.callMeInAppWarn("Se hizo una modificacion en Facturas");
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
