package gcgame.game;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.os.SystemClock;
import android.util.Log;

public class CPlayer extends AnimatedSprite
{

	//constants
	final int DIR_N  = 0,
			  DIR_S  = 1,
			  DIR_E  = 2,
			  DIR_W  = 3,
			  DIR_NE = 4,
			  DIR_NW = 5,
			  DIR_SE = 6,
			  DIR_SW = 7;
	
	
	/****************** member variables  *******************************/

	//entity flags
	boolean takesdamage; //determines whether or not an entity can be attacked and take damage
	boolean solid;       //determines whether entity will collide with solid structures 
	boolean isAlive;     //should entity be removed?
	boolean inAttack;    //is player attacking
	boolean inMotion;
	
	//entity variables
	int health;          //hit points of entity	
	int dmg;             //damage points inflicted 
	float speed;           //speed multiplier
	
	float velocityX;     //velocity in the x direction
	float velocityY;     //velocity in the y direction
	
	long nextthink;      //time until next think in milliseconds
	long nextmove;       //time until next move in milliseconds
	long nextattack;     //time until next attack in millisecons
	long nextdmg;        //time till player can be damaged again
	
	
	
	int dir;             //direction player is facing
	
	
	public int maxHP;
	public Gauge HP;
	public String name; //player name
	
	//Player name modifier
	public void setName(String theName)
	{
		this.name=theName;
	}
	
	public CPlayer(float pX, float pY, float pTileWidth, float pTileHeight,
			TiledTextureRegion pTiledTextureRegion, int maxHP) {
		super(pX, pY, pTileWidth, pTileHeight, pTiledTextureRegion);
		// TODO Auto-generated constructor stub
		this.HP = new Gauge(maxHP);
		
		//Set the initial direction 
		//- it's SW only b/c the topleft image is SW..but could be changed  
		this.dir = DIR_N;
	}
	
	public CPlayer(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, int maxHP) {
		super(pX, pY, pTiledTextureRegion);
		// TODO Auto-generated constructor stub
		this.HP = new Gauge(maxHP); 
		//Set the initial direction
		this.dir = DIR_N;
	}

	//Methods
	
	
	/*spawn - initialize each of the member variables, should be called upon
	 * entity creation
	 */
	public void spawn()
	{
		this.takesdamage = false;  //entity is invincible by default
		this.solid       = true;   //is stopped by walls
		this.isAlive     = true;   //alive by default
		this.inAttack    = false;
		this.inMotion    = false;
		this.health      = maxHP;      //health defaults to max hp
		this.dmg         = 50;      //dmg defaults to 0
		this.nextthink   = 0;      //think right away
		this.nextmove    = 0;      //move right away
		this.nextdmg = 0;
		this.velocityX   = 0;	   //zero the velocity
		this.velocityY   = 0;      //
		this.speed       = 0.75f;
		this.nextattack = 0; 
		
		
	}
	
	public void takedamage(int dmg)
	{
		if (SystemClock.elapsedRealtime() >= this.nextdmg)
		{
			this.health -= dmg;
			HP.subtract(dmg);
		}
		this.nextdmg = SystemClock.elapsedRealtime() + 2000;
	}

