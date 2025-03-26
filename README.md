# AI-BASED-RECOMMENDATION-SYSTEM

COMPANY: CODTECH IT SOLUTIONS

NAME: ROHIT THAKUR

INTERN ID: CT12QNE

DOMAIN: JAVA PROGRAMMING

DURATION: 8 WEEKS

MENTOR: NEELA SANTOSH

##

Core Functionality:
This is a user-based collaborative filtering system that recommends products by:

Finding users with similar rating patterns

Predicting how the target user would rate unrated products

Suggesting the highest predicted items

Key Components:

userRatings - Stores user-product rating data (UserID → {ProductID → Rating})

calculateSimilarity() - Computes cosine similarity between users' rating vectors

findSimilarUsers() - Identifies users with similar tastes

predictRating() - Estimates ratings for unseen products

getRecommendations() - Generates top 3 product suggestions

Workflow:

Load sample rating data (4 users, 5 products)

For target user (User 1):

Find most similar users

Calculate predicted ratings for unrated products

Return top 3 highest predicted products

![Image](https://github.com/user-attachments/assets/9268cee7-1b96-4fba-8e9a-2ce6d87012f7)
