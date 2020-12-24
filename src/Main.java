
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Main extends Canvas implements Runnable, MouseListener {

	private static Node start = null;
	private static Node target = null;
	private static JFrame frame;

	private Node[][] nodeList;
	private static Main runTimeMain;
	private static Algorithm algorithm;

	private final static int WIDTH = 1024;
	private final static int HEIGHT = 768;

	private final static int NODES_WIDTH = 28;
	private final static int NODES_HEIGHT = 19;

	public static void main(String[] args) {
		frame = new JFrame("Maze Solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setLayout(null);
		Main m = new Main();
		algorithm =  new Algorithm();
		// check
		m.setBounds(0, 25, WIDTH, HEIGHT);
		SetupMenu(frame);
		runTimeMain = m;
		// check
		frame.add(m);
		frame.setVisible(true);
		m.start();

	}

	public static void SetupMenu(JFrame frame) {
		JMenuBar bar = new JMenuBar();
		bar.setBounds(0, 0, WIDTH, 25);
		frame.add(bar);
		JMenu fileMenu = new JMenu("File");
		bar.add(fileMenu);
		JMenu boardMenu = new JMenu("Board");
		bar.add(boardMenu);
		JMenu algorithmsMenu = new JMenu("Algorithms");
		bar.add(algorithmsMenu);

		JMenuItem saveMaze = new JMenuItem("Save Maze");
		JMenuItem openMaze = new JMenuItem("Open Maze");
		JMenuItem exit = new JMenuItem("Exit");

		JMenuItem newGrid = new JMenuItem("New Board");
		JMenuItem clearSearch = new JMenuItem("Clear Search Results");

		JMenuItem bfsItem = new JMenuItem("Breadth-First Search");
		JMenuItem dfsItem = new JMenuItem("Depth-First Search");
		JMenuItem astarItem = new JMenuItem("A-star Search");
		JMenuItem searchTime = new JMenuItem("Exploring time per Node");

		openMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					runTimeMain.openMaze();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		saveMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				runTimeMain.clearSearchResults();
				try {
					runTimeMain.saveMaze();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		newGrid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				runTimeMain.createNodes(true);
			}
		});
		clearSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				runTimeMain.clearSearchResults();
			}
		});

		bfsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (runTimeMain.isMazeValid()) {
					algorithm.bfs(runTimeMain.start, runTimeMain.target, runTimeMain.NODES_WIDTH,
							runTimeMain.NODES_HEIGHT);
				} else {
					System.out.println("DIDNT LAUNCH");
				}

			}

		});
		dfsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (runTimeMain.isMazeValid()) {
					algorithm.dfs(runTimeMain.getStart());
				} else {
					System.out.println("DIDNT LAUNCH");
				}

			}

		});
		astarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (runTimeMain.isMazeValid()) {
					algorithm.Astar(runTimeMain.start, runTimeMain.target, runTimeMain.NODES_WIDTH,
							runTimeMain.NODES_HEIGHT);
				} else {
					System.out.println("DIDNT LAUNCH");
				}

			}

		});
		searchTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = JOptionPane.showInputDialog(null, "Enter a time it takes to search each node in miliseconds(default = 100ms) ", "Search Time", JOptionPane.QUESTION_MESSAGE);
				algorithm.setSearchTime(Integer.parseInt(input));
			}
		});

		fileMenu.add(exit);
		fileMenu.add(saveMaze);
		fileMenu.add(openMaze);
		boardMenu.add(newGrid);
		boardMenu.add(clearSearch);
		algorithmsMenu.add(dfsItem);
		algorithmsMenu.add(bfsItem);
		algorithmsMenu.add(astarItem);
		algorithmsMenu.add(searchTime);

	}

	public void run() {
		init();
		while (true) {
			// check
			BufferStrategy bs = getBufferStrategy(); // check
			if (bs == null) {
				// check
				createBufferStrategy(2);
				continue;
			}
			// check
			Graphics2D grap = (Graphics2D) bs.getDrawGraphics(); // check
			render(grap);
			bs.show();
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void init() {
		// check
		requestFocus();
		addMouseListener(this);
		nodeList = new Node[NODES_WIDTH][NODES_HEIGHT];
		createNodes(false);
		setMazeDirections();
	}
	public void setMazeDirections() {
		for (int i = 0; i < nodeList.length; i++) {
			for (int j = 0; j < nodeList[i].length; j++) {
				Node up = null,down = null,left = null,right = null;
				int u = j - 1;
				int d = j + 1;
				int l = i - 1;
				int r = i + 1;
				
				if(u >= 0) up = nodeList[i][u];
				if(d < NODES_HEIGHT) down =  nodeList[i][d];
				if(l >= 0) left = nodeList[l][j];
				if(r < NODES_WIDTH) right =  nodeList[r][j];
				
				nodeList[i][j].setDirections(left, right, up, down);
			}	
		}
	}
	public void createNodes(boolean ref) {
		for (int i = 0; i < nodeList.length; i++) {
			for (int j = 0; j < nodeList[i].length; j++) {
				if(!ref) nodeList[i][j] = new Node(i, j).setX(15 + i * 35).setY(15 + j * 35);
				nodeList[i][j].clearNode();
			}
		}
	}

	public void saveMaze() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		int option = fileChooser.showSaveDialog(frame);
		if (option == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String ext = file.getAbsolutePath().endsWith(".maze") ? "" : ".maze";
			BufferedWriter outputWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath() + ext));
			for (int i = 0; i < nodeList.length; i++) {
				for (int j = 0; j < nodeList[i].length; j++) {
					if (nodeList[i][j].isWall()) {
						outputWriter.write("1");
					} else if (nodeList[i][j].isStart()) {
						outputWriter.write("2");
					} else if (nodeList[i][j].isEnd()) {
						outputWriter.write("3");
					} else {
						outputWriter.write("0");
					}
				}
				outputWriter.newLine();
			}
			outputWriter.flush();
			outputWriter.close();
		}

	}

	public void openMaze() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		int option = fileChooser.showOpenDialog(frame);
		if (option == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
			String line = null;
			for (int i = 0; i < NODES_WIDTH; i++) {
				line = reader.readLine();
				for (int j = 0; j < NODES_HEIGHT; j++) {
					
					//nodeList[i][j].setColor(Color.BLACK);
					int nodeType = Character.getNumericValue(line.charAt(j));
					System.out.println("node is " + nodeType);
					switch (nodeType) {
					case 0:
						nodeList[i][j].setColor(Color.LIGHT_GRAY);
						break;
					case 1:
						nodeList[i][j].setColor(Color.BLACK);
						break;

					case 2:
						nodeList[i][j].setColor(Color.GREEN);
						start = nodeList[i][j];
						break;
					case 3:
						nodeList[i][j].setColor(Color.RED);
						target = nodeList[i][j];
						break;
					}
				}

			}
			reader.close();
			// System.out.println(stringMaze);
		}
	}

	public void clearSearchResults() {
		for (int i = 0; i < nodeList.length; i++) {
			for (int j = 0; j < nodeList[i].length; j++) {
				if (nodeList[i][j].isSearched()) {
					nodeList[i][j].clearNode();
				}
			}
		}
		if (isMazeValid()) {
			target.setColor(Color.RED);
			start.setColor(Color.GREEN);
		}
	}

	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for (int i = 0; i < nodeList.length; i++) {
			for (int j = 0; j < nodeList[i].length; j++) {
				nodeList[i][j].render(g);
			}
		}
	}

	public void start() {
		// check
		new Thread(this).start();
	}

	public void mousePressed(MouseEvent e) {
		Node clickedNode = getNodeAt(e.getX(), e.getY());
		if (clickedNode == null)
			return;

		if (clickedNode.isWall()) {
			clickedNode.clearNode();
			return;
		}

		clickedNode.Clicked(e.getButton());

		if (clickedNode.isEnd()) {
			if (target != null) {
				target.clearNode();
			}
			target = clickedNode;
		} else if (clickedNode.isStart()) {

			if (start != null) {
				start.clearNode();
			}
			start = clickedNode;
		}

	}

	public boolean isMazeValid() {
		return target == null ? false : true && start == null ? false : true;
	}

	private Node getStart() {
		for (int i = 0; i < nodeList.length; i++) {
			for (int j = 0; j < nodeList[i].length; j++) {
				if (nodeList[i][j].isStart()) {
					return nodeList[i][j];
				}
			}
		}
		return null;
	}

	public Node getNodeAt(int x, int y) {
		x -= 15;
		x /= 35;
		y -= 15;
		y /= 35;

		System.out.println(x + ":" + y);
		if (x >= 0 && y >= 0 && x < nodeList.length && y < nodeList[x].length) {
			return nodeList[x][y];
		}
		return null;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
