import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class GameController implements Runnable{

	Scene mS;
	Pane gB;
	ArrayList<Entity> es;
	Bot chr;
	double tls = System.currentTimeMillis();
	Controller c;
	double yjv;
	double ydv;

	public GameController(Pane p, Scene scene, Controller c) {
		mS = scene;
		gB = p;
		es = new ArrayList<Entity>();
		chr = new Bot(this);
		aE(chr.root);
		mS.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.SPACE) {
					yjv = 1;
					new Thread() {
						@Override
						public void run() {
							while((yjv -= .1) > 0) {
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					};
					return;
				} else if(event.getCode() == KeyCode.DOWN) {
					chr.duck();

				} else if(event.getCode() == KeyCode.UP) {
				}
			}

		});
		mS.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
			}

		});
	}

	@Override
	public void run() {
		startGameLoop();
	}

	public Entity createObstacle(boolean isRock) {
		Entity e = new Entity(new Rectangle(20, 20));
		e.setX(500);
		e.setY(isRock ? 290.0 : 265.0);
		e.bird = !isRock;
		e.setXSpeed(-2);
		e.setYSpeed(0);
		aE(e.root);
		es.add(e);
		return e;
	}

	public void startGameLoop() {
		while(true) {
			checkEntities();
			if(System.currentTimeMillis() - tls > 1500) {
				if(Math.random() < .5) {
					createObstacle(false);
				}else if(Math.random() < .5) {
					createObstacle(true);
				}
			tls = System.currentTimeMillis();
			}
			chr.doPhysics();
			for(Entity e : es) {
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
		for(int i = 0; i < es.size(); i++ ) {
			if(es.get(i).x - chr.x < 100 && es.get(i).x - chr.x > 80) {
				System.out.println(1 / (es.get(i).x / 250));
			}
			if(chr.checkCollision(es.get(i))) {
				System.out.println("COLLISION");
				rE(es.get(i).root);
				es.remove(i);
			} else if(es.get(i).x < -100) {
				rE(es.get(i).root);
				es.remove(i);
			}
		}
	}
	/*
	 * Removes an entity and discards it
	 */

	public void rE(Node e) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				gB.getChildren().remove(e);
			}

		});
	}

	public void aE(Node e) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				gB.getChildren().add(e);
			}

		});
	}

}
