/*
 * Copyright (C) 2018-2026 crDroid Android Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lineageos.device.DeviceSettings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceManager;
import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settingslib.widget.SettingsBasePreferenceFragment;

import java.util.Arrays;

public class DeviceSettings extends SettingsBasePreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    private static final String TAG = DeviceSettings.class.getSimpleName();

    private static final String KEY_GAME_SWITCH = "game_mode";
    private static final String KEY_EDGE_TOUCH = "edge_touch";
    private static final String KEY_USB2_SWITCH = "usb2_fast_charge";

    private static final String FILE_GAME = "/proc/touchpanel/game_switch_enable";
    private static final String FILE_EDGE = "/proc/touchpanel/oplus_tp_direction";
    private static final String FILE_FAST_CHARGE = "/sys/module/oplus_chg/parameters/force_fast_charge";

    private SwitchPreferenceCompat mGameModeSwitch;
    private SwitchPreferenceCompat mEdgeTouchSwitch;
    private SwitchPreferenceCompat mUSB2FastChargeModeSwitch;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.main);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        mGameModeSwitch = (SwitchPreferenceCompat) findPreference(KEY_GAME_SWITCH);
        if (Utils.fileWritable(FILE_GAME)) {
            mGameModeSwitch.setEnabled(true);
            mGameModeSwitch.setChecked(sharedPrefs.getBoolean(KEY_GAME_SWITCH,
                Utils.getFileValueAsBoolean(FILE_GAME, false)));
            mGameModeSwitch.setOnPreferenceChangeListener(this);
        } else {
            mGameModeSwitch.setEnabled(false);
        }

        mEdgeTouchSwitch = (SwitchPreferenceCompat) findPreference(KEY_EDGE_TOUCH);
        if (Utils.fileWritable(FILE_EDGE)) {
            mEdgeTouchSwitch.setEnabled(true);
            mEdgeTouchSwitch.setChecked(sharedPrefs.getBoolean(KEY_EDGE_TOUCH,
                Utils.getFileValueAsBoolean(FILE_EDGE, false)));
            mEdgeTouchSwitch.setOnPreferenceChangeListener(this);
        } else {
            mEdgeTouchSwitch.setEnabled(false);
        }

        mUSB2FastChargeModeSwitch = (SwitchPreferenceCompat) findPreference(KEY_USB2_SWITCH);
        if (Utils.fileWritable(FILE_FAST_CHARGE)) {
            mUSB2FastChargeModeSwitch.setEnabled(true);
            mUSB2FastChargeModeSwitch.setChecked(sharedPrefs.getBoolean(KEY_USB2_SWITCH,
                Utils.getFileValueAsBoolean(FILE_FAST_CHARGE, false)));
            mUSB2FastChargeModeSwitch.setOnPreferenceChangeListener(this);
        } else {
            mUSB2FastChargeModeSwitch.setEnabled(false);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mGameModeSwitch) {
            boolean enabled = (Boolean) newValue;
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            sharedPrefs.edit().putBoolean(KEY_GAME_SWITCH, enabled).commit();
    	    Utils.writeValue(FILE_GAME, enabled ? "1" : "0");
            return true;
        } else if (preference == mEdgeTouchSwitch) {
            boolean enabled = (Boolean) newValue;
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            sharedPrefs.edit().putBoolean(KEY_EDGE_TOUCH, enabled).commit();
    	    Utils.writeValue(FILE_EDGE, enabled ? "1" : "0");
            return true;
        } else if (preference == mUSB2FastChargeModeSwitch) {
            boolean enabled = (Boolean) newValue;
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            sharedPrefs.edit().putBoolean(KEY_USB2_SWITCH, enabled).commit();
    	    Utils.writeValue(FILE_FAST_CHARGE, enabled ? "1" : "0");
            return true;
        }

        return false;
    }

    public static void restoreSettings(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (Utils.fileWritable(FILE_GAME)) {
            boolean value = sharedPrefs.getBoolean(KEY_GAME_SWITCH,
                Utils.getFileValueAsBoolean(FILE_GAME, false));
            Utils.writeValue(FILE_GAME, value ? "1" : "0");
        }

        if (Utils.fileWritable(FILE_EDGE)) {
            boolean value = sharedPrefs.getBoolean(KEY_EDGE_TOUCH,
                Utils.getFileValueAsBoolean(FILE_EDGE, false));
            Utils.writeValue(FILE_EDGE, value ? "1" : "0");
        }

        if (Utils.fileWritable(FILE_FAST_CHARGE)) {
            boolean value = sharedPrefs.getBoolean(KEY_USB2_SWITCH,
                Utils.getFileValueAsBoolean(FILE_FAST_CHARGE, false));
            Utils.writeValue(FILE_FAST_CHARGE, value ? "1" : "0");
        }
    }
}
