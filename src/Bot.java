
import javafx.scene.shape.Rectangle;

public class Bot extends Entity{


	double ground = 270;
	GameController gc;

	public Bot(GameController gc) {
		super(new Rectangle(50,50));
		this.gc = gc;
		setX(100.0);
		setY(270.0);
	}

	@Override
	public void doPhysics() {

			if(y > ground) {
				y = ground;
				ySpeed = 0;
			} else if(y != ground){
				ySpeed += .0925;
			}
			super.doPhysics();
	}

	public Rectangle stand() {
		Rectangle root = new Rectangle(50,50);
		gc.rE(this.root);
		ground = 325 - root.getHeight();
		root.relocate(x, y -= 25);
		height = root.getHeight();
		width = root.getWidth();
		gc.aE(root);
		return this.root = root;
	}

	public Rectangle duck() {
		Rectangle root = new Rectangle(50,25);
		gc.rE(this.root);
		root.relocate(x, y += 25);
		ground = 325 - root.getHeight();
		height = root.getHeight();
		width = root.getWidth();
		gc.aE(root);
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(750);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				stand();
			}
		}.start();
		return this.root = root;
	}

	public void jump() {
		if(y == ground) {
			setYSpeed(-4);
		}
	}

}
