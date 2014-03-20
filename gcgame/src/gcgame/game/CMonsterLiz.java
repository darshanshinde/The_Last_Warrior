package gcgame.game;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import java.util.Random;

import android.os.SystemClock;

public class CMonsterLiz extends CBaseEntity
{
	Random generator; 
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

	
	int dir;             //direction player is facing
	
	
	public int maxHP;
	
	public int monsterHeight = 32;
	public int monsterWidth = 32;

	
	long attacktime;
	
	public CMonsterLiz(float pX, float pY, float pTileWidth, float pTileHeight,
			TiledTextureRegion pTiledTextureRegion, int maxHP) 
	{
		super(pX, pY, pTileWidth, pTileHeight, pTiledTextureRegion);
		// TODO Auto-generated constructor stub
	}
	
	public CMonsterLiz(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, int maxHP) 
	{
		super(pX, pY, pTiledTextureRegion);
		// TODO Auto-generated constructor stub 
		this.maxHP = maxHP;
		this.setPosition(pX, pY);
		
		generator = new Random();
	}

	//Methods
	
	
	/*spawn - initialize each of the member variables, should be called upon
	 * entity creation
	 */
	public void spawn()
	{
		this.takesdamage = true;  //entity is invincible by default
		this.solid       = true;   //is stopped by walls
		this.isAlive     = true;   //alive by default
		this.health      = maxHP;      //health defaults to max hp
		this.dmg         = 10;      //dmg defaults to 0
		this.nextthink   = 0;      //think right away
		this.nextmove    = 0;      //move right away
		this.velocityX   = 0;	   //zero the velocity
		this.velocityY   = 0;      //
		this.speed       = 8.0f;
		this.score = 10;
		this.inAttack = false;
		this.attacktime = 0;
		monsterType = 2;
	}
	
	
	
	public void think(CPlayer p)
	{
		int randomIndex = (generator.nextInt( 1001 ) % 8);
		
		dir = randomIndex;
		
		if (inAttack)
			return;
		
		if (dir == DIR_N)
		{
			moveUp();
			velocityX = 0.0f;
			velocityY = -0.5f;
		}
		else if (dir == DIR_S)
		{
			moveDown();
			velocityX = 0.0f;
			velocityY = 0.5f;
		}
		else if (dir == DIR_E)
		{
			moveRight();
			velocityX = 0.5f;
			velocityY = 0.0f;
		}
		else if (dir == DIR_W)
		{
			moveLeft();
			velocityX = -0.5f;
			velocityY = 0.0f;
		}
		else if (dir == DIR_NE)
		{
			moveUpRight();
			velocityX = 0.5f;
			velocityY = -0.5f;
		}
		else if (dir == DIR_SE)
		{
			moveDownRight();
			velocityX = 0.5f;
			velocityY = 0.5f;
		}
		else if (dir == DIR_SW)
		{
			moveDownLeft();
			velocityX = 0.5f;
			velocityY = -0.5f;
		}
		else if (dir == DIR_NW)
		{
			moveUpLeft();
			velocityX = -0.5f;
			velocityY = -0.5f;
		}
		
		this.nextthink = SystemClock.elapsedRealtime() + 3000;
	}
	
