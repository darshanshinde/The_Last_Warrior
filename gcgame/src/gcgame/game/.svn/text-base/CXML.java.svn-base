package gcgame.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
 
import org.xmlpull.v1.XmlSerializer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

 
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;

public class CXML{
	//
	//Field Attributes
	//
	Context context; //Specifies the Android activity
	
	// names of the XML tags
    static final String USERDATA = "userdata";
    static final String USER = "user";
    static final String SCORE = "score";
	
	public CXML(Context pContext){
		this.context = pContext; //Context will be passed from the main Activity
	}
	
    public void CreateXML(String username, int score){
        //Create a new file called "gcdata.xml"
        String newxmlfile = new String("gcdata.xml");
        
        //Bind the new file with a FileOutputStream
        FileOutputStream fileos = null;        
        try{
                fileos = context.openFileOutput(newxmlfile, Context.MODE_PRIVATE);
        }catch(FileNotFoundException e){
                Log.e("FileNotFoundException", "can't create FileOutputStream");
        }
        //Create a XmlSerializer in order to write xml data
        XmlSerializer serializer = Xml.newSerializer();
        try {
                //Set the FileOutputStream as output for the serializer, using UTF-8 encoding
                serializer.setOutput(fileos, "UTF-8");
                //Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null)
                serializer.startDocument(null, Boolean.valueOf(true));
                //Set indentation option
                serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                serializer.startTag(null, USERDATA);
                        serializer.startTag(null, USER);
                  	  	Log.d("Save", username);
                        serializer.text(username);
                        serializer.endTag(null, USER);
                        
                        serializer.startTag(null, SCORE);
                        serializer.text(String.valueOf(score));
                        serializer.endTag(null, SCORE);                 
                serializer.endTag(null, USERDATA);
                serializer.endDocument();
                //Write xml data into the FileOutputStream
                serializer.flush();
                //Close the file stream
                fileos.close();
                Log.d("XMLlog","file has been created");
        } catch (Exception e) {
                Log.e("Exception","error occurred while creating xml file");
        }
    }//End of CreateXML
    
