import java.util.*;

public class QN1B {

    // Function to find the k-th smallest product from the two sorted arrays.
    public static int findKthSmallestProduct(int[] returns1, int[] returns2, int k) {
        // Min-Heap to store the products and their indices in the form of an array.
        // The heap is ordered by the product values (smallest product at the top).
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        // Insert the first product (returns1[0] * returns2[0]) into the heap.
        // The array stores three elements: {product, index in returns1, index in
        // returns2}
        minHeap.add(new int[] { returns1[0] * returns2[0], 0, 0 }); // {product, index in returns1, index in returns2}

        // Variable to store the count of how many products have been processed.
        int count = 0;

        // Process the heap k times to get the k-th smallest product.
        while (!minHeap.isEmpty()) {
            // Pop the smallest element from the heap. This element contains the smallest
            // product.
            int[] current = minHeap.poll();
            int product = current[0]; // The product value (first element in the array)
            int i = current[1]; // The index of the element in returns1 (second element)
            int j = current[2]; // The index of the element in returns2 (third element)

            // Increment the count of processed products.
            count++;
            // If we've processed the k-th smallest product, return it.
            if (count == k) {
                return product; // Return the k-th smallest product
            }

            // Add the next product from the same row in returns1, but the next element in
            // returns2.
            // This checks the next combination of the product with the same index from
            // returns1 and the next element from returns2.
            if (j + 1 < returns2.length) {
                minHeap.add(new int[] { returns1[i] * returns2[j + 1], i, j + 1 });
            }

            // Add the next product from the same column in returns2, but the next element
            // in returns1.
            // This checks the next combination of the product with the same index from
            // returns2 and the next element from returns1.
            if (i + 1 < returns1.length && j == 0) { // Only add from returns1 when j is 0 (to avoid duplicates).
                minHeap.add(new int[] { returns1[i + 1] * returns2[j], i + 1, j });
            }
        }

        // Return -1 if the loop ends without finding the k-th product (this should not
        // happen if k is valid).
        return -1;
    }

    // Main method to test the function with a few test cases.
    public static void main(String[] args) {
        // Test case 1: returns1 = {2, 5}, returns2 = {3, 4}, k = 2
        int[] returns1 = { 2, 5 };
        int[] returns2 = { 3, 4 };
        int k = 2;
        System.out.println(findKthSmallestProduct(returns1, returns2, k)); // Output: 8

        // Test case 2: returns1 = {-4, -2, 0, 3}, returns2 = {2, 4}, k = 6
        k = 6;
        System.out.println(findKthSmallestProduct(new int[] { -4, -2, 0, 3 }, new int[] { 2, 4 }, k)); // Output: 0
    }
}
