import java.util.Arrays; // Importing the Arrays utility class for array operations.

public class QN2A {

    // Function to calculate the minimum rewards based on ratings.
    public static int minRewards(int[] ratings) {
        int n = ratings.length; // Get the length of the ratings array.
        int[] rewards = new int[n]; // Create an array to store the rewards for each employee.
        Arrays.fill(rewards, 1); // Initialize all rewards to 1, as each employee must get at least 1 reward.

        // Left-to-Right Pass (checking for increasing ratings).
        for (int i = 1; i < n; i++) { // Loop through the ratings from left to right, starting from the second
                                      // employee.
            if (ratings[i] > ratings[i - 1]) { // If the current rating is greater than the previous one.
                rewards[i] = rewards[i - 1] + 1; // Reward the current employee more than the previous one.
            }
        }

        // Right-to-Left Pass (checking for decreasing ratings).
        for (int i = n - 2; i >= 0; i--) { // Loop through the ratings from right to left, starting from the second last
                                           // employee.
            if (ratings[i] > ratings[i + 1]) { // If the current rating is greater than the next one.
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1); // Ensure the reward is correct by comparing with
                                                                       // the next employee's reward + 1.
            }
        }

        // Compute the total rewards by summing up the values in the rewards array.
        int totalRewards = 0; // Initialize a variable to store the total reward count.
        for (int reward : rewards) { // Loop through the rewards array.
            totalRewards += reward; // Add each employee's reward to the total.
        }

        return totalRewards; // Return the total reward count.
    }

    // Main method to test the function with different test cases.
    public static void main(String[] args) {
        // Test Case 1: ratings = {1, 0, 2}, the minimum total rewards = 5.
        System.out.println(minRewards(new int[] { 1, 0, 2 })); // Output: 5
        // Explanation: Employee 1 gets 2 rewards, Employee 2 gets 1, Employee 3 gets 2.

        // Test Case 2: ratings = {1, 2, 2}, the minimum total rewards = 4.
        System.out.println(minRewards(new int[] { 1, 2, 2 })); // Output: 4
        // Explanation: Employee 1 gets 1 reward, Employee 2 gets 2, Employee 3 gets 1.
    }
}
