package com.forsyslab.talquest10.model;

/**
 * Created by LENOVO on 03/02/2017.
 */

public class MenuItem{
    int menuImageID;
    String menuTitle;
    String menuDescription;

    public MenuItem(int menuImageID, String menuTitle, String menuDescription) {
        this.menuImageID = menuImageID;
        this.menuTitle = menuTitle;
        this.menuDescription = menuDescription;
    }

    public int getMenuImageID() {
        return menuImageID;
    }

    public void setMenuImageID(int menuImageID) {
        this.menuImageID = menuImageID;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }
}
