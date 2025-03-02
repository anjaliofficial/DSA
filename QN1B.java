/*
 b)  
You have two sorted arrays of investment returns, returns1 and returns2, and a target number k. You 
want to find the kth lowest combined return that can be achieved by selecting one investment from each 
array. 
Rules: 
 The arrays are sorted in ascending order. 
 You can access any element in the arrays. 
Goal: 
Determine the kth lowest combined return that can be achieved. 
Input: 
 returns1: The first sorted array of investment returns. 
 returns2: The second sorted array of investment returns. 
 k: The target index of the lowest combined return. 
Output: 
 The kth lowest combined return that can be achieved. 
Example 1: 
Input: returns1= [2,5], returns2= [3,4], k = 2 
Output: 8 
Explanation: The 2 smallest investments are  are: - returns1 [0] * returns2 [0] = 2 * 3 = 6 - returns1 [0] * returns2 [1] = 2 * 4 = 8 
The 2nd smallest investment  is 8. 
Example 2: 
Input: returns1= [-4,-2,0,3], returns2= [2,4], k = 6 
Output: 0 
Explanation: The 6 smallest products are: - returns1 [0] * returns2 [1] = (-4) * 4 = -16 - returns1 [0] * returns2 [0] = (-4) * 2 = -8 - returns1 [1] * returns2 [1] = (-2) * 4 = -8 - returns1 [1] * returns2 [0] = (-2) * 2 = -4 - returns1 [2] * returns2 [0] = 0 * 2 = 0 - returns1 [2] * returns2 [1] = 0 * 4 = 0 
The 6th smallest investment is 0
*/


// how the algorithm works: 
// 
/*1. Min-Heap Initialization: Create a min-heap to store triplets `{product, index_in_returns1, index_in_returns2}`.
2. Insert the First Product: Insert the product of the first elements from both arrays (`returns1[0] * returns2[0]`) into the heap.
3. Iterative Processing: Extract the smallest product from the heap, increment a counter, and return the product when the counter reaches `k`.
4. Generate New Products: After extracting a product, add the next possible products from the same row or column to the heap.
5. Repeat: Continue the process until the k-th smallest product is found.
6. Return the k-th Product: Once the k-th product is extracted, return it.*/


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
