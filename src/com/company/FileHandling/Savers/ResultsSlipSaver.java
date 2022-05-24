package com.company.FileHandling.Savers;

import com.company.FileHandling.FileHandler;

import java.util.regex.Pattern;

public class ResultsSlipSaver {
    private final FileHandler fileHandler = new FileHandler();

    public void saveResultsSlip(String customFilename, String resultsSlip) {
        Pattern fileExtensionPattern = Pattern.compile(".txt", Pattern.CASE_INSENSITIVE);
        if (!fileExtensionPattern.matcher(customFilename).find()) {
            customFilename = customFilename + ".txt";
        }
        fileHandler.writeFile(customFilename, resultsSlip);
    }
}
