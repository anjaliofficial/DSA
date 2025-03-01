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
