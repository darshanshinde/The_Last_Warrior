package gcgame.game;

public class CScore {
	// ===========================================================
	// Fields
	// ===========================================================
	//public int maxPts;
	public int score;
	public boolean isZero; 
	// ===========================================================
	// Constructors
	// ===========================================================
	public CScore(final int pScore){
		this.score = pScore;
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getScore(){
		return this.score;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	public void subtract(final int subtractPts){
		this.score = this.score - subtractPts;
		if(this.score <= 0){
			this.isZero = true;
			this.score = 0;
		}else {
			this.isZero = false;
		}
	}
	public void add(final int addPts){
		this.score = this.score + addPts;
	}
}