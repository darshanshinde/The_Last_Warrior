package gcgame.game;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;
import android.os.SystemClock;
import android.util.Log;
//hi

// DS Mar 18, 2011 6:46:23 PM - Test 2, just to check if two way synchronisation works on my end. - Darshan Shinde

public class CBaseEntity extends AnimatedSprite
{
	//constants
	final int moveTime = 50;  //calculate moves every 10ms
	
	
	
	/****************** member variables  *******************************/

	public int monsterHeight;
	public int monsterWidth;
	
	
	//entity flags
	boolean takesdamage; //determines whether or not an entity can be attacked and take damage
	boolean solid;       //determines whether entity will collide with solid structures 
	boolean isAlive;     //should entity be removed? 
	
	boolean inAttack;    //is monster in attack sequence from player hit?
	
	long attacktime;
	long attackdur;
	
	//entity variables
	int health;          //hit points of entity	
	int dmg;             //damage points inflicted 
	float speed;           //speed multiplier
	int score;           //points ent is worth
	
	float velocityX;     //velocity in the x direction
	float velocityY;     //velocity in the y direction
	
	long nextthink;      //time until next think in milliseconds
	long nextmove;       //time until next move in milliseconds
	
	int monsterType;
	
	
	
	/******************************** constructors **************************************/
	public CBaseEntity(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion) {
	
		super(pX, pY, pTiledTextureRegion);
	}

	public CBaseEntity(final float pX, final float pY, final float pTileWidth, final float pTileHeight, final TiledTextureRegion pTiledTextureRegion) {
		super(pX, pY, pTileWidth, pTileHeight, pTiledTextureRegion);
	}

	public CBaseEntity(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, final RectangleVertexBuffer pRectangleVertexBuffer) {
		super(pX, pY, pTiledTextureRegion, pRectangleVertexBuffer);
	}

	public CBaseEntity(final float pX, final float pY, final float pTileWidth, final float pTileHeight, final TiledTextureRegion pTiledTextureRegion, final RectangleVertexBuffer pRectangleVertexBuffer) {
		super(pX, pY, pTileWidth, pTileHeight, pTiledTextureRegion, pRectangleVertexBuffer);
	}
	
	//end constructors
	
	
	/***************************** methods **************************************/
	
	/*spawn - initialize each of the member variables, should be called upon
	 * entity creation
	 */
	public void spawn()
	{
		this.takesdamage = false;  //entity is invincible by default
		this.solid       = true;   //is stopped by walls
		this.isAlive     = true;   //alive by default
		this.health      = 0;      //health defaults to 0
		this.dmg         = 0;      //dmg defaults to 0
		this.nextthink   = 0;      //think right away
		this.nextmove    = 0;      //move right away
		this.velocityX   = 0;	   //zero the velocity
		this.velocityY   = 0;      //
		this.speed       = 1.0f;
		this.score = 5;
		this.inAttack = false;
		
		monsterType = 0;
	}
	
	/* think - called every think duration so entity can run some code and do things
	 * 
	 */
	public void think(CPlayer p)
	{
		//think code
		
		this.nextthink = SystemClock.elapsedRealtime() + 50;

	}
	
	public void move(int TileGrid[][])
	{
		
		
		this.nextmove = SystemClock.elapsedRealtime() + moveTime;
	}
	
	
	/* takedamage - will reduce entities health by 'damage'
	 * 
	 */
	
	public void takedamage(int damage)
	{
		if (this.takesdamage)
		{
			this.health -= damage;
			if (this.health <= 0)
				this.isAlive = false;
		}
	}
	
	
	
	
}
