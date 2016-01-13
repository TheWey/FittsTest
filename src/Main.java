import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



@SuppressWarnings("serial")
public class Main extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener{

	public static JFrame jf=new JFrame();
	public static Main t=new Main();
	public static Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
	public Shape startRect=new Shape(100, Toolkit.getDefaultToolkit().getScreenSize().height/2, 15, 15);
	public int instructions=0;
	public static String instructionString="Begin the experiment by pressing Enter...";
	public static String instructionString2="";
	public static String instructionString3="";
	public static String instructionString4="";
	public static Color start1=Color.WHITE;
	public static Color start2=Color.WHITE;
	public static Color start3=Color.WHITE;
	public Shape pointer=new Shape(100,100,15,15);
	public Thread m=new Thread(new mousemove());
	public Thread running=new Thread(new checker());
	public Shape target=new Shape(-1000,-1000,200,50);
	public static boolean exrun=false;
	public static long starttime=0;
	public static long endtime=0;
	public static Random rand=new Random();
	public static boolean done=false;
	public static String colorname="";
	public static String data="";
	public static boolean testing=false;
	
	public static void main(String[] args) {
	  String test="Welcome to the test!\n\nOn the next screen, you'll be walked through the steps to complete this exercise, however we will outline them here first.\n\n1) Press the space bar to begin each trial. IMMEDIATELY move your mouse to the target area as quickly as possible. \nEach trial is timed from when you hit the space bar to when the pointer ends in the target box.\n\n2) Your statistics for the trial will appear. Press enter to continue after this screen. Press enter again to reset for the next trial.\n\n3) Repeat steps 1-3 for each trial until the end.\n\n4) Follow the instructions at the end to submit your data!\n\nLet's begin!";
	  JOptionPane.showMessageDialog(null, test);
	  jf.setBackground(Color.WHITE);
	  jf.setTitle("Test 1");
	  jf.setSize(dim.width,dim.height);
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	  jf.add(t);
   	  jf.setBackground(Color.WHITE);
      jf.addKeyListener(t);
      t.addMouseListener(t);
      t.addMouseMotionListener(t);
	  jf.setVisible(true);
	}
	
	
	public class mousemove implements Runnable{

		@Override
		public void run() {
			while (true){
				try{
			  		Robot robot = new Robot();
			   		robot.mouseMove(pointer.x, pointer.y);
			   		for (int xc=pointer.x;xc<target.y+10;xc++){
			   			Thread.sleep(5);
			    		robot.mouseMove(xc,pointer.y);
			   		}
			   		Thread.sleep(100);
			 	}catch(Exception w){}
			}
		}
		
	}
	
