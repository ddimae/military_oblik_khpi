package ntukhpi.semit.militaryoblik.javafxutils;

import lombok.Getter;

@Getter
public class SettingsStage {
    private String fxmlName;
    private String title;
    private int width;
    private int height;
    private boolean fullScreen;
    boolean resizable;

    public SettingsStage(String fxmlName, String title,
                         int width, int height,
                         boolean fullScreen, boolean resizable) {
        this.fxmlName = fxmlName;
        this.title = title;
        this.width = width;
        this.height = height;
        this.fullScreen = fullScreen;
        this.resizable = resizable;
    }
}
