package models;

import java.time.Duration;

public class Movie {

    private String title;
    private Duration duration;
    private boolean isForbiddenUnder18;

    public Movie(String title, Duration duration, boolean isForbiddenUnder18) {
        this.title = title;
        this.duration = duration;
        this.isForbiddenUnder18 = isForbiddenUnder18;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public boolean isForbiddenUnder18() {
        return isForbiddenUnder18;
    }

    public void setForbiddenUnder18(boolean isForbiddenUnder18) {
        this.isForbiddenUnder18 = isForbiddenUnder18;
    }
}
