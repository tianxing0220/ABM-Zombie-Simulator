import java.awt.image.BufferedImage;

public class Zombie extends Agent{
	private int turnCount; //used for speed, same idea as human, but it moves only once in 5 turns
	private int turnCount2; // determine the 10 steps a zombie takes pursuing a particular target
	public Zombie()
	{
		x = gen.nextInt(City.HEIGHT);
		y = gen.nextInt(City.WIDTH);
		direction = gen.nextInt(4);
		type = "z";
		sightRange = 10;
		turnCount = 1;
		turnCount2 = 10; //10 is not pursuing the set target, less than 10 is pursuing.
	}
	public Zombie(Agent a) //when a human turns into a zombie, this constructor keeps the location info from the human
	{
		this.x = a.x;
		this.y = a.y;
		this.direction = a.direction;
		type = "z";
		sightRange = 10;
		turnCount = 1;
		turnCount2 = 10;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public String getType()
	{
		return type;
	}
	public void bite(int a) //changes the human into a zombie when next to it, a is direction
	{
		if(a==0)
		{
			for(int i = 0; i<City.getAgents().size(); i++)
			{
				if(City.getAgents().get(i).getY()==y&&City.getAgents().get(i).getX()==x-1)
				{
					City.getAgents().set(i, new Zombie(City.getAgents().get(i))); 
					//using the set method to modify the arryalist used in City Class
				}
			}
		}
		else if(a == 1)
		{
			for(int i = 0; i<City.getAgents().size(); i++)
			{
				if(City.getAgents().get(i).getY()==y&&City.getAgents().get(i).getX()==x+1)
				{
					City.getAgents().set(i, new Zombie(City.getAgents().get(i)));
				}
			}
		}
		else if(a ==2 )
		{
			for(int i = 0; i<City.getAgents().size(); i++)
			{
				if(City.getAgents().get(i).getY()==y-1&&City.getAgents().get(i).getX()==x)
				{
					City.getAgents().set(i, new Zombie(City.getAgents().get(i)));
				}
			}
		}
		else
		{
			for(int i = 0; i<City.getAgents().size(); i++)
			{
				if(City.getAgents().get(i).getY()==y+1&&City.getAgents().get(i).getX()==x)
				{
					City.getAgents().set(i, new Zombie(City.getAgents().get(i)));
				}
			}
		}
	}
	public void lookForward(BufferedImage a, int b)
	{
		int i = 0; //index counter, controls the while loop
		if(b==0) //if facing west
		{
			while(i<sightRange)
			{
				if(x-i>=0&&x-i+1<City.WIDTH) //if in bound
				{
					//if a zombie sees a human or a panicked human
					if(a.getRGB(x-i, y)==City.HUMAN_COLOR.getRGB()||a.getRGB(x-i, y)==City.PANICKED_COLOR.getRGB())
					{
						turnCount2 =0; //start pursuing (counting 10 steps)
						for(Agent item : City.getAgents()) //in the arraylist, look for the specific target, and follow it
						{
							if(item.getX()==x-i&&item.getY()==y) //set the target
							{
								direction = item.direction; //pursue that human with the same direction
							}
						}
						i = sightRange; //get out of the loop
					}
				}
				else //if not in bound, turn around
				{
					int temp = direction;
					while(temp == direction)
					{
						direction = gen.nextInt(4);
					}
				}
				i++;
			}
			x--; //move
			turnCount = 1; //reset the speed count
			turnCount2++;
		}
		else if(b==1) //if facing east
		{
			while(i<sightRange)
			{
				if(x+i>=0&&x+i+1<City.WIDTH)
				{
					if(a.getRGB(x+i, y)==City.HUMAN_COLOR.getRGB()||a.getRGB(x+i, y)==City.PANICKED_COLOR.getRGB())
					{
						turnCount2 =0; //start pursuing
						for(Agent item : City.getAgents())
						{
							if(item.getX()==x+i&&item.getY()==y) //set the target
							{
								direction = item.direction; //pursue that human
							}
						}
						i = sightRange;
					} 
					
				}
				else
				{
					int temp = direction;
					while(temp == direction)
					{
						direction = gen.nextInt(4);
					}
				}
				i++;
			}
			x++; //move
			turnCount = 1; //reset the speed count
			turnCount2++;
		}
		else if(b==2) //if facing north
		{
			while(i<sightRange)
			{
				if(y-i>=0&&y-i+1<City.WIDTH)
				{
					if(a.getRGB(x, y-i)==City.HUMAN_COLOR.getRGB()||a.getRGB(x, y-i)==City.PANICKED_COLOR.getRGB())
					{
						turnCount2 = 0;
						i = sightRange;
						for(Agent item : City.getAgents())
						{
							if(item.getX()==x&&item.getY()==y-i)
							{
								direction = item.direction;
							}
						}
					}
				}
				else
				{
					int temp = direction;
					while(temp == direction)
					{
						direction = gen.nextInt(4);
					}
				}
				i ++;
			}
			y--; //move
			turnCount = 1; //reset the speed count
			turnCount2++;
		}
		else //if facing south
		{
			while(i<sightRange)
			{
				if(y+i>=0&&y+i+1<City.WIDTH)
				{
					if(a.getRGB(x, y+i)==City.HUMAN_COLOR.getRGB()||a.getRGB(x, y+i)==City.PANICKED_COLOR.getRGB())
					{
						turnCount2=0;
						i = sightRange;
						for(Agent item : City.getAgents())
						{
							if(item.getX()==x&&item.getY()==y+i)
							{
								direction = item.direction;
							}
						}
					}
				}
				else
				{
					int temp = direction;
					while(temp == direction)
					{
						direction = gen.nextInt(4);
					}
				}
				i ++;
			}
			y++; //move
			turnCount = 1; //reset the speed count
			turnCount2++;
		}
	}
	
	public void move(BufferedImage a) //uses the lookFoward() and bite() methods
	{
		if(x>=0&&x+1<City.WIDTH&&y>=0&&y+1<City.HEIGHT) //if in bound
		{
			if(turnCount2>=10) //if been pursuing for 10 turns
			{
				direction = gen.nextInt(4); //reset direction, clear the target
			}
			if(turnCount>=5) //moves once every 5 turns
			{
				if(direction == 0)
				{
					if(a.getRGB(x-1, y)!=City.WALL_COLOR.getRGB()) //if not blocked
					{
						if(a.getRGB(x-1, y)!=City.HUMAN_COLOR.getRGB()) //and if not next to a human
						{
							lookForward(a, 0); //check to see if a zombie can find one
						}
						else //if a zombie is next to a human
						{
							bite(0); 
						}
					}
					else
					{
						int temp = direction;
						while(direction==temp)
						{
							direction = gen.nextInt(4);
						}
					}
				}
				else if(direction==1)
				{
					if(a.getRGB(x+1, y)!=City.WALL_COLOR.getRGB())
					{
						if(a.getRGB(x+1, y)!=City.HUMAN_COLOR.getRGB())
							{
							lookForward(a, 1);
							}
						else
						{
							bite(1);
						}
					}
					else
					{
						int temp = direction;
						while(direction==temp)
						{
							direction = gen.nextInt(4);
						}
					}
				}
				else if(direction==2)
				{
					if(a.getRGB(x, y-1)!=City.WALL_COLOR.getRGB())
					{
						if(a.getRGB(x, y-1)!=City.HUMAN_COLOR.getRGB())
						{
							lookForward(a, 2);
						}
						else
						{
							bite(2);
						}
					}
					
					else
					{
						int temp = direction;
						while(direction==temp)
						{
							direction = gen.nextInt(4);
						}
					}
				}
				else
				{
					if(a.getRGB(x, y+1)!=City.WALL_COLOR.getRGB())
					{
						if(a.getRGB(x, y+1)!=City.HUMAN_COLOR.getRGB())
							{
							lookForward(a, 3);
							}
						else
						{
							bite(3);
						}
					}
					else
					{
						int temp = direction;
						while(direction==temp)
						{
							direction = gen.nextInt(4);
						}
					}
				}
			}
			else
			{
				turnCount ++;
			}
			}
		}
}
