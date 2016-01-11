package com.quinny898.library.persistentsearch.sample;

import android.graphics.drawable.Drawable;

import com.quinny898.library.persistentsearch.SearchResult;

/**
 * Created by vladkoto on 1/10/16.
 */
public class MySearchResult extends SearchResult {
    public String searchString;
    public MySearchResult(String title, Drawable icon, String searchString) {
        super(title, icon);
        this.searchString = searchString;
    }
}
