

import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextArea;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class FacturaAlta extends JFrame  implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	Label lblFecha  = new Label("Fecha:");
	TextField txtFecha = new TextField("dd/mm/yyyy",20);
	Label lblimporte  = new Label("importeTotal:");
	TextField txtimporte = new TextField(20);
	Label lblClient  = new Label("Cliente:");
	Choice choCliente = new Choice();

	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	Choice chProd = new Choice();
	Button btAgregar = new Button("Agregar");
	TextArea lista = new TextArea(5,10);

	FacturaAlta() throws ParseException
	{
		txtimporte.setEditable(false);
		setTitle("ALTA Factura");
		setLayout(new FlowLayout());
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM Clientes";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) 
			{
				choCliente.add(rs.getInt("DNICliente")+
						"-"+rs.getString("nombreCliente"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		// Cerrar la conexión
		rellenarchProducto(con,chProd);

	

		add(lblFecha);
		add(txtFecha);

		add(chProd);
		add(btAgregar);
		add(lista);

		add(lblimporte);
		add(txtimporte);

		add(lblClient);
		add(choCliente);

		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(200,400);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		
		btAgregar.addActionListener(new ActionListener() {
			Connection con = conectar();
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] AgregarLista=chProd.getSelectedItem().split("-");
				agregar(con,Integer.parseInt(AgregarLista[0]), lista,txtimporte);


			}

			private void agregar(Connection con, int idpan, TextArea lista, TextField txtimporte) {

				String sqlSelect = "select * from pan where idPan = " + idpan+";";
				try {

					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sqlSelect);
					while (rs.next()) 
					{
						int total;
						int total2 =0;
						int total3;
						if(lista.getText().length()==0)
						{
							lista.setText(rs.getInt("precioPan")+"€"+
									", "+rs.getString("tipoPan"));

							total = rs.getInt("precioPan");

							total3 = total2 +total;

							String totalfinal = String.valueOf(total3);
							txtimporte.setText(totalfinal);
						}
						else
						{

							lista.setText(lista.getText() + "\n" +
									rs.getInt("precioPan")+"€"+
									", "+rs.getString("tipoPan"));

							String suma1=txtimporte.getText();
							int suma2 = Integer.parseInt(suma1);
							total = rs.getInt("precioPan");
						int suma3 =suma2 + total;
						
						String totalfinal = String.valueOf(suma3);
						txtimporte.setText(totalfinal);
						}
					}

					rs.close();
					stmt.close();
				} catch (SQLException ex) {
					System.out.println("ERROR:al consultar");
					ex.printStackTrace();
				}
			}
		});	desconectar(con);
	}

	private void rellenarchProducto(Connection con, Choice chProd2) {

		String sqlSelect1 = "SELECT * FROM Pan";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect1);
			while (rs.next()) 
			{
				chProd2.add(rs.getInt("idPan")+"-"+rs.getString("tipoPan")+
						"="+rs.getString("precioPan")+"€");
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}desconectar(con);

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		Object objetoPulsado = e.getSource();
		if(objetoPulsado.equals(btnLimpiar))
		{


			txtFecha.selectAll();
			txtFecha.setText("dd/mm/yyyy");
			txtFecha.requestFocus();

			txtimporte.selectAll();
			txtimporte.setText("");
			txtimporte.requestFocus();

			
			lista.selectAll();
			lista.setText("");
			lista.requestFocus();

		}
		else if(objetoPulsado.equals(btnAceptar))
		{
			//String a fecha
			String fecha = txtFecha.getText();
			SimpleDateFormat date = new SimpleDateFormat("dd/mm/yyyy");
			try {
				System.out.println(date.parse(fecha));
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			Date fecha1 = null;
			try {
				fecha1 = date.parse(fecha);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//fecha a String y cambio
			SimpleDateFormat date1 = new SimpleDateFormat("yyyy/mm/dd");
			String fechaTexto = date1.format(fecha1);
			System.out.println(fechaTexto);
			// Conectar BD
			Connection con = conectar();
			// Hacer el INSERT
		
			String[] Clin=choCliente.getSelectedItem().split("-");
			int respuesta = insertar(con,fechaTexto,txtimporte.getText(),Integer.parseInt(Clin[0]) );
			// Mostramos resultado
			if(respuesta == 0)
			{
				Log4JCoreJava objLog4JCoreJavaSample = new Log4JCoreJava();
				  objLog4JCoreJavaSample.callMeInAppWarn("Se agrego un campo en Facturas");
				JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
				System.out.println("Factura agregado");
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

	public int insertar(Connection con, String txtFecha, String txtimporte, int client) 
	{
		int respuesta = 0;
		try 
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			String cadenaSQL = "INSERT INTO Facturas (fechaFactura,importeTotalFactura, idClienteFK) VALUES "
					+ "('"+txtFecha+"', '"+txtimporte+"', '"+client+"')";
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