package kevinlamcs.android.com.yummlydummy.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kevinlamcs.android.com.yummlydummy.data.model.FullRecipe;
import kevinlamcs.android.com.yummlydummy.data.remote.RecipeRepository;


public class MainViewModel extends ViewModel {

    private MutableLiveData<List<FullRecipe>> recipes = new MutableLiveData<>();
    private RecipeRepository repo;

    public MainViewModel(RecipeRepository repo) {
        this.repo = repo;
    }

    public LiveData<List<FullRecipe>> getRecipes() {
        return recipes;
    }

    public void searchRecipes(String query) {
        repo.load(query)
                .map(response -> response.getRecipes())
                .flatMapIterable(recipeList -> recipeList)
                .flatMap(recipe -> repo.loadDetail(recipe.getId()),
                        (recipe, recipeDetail) -> new FullRecipe(recipe, recipeDetail))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(fullRecipe -> recipes.setValue(fullRecipe),
                        throwable -> Log.e(getClass().getSimpleName(), throwable.getMessage()));
    }
}
