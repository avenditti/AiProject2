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
	double cjv;
	double cdv;
	double yjv;
	double ydv;
	boolean running = true;

	public GameController(Pane p, Scene scene, Controller c) {
		mS = scene;
		gB = p;
		this.c = c;
		es = new ArrayList<Entity>();
		chr = new Bot(this);
		aE(chr.root);
		mS.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.SPACE) {
					chr.jump();
					yjv = 1;
					new Thread() {
						@Override
						public void run() {
							try {
								Thread.sleep(10);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
							while(chr.y < chr.ground) {
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							yjv = 0;
						}
					}.start();
					return;
				} else if(event.getCode() == KeyCode.DOWN) {
					ydv = 1;
					chr.duck();
					new Thread() {
						@Override
						public void run() {
							try {
								Thread.sleep(10);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
							while(chr.height < 30) {
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							ydv = 0;
						}
					}.start();

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
			if(es.get(i).x - chr.x < 85) {
				if(!es.get(i).seen) {
					if(es.get(i).bird) {
						cdv = 1;
					} else {
						cjv = 1;
					}
					System.out.println(cjv + " " + cdv + " " + yjv + " " + ydv);
					System.out.println(c.w[0][0] + " " + c.w[0][1] + " " + c.w[1][0] + " " + c.w[1][1]);
					double[] res = c.think(new double[] {cjv, cdv, yjv, ydv });
					System.out.println(res[0] + " " + res[1]);
					if(res[0] > 0) {
						chr.jump();
					}
					if(res[1] > 0) {
						chr.duck();
					}
					es.get(i).seen = true;
				}
			} else {
				cjv = 0;
				cdv = 0;
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
