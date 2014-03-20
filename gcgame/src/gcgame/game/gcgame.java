/*******************************************************************************
 * gcgame.java
 * 
 * This is the main game file. The methods here are the entry points for 
 * loading various objects into the game. All the gameplay elements will be tied 
 * together here.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */


//package name
package gcgame.game;



import java.io.IOException;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

import android.media.AudioManager;
import android.media.SoundPool;
import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.BoundCamera;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperties;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.anddev.andengine.entity.layer.tiled.tmx.util.exception.TMXLoadException;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnAreaTouchListener;
import org.anddev.andengine.entity.scene.Scene.ITouchArea;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.constants.Constants;

// MF Mar 10, 2011 4:45:49 PM - Added so that we cna get the system time
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;




/* BaseGameActivity - 
 * Main class for the game
 * entry points to the game engine go through the given
 * overrides.
 */
public class gcgame extends BaseGameActivity
{
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 480;   //width in pixels of camera's focus   
	private static final int CAMERA_HEIGHT = 320;  //heaight in pixels of camera's focus

	private static final int MaxPlayerHP = 200;
	
	private static final int TileWidth = 32;
	private static final int lizTileWidth1 = 30;
	private static final int BdBossmonsterTileWidth = 50;
	private static final int BossmonsterTileWidth = 40;
	
	private Scene scene;
	//vars used for menu
	protected static final int MENU_RESET = 0;
	protected static final int MENU_QUIT  = MENU_RESET +1;
	
	protected MenuScene mMenuScene;
	private Texture mMenuTexture;
	protected TextureRegion mMenuResetTextureRegion;
	protected TextureRegion mMenuQuitTextureRegion;
	// ===========================================================
	// Fields
	// ===========================================================

	
	int fps=0;
	
	int monsterCount;
	
	CPlayer player;
    
    private long time = System.currentTimeMillis();
	
	private BoundCamera mBoundChaseCamera; //camera object, will force the 'following' of our player sprite

	private Texture mTexture;
	private TiledTextureRegion mPlayerTextureRegion;
	
	TiledTextureRegion mMonster1TextureRegion;
	
	Texture mMonster1Texture;
	
	TiledTextureRegion mlizTextureRegion;
	Texture mlizTexture;
	
	TiledTextureRegion mBossmonsterTextureRegion;
	Texture mBossmonsterTexture;
	
	TiledTextureRegion mBdBossmonsterTextureRegion;
	Texture mBdBossmonsterTexture;
	
	Texture mFireballTexture;
	
	TiledTextureRegion mFireballTextureRegion;
	//private TiledTextureRegion mMonster1TextureRegion;
	
	// MF Mar 8, 2011 7:25:56 PM - commented out vars. for button
	private Texture mButtonTexture;
	private TiledTextureRegion mButtonTextureRegion;
	
	private TMXTiledMap mTMXTiledMap;
	protected int mCactusCount;
	
	private Texture mOnScreenControlTexture;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private TextureRegion mOnScreenControlKnobTextureRegion;
	
	//SI Mar 26 fields for displaying texts
	private Texture mFontTexture;
	private Font mFont;
	private Font mFontMagenta;
	private Texture mFontMagentaTexture;
	private Texture mFontCyanTexture;
	private Font mFontCyan;
	private String HP;
	
	//SI Apr 14
	private boolean isUserdataLoaded;
	//private Context context; //Specifies the Android activity
	
	//fields for player name display
	private String playername;
	
	private CScore score;//SI Apr 19
	private ChangeableText scoreText;
	
	private CEntityManager EntManager;
	
	//attack sound
	private Sound mAttackSound;
	
	//new sound stuff with soundpool
	
	public static final int SOUND_EXPLOSION = 1;
	public static final int SOUND_PLAYERHIT = 2;
	public static final int SOUND_LIZHIT = 3;
	public static final int SOUND_BEANSDIE = 4;
	public static final int SOUND_LIZDIE = 5;
	public static final int SOUND_BDHIT = 6;
	public static final int SOUND_BDDIE = 7;

	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	
	private Music mMusic;
	
	fireball FBP;
	
	//SI Mar 25 - attack button and the texts are read to HUD
	final HUD hud = new HUD();
	
	
	//tile grid
	private int TileGrid[][];

	final int playerHeight = 32;
	final int playerWidth  = 32;
	final int tileWidth    = 32;
	
	
	//sound functions 
	
