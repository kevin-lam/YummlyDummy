package kevinlamcs.android.com.yummlydummy.ui.recipe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import kevinlamcs.android.com.yummlydummy.R;
import kevinlamcs.android.com.yummlydummy.data.model.FullRecipe;

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

    @BindView(R.id.cuisine_container)
    LinearLayout cuisineContainer;

    public RecipeListingViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(FullRecipe recipe) {
        loadPhoto(recipe);
        addCuisines(recipe);
        name.setText(recipe.getRecipeName());
        source.setText(recipe.getSourceDisplayName());
        calorieCount.setText(String.valueOf(recipe.getCalories()));
        cookingTime.setText(String.valueOf(recipe.getTotalTimeInMinutes()));
        ingredientCount.setText(String.valueOf(recipe.getNumIngredients()));
    }

    private void loadPhoto(FullRecipe recipe) {
        if (recipe.getImageCount() != 0) {
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_foreground);

            Glide.with(itemView)
                    .load(recipe.getFirstImageUrl())
                    .apply(requestOptions)
                    .into(photo);
        }
    }

    private void addCuisines(FullRecipe recipe) {
        for (String cuisine : recipe.getCuisines()) {
            addCuisineToLayout(cuisine);
        }
    }

    private void addCuisineToLayout(String cuisine) {
        Context context = itemView.getContext();
        TextView cuisineText = new TextView(context);
        cuisineText.setText(cuisine);
        cuisineText.setTextAppearance(context, R.style.TinyText);
        cuisineText.setId(cuisine.hashCode());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(4, 4, 8, 4);
        cuisineText.setLayoutParams(params);
        cuisineContainer.addView(cuisineText);
    }
}
