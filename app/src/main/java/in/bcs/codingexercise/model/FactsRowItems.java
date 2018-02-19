package in.bcs.codingexercise.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by saran on 2/19/2018.
 */

public class FactsRowItems {
    @SerializedName("imageHref")
    private String imageHref;

    @SerializedName("description")
    private String description;

    @SerializedName("title")
    private String title;

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return
                "FactRowsItem{" +
                        "imageHref = '" + imageHref + '\'' +
                        ",description = '" + description + '\'' +
                        ",title = '" + title + '\'' +
                        "}";
    }


}