	private void initSounds() {
	     soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	     soundPoolMap = new HashMap<Integer, Integer>();
	     soundPoolMap.put(SOUND_EXPLOSION, soundPool.load(getBaseContext(), R.raw.attack3, 1));
	     soundPoolMap.put(SOUND_PLAYERHIT, soundPool.load(getBaseContext(), R.raw.playerhit, 1));
	     soundPoolMap.put(SOUND_LIZHIT, soundPool.load(getBaseContext(), R.raw.lizhit, 1));
	     soundPoolMap.put(SOUND_BEANSDIE, soundPool.load(getBaseContext(), R.raw.beansdie, 1));
	     soundPoolMap.put(SOUND_LIZDIE, soundPool.load(getBaseContext(), R.raw.lizdie, 1));
	     soundPoolMap.put(SOUND_BDHIT, soundPool.load(getBaseContext(), R.raw.trollpain, 1));
	     soundPoolMap.put(SOUND_BDDIE, soundPool.load(getBaseContext(), R.raw.trolldeath, 1));
	}
	          
	public void playSound(int sound) {
	    /* Updated: The next 4 lines calculate the current volume in a scale of 0.0 to 1.0 */
	    AudioManager mgr = (AudioManager)getBaseContext().getSystemService(Context.AUDIO_SERVICE);
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);    
	    float volume = streamVolumeCurrent / streamVolumeMax;
	    
