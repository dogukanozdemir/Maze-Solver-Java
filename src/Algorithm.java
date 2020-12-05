import java.awt.Color;
import java.util.ArrayList;
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

	public static void bfs(Node start) {
		Queue<Node> queue = new LinkedList<>();

		queue.add(start);

		while (!queue.isEmpty()) {

			Node curNode = queue.poll();
			if (curNode.isEnd()) {
				curNode.setColor(Color.MAGENTA);
				return;
			}

			if (!curNode.isJunction()) {
				curNode.setColor(Color.BLUE);

				for (Node adjacent : curNode.getNeighbours()) {
					queue.add(adjacent);

				}
				try {
					Thread.sleep(25);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void Astar(Node curNode, Node targetNode) {
		List<Node> currentNeighbours = curNode.getNeighbours();
		double min_distance = currentNeighbours.get(0).distanceTo(targetNode);
		Node min_node = currentNeighbours.get(0);

		for (Node adjacent : curNode.getNeighbours()) {
			if (adjacent != null && !adjacent.isWall() && !adjacent.isJunction() && !adjacent.isEnd()) {
				double current_distance = adjacent.distanceTo(targetNode);
				System.out.println(current_distance);
				if (current_distance < min_distance) {
					min_distance = current_distance;
					min_node = adjacent;
				}
			}
		}
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (min_node != null) {
			System.out.println("hey yo I entered here");
			min_node.setColor(Color.BLUE);
			Astar(min_node, targetNode);
		}
	}

}
