package gcgame.game;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import java.util.Random;

import android.os.SystemClock;

public class CMonster extends CBaseEntity
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
	public int monsterWidth = 24;

	
	public CMonster(float pX, float pY, float pTileWidth, float pTileHeight,
			TiledTextureRegion pTiledTextureRegion, int maxHP) 
	{
		super(pX, pY, pTileWidth, pTileHeight, pTiledTextureRegion);
		// TODO Auto-generated constructor stub
	}
	
	public CMonster(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, int maxHP) 
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
		this.dmg         = 5;      //dmg defaults to 0
		this.nextthink   = 0;      //think right away
		this.nextmove    = 0;      //move right away
		this.velocityX   = 0;	   //zero the velocity
		this.velocityY   = 0;      //
		this.speed       = 8.0f;
		this.score = 5;
		monsterType = 1;
	}
	
	
	
	public void think(CPlayer p)
	{
		int randomIndex = (generator.nextInt( 1001 ) % 4);
		
		dir = randomIndex;
		
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
		
		
		this.nextthink = SystemClock.elapsedRealtime() + 3000;
	}
	
	public void move(int TileGrid[][])
	{
		final int playerHeight = monsterHeight;
		final int playerWidth  = monsterWidth;
		final int tileWidth    = 32;
		
		
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
		this.animate(new long[]{200, 200, 200}, 0, 2, true);
		dir = DIR_N;
	}
	public void moveUpRight()
	{
		dir = DIR_NE;
	}
	public void moveRight()
	{
		this.animate(new long[]{200, 200, 200}, 3, 5, true);
		dir = DIR_E;
	}
	public void moveDownRight()
	{
		dir = DIR_SE;
	}
	public void moveDown()
	{
		this.animate(new long[]{200, 200, 200}, 6, 8, true);
		dir = DIR_S;
	}
	public void moveDownLeft()
	{
		dir = DIR_SW;
	}
	public void moveLeft()
	{
		this.animate(new long[]{200, 200, 200}, 9, 11, true);
		dir = DIR_W;
	}
	public void moveUpLeft()
	{
		dir = DIR_NW;
	}
	
	public void takedamage(int damage)
	{
		if (this.takesdamage)
		{
			this.health -= damage;
		}
		if (this.health <= 0)
		{
			this.isAlive = false;
		}
	}
	
	
}