	    /* Play the sound with the correct volume */
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f);
	}
	
	double distance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	double angle(double y1, double y2,double x1,double x2)
	{
		double angle = Math.atan((x2-x1)/(y1-y2))/(Math.PI/180);
		if ( angle > 0 )
		{
			if (y1 < y2)
				return angle;
			else
				return 180 + angle;
		} 
		else 
		{
			if (x1 < x2)
				return 180 + angle;
			else
				return 360 + angle;
		}
	}
	
	
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	/*
	 * First Override - this is called when the engine is first loaded
	 */
	@Override
	public Engine onLoadEngine()
	{
		
		this.mBoundChaseCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mBoundChaseCamera).setNeedsMusic(true));
	}

	
	
	/*
	 * This entry point is for when the engine loads up the game resources. This is where we must associate classes and
	 * variables with their respective resource files, e.g. sprites and img files.
	 */
	@Override
	public void onLoadResources()
	{
		
		initSounds();
		
		this.mTexture = new Texture(512, 128, TextureOptions.DEFAULT);
		this.mPlayerTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/hero.png", 0, 0, 16, 4); //column16 x row4
		
		//this.mMonster1TextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/enemy.png", 0, 0, 3, 4); // 72x128
		this.mMonster1Texture = new Texture(128, 128, TextureOptions.DEFAULT);
		this.mMonster1TextureRegion = TextureRegionFactory.createTiledFromAsset(this.mMonster1Texture, this, "gfx/enemy.png", 0, 0, 3, 4); // 72x128
		
		
		this.mlizTexture = new Texture(256, 1024, TextureOptions.DEFAULT);
		this.mlizTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mlizTexture, this, "gfx/lizman1.png", 0, 0, 4, 16);
		
		this.mBossmonsterTexture = new Texture(512, 512 , TextureOptions.DEFAULT);
		this.mBossmonsterTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mBossmonsterTexture, this, "gfx/snapdragon_tiled.png", 0, 0, 4, 3);
		
		this.mBdBossmonsterTexture = new Texture(512, 512 , TextureOptions.DEFAULT);
		this.mBdBossmonsterTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mBdBossmonsterTexture, this, "gfx/BD_Boss2.png", 0, 0, 4, 6);
		
		this.mFireballTexture = new Texture(512, 512 , TextureOptions.DEFAULT);
		this.mFireballTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mFireballTexture, this, "gfx/FireBall.png", 0, 0, 5, 5);
		// MF Mar 8, 2011 7:25:56 PM - commented out button code
		this.mButtonTexture = new Texture(32, 32, TextureOptions.DEFAULT);
		this.mButtonTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mButtonTexture, this, "gfx/sword.png", 0, 0, 1, 1);
		
		this.mOnScreenControlTexture = new Texture(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mOnScreenControlBaseTextureRegion = TextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "gfx/onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = TextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "gfx/onscreen_control_knob.png", 128, 0);
		
		//if the argument is only one, use .loadTexture
		
		// MF Mar 8, 2011 7:25:56 PM - commented out button code
		this.mEngine.getTextureManager().loadTextures(this.mTexture, this.mMonster1Texture,this.mBossmonsterTexture,this.mBdBossmonsterTexture, this.mlizTexture, this.mButtonTexture, this.mOnScreenControlTexture, this.mFireballTexture);
		
		//load resources for menu
		this.mMenuTexture = new Texture(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMenuResetTextureRegion = TextureRegionFactory.createFromAsset(this.mMenuTexture, this, "gfx/menu_reset.png", 0, 0);
		this.mMenuQuitTextureRegion = TextureRegionFactory.createFromAsset(this.mMenuTexture, this, "gfx/menu_quit.png", 0, 50);
		this.mEngine.getTextureManager().loadTexture(this.mMenuTexture);
		
		this.mFontTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont = new Font(this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 20, true, Color.BLACK);
		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getFontManager().loadFont(this.mFont);
		
		this.mFontMagentaTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFontMagenta = new Font(this.mFontMagentaTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 20, true, Color.rgb(255,0,64));
		this.mEngine.getTextureManager().loadTexture(this.mFontMagentaTexture);
		this.mEngine.getFontManager().loadFont(this.mFontMagenta);
		
		//loads resources for player name
		this.mFontCyanTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFontCyan = new Font(this.mFontCyanTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 20, true, Color.rgb(0,102,153));
		this.mEngine.getTextureManager().loadTexture(this.mFontCyanTexture);
		this.mEngine.getFontManager().loadFont(this.mFontCyan);
		
		 MusicFactory.setAssetBasePath("mfx/");
         try {
                 this.mMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "bloodrave.ogg");
                 this.mMusic.setLooping(true);
                 this.mMusic.setVolume(0.5f);
         } catch (final IOException e) {
                 Debug.e("Error", e);
         }
		
		
		//loads resource for attack sound
		//try {
			try {
				this.mAttackSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "mfx/explosion.ogg");
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//} catch (final IOException e) {
		//	Debug.e("Error", e);
		//}

		//this.mEngine.getTextureManager().loadTexture(this.mTexture);
	}

	@Override
	public Scene onLoadScene()
	{
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		
		//Initialize the scene -> 2 scenes: TopLayer(player & Rectangle) & BottomLayer (tmxLayer)
		this.scene = new Scene(2);
		
		TileGrid = new int[50][50];
		
		for(int i=0; i<= 49; i++)
			for(int j=0; j<= 49; j++)
				TileGrid[i][j] = 0;
		
		EntManager = new CEntityManager();
		
		//SI Mar 25 - attack button and the texts are read to HUD
		//final HUD hud = new HUD();
		
		try 
		{
			final TMXLoader tmxLoader = new TMXLoader(
					this, this.mEngine.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, new ITMXTilePropertiesListener()
			{
				@Override
				public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, final TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties)
				{
					
					
					
					int gridColumn = pTMXTile.getTileColumn();
					int gridRow = pTMXTile.getTileRow();
					
					FBP = new fireball(0, 0, mFireballTextureRegion.clone(), 300);
					
					// We are going to count the tiles that have the property "cactus=true" set. 
					if(pTMXTileProperties.containsTMXProperty("solid", "true"))
					{
						TileGrid[gridColumn][gridRow] = 1;
						//gcgame.this.mCactusCount++;
					}
					else if (pTMXTileProperties.containsTMXProperty("monster", "1"))
					{
						TileGrid[gridColumn][gridRow] = 0;
						EntManager.addEnt(new CMonster(gridColumn*TileWidth, gridRow*TileWidth, mMonster1TextureRegion.clone(), 50));

				
						
					}
					else if (pTMXTileProperties.containsTMXProperty("monster", "2"))
					{
						EntManager.addEnt(new CMonsterLiz(gridColumn*TileWidth, gridRow*TileWidth, mlizTextureRegion.clone(), 100));
						TileGrid[gridColumn][gridRow] = 0;
						
					}
					else if (pTMXTileProperties.containsTMXProperty("monster", "3"))
					{
						EntManager.addEnt(new BdMonster(gridColumn*TileWidth, gridRow*TileWidth, mBdBossmonsterTextureRegion.clone(), 300));
						TileGrid[gridColumn][gridRow] = 0;
					}
					else
					{
						TileGrid[gridColumn][gridRow] = 0;
					}
					
				}
			}
		    );
			
			this.mTMXTiledMap = tmxLoader.loadFromAsset(this, "tmx/test.tmx");
 
		} 
		catch (final TMXLoadException tmxle)
		{
			Debug.e(tmxle);
		}

		
		final TMXLayer tmxLayer = this.mTMXTiledMap.getTMXLayers().get(0);
		scene.getFirstChild().attachChild(tmxLayer);
		
		/* Make the camera not exceed the bounds of the TMXLayer. */
		this.mBoundChaseCamera.setBounds(0, tmxLayer.getWidth(), 0, tmxLayer.getHeight());
		this.mBoundChaseCamera.setBoundsEnabled(true);

		/* Calculate the coordinates for the face, so its centered on the camera. */
		//final int centerX = (CAMERA_WIDTH - this.mPlayerTextureRegion.getTileWidth()) / 2;
		//final int centerY = (CAMERA_HEIGHT - this.mPlayerTextureRegion.getTileHeight()) / 2;
		
		int centerX = 224;
		int centerY = 96;
		
		time = System.currentTimeMillis();
	
		player = new CPlayer(centerX, centerY, this.mPlayerTextureRegion, MaxPlayerHP);
		this.mBoundChaseCamera.setChaseEntity(player);
		
		scene.registerUpdateHandler(
				new IUpdateHandler()
		{
			@Override
			public void reset() { }

			@Override
			public void onUpdate(final float pSecondsElapsed)
			{
				CEntNode cur = null;
				if (System.currentTimeMillis() - time >= 1000) {
	         		Log.d("mylog", fps + "fps");
	         		//Log.d("POSITION", "X: " + player.getX() + " Y: " + player.getX());
	         		fps = 0;
	         		time = System.currentTimeMillis();
	         	}
	         	fps++;
				if (SystemClock.elapsedRealtime() >= EntManager.nextcycle)
				{
					EntManager.cycle(TileGrid, player);
				}
				
				cur = EntManager.head;
				
				while (cur != null)
				{
					
					if (cur.entity.collidesWith(player))
					{
						if (SystemClock.elapsedRealtime() >= player.nextdmg && cur.entity.monsterType < 3)
						{
							player.takedamage(cur.entity.dmg);
							playSound(SOUND_PLAYERHIT);
						}
					}
					
					if (SystemClock.elapsedRealtime() >= player.nextdmg && cur.entity.monsterType == 3 && cur.entity.inAttack)
					{
						if (distance(cur.entity.getX()+40, cur.entity.getY()+30, player.getX()+16,player.getY()+16) <= 75)
						{
							if (player.getY() > cur.entity.getY() && player.getY() < cur.entity.getY()+ 50)
							{
								player.takedamage(cur.entity.dmg);
								playSound(SOUND_PLAYERHIT);
							}
						}
				
					}
					
					cur = cur.next;
				}
			}
		}
		);
		
		/* Create the sprite and add it to the scene. */
		
		
		/////////starting of name input method
		if(!this.isUserdataLoaded){
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
	
			alert.setTitle("Welcome to GC-Game");
			alert.setMessage("Enter your name:");
	
			// Set an EditText view to get user input 
			final EditText input = new EditText(this);
			alert.setView(input);
	
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  //pass the input to the playername->then create text and attach to hud
			  playername = input.getText().toString();
			  final Text textCenter = new Text(10, 5, mFontCyan, playername, HorizontalAlign.CENTER);
			  hud.attachChild(textCenter);
			  
			  //Create score and show it to the screen
			  score = new CScore(0);
			  scoreText = new ChangeableText(435, 5, mFontMagenta, String.valueOf(score.getScore()),5);
			  hud.attachChild(scoreText);
			  }
			});
	
			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
			    // Canceled.
			  }
			});
	
			alert.show();
		}
		else {
			//Create and show the playername Text
			final Text textCenter = new Text(10, 5, mFontCyan, playername, HorizontalAlign.CENTER);
			hud.attachChild(textCenter);
			//Create scoreText and show it to the screen
			scoreText = new ChangeableText(435, 5, mFontMagenta, String.valueOf(score.getScore()),5);
			hud.attachChild(scoreText);
		}
		/////////////////end of name input method
		
		player.spawn();
		
		//Create and display SCORE
		final Text hpTitleText = new Text(10, 23, mFontCyan, "HP:", HorizontalAlign.LEFT);
		hud.attachChild(hpTitleText);
		//Generate HP onto the screen
		this.HP = String.valueOf(player.HP.currentPts);
		final ChangeableText hpText = new ChangeableText(45, 23, this.mFontCyan, this.HP);
		hud.attachChild(hpText);
		//Create and display SCORE
		final Text scoreTitleText = new Text(355, 5, mFontMagenta, "SCORE:", HorizontalAlign.LEFT);
		hud.attachChild(scoreTitleText);

		scene.registerUpdateHandler(
				new IUpdateHandler()
		{
			@Override
			public void reset() { }

			@Override
			public void onUpdate(final float pSecondsElapsed)
			{
				//SI Mar 26 Updates the HP
				hpText.setText(String.valueOf(player.HP.currentPts));
				//SI Apr 19 Updates the score
				scoreText.setText(String.valueOf(score.getScore()));
				/* Get the scene-coordinates of the players feet. */
				final float[] playerFootCordinates = player.convertLocalToSceneCoordinates(12, 31);
				/* Get the tile the feet of the player are currently waking on. */
				/*final TMXTile tmxTile = tmxLayer.getTMXTileAt(playerFootCordinates[Constants.VERTEX_INDEX_X], playerFootCordinates[Constants.VERTEX_INDEX_Y]);
				if(tmxTile != null)
				{

					 //tmxTile.setTextureRegion(null); //<-- Rubber-style removing of tiles =D
					 //currentTileRectangle.setPosition(tmxTile.getTileX(), tmxTile.getTileY());

				}*/
			}
		}
		);
			
		scene.getLastChild().attachChild(player);
		
		EntManager.cur = EntManager.head;
		
		while (EntManager.cur != null)
		{
			//this.mEngine.getTextureManager().loadTextures(EntManager.cur.entity.mTexture);
			EntManager.cur.entity.spawn();
			
			scene.getLastChild().attachChild(EntManager.cur.entity);
			
			EntManager.cur = EntManager.cur.next;
		}
		
		
		final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(0, CAMERA_HEIGHT - this.mOnScreenControlBaseTextureRegion.getHeight(),
					this.mBoundChaseCamera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 
					0.01f, new IAnalogOnScreenControlListener() 
		{
			
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY)
			{
				player.velocityX = pValueX;
				player.velocityY = pValueY;
				
				//Log.d("mylog", "SPEEDS:" + pValueX + " -- " + pValueY);
				
				//animation logic
				if(pValueX > -0.5 && pValueX < 0.5 && pValueY < 0 && pValueY >= -1)
				{
					player.moveUp();
				}
				else if(pValueX >= 0.5 && pValueX <= 1 && pValueY < -0.5 && pValueY >= -1)
				{
					player.moveUpRight();
				}
				else if(pValueX >= -1 && pValueX < -0.5 && pValueY < -0.5 && pValueY >= -1)
				{
					player.moveUpLeft();
				}
				else if(pValueX > 0 && pValueX <= 1 && pValueY < 0.5 && pValueY >= -0.5)
				{
					player.moveRight();
				}
				else if(pValueX > -0.5 && pValueX < 0.5 && pValueY <= 1 && pValueY > 0)
				{
					player.moveDown();
				}
				else if(pValueX >= 0.5 && pValueX <= 1 && pValueY <= 1 && pValueY > 0.5)
				{
					player.moveDownRight();
				}
				else if(pValueX >= -1 && pValueX < -0.5 && pValueY <= 1 && pValueY > 0.5)
				{
					player.moveDownLeft();
				}
				else if(pValueX >= -1 && pValueX < 0 && pValueY < 0.5 && pValueY >= -0.5)
				{
					player.moveLeft();
					
				}
				//Log.d("Position", "X: " + getX() + "Y: " + getY());
				
				//constants
				
				
				
				//current player positions
				float curPosX = player.getX();
				float curPosY = player.getY();
				
				//new player positions after move (if allowed)
				float newPosX = curPosX + pValueX*player.speed;
				float newPosY = curPosY + pValueY*player.speed;
				
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
								player.setPosition(newPosX, curPosY);
							}
						}
					}
					else
					{
						player.setPosition(newPosX, curPosY);
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
							player.setPosition(player.getX(), newPosY);
						}
					}
					else
					{
						player.setPosition(player.getX(), newPosY);
					}
				}
				
				if (SystemClock.elapsedRealtime() >= player.nextthink)
				{
					player.think();
				}
				
			}
			
			@Override
			public void onControlClick(AnalogOnScreenControl pAnalogOnScreenControl)
			{
				/* Nothing. */
			}
		});
		
		//EntManager.addEnt(player);
		
		analogOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setAlpha(0.5f);
		analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
		analogOnScreenControl.getControlBase().setScale(0.75f);
		analogOnScreenControl.getControlKnob().setScale(0.75f);
		analogOnScreenControl.refreshControlKnobPosition();
		scene.setChildScene(analogOnScreenControl);
		
		//SI - defines how the button work
		final TiledSprite button = new TiledSprite(CAMERA_WIDTH - this.mButtonTextureRegion.getWidth()*2, CAMERA_HEIGHT - this.mButtonTextureRegion.getHeight()*2, mButtonTextureRegion){
			
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) 
				{
					if (!player.inAttack)
					{
						playSound(SOUND_EXPLOSION);
					}
					else
					{
						return true;
					}
					player.attack();

					CEntNode cur = EntManager.head;
					
					
					
					while (cur != null)
					{
						float mx = cur.entity.getX()+40;
						float my = cur.entity.getY()+30;
						if(cur.entity.collidesWith(player))
						{
							cur.entity.takedamage(player.dmg);
							
							if (cur.entity.isAlive == false)
							{
								
								score.add(cur.entity.score);
								if (cur.entity.monsterType == 1)
								{
									playSound(SOUND_BEANSDIE);
								}
								else if (cur.entity.monsterType == 2)
								{
									playSound(SOUND_LIZDIE);
								}
								else if (cur.entity.monsterType == 3)
								{
									playSound(SOUND_BDDIE);
									
									FBP.setVisible(true);
									FBP.setPosition(mx, my);
									EntManager.addEnt(FBP);
									FBP.nextthink = 0;
									FBP.think(player);
									
									
									FBP.animate(new long[]{400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400}, 10, 24, false);
									
								}
								scene.detachChild(cur.entity);
							}
							else if (cur.entity.monsterType == 2)
							{
								playSound(SOUND_LIZHIT);
							}
							else if (cur.entity.monsterType == 3)
							{
								playSound(SOUND_BDHIT);
							}
							cur = cur.next;
							continue;
						}
						double dist1, dist2;
						double angle;
						
						//double printangle = Math.atan(((cur.entity.getX()+cur.entity.monsterWidth/2)-(player.getX()+(playerWidth/2)))/((player.getY()+(playerHeight/2)-(cur.entity.getX()+cur.entity.monsterHeight/2))))/(Math.PI/180);
						double printangle = Math.atan((cur.entity.getX()-player.getX())/(player.getY()-cur.entity.getX()))/(Math.PI/180);
						//Log.d("myTAG", "Angle: " + printangle);
						
						int shortStroke = 60;
						int longStroke = 52;
						
						float pointX;
						float pointY;
						
						
						
						
						float playerX = player.getX()+(playerWidth/2);
						float playerY = player.getY()+(playerHeight/2);
						
						//find shortest distance between player and monster 
						
						//top left corner
						dist1 = distance(player.getX()+(playerWidth/2),player.getY()+(playerHeight/2), cur.entity.getX(), cur.entity.getY());
						
						pointX = cur.entity.getX();
						pointY = cur.entity.getY();
						
						angle = angle((player.getY()+(playerHeight/2)),cur.entity.getY(), (player.getX()+(playerWidth/2)),cur.entity.getX());
						//top right corner
						dist2 = distance(player.getX()+(playerWidth/2), player.getY()+(playerHeight/2), cur.entity.getX()+cur.entity.monsterWidth, cur.entity.getY());
						if (dist2 < dist1)
						{
							pointX = cur.entity.getX()+cur.entity.monsterWidth;
							pointY = cur.entity.getY();
							dist1 = dist2;
							angle = angle(player.getY()+(playerHeight/2),cur.entity.getY(),(player.getX()+(playerWidth/2)),(cur.entity.getX()+cur.entity.monsterWidth) );
						}
						//bottom left corner
						dist2 = distance(player.getX()+(playerWidth/2), player.getY()+(playerHeight/2), cur.entity.getX(), cur.entity.getY()+cur.entity.monsterHeight);
						if (dist2 < dist1)
						{
							pointX = cur.entity.getX();
							pointY = cur.entity.getY()+cur.entity.monsterHeight;
							dist1 = dist2;
							angle = angle((player.getY()+(playerHeight/2)),(cur.entity.getY()+cur.entity.monsterHeight),(player.getX()+(playerWidth/2)),cur.entity.getX());
						}
						//bottom right corner
						dist2 = distance(player.getX()+(playerWidth/2), player.getY()+(playerHeight/2), cur.entity.getX()+cur.entity.monsterWidth, cur.entity.getY()+cur.entity.monsterHeight);
						if (dist2 < dist1)
						{
							pointX = cur.entity.getX()+cur.entity.monsterWidth;
							pointY = cur.entity.getY()+cur.entity.monsterHeight;
							dist1 = dist2;
							angle = angle((player.getY()+(playerHeight/2)),(cur.entity.getY()+cur.entity.monsterHeight),(player.getX()+(playerWidth/2)),(cur.entity.getX()+cur.entity.monsterWidth) );
						}
						Log.d("myTAG", "Angle:" + angle + " Dist: " + dist1);
						//var angle = Math.atan2(y2 - y1,x2 - x1);
							
						//angle between the middle of player and middle of monster
						
		
						
						
						if (player.dir == player.DIR_E)
						{
							if (dist1 <= shortStroke && ((pointX > playerX)))
							{
								cur.entity.takedamage(player.dmg);
								
								if (cur.entity.isAlive == false)
								{
									
									score.add(cur.entity.score);
									if (cur.entity.monsterType == 1)
									{
										playSound(SOUND_BEANSDIE);
									}
									else if (cur.entity.monsterType == 2)
									{
										playSound(SOUND_LIZDIE);
									}
									else if (cur.entity.monsterType == 3)
									{
										playSound(SOUND_BDDIE);
										
										FBP.setVisible(true);
										FBP.setPosition(mx, my);
										EntManager.addEnt(FBP);
										
										FBP.nextthink = 0;
										FBP.think(player);
										
										
										FBP.animate(new long[]{400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 400}, 10, 24, false);
										
									}
									scene.detachChild(cur.entity);
								}
								else if (cur.entity.monsterType == 2)
								{
									playSound(SOUND_LIZHIT);
								}
								else if (cur.entity.monsterType == 3)
								{
									playSound(SOUND_BDHIT);
								}
							}
						}
						else if (player.dir == player.DIR_N)
						{
							if (dist1 <= shortStroke && (pointY < playerY ) )
							{
								cur.entity.takedamage(player.dmg);
								
								if (cur.entity.isAlive == false)
								{
									
									score.add(cur.entity.score);
									if (cur.entity.monsterType == 1)
									{
										playSound(SOUND_BEANSDIE);
									}
									else if (cur.entity.monsterType == 2)
									{
										playSound(SOUND_LIZDIE);
									}
									else if (cur.entity.monsterType == 3)
									{
										playSound(SOUND_BDDIE);
									}
									scene.detachChild(cur.entity);
								}
								else if (cur.entity.monsterType == 2)
								{
									playSound(SOUND_LIZHIT);
								}
								else if (cur.entity.monsterType == 3)
								{
									playSound(SOUND_BDHIT);
								}
							}
						}
						else if (player.dir == player.DIR_S)
						{
							if (dist1 <= shortStroke && (pointY > playerY))
							{
								cur.entity.takedamage(player.dmg);
								
								if (cur.entity.isAlive == false)
								{
									
									score.add(cur.entity.score);
									if (cur.entity.monsterType == 1)
									{
										playSound(SOUND_BEANSDIE);
									}
									else if (cur.entity.monsterType == 2)
									{
										playSound(SOUND_LIZDIE);
									}
									else if (cur.entity.monsterType == 3)
									{
										playSound(SOUND_BDDIE);
									}
									scene.detachChild(cur.entity);
								}
								else if (cur.entity.monsterType == 2)
								{
									playSound(SOUND_LIZHIT);
								}
								else if (cur.entity.monsterType == 3)
								{
									playSound(SOUND_BDHIT);
								}
							}
						}
						else if (player.dir == player.DIR_W)
						{
							if (dist1 <= shortStroke && pointX < playerX)
							{
								cur.entity.takedamage(player.dmg);
								
								if (cur.entity.isAlive == false)
								{
									
									score.add(cur.entity.score);
									if (cur.entity.monsterType == 1)
									{
										playSound(SOUND_BEANSDIE);
									}
									else if (cur.entity.monsterType == 2)
									{
										playSound(SOUND_LIZDIE);
									}
									else if (cur.entity.monsterType == 3)
									{
										playSound(SOUND_BDDIE);
									}
									scene.detachChild(cur.entity);
								}
								else if (cur.entity.monsterType == 2)
								{
									playSound(SOUND_LIZHIT);
								}
								else if (cur.entity.monsterType == 3)
								{
									playSound(SOUND_BDHIT);
								}
							}
						}
						else if (player.dir == player.DIR_NE)
						{
							if (dist1 <= shortStroke && (pointY < playerY && pointX > playerX))
							{
								cur.entity.takedamage(player.dmg);
								
								if (cur.entity.isAlive == false)
								{
									
									score.add(cur.entity.score);
									if (cur.entity.monsterType == 1)
									{
										playSound(SOUND_BEANSDIE);
									}
									else if (cur.entity.monsterType == 2)
									{
										playSound(SOUND_LIZDIE);
									}
									else if (cur.entity.monsterType == 3)
									{
										playSound(SOUND_BDDIE);
									}
									scene.detachChild(cur.entity);
								}
								else if (cur.entity.monsterType == 2)
								{
									playSound(SOUND_LIZHIT);
								}
								else if (cur.entity.monsterType == 3)
								{
									playSound(SOUND_BDHIT);
								}
							}
						}
						else if (player.dir == player.DIR_SE)
						{
							if (dist1 <= shortStroke && (pointY > playerY && pointX > playerX))
							{
								cur.entity.takedamage(player.dmg);
								
								if (cur.entity.isAlive == false)
								{
									
									score.add(cur.entity.score);
									if (cur.entity.monsterType == 1)
									{
										playSound(SOUND_BEANSDIE);
									}
									else if (cur.entity.monsterType == 2)
									{
										playSound(SOUND_LIZDIE);
									}
									else if (cur.entity.monsterType == 3)
									{
										playSound(SOUND_BDDIE);
									}
									scene.detachChild(cur.entity);
								}
								else if (cur.entity.monsterType == 2)
								{
									playSound(SOUND_LIZHIT);
								}
								else if (cur.entity.monsterType == 3)
								{
									playSound(SOUND_BDHIT);
								}
							}
						}
						else if (player.dir == player.DIR_NW)
						{
							if (dist1 <= shortStroke && (pointY < playerY && pointX < playerX))
							{
								cur.entity.takedamage(player.dmg);
								
								if (cur.entity.isAlive == false)
								{
									
									score.add(cur.entity.score);
									if (cur.entity.monsterType == 1)
									{
										playSound(SOUND_BEANSDIE);
									}
									else if (cur.entity.monsterType == 2)
									{
										playSound(SOUND_LIZDIE);
									}
									else if (cur.entity.monsterType == 3)
									{
										playSound(SOUND_BDDIE);
									}
									scene.detachChild(cur.entity);
								}
								else if (cur.entity.monsterType == 2)
								{
									playSound(SOUND_LIZHIT);
								}
								else if (cur.entity.monsterType == 3)
								{
									playSound(SOUND_BDHIT);
								}
							}
						}
						else if (player.dir == player.DIR_SW)
						{
							if (dist1 <= shortStroke && (pointY > playerY && pointX < playerX))
							{
								cur.entity.takedamage(player.dmg);
								
								if (cur.entity.isAlive == false)
								{
									
									score.add(cur.entity.score);
									if (cur.entity.monsterType == 1)
									{
										playSound(SOUND_BEANSDIE);
									}
									else if (cur.entity.monsterType == 2)
									{
										playSound(SOUND_LIZDIE);
									}
									else if (cur.entity.monsterType == 3)
									{
										playSound(SOUND_BDDIE);
									}
									scene.detachChild(cur.entity);
								}
								else if (cur.entity.monsterType == 2)
								{
									playSound(SOUND_LIZHIT);
								}
								else if (cur.entity.monsterType == 3)
								{
									playSound(SOUND_BDHIT);
								}
								
							}
						}
					
						
					//	scene.getLastChild().attachChild(EntManager.cur.entity);
						
						cur = cur.next;
					}
					
					

					//gcgame.this.
					//mAttackSound.play();

				}
				else if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
				}
				return true;
			}
		};
		hud.registerTouchArea(button);
		hud.getFirstChild().attachChild(button);
		this.mBoundChaseCamera.setHUD(hud);
		
		if (!this.mMusic.isPlaying())
			this.mMusic.play();
		
		
		return scene;
	} //end onloadscene

	@Override
	public void onLoadComplete()
	{

	}
	
	
	
	//private int searchBtnId = Menu.FIRST;
    //private final int restartBtnId = Menu.FIRST;
	private final int mainmenuBtnId = Menu.FIRST;
    private final int  resumeBtnId = Menu.FIRST + 1;
    private final int saveBtnId = Menu.FIRST + 2;
    private int group1Id = 1;
    private int group2Id = 2;
    
    //SI Apr 13
    @Override
	protected void onCreate(final Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);
		//Check if userdata is loaded (if coming from LOAD activity)
		if(getIntent().hasExtra("playername")){
			playername = getIntent().getStringExtra("playername");
			if(getIntent().hasExtra("score")){
				score = new CScore(getIntent().getIntExtra("score", 0));
			}
			this.isUserdataLoaded = true;
		}
		else {
			this.isUserdataLoaded = false;
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	if(this.mMusic.isPlaying()) {
            this.mMusic.pause();
    	}
        //menu.add(group1Id,searchBtnId ,searchBtnId,"Resume");
    	//menu.add(group2Id,restartBtnId ,restartBtnId,"Restart");
    	menu.add(group2Id,mainmenuBtnId ,mainmenuBtnId,"Main Menu");
    	menu.add(group2Id,resumeBtnId , resumeBtnId,"Resume");
        menu.add(group2Id,saveBtnId, saveBtnId, "Save");
        // the following line will hide search
        // when we turn the 2nd parameter to false
        menu.setGroupVisible(1, false);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
	public boolean onMenuItemSelected(final int pFeatureId, final MenuItem pItem) {
    	
    	
    	
		switch(pItem.getItemId()) {
			/*Go to Main Menu*/
			case mainmenuBtnId:
				this.mMusic.stop();
				this.finish();
				return true;
			/*Resume the game*/
			case resumeBtnId:
				this.onStop();
				this.onRestart();
				this.mMusic.resume();
				return true;
			/*Save the game*/
			case saveBtnId:
				this.onStop();
				this.onRestart();
				Context ctx = getApplicationContext(); 
				CXML cxml = new CXML(ctx);
				cxml.Save(playername, score.getScore());
				Toast.makeText(this, "Game Saved", Toast.LENGTH_LONG).show();
				this.mMusic.resume();
				return true;
			default:
				return super.onMenuItemSelected(pFeatureId, pItem);
		}
	}
	
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
