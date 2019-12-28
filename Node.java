

import java.awt.Color;
import java.awt.Graphics2D;


public class Node {
	
	private int Xpos;
	private int Ypos;
	private Color nodeColor = Color.LIGHT_GRAY;
	private final int WIDTH = 35;
	private final int HEIGHT = 35;
	private Node left, right, up, down;
	private Node Nleft,Nright, Nup,Ndown;
	
	
	
	public Node(int x, int y)
	{
		Xpos = x;
		Ypos = y;
	}
	public Node(){}
	public void render(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.drawRect(Xpos, Ypos, WIDTH, HEIGHT);	
		g.setColor(nodeColor);
		g.fillRect(Xpos + 1,Ypos + 1 , WIDTH - 1, HEIGHT - 1);
	}
	public void Clicked(int buttonCode)
	{
		System.out.println("called");
		if(buttonCode == 1)
		{
			//WALL
			nodeColor = Color.BLACK;
		
		}
		if(buttonCode == 2)
		{
			//START
			nodeColor = Color.GREEN;
			
		}
		if(buttonCode == 3)
		{
			//END
			nodeColor = Color.RED;
			
		}
		if(buttonCode == 4)
		{
			//CLEAR
			clearNode();
		
		}
	}
	public void SetColor(Color c)
	{ 
		nodeColor = c;
	}
	public Color getColor()
	{
		return nodeColor;
	}
	public void SetJunctionNeighbors(Node l,Node r, Node u, Node d)
	{
		Nleft = l;
		Nright = r;
		Nup = u;
		Ndown = d;
	}
	public void setDirections(Node l,Node r, Node u, Node d)
	{
		left = l;
		right = r;
		up = u;
		down = d;
	}
	public void clearNode()
	{
		nodeColor = Color.LIGHT_GRAY;
	}
	public int getX() {
		return (Xpos - 15) / WIDTH ;
	}
	public int getY() {
		return (Ypos - 15) / HEIGHT;
	}

	public Node setX(int x) {
		Xpos = x;
		return this;
	}
	public Node setY(int y) {
		Ypos = y;
		return this;
	}
	public Node getLeft() {
		return left;
	}
	public Node getRight() {
		return right;
	}
	public Node getUp() {
		return up;
	}
	public Node getDown() {
		return down;
	}
	public boolean isWall()
	{
		return (nodeColor == Color.BLACK) ? true : false;
	}
	
	public boolean isStart()
	{
		return (nodeColor == Color.GREEN) ? true : false;
	}
	
	public boolean isEnd()
	{
		return (nodeColor == Color.RED) ? true : false;
	}
	public boolean isPath()
	{
		return (nodeColor == Color.LIGHT_GRAY || nodeColor == Color.BLUE) ? true : false;
	}
	public boolean isJunction()
	{
		return (nodeColor == Color.BLUE) ? true : false;
	}
	
	


	
	

}
