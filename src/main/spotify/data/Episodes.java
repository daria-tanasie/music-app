package main.spotify.data;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public final class Episodes {
    private String name;
    private Integer duration;
    private String description;
    private int stopTime;
    private int remainingTime = 0;

    public Episodes() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(final Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void updateRemainingTime(int timePassed) {

    }

}