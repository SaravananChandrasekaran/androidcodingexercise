package in.bcs.codingexercise.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by saran on 2/19/2018.
 */

public class Facts { @SerializedName("title")
private String title;

    @SerializedName("rows")
    private List<FactsRowItems> rows;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setRows(List<FactsRowItems> rows) {
        this.rows = rows;
    }

    public List<FactsRowItems> getRows() {
        return rows;
    }

    @Override
    public String toString() {
        return
                "Facts{" +
                        "title = '" + title + '\'' +
                        ",rows = '" + rows + '\'' +
                        "}";
    }


    /**
     * Removing the empty objects from the list
     * @param rows List object of the FactsRowsItem
     * @return modified list
     */
    public List<FactsRowItems> removeEmpty(final List<FactsRowItems> rows) {

        for (int i = rows.size() - 1; i >= 0; i--) {

            if (rows.get(i).getTitle() == null && rows.get(i).getImageHref() == null && rows.get(i).getDescription() == null) {
                rows.remove(rows.get(i));
            }
        }

        return rows;
    }

}
