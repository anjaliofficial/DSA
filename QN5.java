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


import java.awt.*;
import java.util.*;
import javax.swing.*;

public class QN5 extends JPanel {
    private JFrame frame; // JFrame to hold the main window
    private JButton addNodeBtn, addEdgeBtn, optimizeBtn, shortestPathBtn; // Buttons for adding nodes, edges, optimization, and finding shortest path
    private Map<String, Map<String, Integer>> graph; // Graph representation: map of nodes and their edges with costs
    private Map<String, Point> nodePositions; // Map to store positions of nodes for visualization
    private Random random = new Random(); // Random object to generate random positions for nodes

    public QN5() {
        // Initialize the JFrame and button controls
        frame = new JFrame("Network Topology Optimizer");
        graph = new HashMap<>(); // Initialize the graph (empty initially)
        nodePositions = new HashMap<>(); // Initialize the node positions (empty initially)

        // Set up the control panel with buttons
        JPanel controlPanel = new JPanel();
        addNodeBtn = new JButton("Add Node"); // Create button to add nodes
        addEdgeBtn = new JButton("Add Edge"); // Create button to add edges
        optimizeBtn = new JButton("Optimize Network"); // Create button for network optimization
        shortestPathBtn = new JButton("Find Shortest Path"); // Create button for finding shortest path

        // Add buttons to the control panel
        controlPanel.add(addNodeBtn);
        controlPanel.add(addEdgeBtn);
        controlPanel.add(optimizeBtn);
        controlPanel.add(shortestPathBtn);

        // Add control panel at the top of the window and this panel in the center
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(this, BorderLayout.CENTER);
        frame.setSize(700, 500); // Set the size of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application when the window is closed
        frame.setVisible(true); // Make the window visible

        // Add action listeners for each button
        addNodeBtn.addActionListener(e -> addNode()); // When "Add Node" button is clicked, call addNode() method
        addEdgeBtn.addActionListener(e -> addEdge()); // When "Add Edge" button is clicked, call addEdge() method
        optimizeBtn.addActionListener(e -> optimizeNetwork()); // When "Optimize Network" button is clicked, call optimizeNetwork() method
        shortestPathBtn.addActionListener(e -> findShortestPath()); // When "Find Shortest Path" button is clicked, call findShortestPath() method
    }

    // Method to add a node to the graph
    private void addNode() {
        String nodeName = JOptionPane.showInputDialog("Enter node name:"); // Ask user for node name
        if (nodeName != null && !nodeName.trim().isEmpty() && !graph.containsKey(nodeName)) {
            // If the name is not empty or duplicate, add the node to the graph
            graph.put(nodeName, new HashMap<>()); // Create an empty edge list for the node
            nodePositions.put(nodeName, new Point(random.nextInt(500), random.nextInt(400))); // Random position for the node
            repaint(); // Repaint the panel to update the UI with the new node
        } else {
            // If the name is invalid or duplicate, show an error message
            JOptionPane.showMessageDialog(frame, "Invalid or duplicate node name!");
        }
    }

    // Method to add an edge between two nodes with a cost
    private void addEdge() {
        String node1 = JOptionPane.showInputDialog("Enter first node:"); // Ask user for the first node
        String node2 = JOptionPane.showInputDialog("Enter second node:"); // Ask user for the second node
        if (graph.containsKey(node1) && graph.containsKey(node2) && !node1.equals(node2)) {
            // If both nodes exist and are not the same, proceed
            try {
                int cost = Integer.parseInt(JOptionPane.showInputDialog("Enter cost:")); // Ask user for edge cost
                graph.get(node1).put(node2, cost); // Add the edge to node1's edge list
                graph.get(node2).put(node1, cost); // Add the edge to node2's edge list (undirected graph)
                repaint(); // Repaint the panel to display the new edge
            } catch (NumberFormatException e) {
                // If input is not a valid number, show an error message
                JOptionPane.showMessageDialog(frame, "Invalid cost input!");
            }
        } else {
            // If the nodes are invalid or the same, show an error message
            JOptionPane.showMessageDialog(frame, "Invalid nodes!");
        }
    }

