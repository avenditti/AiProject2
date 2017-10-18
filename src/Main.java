import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	public static final double boardSizeX = 500;
	public static final double boardSizeY = 500;

	public static void main(String[] args) {
		Main.launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Controller c = new Controller(2, 4, 0, 0, 0, 0, 10, 0, 0, 10);
		Pane root = new Pane();
		Scene scene = new Scene(root, boardSizeX, boardSizeY);
		Rectangle g = new Rectangle(500, 200, Color.GOLDENROD);
		Label l = new Label("Jump: Space \nDuck: Down Arrow");
		l.setFont(Font.font("Consolas"));
		g.relocate(0,300);
		Rectangle s = new Rectangle(500, 300, Color.SKYBLUE);
		root.getChildren().addAll(g, s, l);
		GameController gc = new GameController(root, scene, c);
		Thread t = new Thread(gc);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@SuppressWarnings("deprecation")
			@Override
			public void handle(WindowEvent event) {
				t.stop();
				stage.close();
				gc.running = false;
			}

		});
		stage.setScene(scene);
		stage.show();
		t.start();
	}

}
