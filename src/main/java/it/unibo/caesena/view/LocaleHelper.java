package it.unibo.caesena.view;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleHelper {

    private static final String BUNDLE_NAME = "it.unibo.caesena.displayTexts";
    private static Locale currentLocale = Locale.getDefault();

    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle(BUNDLE_NAME, currentLocale);
    }

    public static void setLocale(final Locale locale) {
        currentLocale = locale;
    }

    public static String getApplicationName() {
        return getResourceBundle().getString("applicationName");
    }

    public static String getViewTitle(String currentView, boolean withApplicationName) {
        char[] charArray = currentView.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        if (withApplicationName) {
            return getApplicationName() + " | " + getResourceBundle().getString(String.valueOf(charArray) + "Title");
        } else {
            return getResourceBundle().getString(String.valueOf(charArray) + "Title");
        }
    }

    public static String getExitDialogTitle() {
        return getResourceBundle().getString("exitDialogTitle") + " " + getApplicationName();
    }

    public static String getPickColorDialogTitle() {
        return getResourceBundle().getString("pickColorDialogTitle");
    }

    public static String getPlayersText() {
        return getResourceBundle().getString("players") + ": ";
    }

    public static String getStartGameText() {
        return getResourceBundle().getString("startViewTitle");
    }

    public static String getNameText() {
        return getResourceBundle().getString("name") + ": ";
    }

    public static String getColorText() {
        return getResourceBundle().getString("color") + ": ";
    }

    public static String getPickColorText() {
        return getResourceBundle().getString("pickColor");
    }

    public static String getResumeGameText() {
        return getResourceBundle().getString("resumeGame");
    }

    public static String getExitApplicationText() {
        return getResourceBundle().getString("exitApplication");
    }

    public static String getBackToStartMenuText() {
        return getResourceBundle().getString("backToStartMenuDialog");
    }

    public static String getConfirmBackToStartMenuText() {
        return getResourceBundle().getString("backToStartMenuDialogConfirm");
    }

    public static String getConfirmExitText() {
        return getResourceBundle().getString("exitDialogConfirm");
    }

}
