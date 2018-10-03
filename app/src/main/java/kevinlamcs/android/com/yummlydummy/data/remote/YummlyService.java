package kevinlamcs.android.com.yummlydummy.data.remote;

import java.util.List;
import java.util.Observable;

import kevinlamcs.android.com.yummlydummy.data.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YummlyService {

    @GET("api/recipes")
    Observable<List<Recipe>> searchRecipes(@Query("q") String query);
}
