package com.quinny898.library.persistentsearch.sample;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchBox.MenuListener;
import com.quinny898.library.persistentsearch.SearchBox.SearchListener;
import com.quinny898.library.persistentsearch.SearchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {
    Boolean isSearch;
    private SearchBox search;
    private View topLayout;
    private View underlying_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = (SearchBox) findViewById(R.id.searchbox);
        topLayout = findViewById(R.id.top_layout);
        underlying_layout = findViewById(R.id.underlying_layout);
        topLayout.setVisibility(View.GONE);
        search.enableVoiceRecognition(this);
        for (int x = 0; x < 10; x++) {
            SearchResult option = new MySearchResult("Result " + Integer.toString(x),
                    getResources().getDrawable(R.drawable.ic_history), "Result " + Integer.toString(x));
            search.addSearchable(option);
        }
        search.setMenuListener(new MenuListener() {

            @Override
            public void onMenuClick() {
                //Hamburger has been clicked
                Toast.makeText(MainActivity.this, "Menu click", Toast.LENGTH_LONG).show();
            }

        });
        search.setSearchListener(new SearchListener() {

            @Override
            public void onSearchOpened() {
                topLayout.setVisibility(View.VISIBLE);
                underlying_layout.setVisibility(View.GONE);
                search.setBackgroundColor(Color.parseColor("#88666666"));
            }

            @Override
            public void onSearchClosed() {
                topLayout.setVisibility(View.GONE);
                underlying_layout.setVisibility(View.VISIBLE);
                search.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void onSearchTermChanged(String term) {
                //React to the search term changing
                //Called after it has updated results
                Log.i("CITA", "TermChanged " + term);
            }

            @Override
            public void onSearch(String searchTerm) {
                Toast.makeText(MainActivity.this, searchTerm + " Searched", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResultClick(SearchResult result) {
                //React to a result being clicked
                Log.i("CITA", "Clicked " + result);
            }

            @Override
            public void onSearchCleared() {
                //Called when the clear button is clicked
            }

        });
//        search.setOverflowMenu(R.menu.overflow_menu);
//        search.setOverflowMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.test_menu_item:
//                        Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
//                        return true;
//                }
//                return false;
//            }
//        });

        search.setHint("Sanemejs!");
        List<SearchResult> initial = Collections.singletonList(
                new SearchResult("Initial result ", getResources().getDrawable(R.drawable.ic_history))
        );
        search.setCardResults(new ArrayList<SearchResult>(initial));

        search.setSearchFilter(new SearchBox.SearchFilter() {
            @Override
            public boolean onFilter(SearchResult searchResult, String searchTerm) {
                if (searchTerm.isEmpty()) {
                    return false;
                }
                String s = searchResult.title;
                if (searchResult instanceof MySearchResult)
                {
                    s = ((MySearchResult) searchResult).searchString;
                }
                return s.toLowerCase().contains(searchTerm.toLowerCase());
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            search.populateEditText(matches.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void reveal(View v) {
        startActivity(new Intent(this, RevealActivity.class));
    }


}
