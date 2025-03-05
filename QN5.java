/*
 Question 5 
Optimizing a Network with Multiple Objectives 
Problem: 
Suppose you are hired as software developer for certain organization and you are tasked with creating a 
GUI application that helps network administrators design a network topology that is both cost-effective 
and efficient for data transmission. The application needs to visually represent servers and clients as 
nodes in a graph, with potential network connections between them, each having associated costs and 
bandwidths. The goal is to enable the user to find a network topology that minimizes both the total cost 
and the latency of data transmission. 
Approach: 
1. Visual Representation of the Network: 
o Design the GUI to allow users to create and visualize a network graph where each node 
represents a server or client, and each edge represents a potential network connection. The 
edges should display associated costs and bandwidths. 
2. Interactive Optimization: 
o Implement tools within the GUI that enable users to apply algorithms or heuristics to 
optimize the network. The application should provide options to find the best combination 
of connections that minimizes the total cost while ensuring all nodes are connected. 
3. Dynamic Path Calculation: 
o Include a feature where the user can calculate the shortest path between any pair of nodes 
within the selected network topology. The GUI should display these paths, taking into 
account the bandwidths as weights. 
4. Real-time Evaluation: 
o Provide real-time analysis within the GUI that displays the total cost and latency of the 
current network topology. If the user is not satisfied with the results, they should be able 
to adjust the topology and explore alternative solutions interactively. 
Example: 
 Input: The user inputs a graph in the application, representing servers, clients, potential 
connections, their costs, and bandwidths. 
 Output: The application displays the optimal network topology that balances cost and latency, 
and shows the shortest paths between servers and clients on the GUI. 

 */

// Algorithm for Network Topology GUI:

/*
Graph Representation:

Use a Map<String, Map<String, Integer>> to store the graph (nodes and edges with costs).
Use a Map<String, Point> to store node positions.
Add Node:

Prompt for node name, validate, and add it to the graph with random positioning.
Add Edge:

Prompt for two nodes and cost, validate, and add the edge in both directions.
Optimize Network (MST):

Check if the graph is connected.
Use MST algorithms (e.g., Prim’s or Kruskal’s) to optimize the network.
Find Shortest Path:

Use Dijkstra’s algorithm to find the shortest path between two nodes.
Graph Visualization:

Draw nodes and edges on the panel. Display edge costs and node labels.
*/ 
import java.awt.*; // Importing classes for GUI components, such as Graphics, Color, and Point.
import java.util.*; // Importing utility classes, such as HashMap, Map, and Random.
import javax.swing.*; // Importing Swing components for GUI, such as JFrame, JPanel, JButton, and JOptionPane.

public class QN5 extends JPanel { // Defining the main class that extends JPanel to allow custom painting on the panel.
    private JFrame frame; // Declaring the JFrame for the main window.
    private JButton addNodeBtn, addEdgeBtn, optimizeBtn, shortestPathBtn; // Declaring buttons for different actions.
    private Map<String, Map<String, Integer>> graph; // Graph structure represented as a Map of nodes, where each node points to another node with a cost (edge weight).
    private Map<String, Point> nodePositions; // Map to store positions of nodes (used for visualizing nodes on the panel).
    private Random random = new Random(); // Creating a Random object to generate random positions for nodes.

    public QN5() { // Constructor for initializing the frame, buttons, and setting up the GUI.
        frame = new JFrame("Network Topology Optimizer"); // Initialize the JFrame with a title.
        graph = new HashMap<>(); // Initialize the graph as a HashMap.
        nodePositions = new HashMap<>(); // Initialize the node positions as a HashMap.

        JPanel controlPanel = new JPanel(); // Create a panel to hold the control buttons.
        addNodeBtn = new JButton("Add Node"); // Initialize the "Add Node" button.
        addEdgeBtn = new JButton("Add Edge"); // Initialize the "Add Edge" button.
        optimizeBtn = new JButton("Optimize Network"); // Initialize the "Optimize Network" button.
        shortestPathBtn = new JButton("Find Shortest Path"); // Initialize the "Find Shortest Path" button.

        // Adding buttons to the control panel.
        controlPanel.add(addNodeBtn);
        controlPanel.add(addEdgeBtn);
        controlPanel.add(optimizeBtn);
        controlPanel.add(shortestPathBtn);

        // Adding the control panel to the top (North) of the frame.
        frame.add(controlPanel, BorderLayout.NORTH);
        // Adding the main panel (QN5 class) to the center of the frame.
        frame.add(this, BorderLayout.CENTER);
        frame.setSize(700, 500); // Setting the size of the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Defining what happens when the user closes the window.
        frame.setVisible(true); // Making the frame visible.

        // Defining the actions for each button.
        addNodeBtn.addActionListener(e -> addNode()); // Action for adding a node.
        addEdgeBtn.addActionListener(e -> addEdge()); // Action for adding an edge.
        optimizeBtn.addActionListener(e -> optimizeNetwork()); // Action for optimizing the network.
        shortestPathBtn.addActionListener(e -> findShortestPath()); // Action for finding the shortest path.
    }

