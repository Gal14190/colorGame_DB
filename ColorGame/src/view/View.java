//Gal Ashkenazi 315566752 - Final Test
package view;

import java.io.FileInputStream;
import java.util.ArrayList;

import model.Icon;
import model.Medal;
import model.Square;
import model.Theme;
import model.TopPlayers;
import model.User;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Handle the all views in this program.
 * @author GalAs
 *
 */
public class View {
	public static enum GAME_FIELD_SCREEN {START, SCORE_TABLE, ENTER_NAME, GAME};
	
	private Square[][] arrSquare;
	private SquareView[][] gameTools;
	private ArrayList<TopPlayers> topPlayers;
	private ArrayList<Theme> themes;
	private ArrayList<Icon> icons;
	private User user;
	
	WindowApplicationView application, setupWindow, profileWindow, medalsWindow;
	
	private VBox vbGame;
	private Text txtScoreValue, txtLevelValue, txtScore, txtLevel, txtSetName, txtGameTitle, txtTop10;
	private Button btnNewGame, btnStartGame, btnSubmit, btnCancel, btnDarkMode, btnProfile;
	private RadioButton rbLevel1, rbLevel2, rbLevel3;
	private TextField tbPlayerName;
	private CheckBox cbRandomColor;
	private ComboBox<Integer> setup_iconBox, setup_themeBox;
	
	/**
	 * c'tor
	 */
	public View() {
			// setup view
		
			btnProfile = new Button("Profile");
			btnDarkMode = new Button("Dark Mode");
			btnDarkMode.getStyleClass().add("darkMode_btn");
			
			btnNewGame = new Button("New Game");
			btnNewGame.getStyleClass().add("newGame_btn");
			
			btnStartGame = new Button("Start game");
			btnStartGame.getStyleClass().add("startGame_btn");
			
			btnSubmit = new Button("Submit");

			btnCancel = new Button("Cancel"); 
			btnCancel.getStyleClass().add("cancel_btn");
			
			ToggleGroup tgRadioButtons = new ToggleGroup();
			rbLevel1 = new RadioButton("Level 1");
			rbLevel1.setSelected(true);
			rbLevel1.setToggleGroup(tgRadioButtons);
			
			rbLevel2 = new RadioButton("Level 2");
			rbLevel2.setToggleGroup(tgRadioButtons);
			
			rbLevel3 = new RadioButton("Level 3");
			rbLevel3.setToggleGroup(tgRadioButtons);
			
			cbRandomColor = new CheckBox("Random Color");
			cbRandomColor.setSelected(true);
			
			tbPlayerName = new TextField();
			
			txtSetName = new Text("Enter your name");
			
			txtTop10 = new Text("-- TOP 10 --");
			txtTop10.getStyleClass().add("top10_txt");
			
			txtGameTitle = new Text("Colors Game");
			txtScore = new Text("Score: ");
			txtLevel = new Text("Level: ");
			
			vbGame = new VBox();
			
			// setup page
			setup_iconBox = new ComboBox<Integer>();
			setup_themeBox = new ComboBox<Integer>();
	}

