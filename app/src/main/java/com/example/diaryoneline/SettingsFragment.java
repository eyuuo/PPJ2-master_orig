package com.example.diaryoneline;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

public class SettingsFragment extends PreferenceFragment {

    SharedPreferences prefs;

    ListPreference FontPreference;
    ListPreference FontSizePreference;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_preferences);
        FontPreference = (ListPreference) findPreference("Font_list");
        FontSizePreference = (ListPreference) findPreference("Font_size_list");

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (!prefs.getString("Font_list", "").equals("")) {
            FontPreference.setSummary(prefs.getString("Font_list", "본고딕"));
        }

        if (!prefs.getString("Font_size_list", "").equals("")) {
            FontSizePreference.setSummary(prefs.getString("Font_size_list", "보통"));
        }

        prefs.registerOnSharedPreferenceChangeListener(prefListener);

    }// onCreate

    SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("Font_list")) {
                FontPreference.setSummary(prefs.getString("Font_list", "본고딕"));
            }

            if (key.equals("Font_size_list")) {
                FontSizePreference.setSummary(prefs.getString("Font_size_list", "보통"));
            }
        }
    };
}
