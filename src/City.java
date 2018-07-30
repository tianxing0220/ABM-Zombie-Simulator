import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.ArrayList;


/**
 * A city to hold and model the zombie outbreak.
 * Some functionality based on the original simulation, http://kevan.org/proce55ing/zombies/.
 * 
 */
public class City
{
	//constants
	public static final int WIDTH = 300;
	public static final int HEIGHT = 300;
	public static final int POPULATION = 5000;
	public static final Color ROAD_COLOR = Color.BLACK;
	public static final Color WALL_COLOR = Color.GRAY;
	public static final Color HUMAN_COLOR = Color.PINK;
	public static final Color ZOMBIE_COLOR = Color.GREEN;
	public static final Color PANICKED_COLOR = Color.YELLOW;
		
	private BufferedImage map; //hold the drawing of the map
	
	private boolean[][] walls; //a two-dimension array of whether a particular pixel is a wall or not

	private BufferedImage image; //holds the drawing of the whole city (re-used to speed rendering);
	private Graphics2D img2d; //a graphics context of the image that we draw on	
	private Random gen = new Random();
	
	private static ArrayList<Agent> agents = new ArrayList<Agent>(POPULATION); //can be accessed by Agent classes
	
	/**
	 * Creates a new City object with walls, people, and a single zombie...
	 */
	public City()
	{
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB); //initialize the image canvas thing
		img2d = image.createGraphics();

		generateWalls();
		generateAgents(); //put humans and one zombie in the arraylist
	}

	
	/**
	 * A method that creates walls and rooms in the city. In effect initializes the map.
	 */
	public void generateWalls()
	{
		map = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB); //our current drawing
		Graphics2D mapg2d = map.createGraphics();
		mapg2d.setPaint(ROAD_COLOR);
		mapg2d.fillRect(0, 0, WIDTH, HEIGHT);
		mapg2d.setPaint(WALL_COLOR); //default color
		mapg2d.fillRect(0, 0, WIDTH, HEIGHT); //empty the city
		
		mapg2d.setPaint(ROAD_COLOR);
		//draw some "rooms"
		for(int i=0; i<50; i++)
			mapg2d.fillRect(gen.nextInt(WIDTH), gen.nextInt(HEIGHT), 20+gen.nextInt(40), 20+gen.nextInt(20));

		//draw some "hallways"
		for(int i=0; i<100; i++)
			mapg2d.drawRect(gen.nextInt(WIDTH), gen.nextInt(HEIGHT), 10+gen.nextInt(60), 10+gen.nextInt(60));

		//add a border around the edge, so people don't wander outside
		mapg2d.setPaint(WALL_COLOR);
		mapg2d.drawRect(0,0,WIDTH-1,HEIGHT-1);

		walls = new boolean[WIDTH][HEIGHT];

		//go through the image and calculate walls (for speed later)
		int wallRGB = WALL_COLOR.getRGB(); //we use color to determine if there is a wall
		for(int i=0; i<WIDTH; i++)
		{
			for(int j=0; j<HEIGHT; j++)
			{
				if(map.getRGB(i,j)==wallRGB)
					walls[i][j] = true;
			}
		}
	}
	
	/**
	 * Renders and returns an image of the current state of the city (map and people)
	 * @return an Image of the city that can be drawn somewhere.
	 */
	public Image getMap()
	{
		img2d.drawImage(map, 0, 0, null); //draw the map onto this new image. Will overwrite old stuff, so creates a new frame
		
		//TODO Draw the humans and the zombies on the map. Note that you can add them directly to the 'image' canvas using the setRGB() method of the BufferedImage class.

		return image;
	}
	
	public void generateAgents()
	{
		for(int i = 0; i<POPULATION; i++) 
		{
			Agent temp = new Human();
			//this loop makes sure that all agents are located in the right area
			while(map.getRGB(temp.getX(), temp.getY())==WALL_COLOR.getRGB())
			{
				temp = new Human();
			}
			agents.add(temp);
			//after adding the agents to the arraylist, print them out.
			map.setRGB(temp.getX(), temp.getY(), HUMAN_COLOR.getRGB());
		}
		Agent temp = new Zombie();
		//makes sure the zombie is located in the right area
		while(map.getRGB(temp.getX(), temp.getY())==WALL_COLOR.getRGB())
		{
			temp = new Zombie();
		}
		agents.add(temp); //start with one zombie
	}
	public static ArrayList<Agent> getAgents() //get method for giving the agent classes access to this arraylist
	{
		return agents;
	}
	public void tick()
	{
		for(Agent i : agents)
		{
			map.setRGB(i.getX(), i.getY(), ROAD_COLOR.getRGB()); //makes sure that the moving agents don't draw lines, but dots instead.
			i.move(map);
			
			if(i.getType().equals("h")) 
			{
				map.setRGB(i.getX(), i.getY(), HUMAN_COLOR.getRGB());
			}
			else if(i.getType().equals("p"))
			{
				map.setRGB(i.getX(), i.getY(), PANICKED_COLOR.getRGB());
			}
			else
			{
				map.setRGB(i.getX(), i.getY(), ZOMBIE_COLOR.getRGB());
				
			}
		}
	}
	
}
