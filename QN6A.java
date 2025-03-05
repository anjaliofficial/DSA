/*
Question 6 
a) 
You are given a class NumberPrinter with three methods: printZero, printEven, and printOdd. 
These methods are designed to print the numbers 0, even numbers, and odd numbers, respectively. 
Task: 
Create a ThreadController class that coordinates three threads: 
5. ZeroThread: Calls printZero to print 0s. 
6. EvenThread: Calls printEven to print even numbers. 
7. OddThread: Calls printOdd to print odd numbers. 
These threads should work together to print the sequence "0102030405..." up to a specified number n. 
The output should be interleaved, ensuring that the numbers are printed in the correct order. 
Example: 
If n = 5, the output should be "0102030405". 
Constraints: 
 The threads should be synchronized to prevent race conditions and ensure correct output. 
 The NumberPrinter class is already provided and cannot be modified. 
*/


// How the Algorithm Works: 


/*
 Initialization:

A NumberPrinter prints 0, odd, and even numbers.
ThreadController manages the sequence and flow of threads.
ZeroThread:

Prints 0 and switches the state to either odd or even based on currentNum.
OddThread:

Waits for the state to be 1 (odd turn), prints the current odd number, and then transitions back to ZeroThread.
EvenThread:

Waits for the state to be 2 (even turn), prints the current even number, and then transitions back to ZeroThread.
Thread Coordination:

Threads use wait() and notifyAll() to ensure only one thread runs at a time, following the order 0 -> odd -> even.
Termination:

Continues until currentNum exceeds n, then stops.
Result:
The program prints a sequence like: 0, odd, 0, even, and repeats until the limit n is reached.
 */public class QN6A {
    // Assumed NumberPrinter class (cannot be modified)
    static class NumberPrinter {
        // Method to print "0"
        public void printZero() {
            System.out.print("0");
        }

        // Method to print an even number
        public void printEven(int n) {
            System.out.print(n);
        }

        // Method to print an odd number
        public void printOdd(int n) {
            System.out.print(n);
        }
    }

    // ThreadController class to coordinate the threads
    static class ThreadController {
        private final NumberPrinter printer; // Instance of NumberPrinter to print numbers
        private final int n; // Upper limit of the sequence
        private volatile int currentNum = 1; // Current number to print, starts at 1 (odd)
        private volatile int state = 0; // State: 0 = zero, 1 = odd, 2 = even

        // Constructor to initialize the printer and upper limit (n)
        public ThreadController(NumberPrinter printer, int n) {
            this.printer = printer;
            this.n = n;
        }

        // Method for ZeroThread to print "0"
        public void printZero() throws InterruptedException {
            synchronized (this) { // Synchronize block to allow only one thread to run at a time
                while (currentNum <= n) { // Loop until currentNum exceeds n
                    while (state != 0) { // Wait if it’s not zero’s turn
                        wait();
                    }
                    if (currentNum <= n) { // Double-check to avoid printing after n
                        printer.printZero(); // Print "0"
                        if (currentNum % 2 == 1) { // If currentNum is odd
                            state = 1; // Set state to odd
                        } else { // If currentNum is even
                            state = 2; // Set state to even
                        }
                    }
                    notifyAll(); // Notify other threads that they can proceed
                }
            }
        }

        // Method for OddThread to print odd numbers
        public void printOdd() throws InterruptedException {
            synchronized (this) { // Synchronize block to allow only one thread to run at a time
                while (currentNum <= n) { // Loop until currentNum exceeds n
                    while (state != 1) { // Wait if it’s not odd’s turn
                        wait();
                    }
                    if (currentNum <= n && currentNum % 2 == 1) { // If currentNum is odd and <= n
                        printer.printOdd(currentNum); // Print the odd number
                        currentNum++; // Increment currentNum to next number (even)
                        state = 0; // Set state back to zero to allow printing "0"
                    }
                    notifyAll(); // Notify other threads that they can proceed
                }
            }
        }

        // Method for EvenThread to print even numbers
        public void printEven() throws InterruptedException {
            synchronized (this) { // Synchronize block to allow only one thread to run at a time
                while (currentNum <= n) { // Loop until currentNum exceeds n
                    while (state != 2) { // Wait if it’s not even’s turn
                        wait();
                    }
                    if (currentNum <= n && currentNum % 2 == 0) { // If currentNum is even and <= n
                        printer.printEven(currentNum); // Print the even number
                        currentNum++; // Increment currentNum to next number (odd)
                        state = 0; // Set state back to zero to allow printing "0"
                    }
                    notifyAll(); // Notify other threads that they can proceed
                }
            }
        }
    }

    // Main method to test the solution
    public static void main(String[] args) {
        int n = 5; // Example: print sequence up to 5
        NumberPrinter printer = new NumberPrinter(); // Create NumberPrinter instance
        ThreadController controller = new ThreadController(printer, n); // Create ThreadController instance

        // Create ZeroThread to print "0"
        Thread zeroThread = new Thread(() -> {
            try {
                controller.printZero(); // Call method to print "0"
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "ZeroThread");

        // Create OddThread to print odd numbers
        Thread oddThread = new Thread(() -> {
            try {
                controller.printOdd(); // Call method to print odd numbers
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "OddThread");

        // Create EvenThread to print even numbers
        Thread evenThread = new Thread(() -> {
            try {
                controller.printEven(); // Call method to print even numbers
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "EvenThread");

        // Start the threads
        zeroThread.start();
        oddThread.start();
        evenThread.start();

        // Wait for all threads to finish execution
        try {
            zeroThread.join();
            oddThread.join();
            evenThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(); // Newline after output for readability
    }
}
