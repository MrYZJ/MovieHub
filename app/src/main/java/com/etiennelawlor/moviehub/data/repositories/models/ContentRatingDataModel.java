package com.etiennelawlor.moviehub.data.repositories.models;

/**
 * Created by etiennelawlor on 12/31/17.
 */

public class ContentRatingDataModel {

    // region Fields
    public String iso31661;
    public String rating;
    // endregion

    // region Getters

    public String getIso31661() {
        return iso31661;
    }

    public String getRating() {
        return rating;
    }

    // endregion

    // region Setters

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    // endregion

    @Override
    public String toString() {
        return "ContentRatingDataModel{" +
                "iso31661='" + iso31661 + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
