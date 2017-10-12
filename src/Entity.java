
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Entity {

	private Node root;
	private double ySpeed;
	private double xSpeed;
	private double x;
	private double y;
	private boolean isCircle;
	private double radius;
	private boolean gravity;
	private double height;
	private double width;
	static double ground = 270;

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
					if(y > ground) {
						y = ground;
						ySpeed = 0;
					} else if(y != ground){
						ySpeed += .0925;
					}
				}
				root.relocate(x = x + xSpeed, y = y + ySpeed);
			}

		});
	}

	public boolean checkCollision(double x2, double  y2) {
		if(isCircle) {
			return Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2)) < radius;
		} else {
			return x2 < x + width / 2  && x2 > x - width / 2 && y2 < y + height / 2  && y2 > y - height / 2;
		}
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

	public Node setNode(Node root) {
		if(root instanceof Circle) {
			root.relocate(x, y);
			x = root.getLayoutX();
			y = root.getLayoutY();
			ground = 270;
			isCircle = true;
			radius = ((Circle) root).getRadius();
			return this.root = root;
		} else if(root instanceof Rectangle){
			root.relocate(x, y + 20);
			y += 20;
			ground = 300;
			isCircle = false;
			width = ((Rectangle) root).getWidth();
			height = ((Rectangle) root).getHeight();
			return this.root = root;
		}
		return null;
	}

}
