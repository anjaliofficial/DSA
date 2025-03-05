/*
Question 6B: 

Scenario: A Multithreaded Web Crawler 
Problem: 
[5 Marks] 
You need to crawl a large number of web pages to gather data or index content. Crawling each page 
sequentially can be time-consuming and inefficient. 
Goal: 
Create a web crawler application that can crawl multiple web pages concurrently using multithreading to 
improve performance. 
Tasks: 
Design the application: 
Create a data structure to store the URLs to be crawled. 
Implement a mechanism to fetch web pages asynchronously. 
Design a data storage mechanism to save the crawled data. 
Create a thread pool: 
Use the ExecutorService class to create a thread pool for managing multiple threads. 
Submit tasks: 
For each URL to be crawled, create a task (e.g., a Runnable or Callable object) that fetches the web page 
and processes the content. 
Submit these tasks to the thread pool for execution. 
Handle responses: 
Process the fetched web pages, extracting relevant data or indexing the content. 
Handle errors or exceptions that may occur during the crawling process. 
Manage the crawling queue: 
Implement a mechanism to manage the queue of URLs to be crawled, such as a priority queue or a 
breadth-first search algorithm. 
By completing these tasks, you will create a multithreaded web crawler that can efficiently crawl large 
numbers of web page
 */


//  This is how Algorithm works: 

/*
Step 1: Initialization
Set up:
A visited URLs set to store URLs that have already been processed.
A URL queue to manage URLs along with their crawl depth.
A thread pool for concurrent URL processing.
Define constants such as:
Maximum depth of crawling (MAX_DEPTH).
Maximum concurrent threads (MAX_THREADS).
Timeout values for HTTP requests (TIMEOUT).


Step 2: Start Crawling
Add the starting URL to the queue with an initial depth of 0.
Begin crawling from this starting URL.


Step 3: Process URLs
Continuously dequeue URLs from the queue.
For each dequeued URL:
Check if it’s already visited or if its depth exceeds the maximum depth.
If true, skip this URL and do not crawl it.
Fetch the webpage content.
Extract the title and URLs from the page.
If the page is valid (status code 200), save the title and URL to a file (e.g., CSV).



Step 4: Extract New URLs
For each URL found on the current page:
Check if the URL is valid (starts with "http") and hasn’t been visited.
Add valid, unvisited URLs to the queue with an incremented depth (depth + 1) for further crawling.


Step 5: Concurrent Crawling
Use a fixed thread pool (MAX_THREADS) to process multiple URLs concurrently, ensuring faster crawling and better resource utilization.


Step 6: Completion
After all URLs in the queue are processed, shut down the thread pool.
Finish the crawl once all tasks have completed and resources have been released. */


import java.io.*; // Import classes for file input/output operations
import java.net.*; // Import classes for network operations (URL, HTTP)
import java.util.*; // Import utility classes like Set, HashSet, Queue
import java.util.concurrent.*; // Import classes for concurrent programming (ExecutorService, Callable)
import java.util.regex.*; // Import classes for regular expressions

public class QN6B {
    // Constants for the crawler behavior
    private static final int MAX_THREADS = 5; // Number of concurrent threads to process URLs
    private static final int MAX_DEPTH = 2; // Maximum depth of the crawl (how deep it goes)
    private static final int TIMEOUT = 5000; // Timeout in milliseconds for HTTP requests

    // Regular expressions for extracting URLs and the page title
    private static final Pattern URL_PATTERN = Pattern.compile("href=\"(http[^\"]+)\""); // Regex to match HTTP links
    private static final Pattern TITLE_PATTERN = Pattern.compile("<title>(.*?)</title>", Pattern.DOTALL); // Regex to extract title

