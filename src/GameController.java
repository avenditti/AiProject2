import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GameController implements Runnable{

	Scene mainScene;
	Pane gameBoard;
	ArrayList<Entity> entities;
	Entity chr;
	double tls = System.currentTimeMillis();

	public GameController(Pane p, Scene scene) {
		mainScene = scene;
		gameBoard = p;
		entities = new ArrayList<Entity>();
	}

	@Override
	public void run() {
		startGameLoop();
	}

	public Entity createRock() {
		Entity e = new Entity(new Circle(20), false);
		e.setX(500.0);
		e.setY(270.0);
		e.setXSpeed(-2);
		e.setYSpeed(0);
		addEntity(e.getNode());
		entities.add(e);
		return e;
	}

	public Entity createBird() {
		Entity e = new Entity(new Circle(20), false);
		e.setX(500.0);
		e.setY(235.0);
		e.setXSpeed(-2);
		e.setYSpeed(0);
		addEntity(e.getNode());
		entities.add(e);
		return e;
	}

	public Entity createCharacter() {
		Entity e = new Entity(new Circle(20), true);
		e.setX(100.0);
		e.setY(270.0);
		addEntity(e.getNode());
		mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.SPACE) {
					if(chr.getY() == Entity.ground) {
						chr.setYSpeed(-4);
					}
					return;
				} else if(event.getCode() == KeyCode.DOWN) {
					gameBoard.getChildren().remove(chr.getNode());
					gameBoard.getChildren().add(chr.setNode(new Rectangle(10,10)));
				} else if(event.getCode() == KeyCode.UP) {
					gameBoard.getChildren().remove(chr.getNode());
					gameBoard.getChildren().add(chr.setNode(new Circle(20)));
				}
			}

		});
		mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
			}

		});
		return e;
	}

	public void startGameLoop() {
		chr = createCharacter();
		createRock();
		while(true) {
			checkEntities();
			if(System.currentTimeMillis() - tls > 1500) {
				if(Math.random() < .5) {
					createBird();
				}else if(Math.random() < .5) {
					createRock();
				}
			tls = System.currentTimeMillis();
			}
			chr.doPhysics();
			for(Entity e : entities) {
				e.doPhysics();
			}
			try {
				Thread.sleep(8);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void checkEntities() {
		for(int i = 0; i < entities.size(); i++ ) {
			if(chr.checkCollision(entities.get(i).getX(), entities.get(i).getY())) {
				System.out.println("COLLISION");
				removeEntity(entities.get(i).getNode());
				entities.remove(i);
			} else if(entities.get(i).getX() < -100) {
				removeEntity(entities.get(i).getNode());
				entities.remove(i);
			}
		}
	}

	public void removeEntity(Node e) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				gameBoard.getChildren().remove(e);
			}

		});
	}

	public void addEntity(Node e) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				gameBoard.getChildren().add(e);
			}

		});
	}

}
