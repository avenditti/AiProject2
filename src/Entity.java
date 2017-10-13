
import javafx.application.Platform;
import javafx.scene.shape.Rectangle;

public class Entity {

	Rectangle root;
	double ySpeed;
	double xSpeed;
	double x;
	double y;
	double height;
	double width;
	boolean bird;
	public boolean seen;

	public Entity(Rectangle rec) {
		root = rec;
		this.x = ((Rectangle) root).getX();
		this.y = ((Rectangle) root).getY();
	}

	public void doPhysics() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				root.relocate(x = x + xSpeed, y = y + ySpeed);
			}

		});
	}

	public boolean checkCollision(Entity e) {
		return root.getBoundsInParent().intersects(e.root.getBoundsInParent());
	}

	public void setXSpeed(double d) {
		this.xSpeed = d;
	}

	public void setYSpeed(double d) {
		this.ySpeed = d;
	}
	public void setX(double d) {
		root.relocate(d, y);
		this.x = d;
	}

	public void setY(double d) {
		root.relocate(x, d);
		this.y = d;
	}

}