    // Thread-safe collection to track visited URLs
    private static final Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<>());
    // Thread-safe queue to manage URLs and their depth in the crawling process
    private static final Queue<UrlDepthPair> urlQueue = new ConcurrentLinkedQueue<>();
    // ExecutorService to manage a fixed number of concurrent threads
    private static final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

    public static void main(String[] args) {
        String startUrl = "https://www.cbeebies.com/"; // Starting URL for crawling, change as needed
        urlQueue.add(new UrlDepthPair(startUrl, 0)); // Add the starting URL to the queue with depth 0

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("crawled_data.csv", true))) { // Open file for writing CSV
            writer.write("Title, URL\n"); // Write the header for CSV
            List<Future<Void>> futures = new ArrayList<>(); // List to track futures of concurrent tasks

            // Crawl while there are URLs in the queue
            while (!urlQueue.isEmpty()) {
                UrlDepthPair urlDepthPair = urlQueue.poll(); // Dequeue a URL and its depth
                if (urlDepthPair == null) continue; // Skip if null (shouldn’t happen)

                // Submit a new crawling task for the dequeued URL
                Future<Void> future = executor.submit(new CrawlerTask(urlDepthPair.url, urlDepthPair.depth, writer));
                futures.add(future); // Add the future to the list
            }

            // Wait for all tasks to complete (blocking until done)
            for (Future<Void> future : futures) {
                future.get(); // Blocks until the task completes
            }
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.err.println("Error writing to CSV: " + e.getMessage()); // Handle exceptions
        } finally {
            executor.shutdown(); // Shutdown the thread pool after the crawl is finished
        }

        System.out.println("Crawling finished."); // Notify that the crawling is done
    }

    // CrawlerTask processes each URL by fetching content and extracting information
    static class CrawlerTask implements Callable<Void> {
        private final String url; // URL to crawl
        private final int depth; // Current depth of crawling
        private final BufferedWriter writer; // Writer to save the crawled data to CSV

        public CrawlerTask(String url, int depth, BufferedWriter writer) {
            this.url = url;
            this.depth = depth;
            this.writer = writer;
        }

        @Override
        public Void call() {
            if (visitedUrls.contains(url) || depth > MAX_DEPTH) return null; // Skip if already visited or depth exceeded
            visitedUrls.add(url); // Mark the URL as visited

            System.out.println("Crawling: " + url); // Print the URL being crawled
            try {
                // Set up a connection to the URL
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0"); // Set user-agent to avoid being blocked
                connection.setConnectTimeout(TIMEOUT); // Set timeout for connection
                connection.setReadTimeout(TIMEOUT); // Set timeout for reading data

                // If the response is successful (200 OK)
                if (connection.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); // Read the input stream
                    StringBuilder content = new StringBuilder(); // StringBuilder to store the content
                    String line;
                    while ((line = reader.readLine()) != null) { // Read each line of content
                        content.append(line);
                    }
                    reader.close(); // Close the reader

                    // Extract the title of the page using regex
                    String title = extractPattern(content.toString(), TITLE_PATTERN, "No Title Found");

                    // Save the title and URL to the CSV file
                    synchronized (writer) { // Ensure thread-safety when writing to the file
                        writer.write(title + ", " + url + "\n");
                        writer.flush(); // Force the data to be written to the file
                    }

                    // Extract and queue new URLs from the current page's content
                    extractUrls(content.toString(), depth + 1);
                }
            } catch (Exception e) {
                System.err.println("Failed to crawl " + url + ": " + e.getMessage()); // Handle exceptions
            }
            return null; // Return null as the task result (no need to return anything)
        }
    }

    // Method to extract URLs from the HTML content of the page
    private static void extractUrls(String content, int depth) {
        Matcher matcher = URL_PATTERN.matcher(content); // Create matcher to find URLs in the content
        while (matcher.find()) { // Iterate over all matches
            String newUrl = matcher.group(1); // Get the matched URL
            if (!visitedUrls.contains(newUrl) && newUrl.startsWith("http")) { // Check if it’s unvisited and valid
                urlQueue.add(new UrlDepthPair(newUrl, depth)); // Add the URL to the queue with incremented depth
                executor.submit(new CrawlerTask(newUrl, depth, null)); // Submit a new task to crawl this URL (no file writer)
            }
        }
    }

    // Helper method to extract data using a regex pattern
    private static String extractPattern(String content, Pattern pattern, String defaultValue) {
        Matcher matcher = pattern.matcher(content); // Create matcher with the pattern
        return matcher.find() ? matcher.group(1) : defaultValue; // Return the first match or default value if not found
    }

    // Helper class to store a URL along with its crawl depth
    static class UrlDepthPair {
        String url; // URL to be crawled
        int depth; // Depth of the crawl

        UrlDepthPair(String url, int depth) {
            this.url = url;
            this.depth = depth;
        }
    }
}