	/**
	 * Show the window application
	 * @param stage
	 */
	public void show(Stage stage) {
		try {	
			// show the title "Colors Game" in the top
			VBox vbTop = new VBox();
			vbTop.setAlignment(Pos.CENTER);
			vbTop.getChildren().addAll(txtGameTitle);
			
			// start the program with the start screen
			vbGame.getChildren().add(gameField(GAME_FIELD_SCREEN.START));
			disableButton_newGame(true);
			
			// create and show the window application
			application = new WindowApplicationView(stage, 600, 500);
			application.show(controlPanel(), scorePanel(), vbTop, vbGame);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showMedal(ArrayList<Medal> medals) {
		try {
			VBox vbMedals = new VBox();
			
			Text winTitle = new Text("Medals:\n");
			winTitle.setFont(Font.font(null, FontWeight.BOLD, 24));
			vbMedals.getChildren().add(winTitle);
			
			if(medals.size() < 1) {
				Text empy = new Text("Play for win medals!");
				empy.setFont(Font.font(null, FontWeight.LIGHT, 22));
				vbMedals.getChildren().add(empy);
			} else 
				
			for(Medal m : medals) {
				HBox hbMedal = new HBox();
				// icon
				Image image = new Image(new FileInputStream("medal\\" + m.getPath()));  
				
				ImageView imageView = new ImageView(image); 
				  
				//setting the fit height and width of the image view 
				imageView.setFitHeight(50); 
				imageView.setFitWidth(50);
				
				// title
				Text title = new Text(m.getTitle());
				title.setFont(Font.font(null, FontWeight.BOLD, 14));
				
				// details
				Text details = new Text("\n   " + m.getDetails());
				details.setFont(Font.font(null, FontWeight.LIGHT, 12));
				
				hbMedal.getChildren().addAll(imageView, title, details);
				
				vbMedals.getChildren().add(hbMedal);
			}
			 		
			vbMedals.setAlignment(Pos.CENTER);
			
			Stage medalStage = new Stage();
			medalsWindow = new WindowApplicationView(medalStage, 500, 400);
			medalsWindow.show(new Pane(), new Pane(), new Pane(), vbMedals);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void showProfile() {
		try {
			VBox vbProfile = new VBox();
			
			// icon
			Image image = new Image(new FileInputStream("icons\\" + user.getIcon().getPath()));  
			
			ImageView imageView = new ImageView(image); 
			  
			//setting the fit height and width of the image view 
			imageView.setFitHeight(100); 
			imageView.setFitWidth(100); 
			
			// nickname
			Text nickname = new Text("\n" + user.getPlayerName());
			nickname.setFont(Font.font(null, FontWeight.BOLD, 20));
			
			// date was join
			Text dateJoin = new Text(user.getDate_join());
			dateJoin.setFont(Font.font(null, FontWeight.LIGHT, 11));
			
			// average score
			Text avgScore = new Text("Average score: " + user.getAverage());
			avgScore.setFont(Font.font(null, FontWeight.LIGHT, 11));
			
			
			vbProfile.getChildren().addAll(imageView, nickname, dateJoin, avgScore);
			
			vbProfile.setAlignment(Pos.CENTER);
			
			Stage profileStage = new Stage();
			profileWindow = new WindowApplicationView(profileStage, 300, 200);
			profileWindow.show(new Pane(), new Pane(), new Pane(), vbProfile);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void showSetupWindow() {
		try {
		// ICONS
		VBox vbIcons = new VBox();
			
		vbIcons.getChildren().add(new Text("ICONS:\n\n"));
		HBox hbIcons = new HBox();
		
		   
		//Setting the image view 
		for (Icon icon : icons) {
			Image image = new Image(new FileInputStream("icons\\" + icon.getPath()));  
			
			ImageView imageView = new ImageView(image); 
			  
			//setting the fit height and width of the image view 
			imageView.setFitHeight(50); 
			imageView.setFitWidth(50); 
			  
			//Creating a Group object  
			hbIcons.getChildren().add(imageView);
		}
		
		vbIcons.getChildren().add(hbIcons);
		
		
		setup_iconBox.setValue(user.getIcon().getId());
		
		if(setup_iconBox.getItems().size() == 0) {
			for (Icon i : icons) {
				setup_iconBox.getItems().add(i.getId());
			}
		}
		vbIcons.getChildren().add(setup_iconBox);
		
		// THEMES
		VBox vbThemes = new VBox();
		
		vbThemes.getChildren().add(new Text("THEMES:\n\n"));
		
		for (Theme t : themes) {
			HBox hbTheme = new HBox();
			Text title = new Text(t.getId() + ")" + t.getTitle() + "\t");
			hbTheme.getChildren().add(title);
			
			for (Color color : t.getColors()) {
				Rectangle sColor = new Rectangle(20,20);
				sColor.setFill(color);
				hbTheme.getChildren().add(sColor);
			}
			
			vbThemes.getChildren().add(hbTheme);
		}
		
		
		setup_themeBox.setValue(user.getTheme().getId());
	
		if(setup_themeBox.getItems().size() == 0) {
			for (Theme t : themes) {
				setup_themeBox.getItems().add(t.getId());
			}
		}
		vbThemes.getChildren().add(setup_themeBox);
		
		Stage setupStage = new Stage();
		setupWindow = new WindowApplicationView(setupStage, 500, 200);
		setupWindow.show(new Pane(), vbIcons, new Pane(), vbThemes);
		
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Collection of 4 type of screen.
	 * Start, Score table, Enter name and the game.
	 * 
	 * @param screen - type of screen to show
	 * @return
	 */
	public Pane gameField(GAME_FIELD_SCREEN screen) {
		VBox vb = new VBox();
		vb.getStyleClass().add("gameField");
		
		switch (screen) {
		case START:
			vb.getChildren().addAll(btnStartGame, cbRandomColor, rbLevel1, rbLevel2, rbLevel3); // show start game button
			break;
		case SCORE_TABLE: 
			// show score table and start game button
			VBox vbTableScore = new VBox();
			vbTableScore.setAlignment(Pos.CENTER);
			
			// show top 10 table
			TopPlayersTableView topPlayersView = new TopPlayersTableView(this.topPlayers);
			TableView<TopPlayers> tvTopPlayers = topPlayersView.show();
			
			// view title, table, start game button and radio buttons
			vbTableScore.getChildren().addAll(txtTop10, tvTopPlayers, btnStartGame, cbRandomColor, rbLevel1, rbLevel2, rbLevel3);
			
			vb.getChildren().add(vbTableScore);
			break;
		case ENTER_NAME: 
			// show insert name field and submit button 
			VBox vbEnterName = new VBox();
			vbEnterName.setMaxSize(200, 200);
			
			tbPlayerName.clear();
			
			HBox vhButtons = new HBox();
			vhButtons.setAlignment(Pos.CENTER);
			vhButtons.getChildren().addAll(btnSubmit, btnCancel);
			
			vbEnterName.getChildren().addAll(txtSetName, tbPlayerName, vhButtons);
			vb.getChildren().add(vbEnterName);
			break;
		case GAME: 
			VBox vbGame = new VBox();
			vbGame.setAlignment(Pos.CENTER);
			
			gameTools = new SquareView[arrSquare.length][arrSquare[0].length];
			
			// show the squares
			for(int i = 0; i < arrSquare.length; i++) {
				HBox vhRowSquares = new HBox(); 
				vhRowSquares.setMaxSize(0, 0);
				for(int j = 0; j < arrSquare[0].length; j++) {
					Square squareDetails = arrSquare[i][j];
					gameTools[i][j] = new SquareView(vhRowSquares, 
													squareDetails.getColor(), 
													squareDetails.isSmallSize());
					gameTools[i][j].draw(); // draw the square
					gameTools[i][j].setOnAction(squareDetails.getEvent());
				}
				vbGame.getChildren().addAll(vhRowSquares);
			}
			
			vb.getChildren().add(vbGame);
			break;
		}
		
		return vb;
	}
	
	/**
	 * Show the control panel.
	 * Include new game button and radio buttons the select level game
	 * @return
	 */
	private Pane controlPanel() {
		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER_LEFT);
		vb.setPadding(new Insets(10));
	
		vb.getChildren().addAll(btnNewGame, btnDarkMode, btnProfile);
		
		return vb;
	}
	
	/**
	 * Show score panel.
	 * Include score and current level.
	 * @return
	 */
	private Pane scorePanel() {
		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.setPadding(new Insets(10));
		
		HBox hbScore = new HBox();
		txtScoreValue = new Text("000");
		hbScore.getChildren().addAll(txtScore, txtScoreValue);
		
		HBox hbLevel = new HBox();
		txtLevelValue = new Text("000");		
		hbLevel.getChildren().addAll(txtLevel, txtLevelValue);
		
		vb.getChildren().addAll(hbScore, hbLevel);
		return vb;
	}
	
	/**
	 * Update score
	 * 
	 * @param value
	 */
	public void setScore(int value) {
		this.txtScoreValue.setText(Integer.toString(value));
	}
	
	/**
	 * Update level
	 * 
	 * @param value
	 */
	public void setLevel(int value) {
		this.txtLevelValue.setText(Integer.toString(value));
	}
	
	/**
	 * Get player name field
	 * @return
	 */
	public String getPlayerName() {
		return tbPlayerName.getText();
	}
	
	/**
	 * Select screen type the display
	 * @param screen
	 */
	public void updateGameField(GAME_FIELD_SCREEN screen) {
		vbGame.getChildren().clear();
		vbGame.getChildren().add(gameField(screen));
	}
	
	/**
	 * Update square array the display
	 * @param _arrSquare
	 */
	public void updateColorArray(model.Square[][] _arrSquare) {
		this.arrSquare = _arrSquare;
	}
	
	/**
	 * Set event to new game button
	 * @param event
	 */
	public void setClickListener_newGame(EventHandler<ActionEvent> event) {
		btnNewGame.setOnAction(event);
	}
	
	/**
	 * Set event to start game button
	 * @param event
	 */
	public void setClickListener_startGame(EventHandler<ActionEvent> event) {
		btnStartGame.setOnAction(event);
	}
	
	/**
	 * Set event to submit button
	 * @param event
	 */
	public void setClickListener_submitScore(EventHandler<ActionEvent> event) {
		btnSubmit.setOnAction(event);
	}
	
	/**
	 * Set event to cancel button
	 * @param event
	 */
	public void setClickListener_cancel(EventHandler<ActionEvent> event) {
		btnCancel.setOnAction(event);
	}
	
	/**
	 * Set event to level radio button
	 * @param event
	 */
	public void setClickListener_levelToggle(int rb, ChangeListener<Boolean> event) {
		if(rb == 1)
			rbLevel1.selectedProperty().addListener(event);
		else if(rb == 2)
			rbLevel2.selectedProperty().addListener(event);
		else if(rb == 3)
			rbLevel3.selectedProperty().addListener(event);
	}
	
	/**
	 * Set setup theme event
	 * @param event
	 */
	public void setClickListener_setupThemeToggle(EventHandler<ActionEvent> event) {
		setup_themeBox.setOnAction(event);
	}
	public ComboBox<Integer> getTbTheme() {
		return setup_themeBox;
	}
	
	/**
	 * Set setup icon event
	 * @param event
	 */
	public void setClickListener_setupIconToggle(EventHandler<ActionEvent> event) {
		setup_iconBox.setOnAction(event);
	}
	public ComboBox<Integer> getTbIcon() {
		profileWindow.close();
		return setup_iconBox;
	}
	
	/**
	 * Set dark mode event
	 * @param event
	 */
	public void setClickListener_darkMode(EventHandler<ActionEvent> event) {
		btnDarkMode.setOnAction(event);
	}
	
	/**
	 * Set profile button event
	 */
	public void setClickListener_profile(EventHandler<ActionEvent> event) {
		btnProfile.setOnAction(event);
	}
	
	/**
	 * onClick event on the game tools
	 * @param event
	 */
	public boolean setOnClick_gameTool(int posX, int posY) {
		return gameTools[posX][posY].select();
	}
	
	/**
	 * Update the game tools display
	 * @param newArrSquare
	 */
	public void updateGameToolArray(model.Square[][] newArrSquare) {
		this.updateColorArray(newArrSquare);
		this.updateGameField(GAME_FIELD_SCREEN.GAME);
	}
	
	/**
	 * Update the TOP 10 players list
	 * @param _topPlayers
	 */
	public void updateTopPlayers(ArrayList<TopPlayers> _topPlayers) {
		this.topPlayers = _topPlayers;
	}
	
	/**
	 * Disable new game button
	 * @param value
	 */
	public void disableButton_newGame(boolean value) {
		btnNewGame.setDisable(value);
	}
	
	public boolean randomModeGame() {
		return cbRandomColor.isSelected();
	}
	
	
	public void setThemes(ArrayList<Theme> themes) {
		this.themes = themes;
	}

	public void setIcons(ArrayList<Icon> icons) {
		this.icons = icons;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Set dark mode
	 */
	public void setDarkMode() {
		if(!application.isDarkMode()) {
			application.setDarkBackground();
			
			txtGameTitle.getStyleClass().add("darkMode_txt");
			txtTop10.getStyleClass().add("darkMode_txt");
			
			rbLevel1.getStyleClass().add("darkMode_txt");
			rbLevel2.getStyleClass().add("darkMode_txt");
			rbLevel3.getStyleClass().add("darkMode_txt");
			cbRandomColor.getStyleClass().add("darkMode_txt");
			
			txtScore.getStyleClass().add("darkMode_txt");
			txtLevel.getStyleClass().add("darkMode_txt");
			txtScoreValue.getStyleClass().add("darkMode_txt");
			txtLevelValue.getStyleClass().add("darkMode_txt");
			txtSetName.getStyleClass().add("darkMode_txt");
			
			btnDarkMode.setText("Light Mode");
			btnDarkMode.getStyleClass().remove("darkMode_btn");
			btnDarkMode.getStyleClass().add("lightMode_btn");
		} else {
			application.setDarkBackground();
			
			txtGameTitle.getStyleClass().remove("darkMode_txt");
			txtTop10.getStyleClass().remove("darkMode_txt");
			
			rbLevel1.getStyleClass().remove("darkMode_txt");
			rbLevel2.getStyleClass().remove("darkMode_txt");
			rbLevel3.getStyleClass().remove("darkMode_txt");
			cbRandomColor.getStyleClass().remove("darkMode_txt");
			
			txtScore.getStyleClass().remove("darkMode_txt");
			txtLevel.getStyleClass().remove("darkMode_txt");
			txtScoreValue.getStyleClass().remove("darkMode_txt");
			txtLevelValue.getStyleClass().remove("darkMode_txt");
			txtSetName.getStyleClass().remove("darkMode_txt");
			
			btnDarkMode.setText("Dark Mode");
			btnDarkMode.getStyleClass().add("darkMode_btn");
			btnDarkMode.getStyleClass().remove("lightMode_btn");
		}
	}
}
