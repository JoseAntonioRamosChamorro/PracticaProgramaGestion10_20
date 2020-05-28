

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;

public class Principal extends Frame implements WindowListener{
	private static final long serialVersionUID = 1L;
	MenuBar MB = new MenuBar();

	Menu mPan = new Menu("Pan");
	Menu mnCliente = new Menu("Clientes");
	Menu mFactura = new Menu("Facturas");
		
	MenuItem mPanAlta = new MenuItem("Alta");
	MenuItem mPanBaja = new MenuItem("Baja");
	MenuItem mPanConsulta = new MenuItem("Consulta");
	MenuItem mPanModificar = new MenuItem("Modificación");

	MenuItem mnClienteAlta = new MenuItem("Alta");
	MenuItem mnClienteBaja = new MenuItem("Baja");
	MenuItem mnClienteConsulta = new MenuItem("Consulta");
	MenuItem mnClienteModifi = new MenuItem("Modificación");
	
	MenuItem FAlta = new MenuItem("Alta");
	MenuItem FBaja = new MenuItem("Baja");
	MenuItem FConsulta = new MenuItem("Consulta");
	MenuItem FModi= new MenuItem("Modificación");

	public Principal() { 
		setTitle("Panaderia");
		addWindowListener(this);
		setLayout(new FlowLayout());
		mPan.add(mPanAlta);
		mPan.add(mPanBaja);
		mPan.add(mPanConsulta);
		mPan.add(mPanModificar);

		mnCliente.add(mnClienteAlta);
		mnCliente.add(mnClienteBaja);
		mnCliente.add(mnClienteConsulta);
		mnCliente.add(mnClienteModifi);

		mFactura.add(FAlta);
		mFactura.add(FBaja);
		mFactura.add(FConsulta);
		mFactura.add(FModi);

		MB.add(mPan);
		MB.add(mnCliente);
		MB.add(mFactura);
		setMenuBar(MB);
		setLocation(550, 300); 
		setSize(200,200);
		setResizable(false);
		setVisible(true);
		//Panes
		mPanAlta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PanAlta();

			}
		});
		mPanBaja.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PanBaja();

			}
		});
		mPanConsulta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PanConsulta();

			}
		});
		mPanModificar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PanModificacion();

			}
		});
		//Clientes
		mnClienteAlta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ClienteAlta();
				
			}
		});
		mnClienteConsulta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			new	ClienteConsulta();
				
			}
		});
		mnClienteBaja.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ClienteBaja();
				
			}
		});
		mnClienteModifi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ClienteModificacion();
				
			}
		});
		FAlta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new FacturaAlta();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		FBaja.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new FacturaBaja();
			}
		});
		FConsulta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new FacturaConsulta();
			}
		});
		FModi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new FacturaModificacion();
				
			}
		});
	}


	@Override
	public void windowActivated(WindowEvent e) {
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
