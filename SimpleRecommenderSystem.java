import java.util.*;

public class SimpleRecommenderSystem {

    // Sample data: User ratings for products (user -> product -> rating)
    private static Map<Integer, Map<Integer, Double>> userRatings = new HashMap<>();

    public static void main(String[] args) {
        // Initialize with sample data
        initializeSampleData();

        // Get recommendations for user 1
        int targetUserId = 1;
        List<RecommendedItem> recommendations = getRecommendations(targetUserId);

        // Display recommendations
        System.out.println("Recommendations for User " + targetUserId + ":");
        for (RecommendedItem item : recommendations) {
            System.out.printf("Product %d (Predicted Rating: %.2f)%n", 
                            item.productId, item.predictedRating);
        }
    }

    private static void initializeSampleData() {
        // User 1 ratings
        Map<Integer, Double> user1Ratings = new HashMap<>();
        user1Ratings.put(101, 5.0);
        user1Ratings.put(102, 3.0);
        user1Ratings.put(103, 4.0);
        userRatings.put(1, user1Ratings);

        // User 2 ratings
        Map<Integer, Double> user2Ratings = new HashMap<>();
        user2Ratings.put(101, 4.0);
        user2Ratings.put(102, 2.0);
        user2Ratings.put(104, 5.0);
        userRatings.put(2, user2Ratings);

        // User 3 ratings
        Map<Integer, Double> user3Ratings = new HashMap<>();
        user3Ratings.put(101, 2.0);
        user3Ratings.put(103, 5.0);
        user3Ratings.put(105, 4.0);
        userRatings.put(3, user3Ratings);

        // User 4 ratings
        Map<Integer, Double> user4Ratings = new HashMap<>();
        user4Ratings.put(102, 3.0);
        user4Ratings.put(104, 4.0);
        user4Ratings.put(105, 5.0);
        userRatings.put(4, user4Ratings);
    }

    private static List<RecommendedItem> getRecommendations(int userId) {
        // Find similar users
        Map<Integer, Double> similarUsers = findSimilarUsers(userId);

        // Get products not rated by the target user
        Set<Integer> unratedProducts = getUnratedProducts(userId);

        // Predict ratings for unrated products
        List<RecommendedItem> recommendations = new ArrayList<>();
        for (int productId : unratedProducts) {
            double predictedRating = predictRating(userId, productId, similarUsers);
            recommendations.add(new RecommendedItem(productId, predictedRating));
        }

        // Sort by predicted rating (highest first)
        recommendations.sort((a, b) -> Double.compare(b.predictedRating, a.predictedRating));

        // Return top 3 recommendations
        return recommendations.subList(0, Math.min(3, recommendations.size()));
    }

    private static Map<Integer, Double> findSimilarUsers(int userId) {
        Map<Integer, Double> similarities = new HashMap<>();
        Map<Integer, Double> targetUserRatings = userRatings.get(userId);

        for (int otherUserId : userRatings.keySet()) {
            if (otherUserId == userId) continue;

            double similarity = calculateSimilarity(targetUserRatings, userRatings.get(otherUserId));
            if (similarity > 0) {
                similarities.put(otherUserId, similarity);
            }
        }

        return similarities;
    }

    private static double calculateSimilarity(Map<Integer, Double> user1, Map<Integer, Double> user2) {
        // Simple cosine similarity implementation
        double dotProduct = 0;
        double norm1 = 0;
        double norm2 = 0;

        // Find common products rated by both users
        Set<Integer> commonProducts = new HashSet<>(user1.keySet());
        commonProducts.retainAll(user2.keySet());

        if (commonProducts.isEmpty()) return 0;

        for (int productId : commonProducts) {
            double rating1 = user1.get(productId);
            double rating2 = user2.get(productId);
            dotProduct += rating1 * rating2;
            norm1 += rating1 * rating1;
            norm2 += rating2 * rating2;
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    private static Set<Integer> getUnratedProducts(int userId) {
        Set<Integer> allProducts = new HashSet<>();
        Set<Integer> ratedProducts = userRatings.get(userId).keySet();

        for (Map<Integer, Double> ratings : userRatings.values()) {
            allProducts.addAll(ratings.keySet());
        }

        allProducts.removeAll(ratedProducts);
        return allProducts;
    }

    private static double predictRating(int userId, int productId, Map<Integer, Double> similarUsers) {
        double weightedSum = 0;
        double similaritySum = 0;

        for (Map.Entry<Integer, Double> entry : similarUsers.entrySet()) {
            int similarUserId = entry.getKey();
            double similarity = entry.getValue();

            // If the similar user has rated this product
            if (userRatings.get(similarUserId).containsKey(productId)) {
                double rating = userRatings.get(similarUserId).get(productId);
                weightedSum += similarity * rating;
                similaritySum += similarity;
            }
        }

        return similaritySum > 0 ? weightedSum / similaritySum : 0;
    }

    static class RecommendedItem {
        int productId;
        double predictedRating;

        RecommendedItem(int productId, double predictedRating) {
            this.productId = productId;
            this.predictedRating = predictedRating;
        }
    }
}



