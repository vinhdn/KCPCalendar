package com.ominext.kcpcalendar.models;

/**
 * Created by Vin on 10/23/2016.
 */
public class DateConfig {
    private boolean hasNote;
    private boolean hasNotify;

    public DateConfig(boolean hasNote, boolean hasNotify) {
        this.hasNote = hasNote;
        this.hasNotify = hasNotify;
    }

    public boolean isHasNotify() {
        return hasNotify;
    }

    public void setHasNotify(boolean hasNotify) {
        this.hasNotify = hasNotify;
    }

    public boolean isHasNote() {
        return hasNote;
    }

    public void setHasNote(boolean hasNote) {
        this.hasNote = hasNote;
    }
}
