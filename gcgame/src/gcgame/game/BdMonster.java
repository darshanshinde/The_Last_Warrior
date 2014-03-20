package gcgame.game;


import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import java.util.Random;

import android.os.SystemClock;

public class BdMonster extends CBaseEntity {
	
	
		Random generator; 
		//constants
		final int //DIR_N  = 0,
				  //DIR_S  = 1,
				  DIR_E  = 0,
				  DIR_W  = 1;
				  //DIR_NE = 4,
				  //DIR_NW = 5,
				  //DIR_SE = 6,
				  //DIR_SW = 7;
		
		
		/****************** member variables  *******************************/

		
		int dir;             //direction player is facing
		
		long attacktime;
		
		long attackdur;
		
		public int maxHP;

		public int monsterHeight = 62;
		public int monsterWidth = 87;
		
		
		
		
		public BdMonster(float pX, float pY, float pTileWidth, float pTileHeight,
				TiledTextureRegion pTiledTextureRegion, int maxHP) 
		{
			super(pX, pY, pTileWidth, pTileHeight, pTiledTextureRegion);
			// TODO Auto-generated constructor stub
		}
		
		public BdMonster(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, int maxHP) 
		{
			super(pX, pY, pTiledTextureRegion);
			// TODO Auto-generated constructor stub 
			this.maxHP = maxHP;
			this.setPosition(pX, pY);
			
			generator = new Random();
		}

		//Methods
		
		
		double distance(double x1, double y1, double x2, double y2)
		{
			return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		}
		
		/*spawn - initialize each of the member variables, should be called upon
		 * entity creation
		 */
		public void spawn()
		{
			this.takesdamage = true;  //entity is invincible by default
			this.solid       = true;   //is stopped by walls
			this.isAlive     = true;   //alive by default
			this.health      = maxHP;      //health defaults to max hp
			this.dmg         = 20;      //dmg defaults to 0
			this.nextthink   = 0;      //think right away
			this.nextmove    = 0;      //move right away
			this.velocityX   = 0;	   //zero the velocity
			this.velocityY   = 0;      //
			this.speed       = 8.0f;
			this.inAttack = false;
			this.attacktime = 0;
			this.attackdur = 0;
			this.dir = DIR_E;
			this.score = 30;
			monsterType = 3;
			moveLeft();
			velocityX = -0.5f;
			velocityY = 0.0f;
		}
		
		
		
		public void think(CPlayer p)
		{
			if (SystemClock.elapsedRealtime() >= attackdur)
			{
				inAttack = false;
			}
			if (inAttack)
				return;
			if (p.getX() < this.getX() && dir == DIR_E)
			{
				
				moveLeft();
				velocityX = -0.5f;
				velocityY = 0.0f;
			}
			else if (p.getX() > this.getX() && dir == DIR_W)
			{
				moveRight();
				velocityX = 0.5f;
				velocityY = 0.0f;
			}
			
			if (!inAttack && SystemClock.elapsedRealtime() >= attacktime)
			{
				if (distance(getX()+40, getY()+30, p.getX()+16,p.getY()-16) <= 60)
				{
					if (p.getX() < this.getX())
					{
						attackLeft();
					}
					else
					{
						attackRight();
					}
					inAttack = true;
					attacktime = SystemClock.elapsedRealtime() + 4000;
					attackdur =  SystemClock.elapsedRealtime() + 800;
				}
			}
			
			
			
			this.nextthink = SystemClock.elapsedRealtime() + 50;
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
		
		
		
		public void moveRight()
		{
			this.animate(new long[]{400, 400, 400, 400}, 12,15, true);
			dir = DIR_E;
		}
	
		public void moveLeft()
		{
			this.animate(new long[]{400, 400, 400, 400}, 0, 3, true);
			dir = DIR_W;
		}
		public void attackLeft()
		{
			this.animate(new long[]{400, 400, 400, 400}, 8, 11, false);
		}
		public void attackRight()
		{
			this.animate(new long[]{400, 400, 400, 400}, 20, 23, false);
		}
		
		/*public void moveUpLeft()
		{
			this.animate(new long[]{400, 400, 400, 400}, 28, 31, true);
			dir = DIR_NW;
		}*/

	}


