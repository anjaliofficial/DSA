import java.util.*;

// Class to handle Union-Find operations (used to track connected components)
class UnionFind {
    private int[] parent; // Array to store the parent of each device
    private int[] rank; // Array to store the rank (or depth) of each device tree

    // Constructor to initialize the UnionFind structure
    public UnionFind(int size) {
        parent = new int[size]; // Initialize the parent array
        rank = new int[size]; // Initialize the rank array
        // Initially, every device is its own parent (self-connected)
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 1; // Every device starts with rank 1
        }
    }

    // Find the root of the device (used to check if two devices are in the same
    // set)
    public int find(int x) {
        if (parent[x] != x) {
            // If the device is not its own parent, recursively find the root
            parent[x] = find(parent[x]);
        }
        return parent[x]; // Return the root device of the set
    }

    // Union of two devices (connect them if they are not already connected)
    public boolean union(int x, int y) {
        int rootX = find(x); // Find the root of device x
        int rootY = find(y); // Find the root of device y
        if (rootX != rootY) {
            // If they belong to different sets, connect them
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX; // Make rootX the parent of rootY
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY; // Make rootY the parent of rootX
            } else {
                parent[rootY] = rootX; // Arbitrarily choose rootX to be parent of rootY
                rank[rootX]++; // Increment the rank of rootX
            }
            return true; // Devices were successfully united
        }
        return false; // Devices were already in the same set
    }
}

// Main class to calculate the minimum cost to connect all devices
public class QN3A {

    // Method to find the minimum cost to connect all devices
    public static int minCostToConnectDevices(int n, int[] modules, int[][] connections) {
        // List to store all connections (both module installations and direct
        // connections)
        List<int[]> allConnections = new ArrayList<>();

        // Add virtual connections from the virtual node (0) to each device with module
        // installation costs
        for (int i = 1; i <= n; i++) {
            allConnections.add(new int[] { 0, i, modules[i - 1] }); // Virtual connections (0, i, cost)
        }

        // Add existing direct connections between devices
        for (int[] conn : connections) {
            allConnections.add(conn); // Add each given connection to the list
        }

        // Sort the connections by cost in ascending order
        allConnections.sort((a, b) -> Integer.compare(a[2], b[2]));

        // Initialize UnionFind to manage the connected components
        UnionFind uf = new UnionFind(n + 1); // n + 1 because we have a virtual node (0)
        int totalCost = 0; // Variable to keep track of the total cost

        // Iterate through the sorted list of connections
        for (int[] conn : allConnections) {
            int device1 = conn[0]; // The first device in the connection
            int device2 = conn[1]; // The second device in the connection
            int cost = conn[2]; // The cost of this connection

            // Try to unite the two devices, if they aren't already connected
            if (uf.union(device1, device2)) {
                totalCost += cost; // If they were successfully connected, add the cost to the total
            }
        }

        // Return the total cost to connect all devices
        return totalCost;
    }

    // Main method to test the function
    public static void main(String[] args) {
        // Test input
        int n = 3; // 3 devices
        int[] modules = { 1, 2, 2 }; // Costs to install modules on devices 1, 2, and 3
        int[][] connections = { { 1, 2, 1 }, { 2, 3, 1 } }; // Direct connections between devices 1-2 and 2-3

        // Call the method to find the minimum cost and print the result
        System.out.println(minCostToConnectDevices(n, modules, connections)); // Expected output: 3
    }
}
