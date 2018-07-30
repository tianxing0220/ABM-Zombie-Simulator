
import java.awt.image.*;

/*
 * This Human Class includes the functions for both Human and Panicked Human movement.
 * Since there is no additional PanickedHuman Class, it saves unnecessary programming structure.
 * The different processing for regular and panicked humans is used in the lookForward() and move() methods.
 * 				The private variable String type can help to determine it.
 */
public class Human extends Agent{
	private int turnCount; //makes sure that the humans move every 2 turns in tick()
	private int turnCount2; //makes sure that for the panicked humans, they would calm dowm after 10 steps without seeing a zombie
	public Human()
	{
		x = gen.nextInt(City.WIDTH);
		y = gen.nextInt(City.HEIGHT);
		direction = gen.nextInt(4); //0=west, 1=east, 2=north, 3=south
		type = "h"; //at the beginning all are humans, but it will change
		sightRange = 10; //they can see as far as 10 units a head of them (in one direction)
		turnCount = 1; //determines the speed
		turnCount2 = 10; //10 is calm, less than 10 is panicked
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
		public void lookForward(BufferedImage a, int b) //b is direction 
		{
			int i = 0; //index counter, controls the while loop
			if(b==0) //if facing west
			{
				while(i<sightRange)
				{
					if(x-i>=0&&x-i+1<City.WIDTH) //keeps it in bound
					{
						if(a.getRGB(x-i, y)==City.ZOMBIE_COLOR.getRGB()) //if theres a zombie ahead
						{
							direction = 1;//turn around
							type = "p"; //turns panicked
							turnCount2 =0; //start counting the 10 steps a panicked human would take to be calm again
							i = sightRange; //get out of the loop to start moving right away.
						}
						else if(a.getRGB(x-i, y)==City.PANICKED_COLOR.getRGB())
						{
							type = "p"; 
							turnCount2 ++;
							i = sightRange;
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
					i++; //makes sure that the human can see far ahead if nothing is blocking
				}
				x--; //move
				turnCount = 1; //reset the speed count to 1
				turnCount2++; //one more step taken without seeing a zombie
			}
			else if(b==1) //if facing east
			{
				while(i<sightRange)
				{
					if(x+i>=0&&x+i+1<City.WIDTH)
					{
						if(a.getRGB(x+i, y)==City.ZOMBIE_COLOR.getRGB())
						{
							direction = 0;
							type = "p"; //turns panicked
							turnCount2 =0;
							i = sightRange; //get out of the loop
						}
						else if(a.getRGB(x+i, y)==City.PANICKED_COLOR.getRGB())
						{
							type = "p"; 
							turnCount2 ++;
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
					i ++;
				}
				x ++; //move
				turnCount = 1; //reset the speed count
				turnCount2++;
			}
			else if(b==2) //if facing north
			{
				while(i<sightRange)
				{
					if(y-i>=0&&y-i+1<City.WIDTH)
					{
						if(a.getRGB(x, y-i)==City.ZOMBIE_COLOR.getRGB())
						{
							direction = 0;
							type = "p"; //turns panicked
							turnCount2 =0;
							i = sightRange; //get out of the loop
						}
						else if(a.getRGB(x, y-i)==City.PANICKED_COLOR.getRGB())
						{
							type = "p"; 
							turnCount2 ++;
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
						if(a.getRGB(x, y+i)==City.ZOMBIE_COLOR.getRGB())
						{
							direction = 0;
							type = "p"; //turns panicked
							turnCount2 =0;
							i = sightRange; //get out of the loop
						}
						else if(a.getRGB(x, y+i)==City.PANICKED_COLOR.getRGB())
						{
							type = "p"; 
							turnCount2 ++;
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
					i ++;
				}
				y++; //move
				turnCount = 1; //reset the speed count
				turnCount2++;
			}
		}
		
		public void move(BufferedImage a) //this method calls the lookForward() method
		{
			if(x>=0&&x+1<City.WIDTH&&y>=0&&y+1<City.HEIGHT) //if in bound
			{
				if(turnCount2>=10) //if not panicked after 10 turns 
				{
					type = "h";
				if(turnCount>=2) //humans move every 2 turns in tick()
				{
					if(direction == 0)
					{
						//if there are no walls or humans ahead of the human
						if(a.getRGB(x-1, y)!=City.WALL_COLOR.getRGB()&&a.getRGB(x-1, y)!=City.HUMAN_COLOR.getRGB()) 
						{
							lookForward(a, 0); //check if there are any panicked human or zombies
						}
						else //then turn around
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
						if(a.getRGB(x+1, y)!=City.WALL_COLOR.getRGB()&&a.getRGB(x+1, y)!=City.HUMAN_COLOR.getRGB())
						{
							lookForward(a, 1);
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
						if(a.getRGB(x, y-1)!=City.WALL_COLOR.getRGB()&&a.getRGB(x, y-1)!=City.HUMAN_COLOR.getRGB())
						{
							lookForward(a, 2);
						}
						else
						{
							int temp = direction;
							while(direction==temp)
							{
								direction = gen.nextInt(4);
							}
							//move(a);
						}
					}
					else
					{
						if(a.getRGB(x, y+1)!=City.WALL_COLOR.getRGB()&&a.getRGB(x, y+1)!=City.HUMAN_COLOR.getRGB())
						{
							lookForward(a, 3);
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
					turnCount ++; //if the turnCount is only 1, then don't move on this turn and increment by 1
				}
				}
				else //if the human is panicked
				{
					if(direction == 0)
					{
						if(a.getRGB(x-1, y)!=City.WALL_COLOR.getRGB()&&a.getRGB(x-1, y)!=City.HUMAN_COLOR.getRGB())
						{
							lookForward(a, 0); //check again
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
						if(a.getRGB(x+1, y)!=City.WALL_COLOR.getRGB()&&a.getRGB(x+1, y)!=City.HUMAN_COLOR.getRGB())
						{
							lookForward(a, 1);
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
						if(a.getRGB(x, y-1)!=City.WALL_COLOR.getRGB()&&a.getRGB(x, y-1)!=City.HUMAN_COLOR.getRGB())
						{
							lookForward(a, 2);
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
						if(a.getRGB(x, y+1)!=City.WALL_COLOR.getRGB()&&a.getRGB(x, y+1)!=City.HUMAN_COLOR.getRGB())
						{
							lookForward(a, 3);
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
			}
			else //if not in bound, turn around
			{
				int temp = direction;
				while(direction==temp)
				{
					direction = gen.nextInt(4);
				}
				//move(a);
			}
		}
		}
		
