/*
a) 
You have a team of n employees, and each employee is assigned a performance rating given in the 
integer array ratings. You want to assign rewards to these employees based on the following rules: 
Every employee must receive at least one reward. 
Employees with a higher rating must receive more rewards than their adjacent colleagues. 
Goal: 
Determine the minimum number of rewards you need to distribute to the employees. 
Input: 
ratings: The array of employee performance ratings. 
Output: 
The minimum number of rewards needed to distribute.  
Example 1: 
Input: ratings = [1, 0, 2] 
Output: 5 
Explanation: You can allocate to the first, second and third employee with 2, 1, 2 rewards respectively. 
Example 2: 
Input: ratings = [1, 2, 2] 
Output: 4 
Explanation: You can allocate to the first, second and third employee with 1, 2, 1 rewards respectively. 
The third employee gets 1 rewards because it satisfies the above two conditions. 

*/ 


// Explanation of how the algorithm works for the minRewards function:
/*
Step 1: Initialization
Objective: Initialize an array to store rewards for each employee.
Action:
Create an array rewards of the same length as ratings where each element is initialized to 1, ensuring every employee gets at least 1 reward.

Step 2: Left-to-Right Pass
Objective: Ensure that employees with higher ratings than the previous one get more rewards.
Action:
Traverse the ratings array from left to right starting from the second employee.
If the current employee's rating is higher than the previous employee's rating, increase the current employee's reward by 1 more than the previous employee's reward.


Step 3: Right-to-Left Pass
Objective: Ensure that employees with higher ratings than the next one also get more rewards, considering the previously updated rewards.
Action:
Traverse the ratings array from right to left starting from the second last employee.
If the current employee's rating is higher than the next employee's rating, update the current employee's reward by taking the maximum of its current reward and the next employee's reward + 1 to handle conflicts from the left-to-right pass.


Step 4: Compute Total Rewards
Objective: Calculate the total number of rewards.
Action:
Sum up all values in the rewards array to get the total reward count.


Step 5: Return Total Rewards
Objective: Return the final total rewards.
Action:
Return the calculated total reward count from the rewards array.
*/ 



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