	public class checker implements Runnable{

		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			
				while (true){
					if (MouseInfo.getPointerInfo().getLocation().x>target.x && MouseInfo.getPointerInfo().getLocation().x<target.x+200){
						endtime=System.nanoTime();
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (MouseInfo.getPointerInfo().getLocation().x>target.x && MouseInfo.getPointerInfo().getLocation().x<target.x+200){
							exrun=false;
							done=true;
							JOptionPane.showMessageDialog(null, "Time: "+(endtime-starttime)+" nanoseconds or about "+(double)(endtime-starttime)/1000000000+" seconds\nAmplitude: "+(target.x+100)+" pixels\nWidth: 200 pixels\nColor: "+colorname+" ("+start2.toString()+")");
							testing=false;
							running.suspend();
						}
						else {
							JOptionPane.showMessageDialog(null, "The mouse pointer must end in the target box.\nThis trial will be rerun.");
							try {
								Robot robot = new Robot();
								robot.mouseMove(pointer.x, pointer.y);
							} catch (AWTException e) {}
							target.setX(rand.nextInt(Toolkit.getDefaultToolkit().getScreenSize().width-330)+130);
							exrun=true;
							start2=randcolor();
							repaint();
							running.suspend();
						}
					}
				}
				
		}
		
	}
	
	public void runtest() {
		try {
			Robot robot = new Robot();
			robot.mouseMove(pointer.x, pointer.y);
		} catch (AWTException e) {}
		target.setX(rand.nextInt(Toolkit.getDefaultToolkit().getScreenSize().width-330)+130);
		start2=randcolor();
		repaint();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exrun=true;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void mousePressed(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	@Override
	public void keyPressed(KeyEvent arg0){
		if (arg0.getKeyCode()==arg0.VK_SPACE){
			if (exrun){
				if (done) running.resume();
				else running.start();
				exrun=false;
				testing=true;
				starttime=System.nanoTime();
			}
		}
		if (arg0.getKeyCode()==arg0.VK_ENTER && instructions==0) {
			instructionString="Calibration phase: Please place your mouse cursor in the box below, then press Enter..."; 
			start1=Color.black;
			repaint(); 
			instructions++; 
		}
		else if (arg0.getKeyCode()==arg0.VK_ENTER && instructions==1) {
			instructionString="Good! The mouse will return to this point at the beginning of each trial. Press Enter..."; 
			pointer.setX(MouseInfo.getPointerInfo().getLocation().x);
			pointer.setY(MouseInfo.getPointerInfo().getLocation().y);
			start1=Color.white;
			start3=Color.DARK_GRAY;
			repaint(); 
			instructions++;
		}
		else if (arg0.getKeyCode()==arg0.VK_ENTER && instructions==2) {
			instructionString="Your goal is to move the pointer to the target area as fast as possible. "; 
			instructionString2="Press space to begin each test, then move your mouse quickly.";
			instructionString3="The timer starts when you hit the space bar and ends in the target box.";
			instructionString4="After the end of each trial, press Enter to reset for the next trial.";
			start2=Color.red;
			target.setX(startRect.x+(Toolkit.getDefaultToolkit().getScreenSize().width/5));
			target.setY(startRect.y-25);
			m.start();
			repaint();
			instructions++;
		}
		else if (arg0.getKeyCode()==arg0.VK_ENTER && instructions==3) {
			instructionString="This first trial is just a test."; 
			instructionString2="After this, there will be 10 trials.";
			instructionString3="Your results will be displayed after each.";
			instructionString4="";
			target.setX(rand.nextInt(Toolkit.getDefaultToolkit().getScreenSize().width-330)+130);
			start2=randcolor();
			m.stop();
			runtest();
			repaint();
			instructions++;
		}
		else if (arg0.getKeyCode()==arg0.VK_ENTER && instructions<14 && !testing){
			instructionString="";
			instructionString2="";
			instructionString3="";
			instructionString4="";
			if (instructions!=4) data=data+";"+(endtime-starttime)+","+(target.x+100)+","+colorname; //"Time: "+(endtime-starttime)+" nanoseconds\nAmplitude: "+(target.x+100)+" pixels\nWidth: 200 pixels\nColor: "+colorname+" ("+start2.toString()+")"
			repaint();
			instructions++;
			runtest();
		}
		else if (!testing){
			data=data+";"+(endtime-starttime)+","+(target.x+100)+","+colorname;
			JOptionPane.showMessageDialog(null, "Congratulations! You have compleated the test.");
			data=data.replaceFirst(";", "");
			System.out.println(data);
			jf.setVisible(false);
			String site="http://www.bit.ly/1bu8Gk0";
			
			StringSelection stringSelection = new StringSelection (site);
			Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
			clpbrd.setContents (stringSelection, null);
			JOptionPane.showMessageDialog(null, "The web address to submit the data has been copied into your clipboard. \nPaste it into your favorite web browser now, then return here and press enter to continue.");
			System.out.println(site);
			
			StringSelection stringSelection2 = new StringSelection (data);
			Clipboard clpbrd2 = Toolkit.getDefaultToolkit ().getSystemClipboard ();
			clpbrd2.setContents (stringSelection2, null);
			try {
				if (((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)).equals(data)) {
					JOptionPane.showMessageDialog(null, "Success!\nPaste the copied data into the web collection interface now!");
				}
				else {
					JOptionPane.showMessageDialog(null, "Failure! The data is below, and in the console for copying.\n"+data);
				}
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedFlavorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.exit(0);
			
		}

		
	}
	
	public Color randcolor(){
		
		int r=rand.nextInt(8);
		Color picked=Color.black;
		if (r==0) {picked=Color.blue; colorname="Blue";}
		if (r==1) {picked=Color.gray; colorname="Gray";}
		if (r==2) {picked=Color.green; colorname="Green";}
		if (r==3) {picked=Color.magenta; colorname="Magenta";}
		if (r==4) {picked=Color.orange; colorname="Orange";}
		if (r==5) {picked=Color.pink; colorname="Pink";}
		if (r==6) {picked=Color.red; colorname="Red";}
		if (r==7) {picked=Color.yellow; colorname="Yellow";}
		return picked;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void paint(Graphics a){
		super.paint(a);
		Font fonta=new Font("Serif", Font.PLAIN, 25);
		a.setFont(fonta);
		a.setColor(Color.black);
		a.drawString(instructionString,10,100);
		a.drawString(instructionString2,10,150);
		a.drawString(instructionString3,10,200);
		a.drawString(instructionString4,10,250);
		a.setColor(start3);
		a.drawOval(startRect.x, startRect.y, 10, 10);
		a.setColor(start2);
		a.fillRect(target.x,target.y,target.width,target.height);
		if (start2!=Color.white) a.setColor(Color.black);
		a.drawRect(target.x,target.y,target.width,target.height);
		a.setColor(start1);
		a.drawRect(startRect.x, startRect.y, startRect.width, startRect.height);
		if (instructions>1){
			a.setColor(start3);
			a.drawOval(startRect.x, startRect.y, 10, 10);
		}
		
		
		
	}
	public class Shape{
		public int x;
		public int y;
		public int width;
		public int height;
		public Shape(int xInput, int yInput, int widthIn, int heightIn){
			x=xInput;
			y=yInput;
			width=widthIn;
			height=heightIn;
		}
		public void setX(int newX){ x=newX; }
		public void setY(int newY){ y=newY; }
	}

}