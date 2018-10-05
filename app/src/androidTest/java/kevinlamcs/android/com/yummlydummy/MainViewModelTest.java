package kevinlamcs.android.com.yummlydummy;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.rx2.idler.Rx2Idler;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import io.reactivex.plugins.RxJavaPlugins;
import kevinlamcs.android.com.yummlydummy.data.remote.RecipeRepository;
import kevinlamcs.android.com.yummlydummy.data.remote.YummlyService;
import kevinlamcs.android.com.yummlydummy.ui.main.MainViewModel;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    public MainViewModel viewModel;
    public MockWebServer webServer;

    @Before
    public void setup() throws IOException {
        webServer = new MockWebServer();
        Retrofit retrofit = createRetrofit();
        webServer.setDispatcher(createDispatcher());
        YummlyService service = retrofit.create(YummlyService.class);

        RecipeRepository repo = new RecipeRepository(service);
        viewModel = new MainViewModel(repo);

        RxJavaPlugins.setInitIoSchedulerHandler(
                Rx2Idler.create("RxJava 2.x IO Scheduler")
        );
    }

    private Dispatcher createDispatcher() {
        Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                if (request.getPath().contains("api/recipes")) {
                    return createResponse("SEARCH");
                } else {
                    return createResponse("GET");
                }
            }
        };
        return dispatcher;
    }

    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(webServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createHttpClient())
                .build();
    }

    private OkHttpClient createHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor())
                .build();
    }

    private Interceptor loggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return loggingInterceptor;
    }

    private MockResponse createResponse(String type) {
        MockResponse response = new MockResponse();
        response.setResponseCode(200);
        if (type.equals("SEARCH")) {
            response.setBody("{ \"attribution\": { \"html\": \"<a href='http://www.yummly.com/recipes/soup'>soup recipes</a> search powered by <img src=''/>\", \"url\": \"http://www.yummly.com/recipes/soup\", \"text\": \"soup recipes: search powered by Yummly\", \"logo\": \"\" }, \"totalMatchCount\": 39, \"facetCounts\": {}, \"matches\": [ { \"attributes\": { \"course\": [ \"Soups\" ], \"cuisine\": [ \"Italian\" ] }, \"flavors\": { \"salty\": 0.6666666666666666, \"sour\": 0.8333333333333334, \"sweet\": 0.6666666666666666, \"bitter\": 0.5, \"meaty\": 0.16666666666666666, \"piquant\": 0.5 }, \"rating\": 4.6, \"id\": \"Vegetarian-Cabbage-Soup-Recipezaar\", \"smallImageUrls\": [], \"sourceDisplayName\": \"Food.com\", \"totalTimeInSeconds\": 4500, \"ingredients\": [ \"garlic cloves\", \"ground pepper\", \"diced tomatoes\", \"celery\", \"tomato juice\", \"salt\", \"cabbage\", \"bell peppers\", \"oregano\", \"carrots\", \"basil\", \"vegetable broth\", \"chili pepper flakes\", \"green beans\", \"onions\", \"onion soup mix\" ], \"recipeName\": \"Vegetarian Cabbage Soup\" }, { \"attributes\": { \"course\": [ \"Soups\" ], \"cuisine\": [ \"Moroccan\", \"Asian\" ] }, \"flavors\": { \"salty\": 0.6666666666666666, \"sour\": 0.6666666666666666, \"sweet\": 0.5, \"bitter\": 0.5, \"meaty\": 0.3333333333333333, \"piquant\": 0.6666666666666666 }, \"rating\": 5, \"id\": \"Oriental-Inspired-Vegetable-Soup-Recipezaar\", \"smallImageUrls\": [], \"sourceDisplayName\": \"Food.com\", \"totalTimeInSeconds\": 24300, \"ingredients\": [ \"tamari\", \"rice vinegar\", \"bamboo shoots\", \"lime juice\", \"pepper\", \"vegetable bouillon\", \"sesame oil\", \"salt\", \"carrots\", \"yellow onions\", \"red pepper\", \"garlic\", \"fish\", \"baby corn\", \"crushed red pepper\", \"spinach\", \"cremini mushrooms\", \"ginger\", \"peanut oil\", \"water\", \"raw sugar\", \"ketchup\", \"chives\", \"cabbage\", \"water chestnuts\", \"hot chili oil\" ], \"recipeName\": \"Oriental Inspired Vegetable Soup\" }, { \"attributes\": { \"course\": [ \"Main Dishes\", \"Soups\" ], \"cuisine\": [ \"Italian\" ] }, \"flavors\": { \"salty\": 0.6666666666666666, \"sour\": 0.5, \"sweet\": 0.5, \"bitter\": 0.8333333333333334, \"meaty\": 0.6666666666666666, \"piquant\": 0.6666666666666666 }, \"rating\": 5, \"id\": \"Chunky-Rice-And-Bean-Soup-Recipezaar\", \"smallImageUrls\": [], \"sourceDisplayName\": \"Food.com\", \"totalTimeInSeconds\": 2700, \"ingredients\": [ \"dried oregano\", \"chili powder\", \"chopped celery\", \"long grain rice\", \"kidney beans\", \"shredded cabbage\", \"red pepper\", \"carrot\", \"onion\", \"minced garlic\", \"green beans\", \"olive oil\", \"pepper\", \"vegetable stock\" ], \"recipeName\": \"Chunky Rice and Bean Soup\" } ] }");
        } else {
            response.setBody("{ \"attribution\": { \"html\": \"<a href='http://www.yummly.com/recipe/Hot-Turkey-Salad-Sandwiches-Allrecipes'>Hot Turkey Salad Sandwiches recipe</a> information powered by <img src='http://static.yummly.com/api-logo.png'/>\", \"url\": \"http://www.yummly.com/recipe/Hot-Turkey-Salad-Sandwiches-Allrecipes\", \"text\": \"Hot Turkey Salad Sandwiches recipes: information powered by Yummly\", \"logo\": \"http://static.yummly.com/api-logo.png\" }, \"ingredientLines\": [ \"2 cups diced cooked turkey\", \"2 celery ribs, diced\", \"1 small onion, diced\", \"2 hard-cooked eggs, chopped\", \"3/4 cup mayonnaise\", \"1/2 teaspoon salt\", \"1/4 teaspoon pepper\", \"6 hamburger buns, split\" ], \"flavors\": { \"Salty\": 0.004261637106537819, \"Meaty\": 0.0019220244139432907, \"Piquant\": 0, \"Bitter\": 0.006931612268090248, \"Sour\": 0.009972159750759602, \"Sweet\": 0.0032512755133211613 }, \"nutritionEstimates\": [ { \"attribute\": \"ENERC_KCAL\", \"description\": \"Energy\", \"value\": 317.4, \"unit\": { \"name\": \"calorie\", \"abbreviation\": \"kcal\", \"plural\": \"calories\", \"pluralAbbreviation\": \"kcal\" } }, { \"attribute\": \"FAT\", \"description\": \"Total lipid (fat)\", \"value\": 13.97, \"unit\": { \"name\": \"gram\", \"abbreviation\": \"g\", \"plural\": \"grams\", \"pluralAbbreviation\": \"grams\" } }, { \"attribute\": \"FASAT\", \"description\": \"Fatty acids, total saturated\", \"value\": 2.7, \"unit\": { \"name\": \"gram\", \"abbreviation\": \"g\", \"plural\": \"grams\", \"pluralAbbreviation\": \"grams\" } }, { \"attribute\": \"CHOLE\", \"description\": \"Cholesterol\", \"value\": 0.11, \"unit\": { \"name\": \"gram\", \"abbreviation\": \"g\", \"plural\": \"grams\", \"pluralAbbreviation\": \"grams\" } }, { \"attribute\": \"NA\", \"description\": \"Sodium, Na\", \"value\": 0.66, \"unit\": { \"name\": \"gram\", \"abbreviation\": \"g\", \"plural\": \"grams\", \"pluralAbbreviation\": \"grams\" } }, { \"attribute\": \"K\", \"description\": \"Potassium, K\", \"value\": 0.2, \"unit\": { \"name\": \"gram\", \"abbreviation\": \"g\", \"plural\": \"grams\", \"pluralAbbreviation\": \"grams\" } }, { \"attribute\": \"CHOCDF\", \"description\": \"Carbohydrate, by difference\", \"value\": 29.92, \"unit\": { \"name\": \"gram\", \"abbreviation\": \"g\", \"plural\": \"grams\", \"pluralAbbreviation\": \"grams\" } }, { \"attribute\": \"FIBTG\", \"description\": \"Fiber, total dietary\", \"value\": 1.3, \"unit\": { \"name\": \"gram\", \"abbreviation\": \"g\", \"plural\": \"grams\", \"pluralAbbreviation\": \"grams\" } }, { \"attribute\": \"SUGAR\", \"description\": \"Sugars, total\", \"value\": 5.25, \"unit\": { \"name\": \"gram\", \"abbreviation\": \"g\", \"plural\": \"grams\", \"pluralAbbreviation\": \"grams\" } }, { \"attribute\": \"PROCNT\", \"description\": \"Protein\", \"value\": 17.6, \"unit\": { \"name\": \"gram\", \"abbreviation\": \"g\", \"plural\": \"grams\", \"pluralAbbreviation\": \"grams\" } }, { \"attribute\": \"VITA_IU\", \"description\": \"Vitamin A, IU\", \"value\": 159.13, \"unit\": { \"name\": \"IU\", \"abbreviation\": \"IU\", \"plural\": \"IU\", \"pluralAbbreviation\": \"IU\" } }, { \"attribute\": \"VITC\", \"description\": \"Vitamin C, total ascorbic acid\", \"value\": 0, \"unit\": { \"name\": \"gram\", \"abbreviation\": \"g\", \"plural\": \"grams\", \"pluralAbbreviation\": \"grams\" } }, { \"attribute\": \"CA\", \"description\": \"Calcium, Ca\", \"value\": 0.08, \"unit\": { \"name\": \"gram\", \"abbreviation\": \"g\", \"plural\": \"grams\", \"pluralAbbreviation\": \"grams\" } }, { \"attribute\": \"FE\", \"description\": \"Iron, Fe\", \"value\": 0, \"unit\": { \"name\": \"gram\", \"abbreviation\": \"g\", \"plural\": \"grams\", \"pluralAbbreviation\": \"grams\" } } ], \"images\": [ { \"hostedLargeUrl\": \"http://i2.yummly.com/Hot-Turkey-Salad-Sandwiches-Allrecipes.l.png\", \"hostedSmallUrl\": \"http://i2.yummly.com/Hot-Turkey-Salad-Sandwiches-Allrecipes.s.png\" } ], \"name\": \"Hot Turkey Salad Sandwiches\", \"yield\": \"6 servings\", \"totalTime\": \"30 Min\", \"attributes\": { \"holiday\": [ \"Christmas\", \"Thanksgiving\" ], \"cuisine\": [ \"Italian\", \"Soul food\", \"American\" ] }, \"totalTimeInSeconds\": 1800, \"rating\": 4.44, \"numberOfServings\": 6, \"source\": { \"sourceRecipeUrl\": \"http://allrecipes.com/Recipe/hot-turkey-salad-sandwiches/detail.aspx\", \"sourceSiteUrl\": \"http://www.allrecipes.com\", \"sourceDisplayName\": \"AllRecipes\" }, \"id\": \"Hot-Turkey-Salad-Sandwiches-Allrecipes\" }");
        }
        return response;
    }

    @After
    public void tearDown() throws IOException {
        webServer.shutdown();
    }

    @Test
    public void testSearchRecipes() throws InterruptedException {
        viewModel.searchRecipes("");
        Thread.sleep(2000);
        assertThat(viewModel.getRecipes().getValue(), is(not(nullValue())));
    }

}
