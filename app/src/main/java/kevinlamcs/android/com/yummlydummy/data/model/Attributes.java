package kevinlamcs.android.com.yummlydummy.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Attributes {

    @SerializedName("course")
    @Expose
    private List<String> course = null;
    @SerializedName("cuisine")
    @Expose
    private List<String> cuisine = null;

    public List<String> getCourse() {
        return course;
    }

    public void setCourse(List<String> course) {
        this.course = course;
    }

    public List<String> getCuisine() {
        return cuisine;
    }

    public void setCuisine(List<String> cuisine) {
        this.cuisine = cuisine;
    }
}

