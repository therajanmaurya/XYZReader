package com.opensource.xyz.reader;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.mockito.Mockito.when;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.opensource.xyz.reader.data.model.Article;
import com.opensource.xyz.reader.test.common.TestComponentRule;
import com.opensource.xyz.reader.test.common.TestDataFactory;
import com.opensource.xyz.reader.ui.article.ArticleActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.List;

import rx.Observable;

@RunWith(AndroidJUnit4.class)
public class ArticleActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<ArticleActivity> main =
            new ActivityTestRule<ArticleActivity>(ArticleActivity.class, false, false) {
                @Override
                protected Intent getActivityIntent() {
                    // Override the default intent so we pass a false flag for syncing so it doesn't
                    // start a sync service in the background that would affect  the behaviour of
                    // this test.
                    return ArticleActivity.getStartIntent(
                            InstrumentationRegistry.getTargetContext(), false);
                }
            };

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public final TestRule chain = RuleChain.outerRule(component).around(main);

    @Test
    public void listOfArticlesShows() {
        List<Article> testDataArticles = TestDataFactory.makeListArticles(20);
        when(component.getMockDataManager().getArticles())
                .thenReturn(Observable.just(testDataArticles));

        main.launchActivity(null);

        int position = 0;
        for (Article article : testDataArticles) {
            onView(withId(R.id.recycler_view))
                    .perform(RecyclerViewActions.scrollToPosition(position));
            onView(withText(article.title()))
                    .check(matches(isDisplayed()));
            position++;
        }
    }

}