	public void think()
	{
		
		if (SystemClock.elapsedRealtime() >= this.nextattack)
		{
			if (this.inAttack)
			{
				this.inAttack = false;
				this.holdPos(this.dir);
			}
		}
		
		if (velocityX == 0.0f && velocityY ==  0.0f)
		{
		
			this.inMotion = false;
			
			if (!this.inAttack)
			{
				this.holdPos(this.dir);
			}
		}
		else
		{
			inMotion = true;
		}
			
			
		this.nextthink = SystemClock.elapsedRealtime() + 1;
	}
	
	
	public void moveUp()
	{
		if (this.inAttack)
			return;
		if (dir != DIR_N)
			this.animate(new long[]{150, 150, 150, 150}, 0, 3, true);
		dir = DIR_N;
	}
	public void moveUpRight()
	{
		if (this.inAttack)
			return;
		if (dir != DIR_NE)
			this.animate(new long[]{150, 150, 150, 150}, 4, 7, true);
		dir = DIR_NE;
	}
	public void moveRight()
	{
		if (this.inAttack)
			return;
		if (dir != DIR_E)	
			this.animate(new long[]{150, 150, 150, 150}, 8, 11, true);
		dir = DIR_E;
	}
	public void moveDownRight()
	{
		if (this.inAttack)
			return;
		if (dir != DIR_SE)
			this.animate(new long[]{150, 150, 150, 150}, 12, 15, true);
		dir = DIR_SE;
	}
	public void moveDown()
	{
		if (this.inAttack)
			return;
		if (dir != DIR_S)
			this.animate(new long[]{150, 150, 150,150}, 16, 19, true);
		dir = DIR_S;
	}
	public void moveDownLeft()
	{
		if (this.inAttack)
			return;
		if (dir != DIR_SW)
			this.animate(new long[]{150, 150, 150, 150}, 20, 23, true);
		dir = DIR_SW;
	}
	public void moveLeft()
	{
		if (this.inAttack)
			return;
		if (dir != DIR_W)
			this.animate(new long[]{150, 150, 150, 150}, 24, 27, true);
		dir = DIR_W;
	}
	public void moveUpLeft()
	{
		if (this.inAttack)
			return;
		if (dir != DIR_NW)
			this.animate(new long[]{150, 150, 150, 150}, 28, 31, true);
		dir = DIR_NW;
	}
	
	public void holdPos(int direction)
	{
		this.dir = direction;
		
		
		
		if(this.dir == this.DIR_N)
		{
			this.setCurrentTileIndex(0);
		}
		else if(this.dir == this.DIR_NE)
		{
			this.setCurrentTileIndex(4);
		}
		else if(this.dir == this.DIR_E)
		{
			this.setCurrentTileIndex(8);
		}
		else if(this.dir == this.DIR_SE)
		{
			this.setCurrentTileIndex(12);
		}
		else if(this.dir == this.DIR_S)
		{
			this.setCurrentTileIndex(16);
		}
		else if(this.dir == this.DIR_SW)
		{
			this.setCurrentTileIndex(20);
		}
		else if(this.dir == this.DIR_W)
		{
			this.setCurrentTileIndex(24);
		}
		else if(this.dir == this.DIR_NW)
		{
			this.setCurrentTileIndex(28);
		}
		
	
		
	}
	
	public void attack()
	{
		
		if (this.inAttack)
			return;
		
		if(this.dir == this.DIR_N)
		{
			this.animate(new long[]{100, 100, 100, 100}, 32, 35, false);
			//I want the player to stop after the attack 
			//but saying the following wont even let the player attack
			//this.stopAnimation(8);
		}
		else if(this.dir == this.DIR_NE)
		{
			this.animate(new long[]{125, 125, 125, 125}, 36, 39, false);
		}
		else if(this.dir == this.DIR_E)
		{
			this.animate(new long[]{125, 125, 125, 125}, 40, 43, false);
		}
		else if(this.dir == this.DIR_SE)
		{
			this.animate(new long[]{125, 125, 125, 125}, 44, 47, false);
		}
		else if(this.dir == this.DIR_S)
		{
			this.animate(new long[]{125, 125, 125, 125}, 48, 51, false);
		}
		else if(this.dir == this.DIR_SW)
		{
			this.animate(new long[]{125, 125, 125, 125}, 52, 55, false);
		}
		else if(this.dir == this.DIR_W)
		{
			this.animate(new long[]{125, 125, 125, 125}, 56, 59, false);
		}
		else if(this.dir == this.DIR_NW)
		{
			this.animate(new long[]{125, 125, 125, 125}, 60, 63, false);
		}
		
		this.inAttack = true;
		
		this.nextattack = SystemClock.elapsedRealtime() + 500;
		
	}
	
	
}
