import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXTemplate extends Application {
  /* hash map to store scenes */
  HashMap < String, Scene > myScene;
  /* welcome text */
  TextField text;
  /* an area displaying if it is player one or player two’s turn to go */
  TextField textTwo;
  /* ) an area displaying each move made. It should have the following info for each
move:*/
  TextField textThree;
  /* enter gamescreen buttom */
  Button startButton;
  /* menu bar in gamescreen */
  MenuBar menu;
  /* new game menu option */
  MenuItem optionTwo;
  /* player tracker */
  int player;
  /* keeps the track of winpattern */
  int winPattern;
  /* stack to store coordinates, helpful in reverse the move */
  Stack < GameButton > coordinateStack;
  /* win stack*/
  Stack < GameButton > winStack;
  /* gridpane */
  GridPane gridPane;
  /* event handlers */
  EventHandler < ActionEvent > myHandler;
  EventHandler < ActionEvent > myHandlerTwo;
  EventHandler < ActionEvent > returnButtons;
  /* stores all buttons present on the gridpane */
  GameButton[][] buttonArray;
  /* this is a replica board of buttonArray, wrote all the game logic using this, helpful in writing test cases */
  char[][] replicaBoard;
  //use this for pausing between actions
  PauseTransition pause = new PauseTransition(Duration.seconds(3));
  TextField winnermessage;
  /* new game button */
  GameButton newGame;
  /* exit button */
  GameButton exit;
  MenuItem optionOne;
  GameButton back;
  int winningPlayer;
  GameButton Arr[] ;

  boolean tie;
  int validMoveTracker;
  char themeSelector ;
  Vector < GameButton > vec ;
 GameButton[] ArrTwo ;
  
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    launch(args);
  }

  //feel free to remove the starter code from this method
  @Override
  public void start(Stage primaryStage) throws Exception {
    // TODO Auto-generated method stub
    primaryStage.setTitle("Welcome to JavaFX");

    /* initilizing all the objects and attributes*/
    validMoveTracker = 0;
    tie = false;
    player = 1;
    themeSelector = 'a';
    winningPlayer = 0;
    winPattern = 0;
    Arr = new GameButton[4];
    vec = new Vector < GameButton > (4);
    ArrTwo = new GameButton[4];
    buttonArray = new GameButton[6][7];
    replicaBoard = new char[6][7];
    myScene = new HashMap < String, Scene > ();
    text = new TextField();
    textTwo = new TextField();
    textThree = new TextField();
    startButton = new Button("Go to Game screen");
    startButton.setPrefWidth(250);
    startButton.setPrefHeight(50);
    coordinateStack = new Stack < GameButton > ();
    optionTwo = new MenuItem("New game"); //menu items go inside a menu
    newGame = new GameButton();
    newGame.setPrefWidth(250);
    newGame.setPrefHeight(50);
    exit = new GameButton();
    exit.setPrefWidth(250);
    exit.setPrefHeight(50);
    back = new GameButton();
    back.setPrefWidth(250);
    back.setPrefHeight(50);

    back.setText("go back to game");
    back.setFocusTraversable(false);

    //styling CSS way
    text.setStyle("-fx-font-size: 18;" + "-fx-border-size: 20;" +
      "-fx-border-color: purple;");

    text.setText("Welcome to ConnectFour");
    text.setEditable(false);
    text.setFocusTraversable(false);

    textTwo.setText("player 1 turn togo");
    textThree.setText("Player x moved to row,col. valid/invalid move.");
    textTwo.setFocusTraversable(false);
    textThree.setFocusTraversable(false);
    winnermessage = new TextField("winner announcement");
    gridPane = new GridPane();

    /* setting gap between each of the grid pane box */
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    /* performing event handler operation on start button, this will navigate to game screen */
    startButton.setOnAction(e -> primaryStage.setScene(myScene.get("game")));
    back.setOnAction(e -> primaryStage.setScene(myScene.get("game")));

    optionOne = new MenuItem("How to play"); //menu items go inside a menu
    /* this will navigate to new game */
    optionTwo.setOnAction(new EventHandler < ActionEvent > () {
      public void handle(ActionEvent a) {
        /* resets the game */
        resetGame();
        /* again freshly setting up the secodnd screen */
        primaryStage.setScene(myScene.get("game"));

      }
    });
    //event handler for how to play button */
    optionOne.setOnAction(new EventHandler < ActionEvent > () {
      public void handle(ActionEvent a) {
        myScene.put("How", fourthScreen());
        /* again freshly setting up the secodnd screen */
        primaryStage.setScene(myScene.get("How"));

      }
    });
    /* event handler for new game button */
    newGame.setOnAction(new EventHandler < ActionEvent > () {
      public void handle(ActionEvent a) {
        /* resets the game */
        resetGame();
        /* again freshly setting up the secodnd screen */
        primaryStage.setScene(myScene.get("game"));

      }
    });
    /* event handler for exit button */
    exit.setOnAction(new EventHandler < ActionEvent > () {
      public void handle(ActionEvent a) {
        System.exit(0);
      }
    });

    //pause for 3 seconds then switch scene from picture buttons to original layout
    pause.setOnFinished(e -> primaryStage.setScene(myScene.get("winner")));

    //two scenes returned from two methods; put in hashmap
    myScene.put("scene", createControlScene());
    myScene.put("game", createGameScene());

    /* initially setting up to show the welcome screen */
    primaryStage.setScene(myScene.get("scene"));
    primaryStage.show();
  }

  /* resetGame this will reset all the buttons and sets everything to default and gives us a new game */
  public void resetGame() {
    for (int x = 0; x < 6; x++) {
      for (int i = 0; i < 7; i++) {
        buttonArray[x][i].setPlayerTurn(0);
        buttonArray[x][i].setCheckButton(false);
        replicaBoard[x][i] = '.';
        buttonArray[x][i].setStyle("-fx-color:gray");
        buttonArray[x][i].setMoveMade(-1);
        buttonArray[x][i].setDisable(false);
      }
    }
    player = 1;
    textTwo.setText("player 1 turn togo");
    textThree.setText("Player x moved to row,col. valid/invalid move.");
    winPattern = 0;
    coordinateStack.clear();
    validMoveTracker = 0;
    vec.clear();
  }
  
  /*getters used for testing */
  public boolean getTie() {
	  return this.tie;
  }
  
  public int getWinPattern() {
	  return this.winPattern;
  }

  /*
   * method to populate a GridPane with buttons and attach a handler to each button
   */
  public void addGrid() {

    for (int x = 0; x < 6; x++) {

      for (int i = 0; i < 7; i++) {
        GameButton b1 = new GameButton();
        b1.setPrefWidth(75);
        b1.setPrefHeight(75);
        b1.setOnAction(myHandler);
        b1.setXcoordinate(x);
        b1.setYcoordinate(i);
        buttonArray[x][i] = b1;
        buttonArray[x][i].setXcoordinate(x);
        buttonArray[x][i].setYcoordinate(i);
        buttonArray[x][i].setStyle("-fx-color:gray");
        replicaBoard[x][i] = '.';
        System.out.println(buttonArray[x][i].getXcoordinate() + ", " + buttonArray[x][i].getYcoordinate());
        gridPane.add(b1, i, x);

      }
    }
  }

  // method to create fourth screen
  public Scene fourthScreen() {
    BorderPane pane = new BorderPane();
    pane.setPadding(new Insets(70));

    //Creating a Text object 
    Text rules = new Text();
    //Setting font to the text 
    rules.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
    //Setting the color 
    rules.setFill(Color.RED);

    //Setting the Stroke  
    rules.setStrokeWidth(2);

    // Setting the stroke color
    rules.setStroke(Color.BLUE);
    //Setting the text to be added. 
    rules.setText("How to Play: ");

    //setting the position of the text 
    rules.setX(50);
    rules.setY(50);

    TextArea area = new TextArea();
    //Setting number of pages
    area.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
    area.setText("Connect Four is played on a grid of 7 columns and 6 rows (see image above). It is a two\n" +
      "player game where each player takes a turn dropping a checker into a slot (one of the\n" +
      "columns) on the game board. That checker will fall down the column and either land on\n" +
      "top of another checker or land on the bottom row.\n" +
      "To win the game, a player needs to get 4 of their checkers in a vertical, horizontal or\n" +
      "diagonal row before the other player");
    area.setEditable(false);
    area.setFocusTraversable(false);
    area.setPrefColumnCount(15);
    area.setPrefHeight(250);
    area.setPrefWidth(900);

    VBox paneCenter = new VBox(50, rules, area, back);

    pane.setCenter(paneCenter);
    //		pane.setLeft(startButton);

    // accordint to selected theme by user
    if (themeSelector == 'a') {
      BackgroundImage bgImg = new BackgroundImage(new Image("banner.jpg"),
        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
      pane.setBackground(new Background(bgImg));
    } if (themeSelector == 'b') {
      BackgroundImage bgImg = new BackgroundImage(new Image("cars.jpg"),
        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
      pane.setBackground(new Background(bgImg));
    } if (themeSelector == 'c') {
      BackgroundImage bgImg = new BackgroundImage(new Image("island.jpg"),
        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
      pane.setBackground(new Background(bgImg));
    }

    return new Scene(pane, 850, 750);
  }

  // method to create third screen winner announcement screen
  public Scene thirdScreen() {
    BorderPane pane = new BorderPane();
    pane.setPadding(new Insets(70));
    
  //Creating a Text object 
    Text rules = new Text();
    //Setting font to the text 
    rules.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
    //Setting the color 
    rules.setFill(Color.RED);

    //Setting the Stroke  
    rules.setStrokeWidth(2);

    // Setting the stroke color
    rules.setStroke(Color.BLUE);
    //Setting the text to be added. 
    rules.setText("Winner Announcement: ");
    //setting the position of the text 

    System.out.println("player= " + player);;
    if (validMoveTracker != 42) {
      winnermessage.setText("Player " + player + " had won the connect 4");
      winnermessage.setEditable(false);
      winnermessage.setFocusTraversable(false);
      winnermessage.setStyle("-fx-font-size: 18;" + "-fx-border-size: 20;" +
        "-fx-border-color: red;" + "-fx-color:black");
    } else if (validMoveTracker == 42) {
      winnermessage.setText("It's a tie");
      winnermessage.setEditable(false);
      winnermessage.setFocusTraversable(false);
      winnermessage.setStyle("-fx-font-size: 18;" + "-fx-border-size: 20;" +
        "-fx-border-color: red;" + "-fx-color:black");
    }

    newGame.setText("New Game");
    

    exit.setText("EXIT");
    VBox paneCenter = new VBox(50, rules, winnermessage, newGame, exit);

    pane.setCenter(paneCenter);
    //		pane.setLeft(startButton);
    // themer setter
    if (themeSelector == 'a') {
      BackgroundImage bgImg = new BackgroundImage(new Image("banner.jpg"),
        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
      pane.setBackground(new Background(bgImg));
    } if (themeSelector == 'b') {
      BackgroundImage bgImg = new BackgroundImage(new Image("cars.jpg"),
        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
      pane.setBackground(new Background(bgImg));
    } if (themeSelector == 'c') {
      BackgroundImage bgImg = new BackgroundImage(new Image("island.jpg"),
        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
        BackgroundSize.DEFAULT);
      pane.setBackground(new Background(bgImg));
    }

    return new Scene(pane, 850, 750);
  }

  //method to create our first scene with controls
  public Scene createControlScene() {

    BorderPane pane = new BorderPane();
    pane.setPadding(new Insets(70));
    

    //Creating a Text object 
    Text rules = new Text();
    //Setting font to the text 
    rules.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
    //Setting the color 
    rules.setFill(Color.RED);

    //Setting the Stroke  
    rules.setStrokeWidth(2);

    // Setting the stroke color
    rules.setStroke(Color.BLUE);
    //Setting the text to be added. 
    rules.setText("Welcome to Connect 4!!!! ");
    //setting the position of the text 
   

    HBox tempBox= new HBox(20, rules);  
    tempBox.setAlignment(Pos.CENTER);

    VBox paneCenter = new VBox(70, tempBox, startButton);

    pane.setCenter(paneCenter);
    //			pane.setLeft(startButton);
    BackgroundImage bgImg = new BackgroundImage(new Image("ab.jpg"),
      BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
      BackgroundSize.DEFAULT);
    pane.setBackground(new Background(bgImg));

    return new Scene(pane, 850, 750);
  }

  /* checkMove function, this check if a move is valid move or not */
  public boolean checkMove(int xCor, int yCor) {
    if (xCor == 5) {
      return true;
    } else if (replicaBoard[xCor + 1][yCor] != '.') {
      return true;
    } else {
      return false;
    }
  }
  /* this is downward win algorithm checks if a player got an connect in vertical direction */
  public boolean winAlgorithm(int xCor, int yCor) {
    int winCounter = 0;
    char whichPlayer = replicaBoard[xCor][yCor];
    //downwards win check;
    while (winCounter <= 4 && xCor <= 5 && whichPlayer == replicaBoard[xCor][yCor]) {
      winCounter++;
      xCor = xCor + 1;
    }
    if (winCounter == 4) {
      winPattern = 1; /* this is first win pattern */
      return true;
    }
    return false;
  }

  // horizontal win pattern check
  public boolean horizontalWin(int x, int y) {
    int winCounter = 1;
    char whichPlayer = replicaBoard[x][y];
    vec.clear();
    vec.add(buttonArray[x][y]);
    int newXcor = x;
    int newYcor = y + 1;
    for (; newYcor <= 6 && replicaBoard[newXcor][newYcor] == whichPlayer;) {
      vec.add(buttonArray[newXcor][newYcor]);
      winCounter++;
      newYcor++;
    }
    newYcor = y - 1;
    newXcor = x;
    for (; newYcor >= 0 && replicaBoard[newXcor][newYcor] == whichPlayer;) {
      vec.add(buttonArray[newXcor][newYcor]);
      winCounter++;
      newYcor--;
    }
    if (winCounter >= 4) {
      winPattern = 2;
      return true;
    }
    return false;
  }

  public boolean diagnolOnes(int x, int y) {
    int winCounter = 1;
    ArrTwo[0] = buttonArray[x][y];
    
    char whichPlayer = replicaBoard[x][y];
    int newXcor = x - 1;
    int newYcor = y - 1;
    for (; newXcor >= 0 && newXcor <= 5 &&
      newYcor >= 0 && newYcor <= 6 &&
      replicaBoard[newXcor][newYcor] == whichPlayer;) {
    	ArrTwo[winCounter] = buttonArray[newXcor][newYcor];
      winCounter++;
      newXcor = newXcor - 1;
      newYcor = newYcor - 1;
    }
    newXcor = x + 1;
    newYcor = y + 1;
    for (; newXcor >= 0 && newXcor <= 5 &&
      newYcor >= 0 && newYcor <= 6 &&
      replicaBoard[newXcor][newYcor] == whichPlayer;) {
    	ArrTwo[winCounter] = buttonArray[newXcor][newYcor];
      winCounter++;
      newXcor = newXcor + 1;
      newYcor = newYcor + 1;
    }
    if (winCounter >= 4) {
      winPattern = 3;
      return true;
    }
    return false;
  }
  //		
  public boolean diagnolTwos(int x, int y) {
    int winCounter = 1;
    Arr[0] = buttonArray[x][y];
    char whichPlayer = replicaBoard[x][y];
    int newXcor = x - 1;
    int newYcor = y + 1;
    for (; newXcor >= 0 && newXcor <= 5 &&
      newYcor >= 0 && newYcor <= 6 &&
      replicaBoard[newXcor][newYcor] == whichPlayer;) {
    	Arr[winCounter] = buttonArray[newXcor][newYcor];
      winCounter++;
      newXcor = newXcor - 1;
      newYcor = newYcor + 1;
    }
    newXcor = x + 1;
    newYcor = y - 1;
    for (; newXcor >= 0 && newXcor <= 5 &&
      newYcor >= 0 && newYcor <= 6 &&
      replicaBoard[newXcor][newYcor] == whichPlayer;) {
    	Arr[winCounter] = buttonArray[newXcor][newYcor];
      winCounter++;
      newXcor = newXcor + 1;
      newYcor = newYcor - 1;
    }
    if (winCounter >= 4) {
      winPattern = 4;
      return true;
    }
    return false;
  }

  /* this function disables all the button and highlights winning connect 4 in a new color */
  public void winTitle(int xCor, int yCor) {
    for (int x = 0; x < 6; x++) {
      for (int i = 0; i < 7; i++) {
        //					b1.setCheckButton(true);
        buttonArray[x][i].setDisable(true);
        //remove if any problem
        replicaBoard[x][i] = '.';
      }
    }
    if (winPattern == 1) {
      int iter = 0;
      while (iter < 4) {
        buttonArray[xCor + iter][yCor].setStyle("-fx-color: green");
        iter++;
      }
    } else if (winPattern == 2) {
      for (int i = 0; i < 4; i++) {
        GameButton temp = vec.get(i);
        temp.setStyle("-fx-color: green");
      }
    } else if (winPattern ==3) {
    	int i =0;
    	while ( i<ArrTwo.length) {
    		ArrTwo[i].setStyle("-fx-color:green");
    		i++;
    	}
    } else if (winPattern ==4) {
    	int i =0; 
    	while (i<Arr.length) {
    		Arr[i].setStyle("-fx-color:green");
    		i++;
    	}
    }
  }
  
  public int getPlayer() {
	  return this.player;
  }
  
  public int getThemeSelector() {
	  return this.themeSelector;
  }

  /* this function is executed when user clicks on reverse move option */
  public void reverseMove() {
    /* if there are no moves to undo it doesn't enter the below block */
    if (coordinateStack.empty() == false) {
      GameButton tempButton = coordinateStack.pop();
      /* setting the latest button to deafualt */
      int xCor = tempButton.getXcoordinate();
      int yCor = tempButton.getYcoordinate();
      buttonArray[xCor][yCor].setPlayerTurn(0);
      buttonArray[xCor][yCor].setCheckButton(false);
      replicaBoard[xCor][yCor] = '.';
      buttonArray[xCor][yCor].setStyle("-fx-color:gray");
      buttonArray[xCor][yCor].setMoveMade(-1);
      /* setting up player turns */
      if (player == 2) {
        player = 1;
        buttonArray[xCor][yCor].setPlayerTurn(1);
        textTwo.setText("player 1 turn to go");
        textThree.setText("Player One did a backtrack on " + xCor + ", " + yCor + " using reverse move");
      } else {
        player = 2;
        buttonArray[xCor][yCor].setPlayerTurn(2);
        textTwo.setText("player 2 turn to go");
        textThree.setText("Player Two did a backtrack on " + xCor + ", " + yCor + " using reverse move");
      }
      buttonArray[xCor][yCor].setDisable(false);
    }

  }
  
/* whenplayer one makes a move this function gest executed */
  public void makeMovePlayerOne(GameButton b1, int xCor, int yCor) {
    textTwo.setStyle("-fx-font-size: 18;" + "-fx-border-size: 20;" +
      "-fx-border-color: purple;");
    textThree.setStyle("-fx-font-size: 18;" + "-fx-border-size: 20;" +
      "-fx-border-color: purple;");

    textTwo.setText("player 2 turn");
    textThree.setText("Player one moved to " + xCor + "," + yCor + "This is a valid move.");
    textTwo.setFocusTraversable(false);
    textThree.setFocusTraversable(false);

    buttonArray[xCor][yCor].setCheckButton(true);
    replicaBoard[xCor][yCor] = 'A';
    buttonArray[xCor][yCor].setMoveMade(1);
    coordinateStack.push(b1);
    validMoveTracker++;
/* if game is tiue */
    if (validMoveTracker == 42) {
      myScene.put("winner", thirdScreen());
      pause.play(); //calls setOnFinished
    }
/* winner check */
    boolean winner = winAlgorithm(xCor, yCor);
    boolean winnerLeft = horizontalWin(xCor, yCor);
    boolean diagnolOne = diagnolOnes(xCor, yCor);
    boolean diagnolTwo = diagnolTwos(xCor, yCor);
    if (winner == true || winnerLeft == true || diagnolOne == true || diagnolTwo == true) {
      textThree.setStyle("-fx-font-size: 18;" + "-fx-border-size: 20;" +
        "-fx-border-color: purple;");
      textThree.setText("Player " + player + " had won the connect 4");
      winningPlayer = 1;
      winTitle(xCor, yCor);
      myScene.put("winner", thirdScreen());
      pause.play(); //calls setOnFinished
    }
/* next player */
    player = 2;

  }

  /* when player 2 makes a move this block executes */
  public void makeMovePlayerTwo(GameButton b1, int xCor, int yCor) {
    textTwo.setStyle("-fx-font-size: 18;" + "-fx-border-size: 20;" +
      "-fx-border-color: purple;");
    textThree.setStyle("-fx-font-size: 18;" + "-fx-border-size: 20;" +
      "-fx-border-color: purple;");

    textTwo.setText("player 1 turn to go");
    textThree.setText("Player Two moved to " + xCor + "," + yCor + "This is a valid move.");
    textTwo.setFocusTraversable(false);
    textThree.setFocusTraversable(false);

    buttonArray[xCor][yCor].setCheckButton(true);
    replicaBoard[xCor][yCor] = 'B';
    buttonArray[xCor][yCor].setMoveMade(2);
    coordinateStack.push(b1);

    validMoveTracker++;
    /* if game is a tie */
    if (validMoveTracker == 42) {
      myScene.put("winner", thirdScreen());
      pause.play(); //calls setOnFinished
    }
    
/* wiiner checkj */
    boolean winner = winAlgorithm(xCor, yCor);
    boolean winnerLeft = horizontalWin(xCor, yCor);
    boolean diagnolOne = diagnolOnes(xCor, yCor);
    boolean diagnolTwo = diagnolTwos(xCor, yCor);
    if (winner == true || winnerLeft == true || diagnolOne == true || diagnolTwo == true) {
      textThree.setStyle("-fx-font-size: 18;" + "-fx-border-size: 20;" +
        "-fx-border-color: purple;");
      textThree.setText("Player " + player + " had won the connect 4");
      winningPlayer = 2;
      winTitle(xCor, yCor);
      myScene.put("winner", thirdScreen());
      pause.play(); //calls setOnFinished

    }

    // update player
    player = 1;

  }

  //method to create second scene with clickable buttons
  public Scene createGameScene() {

    menu = new MenuBar(); //a menu bar takes menus as children
    Menu mOne = new Menu("Game Play Menu"); //a menu goes inside a menu bar
    Menu mTwo = new Menu("Themes");
    Menu mThree = new Menu("Options");
    MenuItem iOne = new MenuItem("Reverse the move"); //menu items go inside a menu
    MenuItem themeOne = new MenuItem("original theme"); //menu items go inside a menu
    MenuItem themeTwo = new MenuItem("theme one"); //menu items go inside a menu
    MenuItem themeThree = new MenuItem("theme two"); //menu items go inside a menu

    MenuItem optionThree = new MenuItem("exit"); //menu items go inside a menu

    mOne.getItems().add(iOne); //add menu item to first menu

    mTwo.getItems().add(themeOne); //add menu item to second menu
    mTwo.getItems().add(themeTwo); //add menu item to second menu
    mTwo.getItems().add(themeThree); //add menu item to second menu

    mThree.getItems().add(optionOne); //add menu item to third menu
    mThree.getItems().add(optionTwo); //add menu item to third menu
    mThree.getItems().add(optionThree); //add menu item to third menu

    menu.getMenus().addAll(mOne, mTwo, mThree); //add two menus to the menu bar

    BorderPane pane = new BorderPane();

    //event handler for menu item
    iOne.setOnAction(new EventHandler < ActionEvent > () {
      public void handle(ActionEvent a) {
        if (winPattern == 0) {
          reverseMove();
        }
      }
    });

    //event handler for menu item - Default Theme
    themeOne.setOnAction(new EventHandler < ActionEvent > () {
      public void handle(ActionEvent a) {
        themeSelector = 'a';
        BackgroundImage bgImg = new BackgroundImage(new Image("banner.jpg"),
          BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);
        pane.setBackground(new Background(bgImg));
      }
    });

    //event handler for menu item - Theme Two
    themeTwo.setOnAction(new EventHandler < ActionEvent > () {
      public void handle(ActionEvent a) {
        themeSelector = 'b';
        BackgroundImage bgImg = new BackgroundImage(new Image("cars.jpg"),
          BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);
        pane.setBackground(new Background(bgImg));
      }
    });

    //			 //event handler for menu item- Theme Three
    themeThree.setOnAction(new EventHandler < ActionEvent > () {
      public void handle(ActionEvent a) {
        themeSelector = 'c';
        //					pane.setStyle("-fx-background-color: black;");
        BackgroundImage bgImg = new BackgroundImage(new Image("island.jpg"),
          BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);
        pane.setBackground(new Background(bgImg));
      }
    });

    optionThree.setOnAction(new EventHandler < ActionEvent > () {
      public void handle(ActionEvent a) {
        System.exit(0);
      }
    });

    //event handler is attached to each button in the GridPane
    myHandler = new EventHandler < ActionEvent > () {

      public void handle(ActionEvent e) {

        GameButton b1 = (GameButton) e.getSource();

        // move valid
        int xCor = b1.getXcoordinate();
        int yCor = b1.getYcoordinate();
        System.out.println(xCor + ", " + yCor);
        boolean check = checkMove(xCor, yCor);

        if (player == 1 && check == true) {
          b1.setStyle("-fx-color: yellow;");
          b1.setPlayerTurn(2);
          b1.setCheckButton(true);
          makeMovePlayerOne(b1, xCor, yCor);

          b1.setDisable(true);

        } else if (player == 2 && check == true) {
          b1.setStyle("-fx-color: red;");
          b1.setPlayerTurn(1);
          b1.setCheckButton(true);
          makeMovePlayerTwo(b1, xCor, yCor);
          b1.setDisable(true);

        } else {
          System.out.println("try again");
          textThree.setStyle("-fx-font-size: 18;" + "-fx-border-size: 20;" +
            "-fx-border-color: purple;");
          textThree.setText("Player " + player + "moved to " + xCor + "," + yCor + ". This is NOT a valid move. " + "Player " + player + "pick again.");
        }
      }
    };

    /* setting the gridpaNE to center */
    gridPane.setAlignment(Pos.CENTER);
    addGrid();
    VBox paneCenter = new VBox(20, menu, textTwo, textThree, gridPane);

    /* setting uyp deafult theme */
    pane.setTop(paneCenter);
    //			pane.setStyle("-fx-background-color: black;");
    BackgroundImage bgImg = new BackgroundImage(new Image("banner.jpg"),
      BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
      BackgroundSize.DEFAULT);
    pane.setBackground(new Background(bgImg));
    return new Scene(pane, 850, 750);

  }

}