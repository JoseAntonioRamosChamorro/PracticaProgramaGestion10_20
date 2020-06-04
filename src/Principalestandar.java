

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.text.ParseException;

public class Principalestandar extends Frame implements WindowListener{
	private static final long serialVersionUID = 1L;
	MenuBar MB = new MenuBar();

	Menu mPan = new Menu("Pan");
	Menu mnCliente = new Menu("Clientes");
	Menu mFactura = new Menu("Facturas");

	MenuItem mPanAlta = new MenuItem("Alta");
	;

	MenuItem mnClienteAlta = new MenuItem("Alta");


	MenuItem FAlta = new MenuItem("Alta");

	Button ayuda = new Button("Ayuda");
	public Principalestandar() { 
		setTitle("Panaderia");
		addWindowListener(this);
		setLayout(new FlowLayout());
		mPan.add(mPanAlta);


		mnCliente.add(mnClienteAlta);


		mFactura.add(FAlta);


		MB.add(mPan);
		MB.add(mnCliente);
		MB.add(mFactura);
		setMenuBar(MB);
		setLocation(550, 300); 
		setSize(200,200);
		setResizable(false);
		setVisible(true);
		add(ayuda);
		ayuda.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					Runtime.getRuntime().exec("hh.exe AyudaUsu.chm");
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}

			}
		});
		//Panes
		mPanAlta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PanAlta();

			}
		});


		//Clientes
		mnClienteAlta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ClienteAlta();

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
