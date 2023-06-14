package it.unibo.caesena.view;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A class that provides support for different languages in the game.
 * Normally, it returns texts from the bundle of the current default Locale.
 */
public final class LocaleHelper {

    private static final String BUNDLE_NAME = "it.unibo.caesena.displayTexts";
    private static Locale currentLocale = Locale.getDefault();

    /**
     * Class constructor marked as private as its not needed and all the methods are
     * static.
     */
    private LocaleHelper() {
    }

    /**
     * Gets the ResourceBundle that is currently being used for texts.
     *
     * @return the current ResourceBundle
     */
    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle(BUNDLE_NAME, currentLocale);
    }

    /**
     * Sets the locale to be used when getting texts.
     *
     * @param locale the locale to use when getting texts
     */
    public static void setLocale(final Locale locale) {
        currentLocale = locale;
    }

    /**
     * Gets the name of the application.
     *
     * @return the name of the Application
     */
    public static String getApplicationName() {
        return getResourceBundle().getString("applicationName");
    }

    /**
     * Gets the corresponding title for the given <code>currentScene</code>.
     *
     * @param currentScene scene to get the title of
     * @param withApplicationName whether or not the application name should be included in the title
     * @return the corresponding title to the given <code>currentScene</code>
     */
    public static String getSceneTitle(final String currentScene, final boolean withApplicationName) {
        final char[] charArray = currentScene.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        if (withApplicationName) {
            return getApplicationName() + " | " + getResourceBundle().getString(String.valueOf(charArray) + "Title");
        } else {
            return getResourceBundle().getString(String.valueOf(charArray) + "Title");
        }
    }

    /**
     * Gets the title of the exit dialog with the application name at the end.
     *
     * @return the title of the exit dialog
     */
    public static String getExitDialogTitle() {
        return getResourceBundle().getString("exitDialogTitle") + " " + getApplicationName();
    }

    /**
     * Gets the title of the dialog used to pick a color.
     *
     * @return the title of the dialog used to pick a color
     */
    public static String getPickColorDialogTitle() {
        return getResourceBundle().getString("pickColorDialogTitle");
    }

    /**
     * Gets the text for the sentence "new game" according to the current Locale.
     *
     * @return the sentence "new game" according to the current Locale
     */
    public static String getNewGameText() {
        return getResourceBundle().getString("newGame");
    }

    /**
     * Gets the text for the sentence "list all open games" according to the current Locale.
     *
     * @return the sentence "list all open games" according to the current Locale
     */
    public static String getGameListText() {
        return getResourceBundle().getString("listGames");
    }

    /**
     * Gets the text for the sentence "show statistics" according to the current Locale.
     *
     * @return the sentence "show statistics" according to the current Locale
     */
    public static String getStatisticsText() {
        return getResourceBundle().getString("showStatistics");
    }

    /**
     * Gets the text for the word "back" according to the current Locale.
     *
     * @return the word "back" according to the current Locale
     */
    public static String getBackText() {
        return getResourceBundle().getString("back");
    }

    /**
     * Gets the text for the word "players" according to the current Locale.
     *
     * @return the word "players" according to the current Locale
     */
    public static String getPlayersText() {
        return getResourceBundle().getString("players") + ": ";
    }

    /**
     * Gets the text for the word "server" according to the current Locale.
     *
     * @return the word "server" according to the current Locale
     */
    public static String getServerText() {
        return getResourceBundle().getString("server") + ": ";
    }

    /**
     * Gets the text for the word "expansions" according to the current Locale.
     *
     * @return the word "expansions" according to the current Locale
     */
    public static String getExpansionsText() {
        return getResourceBundle().getString("expansions") + ": ";
    }

    /**
     * Gets the text for the sentence "start game" according to the current Locale.
     *
     * @return the sentence "start game" according to the current Locale
     */
    public static String getStartGameText() {
        return getResourceBundle().getString("startSceneTitle");
    }

    /**
     * Gets the text for the word "name" according to the current Locale,
     * also adds a semicolon with whitespace at the end.
     *
     * @return the word "name" according to the current Locale
     */
    public static String getNameText() {
        return getResourceBundle().getString("name") + ": ";
    }

    /**
     * Gets the text for the word "score" according to the current Locale,
     * also adds a semicolon with whitespace at the end.
     *
     * @return the word "score" according to the current Locale
     */
    public static String getScoreText() {
        return getResourceBundle().getString("score") + ": ";
    }

    /**
     * Gets the text for the word "color" according to the current Locale,
     * also adds a semicolon with whitespace at the end.
     *
     * @return the word "color" according to the current Locale
     */
    public static String getColorText() {
        return getResourceBundle().getString("color") + ": ";
    }

    /**
     * Gets the text for the sentence "place tile" according to the current Locale.
     *
     * @return the sentence "place tile" according to the current Locale
     */
    public static String getPlaceTileText() {
        return getResourceBundle().getString("placeTile");
    }

    /**
     * Gets the text for the sentence "place meeple" according to the current Locale.
     *
     * @return the sentence "place meeple" according to the current Locale
     */
    public static String getPlaceMeepleText() {
        return getResourceBundle().getString("placeMeeple");
    }

    /**
     * Gets the text for the sentence "show board" according to the current Locale.
     *
     * @return the sentence "show board" according to the current Locale
     */
    public static String getShowBoardText() {
        return getResourceBundle().getString("showBoard");
    }

    /**
     * Gets the text for the sentence "discard" according to the current Locale.
     *
     * @return the sentence "discard" according to the current Locale
     */
    public static String getDiscardText() {
        return getResourceBundle().getString("discard");
    }

    /**
     * Gets the text for the sentence "end turn" according to the current Locale.
     *
     * @return the sentence "end turn" according to the current Locale
     */
    public static String getEndTurnText() {
        return getResourceBundle().getString("endTurn");
    }

    /**
     * Gets the text for the sentence "remaining tiles" according to the current Locale,
     * also adds a semicolon with whitespace at the end.
     *
     * @return the sentence "remaining tiles" according to the current Locale
     */
    public static String getRemainingTilesText() {
        return getResourceBundle().getString("remainingTiles") + ": ";
    }

    /**
     * Gets the text for the sentence "pick the color" according to the current Locale.
     *
     * @return the sentence "pick the color" according to the current Locale
     */
    public static String getPickColorText() {
        return getResourceBundle().getString("pickColor");
    }

    /**
     * Gets the text for the sentence "resume the game" according to the current Locale.
     *
     * @return the sentence "resume the game" according to the current Locale
     */
    public static String getResumeGameText() {
        return getResourceBundle().getString("resumeGame");
    }

    /**
     * Gets the text for the sentence "exit the game" according to the current Locale.
     *
     * @return the sentence "exit the game" according to the current Locale
     */
    public static String getExitApplicationText() {
        return getResourceBundle().getString("exitApplication");
    }

    /**
     * Gets the text for the sentence "back to the start menu" according to the current Locale.
     *
     * @return the sentence "back to the start menu" according to the current Locale
     */
    public static String getBackToStartMenuText() {
        return getResourceBundle().getString("backToStartMenuDialog");
    }

    /**
     * Gets the text used to confirm that the user wants to go back to the start menu.
     *
     * @return the confirm text to go back to the start menu according to the current Locale
     */
    public static String getConfirmBackToStartMenuText() {
        return getResourceBundle().getString("backToStartMenuDialogConfirm");
    }

    /**
     * Gets the text used to confirm that the user wants to exit the game.
     *
     * @return the confirm text to exit the game according to the current Locale
     */
    public static String getConfirmExitText() {
        return getResourceBundle().getString("exitDialogConfirm");
    }

    /**
     * Gets the text for the word "swatches" according to the current Locale.
     * Used to keep the swatches color panel in the dialog for the {@link javax.swing.JColorChooser}
     *
     * @return the word "swatches" according to the current Locale
     */
    public static String getSwatchesColorPanelName() {
        return getResourceBundle().getString("swatchesColorPanelName");
    }

    /**
     * Gets the text for the word "leaderboard" according to the current Locale.
     *
     * @return the word "leaderboard" according to the current Locale
     */
    public static String getLeaderboardName() {
        return getResourceBundle().getString("leaderboardName");
    }

	public static String getStatisticsTitleColor() {
		return getResourceBundle().getString("statisticsTitleColor");
	}

	public static String getStatisticsHeader1Color() {
		return getResourceBundle().getString("statisticsHeader1Color");
	}

	public static String getStatisticsHeader2Color() {
        return getResourceBundle().getString("statisticsHeader2Color");
	}

    public static String getStatisticsTitleRegion() {
        return getResourceBundle().getString("statisticsTitleRegion");
    }

	public static String getStatisticsHeader1Region() {
		return getResourceBundle().getString("statisticsHeader1Region");
	}

	public static String getStatisticsHeader2Region() {
		return getResourceBundle().getString("statisticsHeader2Region");
	}

	public static String getStatisticsTitleEnemy() {
		return getResourceBundle().getString("statisticsTitleEnemy");
	}

	public static String getStatisticsHeader1Enemy() {
		return getResourceBundle().getString("statisticsHeader1Enemy");
	}

	public static String getStatisticsHeader2Enemy() {
		return getResourceBundle().getString("statisticsHeader2Enemy");
	}

	public static String getStatisticsTitleExpansion() {
		return getResourceBundle().getString("statisticsTitleExpansion");
	}

	public static String getStatisticsHeader1Expansion() {
		return getResourceBundle().getString("statisticsHeader1Expansion");
	}

	public static String getStatisticsHeader2Expansion() {
		return getResourceBundle().getString("statisticsHeader2Expansion");
	}

    public static String getGameListGameID() {
		return getResourceBundle().getString("gameListGameID");
	}

    public static String getGameListPlayers() {
		return getResourceBundle().getString("gameListPlayers");
	}

    public static String getGameListTiles() {
		return getResourceBundle().getString("gameListTiles");
	}

    public static String getGameListServer() {
		return getResourceBundle().getString("gameListServer");
	}

    public static String getGameListJoin() {
		return getResourceBundle().getString("gameListJoin");
	}

}
