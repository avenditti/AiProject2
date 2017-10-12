import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	public static final double boardSizeX = 500;
	public static final double boardSizeY = 500;

	public static void main(String[] args) {
		System.out.println(c);
		Main.launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Controller c = new Controller(1, 4, 0, 0, 10, 10);
		Pane root = new Pane();
		Scene scene = new Scene(root, boardSizeX, boardSizeY);
		Rectangle g = new Rectangle(500, 200, Color.GOLDENROD);
		g.relocate(0,300);
		Rectangle s = new Rectangle(500, 300, Color.SKYBLUE);
		root.getChildren().addAll(g, s);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
			}
		});

		Thread t = new Thread(new GameController(root, scene, c));
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@SuppressWarnings("deprecation")
			@Override
			public void handle(WindowEvent event) {
				t.stop();
				stage.close();
			}

		});
		stage.setScene(scene);
		stage.show();
		t.start();
	}

}
