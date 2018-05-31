package io.woo.htmltopdf;

public class HtmlToPdfProgress {
    private final int phase;
    private final String phaseDescription;
    private final int totalPhases;
    private final int phaseProgress;

    public HtmlToPdfProgress(int phase, String phaseDescription, int totalPhases, int phaseProgress) {
        this.phase = phase;
        this.phaseDescription = phaseDescription;
        this.totalPhases = totalPhases;
        this.phaseProgress = phaseProgress;
    }

    /**
     * Returns the current phase index of the conversion process.
     */
    public int getPhase() {
        return phase;
    }

    /**
     * Returns the description of the current phase of the conversion process.
     */
    public String getPhaseDescription() {
        return phaseDescription;
    }

    /**
     * Returns the total amount of phases of the conversion process.
     */
    public int getTotalPhases() {
        return totalPhases;
    }

    /**
     * Returns the progress of the current phase, in percentage (0-100).
     */
    public int getPhaseProgress() {
        return phaseProgress;
    }
}