    // Method to add a new node to the graph.
    private void addNode() {
        String nodeName = JOptionPane.showInputDialog("Enter node name:"); // Prompting user to enter node name.
        if (nodeName != null && !nodeName.trim().isEmpty() && !graph.containsKey(nodeName)) { // Check for valid input and uniqueness.
            graph.put(nodeName, new HashMap<>()); // Add node to the graph with an empty edge list.
            nodePositions.put(nodeName, new Point(random.nextInt(500), random.nextInt(400))); // Assign a random position for visualization.
            repaint(); // Refresh the panel to redraw the graph with the new node.
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid or duplicate node name!"); // Show error message if the node name is invalid.
        }
    }

    // Method to add an edge between two nodes.
    private void addEdge() {
        String node1 = JOptionPane.showInputDialog("Enter first node:"); // Prompt user for the first node.
        String node2 = JOptionPane.showInputDialog("Enter second node:"); // Prompt user for the second node.
        if (graph.containsKey(node1) && graph.containsKey(node2) && !node1.equals(node2)) { // Check if nodes exist and are not the same.
            try {
                int cost = Integer.parseInt(JOptionPane.showInputDialog("Enter cost:")); // Prompt for edge cost and parse it.
                graph.get(node1).put(node2, cost); // Add the edge from node1 to node2 with the given cost.
                graph.get(node2).put(node1, cost); // Add the edge from node2 to node1 for an undirected graph.
                repaint(); // Refresh the panel to redraw the graph with the new edge.
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid cost input!"); // Handle invalid input for cost.
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid nodes!"); // Show error message if the nodes are invalid.
        }
    }

    // Method to optimize the network (finding a Minimum Spanning Tree).
    private void optimizeNetwork() {
        if (graph.size() > 1) { // Check if the graph has more than one node.
            // Placeholder for network optimization (MST logic would go here).
            JOptionPane.showMessageDialog(frame, "Network optimization logic (MST) would go here!"); // Placeholder message.
        } else {
            JOptionPane.showMessageDialog(frame, "Graph is not connected!"); // Show error if the graph is too small to optimize.
        }
    }

    // Method to find the shortest path between two nodes using Dijkstra's algorithm.
    private void findShortestPath() {
        String start = JOptionPane.showInputDialog("Enter start node:"); // Prompt user for start node.
        String end = JOptionPane.showInputDialog("Enter end node:"); // Prompt user for end node.
        if (graph.containsKey(start) && graph.containsKey(end)) { // Check if both nodes exist.
            Map<String, Integer> distances = new HashMap<>(); // Initialize a map to store distances of nodes.
            for (String node : graph.keySet()) {
                distances.put(node, Integer.MAX_VALUE); // Set initial distance for all nodes to infinity.
            }
            distances.put(start, 0); // Set the start node distance to 0.
            PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get)); // Priority queue to get the node with the smallest distance.
            pq.add(start); // Add the start node to the queue.

            while (!pq.isEmpty()) { // While there are nodes to process.
                String currentNode = pq.poll(); // Get the node with the smallest distance.
                if (currentNode.equals(end)) { // If the current node is the end node, exit the loop.
                    break;
                }
                for (String neighbor : graph.get(currentNode).keySet()) { // For each neighboring node of the current node.
                    int newDist = distances.get(currentNode) + graph.get(currentNode).get(neighbor); // Calculate new distance to the neighbor.
                    if (newDist < distances.get(neighbor)) { // If the new distance is smaller, update the neighbor's distance.
                        distances.put(neighbor, newDist);
                        pq.add(neighbor); // Add the neighbor to the queue for further processing.
                    }
                }
            }

            JOptionPane.showMessageDialog(frame, "Shortest Path Distance: " + distances.get(end)); // Show the shortest path distance.
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid nodes!"); // Show error if the nodes are invalid.
        }
    }

    // Method to paint the graph (visualization of nodes and edges).
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method for proper painting setup.
        g.setColor(Color.BLACK); // Set the color for drawing edges.

        // Draw edges between nodes.
        for (String source : graph.keySet()) {
            Point p1 = nodePositions.get(source); // Get the position of the source node.
            for (String target : graph.get(source).keySet()) { // For each neighboring node.
                Point p2 = nodePositions.get(target); // Get the position of the target node.
                if (p1 != null && p2 != null) {
                    g.drawLine(p1.x, p1.y, p2.x, p2.y); // Draw a line between the nodes.
                    int midX = (p1.x + p2.x) / 2; // Calculate the midpoint of the edge.
                    int midY = (p1.y + p2.y) / 2; // Calculate the midpoint of the edge.
                    g.drawString(String.valueOf(graph.get(source).get(target)), midX, midY); // Draw the edge cost at the midpoint.
                }
            }
        }

        // Draw nodes as circles and label them with their names.
        for (Map.Entry<String, Point> entry : nodePositions.entrySet()) {
            g.setColor(Color.BLUE); // Set the color for the nodes.
            Point p = entry.getValue(); // Get the position of the node.
            g.fillOval(p.x - 10, p.y - 10, 20, 20); // Draw a filled circle for the node.
            g.setColor(Color.WHITE); // Set the color for the node label.
            g.drawString(entry.getKey(), p.x - 5, p.y + 5); // Draw the node name at its position.
        }
    }

    // Main method to run the program.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(QN5::new); // Ensure that the GUI is created on the Event Dispatch Thread.
    }
}
