package kevinlamcs.android.com.yummlydummy.data.model;

import java.util.List;

public class FullRecipe {

    private static final int SECONDS_IN_A_MINUTE = 60;
    private final RecipeDetail detail;
    private final Recipe recipe;

    public FullRecipe(Recipe recipe, RecipeDetail detail) {
        this.recipe = recipe;
        this.detail = detail;
    }

    public String getRecipeName() {
        return recipe.getRecipeName();
    }

    public String getSourceDisplayName() {
        return recipe.getSourceDisplayName();
    }

    public long getTotalTimeInSeconds() {
        return recipe.getTotalTimeInSeconds();
    }

    public long getTotalTimeInMinutes() {
        return recipe.getTotalTimeInSeconds() / SECONDS_IN_A_MINUTE;
    }

    public int getNumIngredients() {
        return recipe.getIngredients().size();
    }

    public int getImageCount() {
        return recipe.getSmallImageUrls().size();
    }

    public String getFirstImageUrl() {
        return ((String)(recipe.getSmallImageUrls().get(0)));
    }

    public double getCalories() {
        for (NutritionEstimate estimate : detail.getNutritionEstimates()) {
            if (estimate.getDescription() != null && estimate.getDescription().equals("Energy")) {
                return estimate.getValue();
            }
        }
        return 0.0;
    }

    public List<String> getCuisines() {
        return recipe.getAttributes().getCuisine();
    }
}
