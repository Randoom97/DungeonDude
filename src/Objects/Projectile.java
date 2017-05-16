package Objects;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Projectile {

	private double xPos, yPos;
	private double xUpdate, yUpdate;

	public int TTL = 90, TL = 0;

	public BufferedImage img[] = new BufferedImage[4];

	public boolean exists, frost = false, poison = false;

	public Projectile() {
		for (int i = 0; i < img.length; i++) {
			img[i] = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
		}
	}

	public int getX() {
		return (int) xPos;
	}

	public int getY() {
		return (int) yPos;
	}

	public void update() {
		if (exists) {
			xPos += xUpdate;
			yPos += yUpdate;
			if (TL >= TTL) {
				poison = false;
				frost = false;
				exists = false;
			}
			TL++;
		} else {
			TL = 0;
		}
	}

	public void fire(double angle, int speed, int x, int y) {

		xPos = x;
		yPos = y;
		// Image rotation
		for (int i = 0; i < img.length; i++) {
			BufferedImage img2 = img[i];
			AffineTransform tx = new AffineTransform();
			tx.translate(img2.getHeight() / 2, img2.getWidth() / 2);
			tx = AffineTransform.getRotateInstance(angle + Math.PI, img2.getHeight(), img2.getWidth());
			tx.translate(img2.getWidth() / 2, img2.getHeight() / 2);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			img[i] = null;
			img[i] = op.filter(img2, img[i]);
		}
		// speed
		xUpdate = speed * Math.cos(angle);
		yUpdate = speed * Math.sin(angle);

		this.exists = true;
	}

	public Rectangle getBounds() {
		if (exists == true) {
			return new Rectangle((int) this.xPos - 24, (int) this.yPos - 24, 48, 48);
		}
		return new Rectangle(0, 0, 0, 0);
	}
}
