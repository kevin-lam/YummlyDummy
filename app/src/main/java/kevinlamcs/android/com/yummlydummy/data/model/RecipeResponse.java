package kevinlamcs.android.com.yummlydummy.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeResponse {

    @SerializedName("attribution")
    @Expose
    private Attribution attribution;
    @SerializedName("totalMatchCount")
    @Expose
    private Long totalMatchCount;
    @SerializedName("matches")
    @Expose
    private List<Recipe> recipes = null;

    public Attribution getAttribution() {
        return attribution;
    }

    public void setAttribution(Attribution attribution) {
        this.attribution = attribution;
    }

    public Long getTotalMatchCount() {
        return totalMatchCount;
    }

    public void setTotalMatchCount(Long totalMatchCount) {
        this.totalMatchCount = totalMatchCount;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

}
