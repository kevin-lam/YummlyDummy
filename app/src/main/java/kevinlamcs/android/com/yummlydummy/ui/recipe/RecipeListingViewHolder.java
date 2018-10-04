package kevinlamcs.android.com.yummlydummy.ui.recipe;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
        loadPhoto(recipe);
        name.setText(recipe.getRecipeName());
        source.setText(recipe.getSourceDisplayName());
        cookingTime.setText(recipe.getTotalTimeInSeconds().toString());
        ingredientCount.setText(String.valueOf(recipe.getIngredients().size()));
    }

    private void loadPhoto(Recipe recipe) {
        if (recipe.getSmallImageUrls().size() != 0) {
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_foreground);

            Glide.with(itemView)
                    .load(recipe.getSmallImageUrls().get(0))
                    .apply(requestOptions)
                    .into(photo);
        }
    }
}
