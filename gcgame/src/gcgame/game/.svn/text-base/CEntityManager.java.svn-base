package gcgame.game;

import android.os.SystemClock;
import android.util.Log;
import gcgame.game.CBaseEntity;
import gcgame.game.CEntNode;


public class CEntityManager
{
	//constants
	final long tickrate = 1; //time in ms between cycles
	
	//variables
	public int entCount;  //number of entities the manager is using
	
	long nextcycle;       //time in ms till next cycle
	
	//linked list variables
	
	CEntNode head;   //head of the linked list
	CEntNode cur;    // LL node for traversal operations
	CEntNode prev;   //for deletion
	
	
	//constructor
	CEntityManager()
	{
		entCount = 0;
		nextcycle = 0;
		head = null;
		cur = null;
		prev = null;
	}
	
	/********************** methods *********************************/
	
	/*addEnt - will add an entity to the list
	 * 
	 */
	public void addEnt(CBaseEntity ent)
	{
		if (head == null)
		{
			head = new CEntNode(ent);
		}
		else
		{
			cur = head;
			while (cur.next != null)
			{
				cur = cur.next;
			}
			cur.next = new CEntNode(ent);
		}
		entCount++;
	}
	
	public void cycle(int TileGrid[][], CPlayer p)
	{
		CBaseEntity store = null;
		CEntNode cur = head;
		while (cur != null)
		{
			if (cur.entity.health <= 0)
				cur.entity.isAlive = false;
			if (cur.entity.isAlive)
			{	
				cur.checkThink(TileGrid, p);
				
				prev = cur;
				cur = cur.next;
			}
			else
			{
				store = cur.entity;
				if (cur == head)
				{
					head = cur.next;
					cur = cur.next;
				}
				else
				{
					if (prev != null)
					{
						prev.next = cur.next;
						cur = cur.next;
					}
				}
				store.setVisible(false);
			}
			
		}
		nextcycle = SystemClock.elapsedRealtime() + tickrate;
	}
	
}
