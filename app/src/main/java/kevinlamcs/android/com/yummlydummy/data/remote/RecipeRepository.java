package kevinlamcs.android.com.yummlydummy.data.remote;

import io.reactivex.Observable;
import kevinlamcs.android.com.yummlydummy.data.model.RecipeDetail;
import kevinlamcs.android.com.yummlydummy.data.model.RecipeResponse;

public class RecipeRepository {

    private final YummlyService apiService;

    public RecipeRepository(YummlyService apiService) {
        this.apiService = apiService;
    }

    public Observable<RecipeResponse> load(String query) {
        return apiService.searchRecipes(query);
    }

    public Observable<RecipeDetail> loadDetail(String recipeId) {
        return apiService.getRecipe(recipeId);
    }
}