    public ArrayList<Userdata> ReadXML(){
    	String filename = "gcdata.xml";
    	File file = context.getFileStreamPath(filename);
    	FileInputStream fileis = null;
    	
    	//Log.d("READ", "file");
    	if(file.exists()){
    		try{
    			 fileis = context.openFileInput(filename);
    		}catch(FileNotFoundException e){
    				Log.e("FileNotFoundException", "can't create FileInputStream");
    				//return null;
    		}
    	}
    	else {
    		Log.d("READ", "file doesn't exist");
    		return new ArrayList<Userdata>();
    	}
        BufferedReader bufferin = new BufferedReader(new InputStreamReader(fileis));
	    XmlPullParserFactory factory = null;
	    ArrayList<Userdata> userList = new ArrayList<Userdata>();
		try {
			factory = XmlPullParserFactory.newInstance();
		
			factory.setNamespaceAware(true);
			XmlPullParser xpp = null;
		
			xpp = factory.newPullParser();		
			xpp.setInput(bufferin);
			int eventType = xpp.getEventType();
			String name;
			Userdata userNode = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType){
	    			case XmlPullParser.START_DOCUMENT:
	    				//Do nothing
	    				break;
	    			
	    			case XmlPullParser.START_TAG:
	    				name = xpp.getName();
	    				if (name.equalsIgnoreCase(USERDATA)){
                        	userNode = new Userdata();
                        }
	    				else if (name.equalsIgnoreCase(USER)){
                        	userNode.username = xpp.nextText();
                        	//Log.d("XMLlog:Text",xpp.nextText());
                        }
                        else if (name.equalsIgnoreCase(SCORE)){
                        	userNode.score = Integer.parseInt(xpp.nextText());
                        	//Log.d("XMLlog:Text",xpp.nextText());
                        }
	    				break;
	    			case XmlPullParser.END_TAG:
	    				name = xpp.getName();
	    				if (name.equalsIgnoreCase(USERDATA)){
                        	userList.add(userNode);
                        }
	    				//Log.d("XMLlog:End",name);
	    				break;
	    			default:
	    				break;
				}
				eventType = xpp.next();
			}
			for(Userdata user:userList){
				Log.d("XMLlog:Result",user.username + " " + user.score);
			}
			//Close the file stream
	        fileis.close();	
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
		return userList;
    }//End of ReadXML

    public void updateScore(ArrayList<Userdata> pUserList){
    	
    	String newxmlfile = new String("gcdata.xml");
        FileOutputStream fileos = null;        
        try{
                fileos = context.openFileOutput(newxmlfile, Context.MODE_PRIVATE);
        }catch(FileNotFoundException e){
                Log.e("FileNotFoundException", "can't create FileOutputStream");
        }
        //Create a XmlSerializer to write xml data
        XmlSerializer serializer = Xml.newSerializer();
        try {
                //Set the FileOutputStream as output for the serializer, using UTF-8 encoding
                serializer.setOutput(fileos, "UTF-8");
                //Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null)
                serializer.startDocument(null, Boolean.valueOf(true));
                //Set indentation option
                serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                
                for(Userdata user:pUserList){
                	serializer.startTag(null, USERDATA);
                		serializer.startTag(null, USER);
                		serializer.text(user.username);
                		serializer.endTag(null, USER);
                        
                		serializer.startTag(null, SCORE);
                		serializer.text(String.valueOf(user.score));
                		serializer.endTag(null, SCORE);             
                    serializer.endTag(null, USERDATA);
                }
                serializer.endDocument();
                //Write xml data into the FileOutputStream
                serializer.flush();
                //Close the file stream
                fileos.close();
        } catch (Exception e) {
                Log.e("Exception","error occurred while creating xml file");
        }
    	
    }//End of updateScore
    
    public void addUserNode(String pUsername, int pScore){
    	Userdata userNode = new Userdata(pUsername, pScore);
    	ArrayList<Userdata> userList = ReadXML();
    	userList.add(userNode);
    	
    	String newxmlfile = new String("gcdata.xml");
        FileOutputStream fileos = null;        
        try{
                fileos = context.openFileOutput(newxmlfile, Context.MODE_PRIVATE);
        }catch(FileNotFoundException e){
                Log.e("FileNotFoundException", "can't create FileOutputStream");
        }
        //Create a XmlSerializer to write xml data
        XmlSerializer serializer = Xml.newSerializer();
        try {
                //Set the FileOutputStream as output for the serializer, using UTF-8 encoding
                serializer.setOutput(fileos, "UTF-8");
                //Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null)
                serializer.startDocument(null, Boolean.valueOf(true));
                //Set indentation option
                serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                
                for(Userdata user:userList){
                	serializer.startTag(null, USERDATA);
                		serializer.startTag(null, USER);
                		serializer.text(user.username);
                		serializer.endTag(null, USER);
                        
                		serializer.startTag(null, SCORE);
                		serializer.text(String.valueOf(user.score));
                		serializer.endTag(null, SCORE);             
                    serializer.endTag(null, USERDATA);
                }
                serializer.endDocument();
                //Write xml data into the FileOutputStream
                serializer.flush();
                //Close the file stream
                fileos.close();
        } catch (Exception e) {
                Log.e("Exception","error occurred while creating xml file");
        }
    }//End of addUserNode
    
    public void Save(String pUsername, int pScore){
  	  	//Execute ReadXML() -> Read existing XML into loadData
    	ArrayList<Userdata> userList = this.ReadXML();
  	  	
  	  	//If readXML returns empty ArrayList, execute CreateXML()
  	  	if(userList.isEmpty() || userList == null){
  	  		this.CreateXML(pUsername, pScore);

  	  	}
  	  	//Else: Check if playername is in the arraylist
  	  	else{
  	  		
  	  		//Loop for each Userdata node
  	  		boolean updateFlag = false;
  	  		for(Userdata u:userList){
  	  			if(u.username.equals(pUsername)){
  	  				Log.d("Save", "Update");
  	  				u.score = pScore;
  	  				updateFlag = true;
  	  			}
  	  		}
  	  		if(updateFlag == true){
  	  			//Update
  	  			this.updateScore(userList);
  	  		}
  	  		else {
  	  			//If the for-loop doesn't catch the node, execute AddUserNode()
  	  			this.addUserNode(pUsername, pScore);
  	  		}
  	  	}    	
    }//End of Save
    
}//End of CXML class
