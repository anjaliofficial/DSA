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
    private JFrame frame;
    private JButton addNodeBtn, addEdgeBtn, optimizeBtn, shortestPathBtn;
    private Map<String, Map<String, Integer>> graph;
    private Map<String, Point> nodePositions;
    private Random random = new Random();

    public QN5() {
        // Initialize frame and buttons
        frame = new JFrame("Network Topology Optimizer");
        graph = new HashMap<>();
        nodePositions = new HashMap<>();

        JPanel controlPanel = new JPanel();
        addNodeBtn = new JButton("Add Node");
        addEdgeBtn = new JButton("Add Edge");
        optimizeBtn = new JButton("Optimize Network");
        shortestPathBtn = new JButton("Find Shortest Path");

        controlPanel.add(addNodeBtn);
        controlPanel.add(addEdgeBtn);
        controlPanel.add(optimizeBtn);
        controlPanel.add(shortestPathBtn);

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(this, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Button Actions
        addNodeBtn.addActionListener(e -> addNode());
        addEdgeBtn.addActionListener(e -> addEdge());
        optimizeBtn.addActionListener(e -> optimizeNetwork());
        shortestPathBtn.addActionListener(e -> findShortestPath());
    }

    // Method to add a new node
    private void addNode() {
        String nodeName = JOptionPane.showInputDialog("Enter node name:");
        if (nodeName != null && !nodeName.trim().isEmpty() && !graph.containsKey(nodeName)) {
            graph.put(nodeName, new HashMap<>());
            nodePositions.put(nodeName, new Point(random.nextInt(500), random.nextInt(400)));
            repaint(); // Refresh graph
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid or duplicate node name!");
        }
    }

    // Method to add a new edge
    private void addEdge() {
        String node1 = JOptionPane.showInputDialog("Enter first node:");
        String node2 = JOptionPane.showInputDialog("Enter second node:");
        if (graph.containsKey(node1) && graph.containsKey(node2) && !node1.equals(node2)) {
            try {
                int cost = Integer.parseInt(JOptionPane.showInputDialog("Enter cost:"));
                graph.get(node1).put(node2, cost);
                graph.get(node2).put(node1, cost); // For undirected graph
                repaint(); // Refresh graph
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid cost input!");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid nodes!");
        }
    }

    // Optimize network using Minimum Spanning Tree (MST)
    private void optimizeNetwork() {
        if (graph.size() > 1) {
            // Using Prim's algorithm or Kruskal's could be implemented here (simplified for now)
            JOptionPane.showMessageDialog(frame, "Network optimization logic (MST) would go here!");
        } else {
            JOptionPane.showMessageDialog(frame, "Graph is not connected!");
        }
    }

    // Find the shortest path using Dijkstra's Algorithm
    private void findShortestPath() {
        String start = JOptionPane.showInputDialog("Enter start node:");
        String end = JOptionPane.showInputDialog("Enter end node:");
        if (graph.containsKey(start) && graph.containsKey(end)) {
            // Implementing Dijkstra's algorithm
            Map<String, Integer> distances = new HashMap<>();
            for (String node : graph.keySet()) {
                distances.put(node, Integer.MAX_VALUE);
            }
            distances.put(start, 0);
            PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
            pq.add(start);

            while (!pq.isEmpty()) {
                String currentNode = pq.poll();
                if (currentNode.equals(end)) {
                    break;
                }
                for (String neighbor : graph.get(currentNode).keySet()) {
                    int newDist = distances.get(currentNode) + graph.get(currentNode).get(neighbor);
                    if (newDist < distances.get(neighbor)) {
                        distances.put(neighbor, newDist);
                        pq.add(neighbor);
                    }
                }
            }

            JOptionPane.showMessageDialog(frame, "Shortest Path Distance: " + distances.get(end));
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid nodes!");
        }
    }

    // Paint method to visualize the graph
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        // Draw edges
        for (String source : graph.keySet()) {
            Point p1 = nodePositions.get(source);
            for (String target : graph.get(source).keySet()) {
                Point p2 = nodePositions.get(target);
                if (p1 != null && p2 != null) {
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                    int midX = (p1.x + p2.x) / 2;
                    int midY = (p1.y + p2.y) / 2;
                    g.drawString(String.valueOf(graph.get(source).get(target)), midX, midY);
                }
            }
        }

        // Draw nodes
        for (Map.Entry<String, Point> entry : nodePositions.entrySet()) {
            g.setColor(Color.BLUE);
            Point p = entry.getValue();
            g.fillOval(p.x - 10, p.y - 10, 20, 20);
            g.setColor(Color.WHITE);
            g.drawString(entry.getKey(), p.x - 5, p.y + 5);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QN5::new);
    }
}
