package application;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SpaceInvaders extends Application {
	

	private static final Random random = new Random();
	static final Image Spaceship = new Image("file:/Users/nimarahimian/Downloads/Space-Invaders-master/images/starshiip.png"); 
	static final Image Explosion = new Image("file:/Users/nimarahimian/Downloads/Space-Invaders-master/images/explosionny.png");
	static final int Explosion_widht = 510;
	static final int Explosion_height = 510;
	static final int Explosion_rows = 8;
	static final int Explosion_col = 8;
	static final int EXPLOSION_STEPS = 64;
	
	static final Image Meteors [] = {
			new Image("file:/Users/nimarahimian/Downloads/Space-Invaders-master/images/1.png"),
			new Image("file:/Users/nimarahimian/Downloads/Space-Invaders-master/images/2.png"),
			new Image("file:/Users/nimarahimian/Downloads/Space-Invaders-master/images/3.png"),
			new Image("file:/Users/nimarahimian/Downloads/Space-Invaders-master/images/4.png"),
			new Image("file:/Users/nimarahimian/Downloads/Space-Invaders-master/images/6.png"),
			new Image("file:/Users/nimarahimian/Downloads/Space-Invaders-master/images/7.png"),
			new Image("file:/Users/nimarahimian/Downloads/Space-Invaders-master/images/8.png"),
			new Image("file:/Users/nimarahimian/Downloads/Space-Invaders-master/images/9.png"),

			
	};
	
	private double player_position;
	private int score;
	private GraphicsContext gc;
	int Meteor_constraint = 10;
	int MAX_SHOTS = 3;
	boolean gameOver = false;
	
	
	public void start(Stage stage) throws Exception {
		Canvas canvas = new Canvas(1280, 720);	
		gc = canvas.getGraphicsContext2D();
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(75), e -> run(gc)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		canvas.setCursor(Cursor.MOVE);
		canvas.setOnMouseMoved(e -> player_position = e.getX());
		canvas.setOnMouseClicked(e -> {
			if(shots.size() < MAX_SHOTS) shots.add(player.shoot());
			if(gameOver) { 
				gameOver = false;
				setup();
			}
		});
		
		setup();
		stage.setScene(new Scene(new StackPane(canvas)));
		stage.setTitle("Space Defenders");
		stage.show();
	}
	
	Rocket player;
	List<Shot> shots;
	List<Universe> univ;
	List<Bomb> Bombs;
	

	//setup the game
	private void setup() {
		univ = new ArrayList<>();
		shots = new ArrayList<>();
		Bombs = new ArrayList<>();
		player = new Rocket(1280 / 2, 720 - 120, 120, Spaceship);
		score = 0;
		IntStream.range(0, Meteor_constraint).mapToObj(i -> this.newBomb()).forEach(Bombs::add);
	}
	
	
	private void run(GraphicsContext gc) {
		gc.setFill(Color.grayRgb(20));
		gc.fillRect(0, 0, 1280, 720);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(Font.font("Courier New", 25));
		gc.setFill(Color.PURPLE);
		gc.fillText("Score: " + score, 60,  20);
	
		
		if(gameOver) {
			gc.setFont(Font.font(50));
			gc.setFill(Color.PURPLE);
			gc.fillText("Game Over", 1280 / 2, 720 /2.5);
			Meteor_constraint = 10;
		
		}
		
		univ.forEach(Universe::draw);
	
		player.update();
		player.draw();
		player.posX = (int) player_position;
		
		Bombs.stream().peek(Rocket::update).peek(Rocket::draw).forEach(e -> {
			if(player.colide(e) && !player.exploding) {
				player.explode();
			}
		});
		
		
		for (int i = shots.size() - 1; i >=0 ; i--) {
			Shot shot = shots.get(i);
			if(shot.posY < 0 || shot.toRemove)  { 
				shots.remove(i);
				continue;
			}
			shot.update();
			shot.draw();
			for (Bomb bomb : Bombs) {
				if(shot.colide(bomb) && !bomb.exploding) {
					score++;
					bomb.explode();
					shot.toRemove = true;
				}
			}
		}
		
		for (int i = Bombs.size() - 1; i >= 0; i--){  
			if(Bombs.get(i).destroyed)  {
				Bombs.set(i, newBomb());
			}
		}
	
		gameOver = player.destroyed;
		if(random.nextInt(10) > 2) {
			univ.add(new Universe());
			Meteor_constraint += 1;
		}
		for (int i = 0; i < univ.size(); i++) {
			if(univ.get(i).posY > 720)
				univ.remove(i);
		}
	}

	
	public class Rocket {

		int posX, posY, size;
		boolean exploding, destroyed;
		Image img;
		int explosionStep = 0;
		
		public Rocket(int posX, int posY, int size,  Image image) {
			this.posX = posX;
			this.posY = posY;
			this.size = size;
			img = image;
		}
		
		public Shot shoot() {
			return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size);
		}

		public void update() {
			if(exploding) 
				explosionStep++;
			
			destroyed = explosionStep > EXPLOSION_STEPS;
			
		}
		
		public void draw() {
			if(exploding) {
				gc.drawImage(Explosion, explosionStep % Explosion_col * Explosion_height, (explosionStep / Explosion_rows) * Explosion_height + 1,
						Explosion_widht, Explosion_height,
						posX, posY, size, size);
			}
			else {
				gc.drawImage(img, posX, posY, size, size);
			}
		}
	
		public boolean colide(Rocket other) {
			int d = distance(this.posX + size / 2, this.posY + size /2, 
							other.posX + other.size / 2, other.posY + other.size / 2);
			return d < other.size / 2 + this.size / 2 ;
		}
		
		public void explode() {
			exploding = true;
			explosionStep = -1;
		}

	}
	
	
	public class Bomb extends Rocket {
		
		int SPEED = (score/5)+2;
		
		public Bomb(int posX, int posY, int size, Image image) {
			super(posX, posY, size, image);
		}
		
		public void update() {
			super.update();
			if(!exploding && !destroyed) posY += SPEED;
			if(posY > 720) destroyed = true;
		}
	}

	//bullets
	public class Shot {
		
		public boolean toRemove;
		int posX, posY, speed = 30;
		static final int size = 1;
			
		public Shot(int posX, int posY) {
			this.posX = posX;
			this.posY = posY;
		}

		public void update() {
			posY-=speed;
		}
		

		public void draw() {
			gc.setFill(Color.PURPLE);
			gc.fillRect(posX-5, posY-5, size+2, size+50);
			if (score >=10 && score<=20 ) {
				
				speed = 50;
				
			}
			else if (score >=20 && score<=30)  {
				
				speed = 70;
				
				
			}
			
			
		}
		
		public boolean colide(Rocket Rocket) {
			int distance = distance(this.posX + size / 2, this.posY + size / 2, 
					Rocket.posX + Rocket.size / 2, Rocket.posY + Rocket.size / 2);
			return distance  < Rocket.size / 2 + size / 2;
		} 
		
		
	}
	
	
	public class Universe {
		int posX, posY;
		private int h, w, r, g, b;
		private double opacity;
		
		public Universe() {
			posX = random.nextInt(1280);
			posY = 0;
			w = random.nextInt(5) + 1;
			h =  random.nextInt(5) + 1;
			r = random.nextInt(100) + 150;
			g = random.nextInt(100) + 150;
			b = random.nextInt(100) + 150;
			opacity = random.nextFloat();
	
		}
		
		public void draw() {
			if(opacity < 0.1) opacity+=0.01;
			gc.setFill(Color.rgb(r, g, b, opacity));
			gc.fillOval(posX, posY, w, h);
			posY+=50;
		}
	}
	

	
	Bomb newBomb() {
		return new Bomb(50 + random.nextInt(1280 - 100), -20, 90, Meteors[random.nextInt(Meteors.length)]);
	}
	
	int distance(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt(Math.pow((x1 - x2) + 30, 2) + Math.pow((y1 - y2), 2));
	}
	
	
	public static void main(String[] args) {
		launch();
	}
}
