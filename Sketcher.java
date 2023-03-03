import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Sketcher extends Application {
        
    private Canvas canvas;     // The canvas where the user will draw.
    private GraphicsContext g; // The graphics context for drawing on the canvas.

    private double prevX, prevY;  // Previous mouse position for doMouseDown
    private double firstX, firstY; // Used for the burst tool, to record where the first mouse press was.
    private TextField textField;  // The text that is stamped on the canvas.
    
    private int currentTool;   // The currently selected tool.
    private Color textColor;   // The currently selected text color. 
    
    private final static int DRAW_TOOL = 0;
    private final static int STAMP_TOOL = 1;
    private final static int BURST_TOOL = 2;
    
    /**
     * Create and set up the program window and event handling.
     */
    public void start(Stage stage) {
        canvas = new Canvas(800,600);
        g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE);
        g.fillRect(0, 0, 800, 600);
        g.setLineCap( StrokeLineCap.ROUND );
        
        textColor = Color.BLACK; // set initial text color to black
        
        
        canvas.setOnMousePressed( evt -> doMouseDown(evt.getX(), evt.getY()) );
        canvas.setOnMouseDragged( evt -> doMouseDragged(evt.getX(), evt.getY()) );
        
        
        BorderPane content = new BorderPane();
        content.setCenter(canvas);
        content.setTop( makeMenus() );
        content.setBottom( makeBottom() );
        
        Scene scene = new Scene(content);
        stage.setScene(scene);
        stage.setTitle("Sketcher: Draw on a Canvas");
        stage.setResizable(false); 
        stage.show();
    }
    
    /**
     * Sets up variables to allow drawing of a line.
     * @param x x-coordinate of mouse click.
     * @param y y-coordinate of mouse click.
     */
    private void doMouseDown(double x, double y) {
    	if(currentTool == 0) {
    		prevX = x;	
    		prevY = y;
    	}
    	else if(currentTool == 1){
    		String str = textField.getText();
    		g.setFill(textColor);
    		g.fillText(str, x, y);
    	}
    	else if(currentTool == 2) {
    		firstX = x;
    		firstY = y;
    	}
	}
    
    /**
     * Draws a line and updates variables.
     * @param x x-coordinate of mouse click.
     * @param y y-coordinate of mouse click.
     */
    private void doMouseDragged(double x, double y) {
    	// curve
    	if(currentTool == 0) {
    		g.strokeLine(prevX, prevY, x, y);
    	
    		// update prevX,prevY for next method call
    		prevX = x;
    		prevY = y;
    	}	
    	// burst
    	else if(currentTool == 2) {
    		g.strokeLine(x, y, firstX, firstY);
    	}
    }
    
    /**
     * Make an HBox for the bottom of the window, containing a "Clear" button
     * and a text input box to be used for the Text Stamper tool.
     */
    private HBox makeBottom() {
        
        Button clearButton = new Button("Clear");
        clearButton.setOnAction( evt -> { 
            g.setFill(Color.WHITE); 
            g.fillRect(0,0,800,600); 
        });
        
        Label label = new Label("Text for Stamper:");
        
        textField = new TextField("Hello World");  // an input box containing "Hello World"
        textField.setPrefColumnCount(30); // make it big enough to contain 30 chars
        
        HBox container = new HBox(15, clearButton, label, textField);
        container.setAlignment(Pos.CENTER); // center contents in the HBox
        container.setStyle( // CSS styling for the HBox
            "-fx-padding: 5px; -fx-border-color: black; -fx-background-color: lightgray" );
        return container;
    }
    

    
    /**
     * Create a menu bar to be added to the top of the program window.
     */
    private MenuBar makeMenus() {
        MenuBar menubar;
        Menu menu;
        MenuItem item;
        
        menubar = new MenuBar();  // Create the menu bar.
        
        // ------------- COLOR MENU ------------- //
        menu = new Menu("CurveColor");   // Create the color menu
        menubar.getMenus().add(menu);    //  ... and add it to the menu bar.
        
        ToggleGroup colorGroup = new ToggleGroup();
        item = new RadioMenuItem("Black");
        item.setOnAction( evt -> g.setStroke(Color.BLACK) );
        ((RadioMenuItem) item).setToggleGroup(colorGroup);
        ((RadioMenuItem) item).setSelected(true);
        menu.getItems().add(item);
        item = new RadioMenuItem("Red");
        item.setOnAction( evt -> g.setStroke(Color.RED) );
        ((RadioMenuItem) item).setToggleGroup(colorGroup);
        menu.getItems().add(item);
        item = new RadioMenuItem("Green");
        item.setOnAction( evt -> g.setStroke(Color.GREEN) );
        ((RadioMenuItem) item).setToggleGroup(colorGroup);
        menu.getItems().add(item);
        item = new RadioMenuItem("Blue");
        item.setOnAction( evt -> g.setStroke(Color.BLUE) );
        ((RadioMenuItem) item).setToggleGroup(colorGroup);
        menu.getItems().add(item);
        item = new RadioMenuItem("Orange");
        item.setOnAction( evt -> g.setStroke(Color.ORANGE) );
        ((RadioMenuItem) item).setToggleGroup(colorGroup);
        menu.getItems().add(item);
        item = new RadioMenuItem("Yellow");
        item.setOnAction( evt -> g.setStroke(Color.YELLOW) );
        ((RadioMenuItem) item).setToggleGroup(colorGroup);
        menu.getItems().add(item);
        item = new RadioMenuItem("Brown");
        item.setOnAction( evt -> g.setStroke(Color.BROWN) );
        ((RadioMenuItem) item).setToggleGroup(colorGroup);
        menu.getItems().add(item);
        
        // ------------- TEXT COLOR MENU  ------------- //
        
        menu = new Menu("TextColor");   // Create the color menu
        menubar.getMenus().add(menu);    //  ... and add it to the menu bar.
        
        ToggleGroup textColorGroup = new ToggleGroup();
        item = new RadioMenuItem("Black");
        item.setOnAction( evt -> textColor = Color.BLACK );
        ((RadioMenuItem) item).setToggleGroup(textColorGroup);
        ((RadioMenuItem) item).setSelected(true);
        menu.getItems().add(item);
        item = new RadioMenuItem("Red");
        item.setOnAction( evt -> textColor = Color.RED );
        ((RadioMenuItem) item).setToggleGroup(textColorGroup);
        menu.getItems().add(item);
        item = new RadioMenuItem("Green");
        item.setOnAction( evt -> textColor = Color.GREEN );
        ((RadioMenuItem) item).setToggleGroup(textColorGroup);
        menu.getItems().add(item);
        item = new RadioMenuItem("Blue");
        item.setOnAction( evt -> textColor = Color.BLUE );
        ((RadioMenuItem) item).setToggleGroup(textColorGroup);
        menu.getItems().add(item);
        item = new RadioMenuItem("Orange");
        item.setOnAction( evt -> textColor = Color.ORANGE );
        ((RadioMenuItem) item).setToggleGroup(textColorGroup);
        menu.getItems().add(item);
        item = new RadioMenuItem("Yellow");
        item.setOnAction( evt -> textColor = Color.YELLOW );
        ((RadioMenuItem) item).setToggleGroup(textColorGroup);
        menu.getItems().add(item);
        item = new RadioMenuItem("Brown");
        item.setOnAction( evt -> textColor = Color.BROWN );
        ((RadioMenuItem) item).setToggleGroup(textColorGroup);
        menu.getItems().add(item);
        
        // ------------- CURVE WIDTH MENU ------------- //
        menu = new Menu("CurveWidth");
        menubar.getMenus().add(menu);
        
        ToggleGroup curveWidthGroup = new ToggleGroup();
        item = new RadioMenuItem("1");
        item.setOnAction( evt -> g.setLineWidth(1) );
        ((RadioMenuItem) item).setToggleGroup(curveWidthGroup);
        ((RadioMenuItem) item).setSelected(true);
        menu.getItems().add(item);
        item = new RadioMenuItem("2");
        item.setOnAction( evt -> g.setLineWidth(2) );
        ((RadioMenuItem) item).setToggleGroup(curveWidthGroup);
        menu.getItems().add(item);
        item = new RadioMenuItem("5");
        item.setOnAction( evt -> g.setLineWidth(5) );
        ((RadioMenuItem) item).setToggleGroup(curveWidthGroup);
        menu.getItems().add(item);
        item = new RadioMenuItem("10");
        item.setOnAction( evt -> g.setLineWidth(10) );
        ((RadioMenuItem) item).setToggleGroup(curveWidthGroup);
        menu.getItems().add(item);
        item = new RadioMenuItem("20");
        item.setOnAction( evt -> g.setLineWidth(20) );
        ((RadioMenuItem) item).setToggleGroup(curveWidthGroup);
        menu.getItems().add(item);
        
        // ------------- FONT SIZE MENU ------------- //
        menu = new Menu("FontSize");
        menubar.getMenus().add(menu);
        
        ToggleGroup fontSizeGroup = new ToggleGroup();
        item = new RadioMenuItem("12");	
        item.setOnAction( evt -> g.setFont(Font.font(12)) );
        ((RadioMenuItem) item).setToggleGroup(fontSizeGroup);
        ((RadioMenuItem) item).setSelected(true);
        menu.getItems().add(item);
        item = new RadioMenuItem("24");	
        item.setOnAction( evt -> g.setFont(Font.font(24)) );
        ((RadioMenuItem) item).setToggleGroup(fontSizeGroup);
        menu.getItems().add(item);
        item = new RadioMenuItem("36");	
        item.setOnAction( evt -> g.setFont(Font.font(36)) );
        ((RadioMenuItem) item).setToggleGroup(fontSizeGroup);
        menu.getItems().add(item);
        
        
        // ------------- TOOL MENU ------------- //
        menu = new Menu("Tool");
        menubar.getMenus().add(menu);
        
        ToggleGroup toolGroup = new ToggleGroup();
        item = new RadioMenuItem("Draw Curve");
        item.setOnAction( evt -> currentTool = 0);
        ((RadioMenuItem) item).setToggleGroup(toolGroup);
        ((RadioMenuItem) item).setSelected(true);
        menu.getItems().add(item);
        item = new RadioMenuItem("Stamp Text");
        item.setOnAction( evt -> currentTool = 1);
        ((RadioMenuItem) item).setToggleGroup(toolGroup);
        menu.getItems().add(item);
        item = new RadioMenuItem("Burst");
        item.setOnAction( evt -> currentTool = 2);
        ((RadioMenuItem) item).setToggleGroup(toolGroup);
        menu.getItems().add(item);
        
            
        return menubar;
    }
    
    public static void main(String[] args) {
        launch(); // Run the Application; this will not return.
    }

}