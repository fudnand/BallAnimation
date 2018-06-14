import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class BallAnimation extends JFrame{
	BallPanel pnl;
	
	public BallAnimation(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		this.setSize(width, height);
		this.setLocation(0, 0);
		pnl = new BallPanel();
		this.add(pnl);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				pnl.stopAnimation();
				e.getWindow().dispose();
				System.exit(0);
			}
		});
		this.setVisible(true);
	}
}

class BallPanel extends JPanel{
	private BallTask task;	
	private Thread animator;
	
	public BallPanel() {
		this.setSize(1700, 700);
		this.setBackground(Color.WHITE);
		task = new BallTask();
		animator = new Thread(task);
		animator.start();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent((g));
		task.drawBalls(g);
	}
	
	public void stopAnimation(){
		task.stopSimulation();
	}
	
	class BallTask implements Runnable{
		ArrayList<Ball> balls;
		boolean done = false;
		
		public BallTask() {
			
			balls = new ArrayList<Ball>();
			for (int i = 0; i< 5; i++){
				balls.add(createBall("left"));
			}
			for (int i = 0; i< 5; i++){
				balls.add(createBall("right"));
			}
		}
		
		public ArrayList<Ball> OtherBalls(Ball ball){
			ArrayList<Ball> temp = new ArrayList<Ball>();
			for (Ball k: balls){
				if(!ball.equals(k)){
					temp.add(k);
				}
			}			
			return temp;
		}
		
		public Ball createBall(String side){			
			Ball newBall =  new Ball(side, getWidth(), getHeight());
			while(checkForNewBall(newBall)){
				newBall =  new Ball(side, getWidth(), getHeight());
			}			
			return newBall;
		}
		
		public boolean checkForNewBall(Ball ball){
			for (Ball k: OtherBalls(ball)){
				if(Math.abs(ball.getX() - k.getX()) <= ball.getSize() ){
					if(Math.abs(ball.getY() - k.getY()) <= ball.getSize()){
						ball.setHasCollided(true);
						k.setHasCollided(true);
						return true;
					}
				}
			}
			return false;						
		}//checks to make sure newballs being used for replacement aren't colliding at creation  
		
		@Override
		public void run() {
			ArrayList<Ball> hold = new ArrayList<Ball>();
			while(!done){
				for(int i = 0; i< balls.size(); i++){
					balls.get(i).Moveforward();
					
					if(balls.get(i).collisionWithEdge(getWidth())){
						replaceBall(balls.get(i));
					}
					
					for(int j= i+1; j< balls.size(); j++){
						if(balls.get(i).collision(balls.get(j).getX(), balls.get(j).getY())){												
							replaceBall(balls.get(j));
							replaceBall(balls.get(i));
						}	
					}		
				}
								
			try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					System.out.println(e.getStackTrace().toString());
				}
				repaint();
			}			
		}

	public void replaceBall(Ball toRemove){
		String side = toRemove.getSide();
		balls.remove(toRemove);		
		balls.add(createBall(side));
	}
	
	public synchronized void drawBalls(Graphics g){
		for(Ball k : balls){
			k.draw(g);
		}
	}
	
	public synchronized void stopSimulation(){
		done = true;
	}
		
	}
}