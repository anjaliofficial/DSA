import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QN4A {

    public static void main(String[] args) {
        // Sample input: Tweets posted in February 2024
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(new Tweet(13, "Hello #HappyDay!", LocalDate.of(2024, 2, 1)));
        tweets.add(new Tweet(14, "#HappyDay is awesome! #HappyDay", LocalDate.of(2024, 2, 2)));
        tweets.add(new Tweet(15, "#WorkLife balance", LocalDate.of(2024, 2, 3)));
        tweets.add(new Tweet(16, "#TechLife and #TechLife", LocalDate.of(2024, 2, 4)));
        tweets.add(new Tweet(17, "#HappyDay", LocalDate.of(2024, 2, 5)));
        tweets.add(new Tweet(18, "#TechLife", LocalDate.of(2024, 2, 28))); // Now in February
        tweets.add(new Tweet(19, "#Nature", LocalDate.of(2024, 2, 9)));


        // Get the top 3 trending hashtags
        List<String> topHashtags = getTopHashtags(tweets);

        // Print the results
        topHashtags.forEach(System.out::println);
    }

    /**
     * Finds the top 3 trending hashtags in February 2024.
     *
     * @param tweets List of tweets to analyze.
     * @return List of top 3 hashtags with their counts, formatted as "hashtag: count".
     */
    public static List<String> getTopHashtags(List<Tweet> tweets) {
        // Map to store hashtag counts
        Map<String, Integer> hashtagCounts = new HashMap<>();

        // Process each tweet
        for (Tweet tweet : tweets) {
            LocalDate date = tweet.getDate();
            // Check if the tweet is from February 2024
            if (date.getYear() == 2024 && date.getMonth() == Month.FEBRUARY) {
                // Extract unique hashtags from the tweet's content
                Set<String> hashtags = extractHashtags(tweet.getContent());
                // Update the count for each hashtag
                for (String hashtag : hashtags) {
                    hashtagCounts.put(hashtag, hashtagCounts.getOrDefault(hashtag, 0) + 1);
                }
            }
        }

        // Sort the hashtags by count (descending) and then by hashtag name (descending)
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(hashtagCounts.entrySet());
        sortedEntries.sort((e1, e2) -> {
            int countCompare = e2.getValue().compareTo(e1.getValue()); // Count descending
            if (countCompare != 0) {
                return countCompare;
            } else {
                return e2.getKey().compareTo(e1.getKey()); // Hashtag name descending
            }
        });

        // Prepare the result: top 3 hashtags
        List<String> result = new ArrayList<>();
        int limit = Math.min(3, sortedEntries.size()); // Ensure we don't exceed 3
        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Integer> entry = sortedEntries.get(i);
            result.add(entry.getKey() + ": " + entry.getValue());
        }

        return result;
    }

    /**
     * Extracts unique hashtags from a tweet's content.
     *
     * @param content The tweet's content.
     * @return A set of unique hashtags.
     */
    private static Set<String> extractHashtags(String content) {
        Set<String> hashtags = new HashSet<>();
        Pattern pattern = Pattern.compile("#\\w+"); // Regex to match hashtags
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            hashtags.add(matcher.group()); // Add each hashtag to the set
        }
        return hashtags;
    }

    /**
     * Represents a tweet with an ID, content, and date.
     */
    static class Tweet {
        private int tweetId;
        private String content;
        private LocalDate date;

        public Tweet(int tweetId, String content, LocalDate date) {
            this.tweetId = tweetId;
            this.content = content;
            this.date = date;
        }

        public int getTweetId() { return tweetId; }
        public String getContent() { return content; }
        public LocalDate getDate() { return date; }
    }
}