    // Method to optimize the network (MST logic placeholder)
    private void optimizeNetwork() {
        if (graph.size() > 1) {
            // If the graph has more than one node, proceed with optimization (e.g., Minimum Spanning Tree)
            JOptionPane.showMessageDialog(frame, "Network optimization logic (MST) would go here!");
        } else {
            // If the graph has only one node or no nodes, display a message
            JOptionPane.showMessageDialog(frame, "Graph is not connected!");
        }
    }

    // Method to find the shortest path between two nodes using Dijkstra's algorithm
    private void findShortestPath() {
        String start = JOptionPane.showInputDialog("Enter start node:"); // Ask user for the start node
        String end = JOptionPane.showInputDialog("Enter end node:"); // Ask user for the end node
        if (graph.containsKey(start) && graph.containsKey(end)) {
            // If both nodes exist in the graph, proceed
            Map<String, Integer> distances = new HashMap<>(); // Map to store the shortest distances from start node
            for (String node : graph.keySet()) {
                distances.put(node, Integer.MAX_VALUE); // Initialize all distances to infinity
            }
            distances.put(start, 0); // The distance to the start node is 0
            PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get)); // Priority queue for Dijkstra's algorithm
            pq.add(start); // Add the start node to the queue

            // Dijkstra's algorithm to calculate the shortest path
            while (!pq.isEmpty()) {
                String currentNode = pq.poll(); // Get the node with the smallest distance
                if (currentNode.equals(end)) {
                    break; // Stop if we reach the end node
                }
                for (String neighbor : graph.get(currentNode).keySet()) {
                    // For each neighbor of the current node
                    int newDist = distances.get(currentNode) + graph.get(currentNode).get(neighbor); // Calculate the new distance
                    if (newDist < distances.get(neighbor)) {
                        distances.put(neighbor, newDist); // Update the distance for the neighbor
                        pq.add(neighbor); // Add the neighbor to the priority queue
                    }
                }
            }

            // Display the shortest path distance
            JOptionPane.showMessageDialog(frame, "Shortest Path Distance: " + distances.get(end));
        } else {
            // If the nodes are invalid, show an error message
            JOptionPane.showMessageDialog(frame, "Invalid nodes!");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Set a light background color for the panel
        g.setColor(new Color(240, 240, 240)); 
        g.fillRect(0, 0, getWidth(), getHeight()); // Fill the background with the light color

        // Draw the edges between nodes
        for (String source : graph.keySet()) {
            Point p1 = nodePositions.get(source); // Get the position of the source node
            for (String target : graph.get(source).keySet()) {
                Point p2 = nodePositions.get(target); // Get the position of the target node
                if (p1 != null && p2 != null) {
                    int cost = graph.get(source).get(target); // Get the cost of the edge

                    // Set edge color based on the cost
                    if (cost < 10) {
                        g.setColor(Color.GREEN); // Low cost edges are green
                    } else if (cost < 50) {
                        g.setColor(Color.ORANGE); // Medium cost edges are orange
                    } else {
                        g.setColor(Color.RED); // High cost edges are red
                    }

                    // Adjust edge thickness based on the cost
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setStroke(new BasicStroke(cost / 10 + 1)); // Thicker edges for higher costs
                    g.drawLine(p1.x, p1.y, p2.x, p2.y); // Draw the line representing the edge

                    // Draw the cost of the edge at the midpoint
                    int midX = (p1.x + p2.x) / 2; // Calculate midpoint X coordinate
                    int midY = (p1.y + p2.y) / 2; // Calculate midpoint Y coordinate
                    g.setColor(Color.BLACK); // Set text color to black
                    g.drawString(String.valueOf(cost), midX, midY); // Draw the cost at the midpoint of the edge
                }
            }
        }

        // Draw the nodes
        for (Map.Entry<String, Point> entry : nodePositions.entrySet()) {
            Point p = entry.getValue(); // Get the position of the node
            g.setColor(Color.BLUE); // Set node color to blue
            g.fillOval(p.x - 15, p.y - 15, 30, 30); // Draw the node as a circle
            g.setColor(Color.WHITE); // Set text color to white
            g.setFont(new Font("Arial", Font.BOLD, 12)); // Set the font for node labels
            g.drawString(entry.getKey(), p.x - 10, p.y + 5); // Draw the node label (name) at the node's position
        }
    }

    public static void main(String[] args) {
        // Launch the application and create the QN5 instance on the Event Dispatch Thread
        SwingUtilities.invokeLater(QN5::new);
    }
}
