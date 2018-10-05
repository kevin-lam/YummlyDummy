package kevinlamcs.android.com.yummlydummy.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeDetail {

    @SerializedName("attribution")
    @Expose
    private Attribution attribution;
    @SerializedName("ingredientLines")
    @Expose
    private List<String> ingredientLines = null;
    @SerializedName("flavors")
    @Expose
    private Flavors flavors;
    @SerializedName("nutritionEstimates")
    @Expose
    private List<NutritionEstimate> nutritionEstimates = null;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("yield")
    @Expose
    private String yield;
    @SerializedName("totalTime")
    @Expose
    private String totalTime;
    @SerializedName("attributes")
    @Expose
    private Attributes attributes;
    @SerializedName("totalTimeInSeconds")
    @Expose
    private Long totalTimeInSeconds;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("numberOfServings")
    @Expose
    private Long numberOfServings;
    @SerializedName("source")
    @Expose
    private Source source;
    @SerializedName("id")
    @Expose
    private String id;

    public Attribution getAttribution() {
        return attribution;
    }

    public void setAttribution(Attribution attribution) {
        this.attribution = attribution;
    }

    public List<String> getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(List<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public Flavors getFlavors() {
        return flavors;
    }

    public void setFlavors(Flavors flavors) {
        this.flavors = flavors;
    }

    public List<NutritionEstimate> getNutritionEstimates() {
        return nutritionEstimates;
    }

    public void setNutritionEstimates(List<NutritionEstimate> nutritionEstimates) {
        this.nutritionEstimates = nutritionEstimates;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYield() {
        return yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Long getTotalTimeInSeconds() {
        return totalTimeInSeconds;
    }

    public void setTotalTimeInSeconds(Long totalTimeInSeconds) {
        this.totalTimeInSeconds = totalTimeInSeconds;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(Long numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}