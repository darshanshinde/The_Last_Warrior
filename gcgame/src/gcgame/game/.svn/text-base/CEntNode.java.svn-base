package gcgame.game;

import gcgame.game.CBaseEntity;
import android.os.SystemClock;

public class CEntNode 
{
	//variables
	public CBaseEntity entity;
	
	//pointer to next node
	public CEntNode next;
	
	
	
	//constructor
	public CEntNode(CBaseEntity ent)
	{
		this.entity = ent;
		this.next = null;
	}
	
	//methods
	
	//this will check if this ent is ready to think / move
	public void checkThink(int TileGrid[][], CPlayer p)
	{
		if (SystemClock.elapsedRealtime() >= this.entity.nextthink)
		{
			this.entity.think(p);
		}
		if (SystemClock.elapsedRealtime() >= this.entity.nextmove)
		{
			this.entity.move(TileGrid);
		}
	}
	
}
