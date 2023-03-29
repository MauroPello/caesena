package it.unibo.caesena.view;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleHelper {

    private static final String BUNDLE_NAME = "it.unibo.caesena.displayTexts";
    private static Locale currentLocale = Locale.getDefault();

    /**
     * 
     * @return 
     */
    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle(BUNDLE_NAME, currentLocale);
    }

    /**
     * 
     * @param locale to set the location of currentLocale
     */
    public static void setLocale(final Locale locale) {
        currentLocale = locale;
    }

    /**
     * 
     * @return in String, the name of Application
     */
    public static String getApplicationName() {
        return getResourceBundle().getString("applicationName");
    }

    /**
     * 
     * @param currentView 
     * @param withApplicationName 
     * @return
     */
    public static String getViewTitle(String currentView, boolean withApplicationName) {
        char[] charArray = currentView.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        if (withApplicationName) {
            return getApplicationName() + " | " + getResourceBundle().getString(String.valueOf(charArray) + "Title");
        } else {
            return getResourceBundle().getString(String.valueOf(charArray) + "Title");
        }
    }

    /**
     * 
     * @return 
     */
    public static String getExitDialogTitle() {
        return getResourceBundle().getString("exitDialogTitle") + " " + getApplicationName();
    }

    /**
     * 
     * @return
     */
    public static String getPickColorDialogTitle() {
        return getResourceBundle().getString("pickColorDialogTitle");
    }

    /**
     * 
     * @return
     */
    public static String getPlayersText() {
        return getResourceBundle().getString("players") + ": ";
    }

    /**
     * 
     * @return
     */
    public static String getStartGameText() {
        return getResourceBundle().getString("startViewTitle");
    }

    /**
     * 
     * @return
     */
    public static String getNameText() {
        return getResourceBundle().getString("name") + ": ";
    }

    /**
     * 
     * @return
     */
    public static String getColorText() {
        return getResourceBundle().getString("color") + ": ";
    }

    /**
     * 
     * @return
     */
    public static String getPickColorText() {
        return getResourceBundle().getString("pickColor");
    }

    /**
     * 
     * @return
     */
    public static String getResumeGameText() {
        return getResourceBundle().getString("resumeGame");
    }

    /**
     * 
     * @return
     */
    public static String getExitApplicationText() {
        return getResourceBundle().getString("exitApplication");
    }

    /**
     * 
     * @return
     */
    public static String getBackToStartMenuText() {
        return getResourceBundle().getString("backToStartMenuDialog");
    }

    /**
     * 
     * @return
     */
    public static String getConfirmBackToStartMenuText() {
        return getResourceBundle().getString("backToStartMenuDialogConfirm");
    }

    /**
     * 
     * @return
     */
    public static String getConfirmExitText() {
        return getResourceBundle().getString("exitDialogConfirm");
    }

    /**
     * 
     * @return
     */
    public static String getSwatchesColorPanelName() {
        return getResourceBundle().getString("swatchesColorPanelName");
    }

}
