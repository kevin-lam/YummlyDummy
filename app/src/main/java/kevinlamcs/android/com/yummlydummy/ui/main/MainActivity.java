package kevinlamcs.android.com.yummlydummy.ui.main;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import kevinlamcs.android.com.yummlydummy.R;
import kevinlamcs.android.com.yummlydummy.data.model.Recipe;
import kevinlamcs.android.com.yummlydummy.data.remote.YummlyService;
import kevinlamcs.android.com.yummlydummy.ui.recipe.RecipeListingAdapter;

public class MainActivity extends Activity {

    private RecipeListingAdapter recipeAdapter;
    private RecyclerView.LayoutManager recipeLayoutManager;
    private YummlyService recipeService;

    @BindView(R.id.recipe_listing)
    private RecyclerView recipeListing;

    @BindView(R.id.search_bar)
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();
        setupSearch();
    }

    private void setupRecyclerView() {
        recipeAdapter = new RecipeListingAdapter(new ArrayList<Recipe>());
        recipeLayoutManager = new LinearLayoutManager(this);
        recipeListing.setLayoutManager(recipeLayoutManager);
        recipeListing.setAdapter(recipeAdapter);
    }

    private void setupSearch() {
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {
                    recipeService.searchRecipes(searchBar.getText().toString()).
                    return true;
                }
                return false;
            }
        });
    }
}
