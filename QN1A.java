/*   
 Question1 
a) 
You have a material with n temperature levels. You know that there exists a critical temperature f where 
0 <= f <= n such that the material will react or change its properties at temperatures higher than f but 
remain unchanged at or below f. 
Rules: 
 You can measure the material's properties at any temperature level once. 
 If the material reacts or changes its properties, you can no longer use it for further measurements. 
 If the material remains unchanged, you can reuse it for further measurements. 
Goal: 
Determine the minimum number of measurements required to find the critical temperature. 
Input: 
 k: The number of identical samples of the material. 
 n: The number of temperature levels. 
Output: 
 The minimum number of measurements required to find the critical temperature. 
Example 1: 
Input: k = 1, n = 2 
Output: 2 
Explanation:  
Check the material at temperature 1. If its property changes, we know that f = 0. 
Otherwise, raise temperature to 2 and check if property changes. If its property changes, we know that f = 
1.If its property changes at temperature, then we know f = 2. 
Hence, we need at minimum 2 moves to determine with certainty what the value of f is. 
Example 2: 
Input: k = 2, n = 6 
Output: 3 
Example 3: 
Input: k = 3, n = 14 
Output: 4

*/
//  This is how logic works:- 
/*
 Step 1 :- Initialize DP Table: Create a table where dp[i][j] represents the number of temperature levels that can be tested with i samples and j attempts

Step 2 :- Set the initial number of attempts to 0.

Step 3 :- Increment attempts until the number of levels that can be tested with k samples and the current number of attempts is greater than or equal to n.

Step 4 :- Update the DP table using the recurrence relation:
    - dp[samples][attempts] = 1 + dp[samples - 1][attempts - 1] + dp[samples][attempts - 1]

Step 5 :- For each sample, calculate how many temperature levels can be tested with the current number of attempts.

Step 6 :- Repeat steps 3 and 4 until dp[k][attempts] is greater than or equal to n.

Step 7 :- Return the number of attempts when dp[k][attempts] is sufficient to test all n temperature levels.

 
 */

 

 // Solution: 
public class QN1A {

    // Function to find the critical temperature using a dynamic programming
    // approach
    public static int findCriticalTemperature(int k, int n) {
        // Create a DP table where dp[i][j] represents the maximum number of temperature
        // levels that can be checked with i samples (materials) and j attempts.
        int[][] dp = new int[k + 1][n + 1]; // k samples (materials) and n temperature levels

        // Variable to track the number of attempts we are making
        int attempts = 0;

        // Iterate until we can check all 'n' levels with 'k' samples within the allowed
        // attempts
        while (dp[k][attempts] < n) {
            attempts++; // Increase the number of attempts we're testing with

            // For each number of samples (materials) available, compute the number of
            // levels we can test
            // in the current attempt
            for (int samples = 1; samples <= k; samples++) {
                // The recurrence relation:
                // dp[samples][attempts] = 1 + (dp[samples - 1][attempts - 1] +
                // dp[samples][attempts - 1])
                // Where:
                // - dp[samples - 1][attempts - 1] is the number of levels tested if the
                // material
                // reacts (property changes)
                // - dp[samples][attempts - 1] is the number of levels tested if the material
                // does
                // not react (property remains unchanged)
                // The "+ 1" is because we're testing the current level.
                dp[samples][attempts] = 1 + dp[samples - 1][attempts - 1] + dp[samples][attempts - 1];
            }
        }

        // Return the number of attempts needed to find the critical temperature level
        return attempts;
    }

    // Main method to test the function with a few test cases
    public static void main(String[] args) {
        // Test case 1: 1 sample and 2 temperature levels
        System.out.println(findCriticalTemperature(1, 2)); // Output: 2 (With 1 sample, you need 2 attempts to find the
                                                           // critical temperature)

        // Test case 2: 2 samples and 6 temperature levels
        System.out.println(findCriticalTemperature(2, 6)); // Output: 3 (With 2 samples, you can find the critical
                                                           // temperature in 3 attempts)

        // Test case 3: 3 samples and 14 temperature levels
        System.out.println(findCriticalTemperature(3, 14)); // Output: 4 (With 3 samples, you can find the critical
                                                            // temperature in 4 attempts)
    }
}
