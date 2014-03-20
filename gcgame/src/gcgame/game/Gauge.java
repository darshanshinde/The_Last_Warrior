package gcgame.game;

public class Gauge {
	// ===========================================================
	// Fields
	// ===========================================================
	public int maxPts;
	public int currentPts;
	public boolean isZero; 
	// ===========================================================
	// Constructors
	// ===========================================================
	public Gauge(final int pMaxPts){
		this.maxPts = pMaxPts;
		this.currentPts = this.maxPts;
		this.isZero = false;
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getMaxPts(){
		return this.maxPts;
	}
	public int getCurrentPts(){
		return this.currentPts;
	}
	public boolean isZero(){
		return this.isZero;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	public void subtract(final int subtractPts){
		this.currentPts = this.currentPts - subtractPts;
		if(this.currentPts <= 0){
			this.isZero = true;
			this.currentPts = 0;
		}else {
			this.isZero = false;
		}
	}
	public void add(final int addPts){
		this.currentPts = this.currentPts + addPts;
		if(this.currentPts > this.maxPts){
			this.currentPts = this.maxPts;
		}
	}
}