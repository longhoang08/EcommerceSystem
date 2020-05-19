package com.example.mobile_ui.Model;

public class Review {
    private int imageCustomer;
    private String nameCustormer;
    private int rating;
    private String comment;
    private String timeReview;

    public Review(int imageCustomer, String nameCustormer, int rating, String comment, String timeReview) {
        this.imageCustomer = imageCustomer;
        this.nameCustormer = nameCustormer;
        this.rating = rating;
        this.comment = comment;
        this.timeReview = timeReview;
    }

    public int getImageCustomer() {
        return imageCustomer;
    }

    public void setImageCustomer(int imageCustomer) {
        this.imageCustomer = imageCustomer;
    }

    public String getNameCustormer() {
        return nameCustormer;
    }

    public void setNameCustormer(String nameCustormer) {
        this.nameCustormer = nameCustormer;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimeReview() {
        return timeReview;
    }

    public void setTimeReview(String timeReview) {
        this.timeReview = timeReview;
    }
}
