package fr.neamar.kiss.pojo;

import android.content.ComponentName;
import android.icu.text.Transliterator;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import fr.neamar.kiss.normalizer.StringNormalizer;
import fr.neamar.kiss.utils.UserHandle;

public final class AppPojo extends PojoWithTags {

    /** Romanized alias generated once when the app name is loaded. */
    public StringNormalizer.Result normalizedRomanizedName = null;

    public static String getComponentName(String packageName, String activityName,
                                          UserHandle userHandle) {
        return userHandle.addUserSuffixToString(packageName + "/" + activityName, '#');
    }

    public final String packageName;
    public final String activityName;
    private final ComponentName componentName;
    public final UserHandle userHandle;

    private boolean excluded;
    private boolean excludedFromHistory;
    /**
     * Whether shortcuts are excluded for this app
     */
    private boolean excludedShortcuts;
    private final boolean disabled;

    public AppPojo(String id, @NonNull String packageName, @NonNull String activityName, @NonNull UserHandle userHandle,
                   boolean isExcluded, boolean isExcludedFromHistory, boolean isExcludedShortcuts, boolean disabled) {
        super(id);

        this.packageName = packageName;
        this.activityName = activityName;
        this.userHandle = userHandle;

        this.excluded = isExcluded;
        this.excludedFromHistory = isExcludedFromHistory;
        this.excludedShortcuts = isExcludedShortcuts;
        this.disabled = disabled;
        this.componentName = new ComponentName(packageName, activityName);
    }

    public String getComponentName() {
        return getComponentName(packageName, activityName, userHandle);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        normalizedRomanizedName = null;

        if (name != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && containsNonAscii(name)) {
            StringNormalizer.Result romanizedName = Romanizer.normalize(name);
            if (!romanizedName.equals(normalizedName)) {
                normalizedRomanizedName = romanizedName;
            }
        }
    }

    private static boolean containsNonAscii(String name) {
        for (int i = 0; i < name.length(); i += 1) {
            if (name.charAt(i) > 0x7f) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private static final class Romanizer {
        private static final Transliterator TRANSLITERATOR =
                Transliterator.getInstance("Any-Latin; Latin-ASCII");

        private Romanizer() {
        }

        private static StringNormalizer.Result normalize(String name) {
            final String romanized;
            synchronized (TRANSLITERATOR) {
                romanized = TRANSLITERATOR.transliterate(name);
            }
            return StringNormalizer.normalizeWithResult(romanized, false);
        }
    }

    public boolean isExcluded() {
        return excluded;
    }

    public void setExcluded(boolean excluded) {
        this.excluded = excluded;
    }

    public boolean isExcludedFromHistory() {
        return excludedFromHistory;
    }

    public void setExcludedFromHistory(boolean excludedFromHistory) {
        this.excludedFromHistory = excludedFromHistory;
    }

    public boolean isExcludedShortcuts() {
        return excludedShortcuts;
    }

    public void setExcludedShortcuts(boolean excludedShortcuts) {
        this.excludedShortcuts = excludedShortcuts;
    }

    public String getPackageKey() {
        return userHandle.getRealHandle().hashCode() + "|" + packageName;
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public UserHandle getUserHandle() {
        return userHandle;
    }

    public ComponentName getComponent() {
        return componentName;
    }

    @Override
    public String getCustomIconId() {
        return getComponent().flattenToString();
    }
}
