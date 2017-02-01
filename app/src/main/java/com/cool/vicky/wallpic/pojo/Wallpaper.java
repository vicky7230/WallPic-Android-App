
package com.cool.vicky.wallpic.pojo;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wallpaper {

    @SerializedName("totalHits")
    @Expose
    private Integer totalHits;
    @SerializedName("hits")
    @Expose
    private List<Hit> hits = new ArrayList<Hit>();
    @SerializedName("total")
    @Expose
    private Integer total;

    /**
     * @return The totalHits
     */
    public Integer getTotalHits() {
        return totalHits;
    }

    /**
     * @param totalHits The totalHits
     */
    public void setTotalHits(Integer totalHits) {
        this.totalHits = totalHits;
    }

    /**
     * @return The hits
     */
    public List<Hit> getHits() {
        return hits;
    }

    /**
     * @param hits The hits
     */
    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

    /**
     * @return The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

}
