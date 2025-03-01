public class QN2B {

    // Function to find the closest pair of points
    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length; // Get the number of points (length of the x_coords array)
        int minDistance = Integer.MAX_VALUE; // Initialize the minimum distance as the largest possible value
        int[] closestPair = new int[2]; // Array to store the indices of the closest pair of points

        // Iterate through all pairs of points
        for (int i = 0; i < n; i++) { // Outer loop iterates through each point i
            for (int j = i + 1; j < n; j++) { // Inner loop starts from i+1 to avoid comparing the same pair twice
                // Calculate the Manhattan distance between the points (i, j)
                int distance = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);

                // If the calculated distance is smaller than the current minimum distance
                if (distance < minDistance) {
                    // Update the minimum distance and the closest pair of indices
                    minDistance = distance; // Set the new minimum distance
                    closestPair[0] = i; // Store the first index of the closest pair
                    closestPair[1] = j; // Store the second index of the closest pair
                } else if (distance == minDistance) {
                    // If the distance is the same, choose the lexicographically smaller pair
                    // A pair (i1, j1) is lexicographically smaller than (i2, j2) if i1 < i2 or (i1
                    // == i2 && j1 < j2)
                    if (i < closestPair[0] || (i == closestPair[0] && j < closestPair[1])) {
                        closestPair[0] = i; // Update the first index of the closest pair
                        closestPair[1] = j; // Update the second index of the closest pair
                    }
                }
            }
        }

        // Return the closest pair of indices
        return closestPair;
    }

    public static void main(String[] args) {
        // Test input: x_coords and y_coords representing the 2D points
        int[] x_coords = { 1, 2, 3, 2, 4 }; // X-coordinates of points
        int[] y_coords = { 2, 3, 1, 2, 3 }; // Y-coordinates of points

        // Call the function to find the closest pair of points
        int[] closestPair = findClosestPair(x_coords, y_coords);

        // Print the indices of the closest pair
        System.out.println("[" + closestPair[0] + ", " + closestPair[1] + "]"); // Output the result
    }
}
