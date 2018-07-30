import java.util.Random;
import java.awt.image.*;

public abstract class Agent {
	protected int x; //x coordinate of the agent
	protected int y; //y coordinate of the agent
	protected Random gen = new Random();
	protected int direction;
	protected String type; //h, z, and p
	protected int sightRange;
	
	public abstract int getX();
	public abstract int getY();
	public abstract void lookForward(BufferedImage a, int b);
	public abstract void move(BufferedImage a);
	public abstract String getType();
}
