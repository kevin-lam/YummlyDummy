package kevinlamcs.android.com.yummlydummy.ui.recipe;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.zip.Inflater;

import kevinlamcs.android.com.yummlydummy.R;
import kevinlamcs.android.com.yummlydummy.data.model.FullRecipe;
import kevinlamcs.android.com.yummlydummy.data.model.Recipe;

public class RecipeListingAdapter extends RecyclerView.Adapter<RecipeListingViewHolder> {

    private List<FullRecipe> recipes;

    public RecipeListingAdapter(List<FullRecipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeListingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recipe_entry, viewGroup, false);
        return new RecipeListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListingViewHolder recipeListingViewHolder, int i) {
        recipeListingViewHolder.bind(recipes.get(i));
    }

    @Override
    public int getItemCount() {
        return recipes!= null ? recipes.size() : 0;
    }

    public void setRecipes(List<FullRecipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }
}