	public void move(int TileGrid[][])
	{
		final int playerHeight = monsterHeight;
		final int playerWidth  = monsterWidth;
		final int tileWidth    = 32;
		
		
		//handle attack stuff
		if (SystemClock.elapsedRealtime() >= this.attacktime)
		{
			this.inAttack = false;
		}
		
		
		//current player positions
		float curPosX = getX();
		float curPosY = getY();
		
		//new player positions after move (if allowed)
		float newPosX = curPosX + velocityX*speed;
		float newPosY = curPosY + velocityY*speed;
		
		//current grid positions, calculated by dividing curPosX by width of tile
		int curGridX = ((int) (curPosX / tileWidth)); 
		int curGridY = ((int) (curPosY / tileWidth));
		
		//new grid positions, if allowed
		int newGridX; 
		int newGridY;
		
		int newGridFeetX;
		int newGridFeetY;
		
		
		
		//Traveling right, account for sprite size offset
		if (newPosX > curPosX)
		{
			newGridX = ((int) ((newPosX + playerWidth) / tileWidth));
		}
		else
		{
			newGridX = ((int) (newPosX / tileWidth));
		}
		
		//Traveling down, account for player height offset
		if (newPosY > curPosY)
		{
			newGridY = ((int) ((newPosY + playerHeight) / tileWidth)); 
		}
		else
		{
			newGridY = ((int) (newPosY / tileWidth));
		}
		
		newGridFeetX = newGridX;
		newGridFeetY = ((int) ((curPosY + playerHeight) / tileWidth));
		
		//handle player X axis movement
		
		//clamp player to map boundaries (Based on 40x40 tile maps)
		if ( newPosX < 1250 && newPosX > 0 )
		{
			//prevent from going into solid objects 
			/*if ( TileGrid[gXc][gXr] != 1 )
			{
				player.setPosition(player.getX()+pValueX*player.speed, player.getY());
			}*/
			if (curGridX != newGridX)
			{
				if (TileGrid[newGridX][curGridY] != 1)
				{
					if (TileGrid[newGridFeetX][newGridFeetY] != 1)
					{
						setPosition(newPosX, curPosY);
					}
				}
			}
			else
			{
				setPosition(newPosX, curPosY);
			}
			
		}
		
		//handle player Y axis movement
		
		//clamp player to map boundaries (Based on 40x40 tile maps)
		if ( newPosY < 1225 && newPosY > 0 )
		{
			if (curGridY != newGridY)
			{
				if (TileGrid[curGridX][newGridY] != 1)
				{
					setPosition(getX(), newPosY);
				}
			}
			else
			{
				setPosition(getX(), newPosY);
			}
		}
		
		this.nextmove = SystemClock.elapsedRealtime() + moveTime;
	}
	
	
	public void moveUp()
	{
		this.animate(new long[]{400, 400, 400, 400}, 0, 3, true);
		dir = DIR_N;
	}
	public void moveUpRight()
	{
		this.animate(new long[]{400, 400, 400, 400}, 4, 7, true);
		dir = DIR_NE;
	}
	public void moveRight()
	{
		this.animate(new long[]{400, 400, 400, 400}, 8, 11, true);
		dir = DIR_E;
	}
	public void moveDownRight()
	{
		this.animate(new long[]{400, 400, 400, 400}, 12, 15, true);
		dir = DIR_SE;
	}
	public void moveDown()
	{
		this.animate(new long[]{400, 400, 400, 400}, 16, 19, true);
		dir = DIR_S;
	}
	public void moveDownLeft()
	{
		this.animate(new long[]{400, 400, 400, 400}, 20, 23, true);
		dir = DIR_SW;
	}
	public void moveLeft()
	{
		this.animate(new long[]{400, 400, 400, 400}, 24, 27, true);
		dir = DIR_W;
	}
	public void moveUpLeft()
	{
		this.animate(new long[]{400, 400, 400, 400}, 28, 31, true);
		dir = DIR_NW;
	}

	public void takedamage(int damage)
	{
		this.health -= damage;
		
		this.inAttack = true;
		this.attacktime = SystemClock.elapsedRealtime() + 500;
		
		if (this.health <= 0)
		{
			this.isAlive = false;
		}
		
		this.velocityX = 0.0f;
		this.velocityY = 0.0f;
	
		if (dir == DIR_N)
		{
			this.animate(new long[]{400, 400, 400, 400}, 32, 35, true);
		}
		else if (dir == DIR_S)
		{
			this.animate(new long[]{400, 400, 400, 400}, 36, 39, true);
		}
		else if (dir == DIR_E)
		{
			this.animate(new long[]{400, 400, 400, 400}, 40, 43, true);
		}
		else if (dir == DIR_W)
		{
			this.animate(new long[]{400, 400, 400, 400}, 44, 47, true);
		}
		else if (dir == DIR_NE)
		{
			this.animate(new long[]{400, 400, 400, 400}, 48, 51, true);
		}
		else if (dir == DIR_SE)
		{
			this.animate(new long[]{400, 400, 400, 400}, 52, 55, true);
		}
		else if (dir == DIR_SW)
		{
			this.animate(new long[]{400, 400, 400, 400}, 56, 59, true);
		}
		else if (dir == DIR_NW)
		{
			this.animate(new long[]{400, 400, 400, 400}, 60, 63, true);
		}
	}
}

