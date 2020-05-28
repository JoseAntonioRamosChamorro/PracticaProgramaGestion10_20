

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



public class Login extends Frame implements WindowListener{
	private static final long serialVersionUID = 1L;
	Label usua = new Label("Usuario"); 
	Label pass = new Label("Contraseña"); 
	JTextField txusu = new JTextField(21); 
	JPasswordField passwordfiel = new JPasswordField(21); 
	Button acept = new Button("Acceder"); 
	//paneles 
	Panel pn1 = new Panel(); 
	Panel pn2 = new Panel(); 
	//BD
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/panaderiaprog?autoReconnect=true&useSSL=false";
	String login = "root2";
	String password = "12345678A";
	Connection connection = null;
	java.sql.Statement statement = null;
	ResultSet rs = null;

	public Login()  { 	
		setTitle("Login"); 
		setLocation(550, 300); 
		setSize(250, 200); 
		setResizable(false); 
		addWindowListener(this); 
		add(pn1);  
		pn1.add(usua); 
		pn1.add(txusu); 
		pn1.add(pass); 
		pn1.add(passwordfiel); 
		add(pn2, BorderLayout.SOUTH); 
		pn2.add(acept); 
		setVisible(true); 
		//Cargar el Driver
		try

		{
			Class.forName(driver);
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Se ha producido un error al cargar el Driver");
		}
		//Establecer la conexión con la base de datos
		try
		{
			connection = DriverManager.getConnection(url, login, password);
		}
		catch(SQLException e)
		{
			System.out.println("Se produjo un error al conectar a la Base de Datos");
		}
		//Final
		acept.addActionListener(new ActionListener() { 
			@SuppressWarnings("deprecation")
			@Override 
			public void actionPerformed(ActionEvent e) { 
				//hash	sha1	 
				
				MessageDigest md = null;
				try {
					md = MessageDigest.getInstance("SHA1");
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
				md.update(passwordfiel.getText().getBytes());

				byte[] digest = md.digest();
				StringBuffer sb = new StringBuffer();
				for (byte b : digest){
					sb.append(String.format("%02x", b & 0xff));
				}
				String sha1;

				sha1 = sb.toString();
				System.out.println(passwordfiel.getText());
				System.out.println("SHA1 Hash: " + sha1);
				//hash	sha1

				//empezar conexión
				try {
					statement =connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					rs= statement.executeQuery("select idAdmin from adminnistradores"
							+ " where  UsuarioAdmin = '"+txusu.getText()+"' and PassAdmin = '"+sha1+"';");

					boolean entrada = false;
					entrada = rs.next();
					if(entrada)
					{
						JOptionPane.showMessageDialog(null, "Autentificación lograda");

						int pro = rs.getInt("idAdmin");
						Log4JCoreJava objLog4JCoreJavaSample = new Log4JCoreJava();
						  objLog4JCoreJavaSample.callMeInAppInfo(txusu.getText());
						if (pro == 1) {
							
							new Principal();
						}else if(pro ==2){

							new Principalestandar();
						}





						setVisible(false);


					}else {
						JOptionPane.showMessageDialog(null, "Error en la Autentificación");
					}
				} catch (Exception e2) {
					System.out.println("Error");
				}


			} 
		}); 

	}
	public static void main(String[] args) {
		new Login();
		
	}


	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
