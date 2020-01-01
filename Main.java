

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;




public class Main extends Canvas implements Runnable, MouseListener
{

	private static Node start = null;
	private static Node target = null;
	
	private Node[][] nodeList;
	private ArrayList<Node> Junctions = new ArrayList<Node>();
	private static Main runTimeMain;

	
	private final static int WIDTH = 600;
	private final static int HEIGHT = 640;
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Maze Solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH,HEIGHT);
		frame.setResizable(false);
		frame.setLayout(null);
		Main m =  new Main();
		//check
		m.setBounds(0,25,WIDTH,HEIGHT);
		SetupMenu(frame);
		runTimeMain = m;
		//check
		frame.add(m);
		frame.setVisible(true);
		m.start();
		
	}

	public static void SetupMenu(JFrame frame)
	{
		JMenuBar bar = new JMenuBar();
		bar.setBounds(0, 0, WIDTH, 25);
		frame.add(bar);
		JMenu file = new JMenu("File");
		bar.add(file);
		JMenu options =  new JMenu("Options");
		bar.add(options);
		
		JMenuItem exit =  new JMenuItem("Exit");
		JMenuItem newGrid =  new JMenuItem("New Board");
		JMenuItem calcPath =  new JMenuItem("Calculate Path");
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		newGrid.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				runTimeMain.refreshNodes();
			}
		});
		calcPath.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(runTimeMain.isMazeComplete()){
					runTimeMain.depthFirstSearch(runTimeMain.getStart());
				}else{
					System.out.println("DIDNT LAUNCH");
				}
				
			}
			
		});
		
		file.add(exit);
		options.add(newGrid);
		options.add(calcPath);

	}
	public void run() 
	{
		init();
		while(true)
		{
			//check
			BufferStrategy bs = getBufferStrategy(); //check
			if(bs == null)
			{
				//check
				createBufferStrategy(2);
				continue;
			}
			//check
			Graphics2D grap = (Graphics2D) bs.getDrawGraphics(); //check
			render(grap);
			bs.show();
			try
			{
				Thread.sleep(1);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	public void init()
	{
		//check
		requestFocus();
		addMouseListener(this);
		nodeList = new Node[10][10];
		refreshNodes();
		SetMazeDirections();
	}
	public void refreshNodes()
	{
		for(int i = 0; i < nodeList.length; i++){
			for(int j = 0; j < nodeList[i].length; j++)
			{
				nodeList[i][j] = new Node(i,j).setX(15 + i * 35).setY( 15 + j * 35);
				nodeList[i][j].clearNode();			
			}
		}	
	}

	public void SetMazeDirections()
	{
		for(int i = 0; i < nodeList.length; i++){
			for(int j = 0; j < nodeList[i].length; j++){
				int u = j - 1;
				int d = j + 1;
				int l = i - 1;
				int r = i + 1;
				Node up = null,down = null,left = null,right = null;
				
				if(isInBoundsEqual(u,0,nodeList[i].length))
					up = nodeList[i][u];
				
				if(isInBoundsEqual(d,0,nodeList[i].length))
					down = nodeList[i][d];
				
				if(isInBoundsEqual(l,0,nodeList.length))
					left = nodeList[l][j];
				
				if(isInBoundsEqual(r,0,nodeList.length))
					right = nodeList[r][j];
				
				nodeList[i][j].setDirections(left, right, up,down);
			}
		}
	}
	public void render(Graphics2D g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for(int i = 0; i < nodeList.length; i++){
			for(int j = 0; j < nodeList[i].length; j++)
			{
				nodeList[i][j].render(g);
			}
		}
	}
	public void start()
	{
		//check
		new Thread(this).start();
	}
	public void mousePressed(MouseEvent e) {
		Node clickedNode = getNodeAt(e.getX(),e.getY());
		if(clickedNode == null) return;

		if(clickedNode.isWall()){
			clickedNode.clearNode();
			return;
		}

		clickedNode.Clicked(e.getButton());

		if(clickedNode.isEnd())
		{	
			if(target != null)
			{
				target.clearNode();
			}
			target = clickedNode;	
		}
		else if(clickedNode.isStart())
		{
			
			if(start != null)
			{
				start.clearNode();
			}
			start = clickedNode;
		}	

	
	}
	public boolean isMazeComplete(){
		return target == null ? false : true && start == null ? false : true;
	}
	private Node getStart(){
		for(int i = 0; i < nodeList.length; i++){
			for(int j = 0; j < nodeList[i].length;j++){
				if(nodeList[i][j].isStart()){
					return nodeList[i][j];
				}
			}
		}
		return null;
	}
	public boolean breadthSearch()
	{	

		ArrayList<Node> closed = new ArrayList<Node>();
		boolean completed = false;
		boolean succesfull = false;
		Node curNode = getStart();

		while(!completed)
		{
				System.out.println("curNode is at x:" + curNode.getX() + " y: " + curNode.getY());

				if(curNode.getLeft() != null && curNode.getLeft().isEnd()) {
					completed = true;
					succesfull = true;
					break;
				}
				if(curNode.getRight() != null && curNode.getRight().isEnd()) {
					completed = true;
					succesfull = true;
					break;
				}
				if( curNode.getUp() != null && curNode.getUp().isEnd()) {
					completed = true;
					succesfull = true;
					break;
				}
				if( curNode.getDown() != null && curNode.getDown().isEnd()) {
					completed = true;
					succesfull = true;
					break;
				}
				
				
				if(curNode.getLeft() != null && !closed.contains(curNode.getLeft()) && curNode.getLeft().isPath())
				{
					curNode = curNode.getLeft();
					curNode.SetColor(Color.ORANGE);
					closed.add(curNode);
				}
				else if(curNode.getDown() != null && !closed.contains(curNode.getDown()) &&  curNode.getDown().isPath())
				{
					curNode = curNode.getDown();
					curNode.SetColor(Color.ORANGE);
					closed.add(curNode);
				}
				else if(curNode.getUp() != null && !closed.contains(curNode.getUp()) &&  curNode.getUp().isPath())
				{
					curNode = curNode.getUp();
					curNode.SetColor(Color.ORANGE);
					closed.add(curNode);
				}
				else if(curNode.getRight() != null && !closed.contains(curNode.getRight()) &&  curNode.getRight().isPath())
				{
					curNode = curNode.getRight();
					curNode.SetColor(Color.ORANGE);
					closed.add(curNode);
				} 
				else
				{
					completed = true;
					succesfull = false;
				}
		}
		return succesfull;
		
	}

	private void depthFirstSearch(Node curNode){
		ArrayList<Node> neighbours =  new ArrayList<>();
		neighbours.add(curNode.getUp());
		neighbours.add(curNode.getRight());
		neighbours.add(curNode.getDown());
		neighbours.add(curNode.getLeft());

		curNode.SetColor(Color.BLUE);
		for(Node node : neighbours){
			if(node != null){
				if(node.isEnd()){
					break;
				}else if(!node.isJunction() && !node.isWall()){
					try
					{
						Thread.sleep(500);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					depthFirstSearch(node);
				}
			}
		}
	}

	public void LocateJunctions()
	{
		for(int i = 0; i < nodeList.length; i++){
			for(int j = 0; j < nodeList[i].length; j++)
			{
				Node curNode = nodeList[i][j];
				if(curNode != null && curNode.isPath())
				{
					if(curNode.getLeft() != null && curNode.getRight() != null &&  curNode.getUp() != null && curNode.getDown() != null)
					{
						if((!curNode.getLeft().isWall()  || !curNode.getRight().isWall()) && (!curNode.getUp().isWall() || !curNode.getDown().isWall()))
						{
							curNode.SetColor(Color.BLUE);
							Junctions.add(curNode);
						}
						if(!curNode.getLeft().isWall() && curNode.getRight().isWall()  && curNode.getUp().isWall()  && curNode.getDown().isWall() )
						{
							curNode.SetColor(Color.BLUE);
							Junctions.add(curNode);
						}
						if(curNode.getLeft().isWall()  && !curNode.getRight().isWall() && curNode.getUp().isWall()  && curNode.getDown().isWall() )
						{
							curNode.SetColor(Color.BLUE);
							Junctions.add(curNode);
						}
						if(curNode.getLeft().isWall()  && curNode.getRight().isWall()  && !curNode.getUp().isWall() && curNode.getDown().isWall() )
						{
							curNode.SetColor(Color.BLUE);
							Junctions.add(curNode);
						}
						if(curNode.getLeft().isWall() && curNode.getRight().isWall()  && curNode.getUp().isWall()  && !curNode.getDown().isWall())
						{
							curNode.SetColor(Color.BLUE);
							Junctions.add(curNode);
						}
					}
				}
			}
		}	
	}	

	public Node getNodeAt(int x, int y)
	{
		x -= 15;
		x /= 35;
		y -= 15;
		y /= 35;
		System.out.println(x + ":" + y);
		if(x >= 0 && y >= 0 && x < nodeList.length && y < nodeList[x].length)
		{
			return nodeList[x][y];
		}
		return null;
	}
	public boolean isInBoundsEqual(int value,int low,int high)
	{
		if(value >= low && value < high)
			return true;
		return false;
	}
	public boolean isInBounds(int value,int low,int high)
	{
		if(value > low && value < high)
			return true;
		return false;
	}
	
	
	
	public void mouseClicked(MouseEvent e) {	
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
}
