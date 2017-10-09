
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Entity {

	Node root;
	double ySpeed;
	double xSpeed;
	double x;
	double y;
	boolean isCircle;
	double radius;
	boolean gravity;

	public Entity(Node size, boolean gravity) {
		this.gravity = gravity;
		root = size;
		if(root instanceof Circle) {
			this.x = ((Circle) root).getCenterX();
			this.y = ((Circle) root).getCenterY();
			this.radius = ((Circle) root).getRadius();
			isCircle = true;
		} else if(root instanceof Rectangle) {
			this.x = ((Rectangle) root).getX();
			this.y = ((Rectangle) root).getY();
		}
	}

	public void doPhysics() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if(gravity) {
					if(y > GameController.ground) {
						y = GameController.ground;
						ySpeed = 0;
					} else if(y != GameController.ground){
						ySpeed += .0925;
					}
				}
				root.relocate(x = x + xSpeed, y = y + ySpeed);
			}

		});
	}

	public boolean checkCollision(double x2, double  y2) {
		return Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2)) < 40;
	}

	public void setXSpeed(double d) {
		this.xSpeed = d;
	}

	public void setYSpeed(double d) {
		this.ySpeed = d;
	}

	public Node getNode() {
		return root;
	}

	public void setX(double d) {
		root.relocate(d, y);
		this.x = d;
	}

	public void setY(double d) {
		root.relocate(x, d);
		this.y = d;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setNode(Node root) {

	}

}
