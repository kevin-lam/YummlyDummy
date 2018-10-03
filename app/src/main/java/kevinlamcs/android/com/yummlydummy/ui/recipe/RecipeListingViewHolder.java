package kevinlamcs.android.com.yummlydummy.ui.recipe;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kevinlamcs.android.com.yummlydummy.R;
import kevinlamcs.android.com.yummlydummy.data.model.Recipe;

public class RecipeListingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_image)
    ImageView photo;

    @BindView(R.id.recipe_name)
    TextView name;

    @BindView(R.id.recipe_source)
    TextView source;

    @BindView(R.id.recipe_ingredient_count)
    TextView ingredientCount;

    @BindView(R.id.recipe_calorie_count)
    TextView calorieCount;

    @BindView(R.id.recipe_cooking_time)
    TextView cookingTime;

    public RecipeListingViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Recipe recipe) {
        name.setText(recipe.getRecipeName());
        source.setText(recipe.getSourceDisplayName());
        cookingTime.setText(recipe.getTotalTimeInSeconds().toString());
        ingredientCount.setText(recipe.getIngredients().size());
    }
}
