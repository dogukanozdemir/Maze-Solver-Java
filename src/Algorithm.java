import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;


public class Algorithm {

	public static void dfs(Node start) {
		Stack<Node> nodes = new Stack<>();
		nodes.push(start);

		while (!nodes.empty()) {

			Node curNode = nodes.pop();
			if (!curNode.isEnd()) {

				curNode.setColor(Color.BLUE);

				for (Node adjacent : curNode.getNeighbours()) {
					nodes.push(adjacent);
				}
				try {
					Thread.sleep(25);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				curNode.setColor(Color.MAGENTA);
				break;
			}
		}

	}

	public static void bfs(Node start, Node end, int graphWidth, int graphHeight) {
		Queue<Node> queue = new LinkedList<>();
		Node[][] prev = new Node[graphWidth][graphHeight];

		queue.add(start);
		while (!queue.isEmpty()) {

			Node curNode = queue.poll();
			if (curNode.isEnd()) {
				curNode.setColor(Color.MAGENTA);
				break;
			}

			if (!curNode.isSearched()) {
				curNode.setColor(Color.BLUE);

				for (Node adjacent : curNode.getNeighbours()) {
					queue.add(adjacent);
					prev[adjacent.getX()][adjacent.getY()] = curNode;
					

				}
				try {
					Thread.sleep(25);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		shortpath(prev, end);
	}
	
	private static Node getLeastHeuristic(List<Node> nodes,Node end,Node start) {
		if(!nodes.isEmpty()) {
			Node leastH = nodes.get(0);
			for(int i = 1; i < nodes.size();i++) {
				double f1 = Node.distance(nodes.get(i), end);
				double h1 = Node.distance(nodes.get(i), start);
						
				double f2 = Node.distance(leastH, end);
				double h2 = Node.distance(leastH, start);
				if(f1 + h1 < f2 + h2) {
					leastH = nodes.get(i);
				}
			}
			return leastH;
		}
		return null;
	}

	private static  void shortpath(Node[][] prev, Node end) {
		System.out.println("gonna look at somtin");
		Node pathConstructor = end;
		while(pathConstructor != null) {
			pathConstructor = prev[pathConstructor.getX()][pathConstructor.getY()];

			if(pathConstructor != null) {
			pathConstructor.setColor(Color.ORANGE);
			}
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void Astar(Node start, Node targetNode,  int graphWidth, int graphHeight) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		//Node[][] prev = new Node[graphWidth][graphHeight];
		openList.add(start);
		
		while(!openList.isEmpty()) {
			Node leastH =  getLeastHeuristic(openList,targetNode,start);
			openList.remove(leastH);
			if(leastH.isEnd()) {
				leastH.setColor(Color.MAGENTA);
				break;
			}
			
			if(!leastH.isSearched()) {
				leastH.setColor(Color.BLUE);
				for (Node adjacent : leastH.getNeighbours()) {
					openList.add(adjacent);
					//prev[adjacent.getX()][adjacent.getY()] = leastH;
				}
			}
			try {
				Thread.sleep(250);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		//shortpath(prev, targetNode);
		
	}


}
