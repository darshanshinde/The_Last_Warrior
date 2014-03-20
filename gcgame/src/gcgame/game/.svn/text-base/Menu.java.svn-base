package gcgame.game;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Menu extends ListActivity {
	
	// ===========================================================
	// Constants
	// ===========================================================

	private static final String PREF_LAST_APP_LAUNCH_VERSIONCODE_ID = "last.app.launch.versioncode";
	private static final int DIALOG_FIRST_APP_LAUNCH = 0;
	private static final int DIALOG_NEW_IN_THIS_VERSION = DIALOG_FIRST_APP_LAUNCH + 1;
	private static final int DIALOG_BENCHMARKS_SUBMIT_PLEASE = DIALOG_NEW_IN_THIS_VERSION + 1;

	// ===========================================================
	// Fields
	// ===========================================================

	private int mVersionCodeCurrent;
	private int mVersionCodeLastLaunch;
		
	// ===========================================================
	// Constructors
	// ===========================================================

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
	  super.onCreate(savedInstanceState);

	  setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, PLAYS));
	  
	}
	
	static final String[] PLAYS = new String[] {
	    "PLAY", "LOAD", "EXIT"
	    };

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		// When clicked, show a toast with the TextView text
		String clicked_str = ((TextView) v).getText().toString();
		if (clicked_str.matches("PLAY")) {
			Intent it = new Intent(this, gcgame.class);
			this.startActivity(it);
		}
		else if (clicked_str.matches("LOAD")) {
			Intent it = new Intent(this, Load.class);
			this.startActivity(it);
		}
		else {
			this.finish();
		}
	}
}

