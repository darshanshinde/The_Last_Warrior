package gcgame.game;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import java.util.Random;

import android.os.SystemClock;

public class fireball extends CBaseEntity
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

	
	public fireball(float pX, float pY, float pTileWidth, float pTileHeight,
			TiledTextureRegion pTiledTextureRegion, int maxHP) 
	{
		super(pX, pY, pTileWidth, pTileHeight, pTiledTextureRegion);
		// TODO Auto-generated constructor stub
	}
	
	public fireball(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, int maxHP) 
	{
		super(pX, pY, pTiledTextureRegion);
		// TODO Auto-generated constructor stub 
		this.maxHP = maxHP;
		this.setPosition(pX, pY);
		
		spawn();
		
		generator = new Random();
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
		this.health      = maxHP;      //health defaults to max hp
		this.dmg         = 20;      //dmg defaults to 0
		this.nextthink   = 0;      //think right away
		this.nextmove    = 0;      //move right away
		this.velocityX   = 0;	   //zero the velocity
		this.velocityY   = 0;      //
		this.speed       = 8.0f;
		this.score = 5;
		monsterType = 4;
	}
	
	
	
	public void think(CPlayer p)
	{
		if (nextthink < 1)
		{
			this.animate(new long[]{400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400}, 10, 24, false);
			nextthink = SystemClock.elapsedRealtime() + 4000;
		}
		else
		{
			isAlive = false;
			this.setVisible(false);
		}
	}
}	
