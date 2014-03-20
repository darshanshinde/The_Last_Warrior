package gcgame.game;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Load extends ListActivity {
	
	// ===========================================================
	// Constants
	// ===========================================================
	/*
	private static final String PREF_LAST_APP_LAUNCH_VERSIONCODE_ID = "last.app.launch.versioncode";
	private static final int DIALOG_FIRST_APP_LAUNCH = 0;
	private static final int DIALOG_NEW_IN_THIS_VERSION = DIALOG_FIRST_APP_LAUNCH + 1;
	private static final int DIALOG_BENCHMARKS_SUBMIT_PLEASE = DIALOG_NEW_IN_THIS_VERSION + 1;
	 */
	// ===========================================================
	// Fields
	// ===========================================================
	/*
	private int mVersionCodeCurrent;
	private int mVersionCodeLastLaunch;
	*/
	
	
	/*static final */String[] MENU;/* = new String[] {
	    "PLAY", "EXIT"
	    };*/
	ArrayList<String> MenuList;
	ArrayList<Userdata> loadData;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
	  super.onCreate(savedInstanceState);
	  
	  Context ctx = getApplicationContext(); 
	  CXML cxml = new CXML(ctx);
	  //Read existing XML into loadData
	  this.loadData = cxml.ReadXML();
	  
	  //Initialize the menu/user list
	  this.MenuList = new ArrayList<String>();
	  
	  if(this.loadData.isEmpty() || this.loadData == null){
		  String[] MENU = {"No saved data", "Go Back"};
		  this.MENU = MENU;
	  }
	  else{
		  //Loop for each Userdata node
		  for(Userdata u:this.loadData){
			  //Create a usr info string
			  String loadUser = u.username + " (Score: " + u.score + ")";
			  this.MenuList.add(loadUser); //Add user info into String ArrayList
		  }
		  this.MenuList.add("Go Back");
		  //Convert MenuList into string array and put into MENU
		  this.MENU = this.MenuList.toArray(new String[this.MenuList.size()]);
	  }

	  setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, MENU));
	}//End of onCreate
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String clicked_str = ((TextView) v).getText().toString();
		if(!clicked_str.matches("Go Back")){
			int counter = 0;
			for(String s: this.MenuList){
				if (clicked_str.equals(s)) {
					Intent it = new Intent(this, gcgame.class);
					it.putExtra("playername", this.loadData.get(counter).username);
					it.putExtra("score", this.loadData.get(counter).score);
					this.startActivity(it);
					break;
				}
				else {
					counter++;
				}
			}
		}
		else if(clicked_str.matches("Go Back")){
			this.finish();
		}
	}
}

