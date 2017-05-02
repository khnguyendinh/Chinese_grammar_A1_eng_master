package com.lamstudio.chinese.grammar.object;

/**
 * Created by AdministratorPC on 12/6/2015.
 */
public class MenuObject {
    String title = "";
    String sub_title = "";
    int resource = -1;
    String viewType = "";

    public MenuObject(String viewType, String tile) {
        this.viewType = viewType;
        this.title = tile;
    }

    public MenuObject(String viewType, String title, String sub_title, int resource) {
        this.viewType = viewType;
        this.title = title;
        this.resource = resource;
        this.sub_title = sub_title;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

}
