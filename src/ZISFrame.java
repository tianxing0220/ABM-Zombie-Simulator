import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A frame to contain and show the Zombie Infection Simulation.
 */
public class ZISFrame extends JFrame implements ActionListener
{
	private CityPanel cityPanel;
	private City city;
	private Timer clock;
		
	public ZISFrame()
	{
		super("Zombie Infection");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit on close

		city = new City(); //make the city
		cityPanel = new CityPanel(); //make the panel
		getContentPane().add(cityPanel); //add to the canvas
		
		pack(); //pack & show
		setVisible(true);
		
		clock = new Timer(10,this); //make a clock to run the simulation
		clock.start(); //start the clock!
	}	

	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		city.tick(); //have the city move everything
		cityPanel.repaint(); //refresh the display
	}

	/**
	 * An inner class that holds a JPanel that can draw the city
	 */
	private class CityPanel extends JPanel
	{
		public CityPanel()
		{
			super(); //so we don't forget anything
			setPreferredSize(new Dimension(City.WIDTH, City.HEIGHT)); //we want to be as large as the City
		}

		public void paintComponent(Graphics g)
		{
			g.drawImage(city.getMap(), 0, 0, null); //draw the city on repaint
		}

	}
	
	public static void main(String[] args)
	{
		new ZISFrame();
	}
}
