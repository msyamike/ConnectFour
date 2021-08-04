import javafx.scene.control.Button;

public class GameButton extends Button{
	//cooordinates
	private int xCoordinate, yCoordinate;
	//playerturn
	private int playerTurn;
	//boolean filled
	private boolean checkButton;
	// moveMade
	private int moveMade;
	
	/* constructor for GameButton Class */
	public GameButton() {
		this.xCoordinate = 0;
		this.yCoordinate = 0;
		this.playerTurn = 1;
		this.checkButton = false;
		moveMade = -1;
	}
	
	/* below are the getters and setters that allows us to access the private variables */
	public int getMoveMade() {
		return this.moveMade;
	}
	
//	getters and setters that allows us to access the private variables *
	public void setMoveMade(int moveMade) {
		this.moveMade = moveMade;
	}
	
//	getters and setters that allows us to access the private variables *
	public int getXcoordinate() {
		return this.xCoordinate;
	}
	
//	getters and setters that allows us to access the private variables *
	public int getYcoordinate() {
		return this.yCoordinate;
	}
	
//	getters and setters that allows us to access the private variables *
	public int getPlayerTurn() {
		return this.playerTurn;
	}
	
//	getters and setters that allows us to access the private variables *
	public boolean getCheckButton() {
		return this.checkButton;
	}
//	getters and setters that allows us to access the private variables *
	public void setXcoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
//	getters and setters that allows us to access the private variables *
	
	public void setYcoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
//	getters and setters that allows us to access the private variables *
	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn; 
	}
//	getters and setters that allows us to access the private variables *
	public void setCheckButton(boolean checkButton) {
		this.checkButton = checkButton; 
	}
}
