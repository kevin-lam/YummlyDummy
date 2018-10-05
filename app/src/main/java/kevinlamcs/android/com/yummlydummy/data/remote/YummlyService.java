package kevinlamcs.android.com.yummlydummy.data.remote;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import kevinlamcs.android.com.yummlydummy.data.model.RecipeDetail;
import kevinlamcs.android.com.yummlydummy.data.model.RecipeResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YummlyService {

    @GET("api/recipes")
    Observable<RecipeResponse> searchRecipes(@Query("q") String query);

    @GET("api/recipe/{id}")
    Observable<RecipeDetail> getRecipe(@Path("id") String recipeId);
}
