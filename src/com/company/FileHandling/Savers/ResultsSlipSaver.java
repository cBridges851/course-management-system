package com.company.FileHandling.Savers;

import com.company.FileHandling.FileHandler;

import java.util.regex.Pattern;

/**
 * A class that handles the saving of results slips.
 */
public class ResultsSlipSaver {
    private final FileHandler fileHandler = new FileHandler();

    /**
     * Saves a results slip
     * @param customFilename the name of the file to be saved
     * @param resultsSlip the contents of a results slip, including the name, level, year and results for
     *                    the course modules of a student
     */
    public void saveResultsSlip(String customFilename, String resultsSlip) {
        Pattern fileExtensionPattern = Pattern.compile(".txt", Pattern.CASE_INSENSITIVE);
        if (!fileExtensionPattern.matcher(customFilename).find()) {
            customFilename = customFilename + ".txt";
        }
        fileHandler.writeFile(customFilename, resultsSlip);
    }
}
