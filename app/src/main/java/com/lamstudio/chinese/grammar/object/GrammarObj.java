package com.lamstudio.chinese.grammar.object;

/**
 * Created by AdministratorPC on 12/10/2015.
 */
public  class GrammarObj {
    private int g_id = 0;
    private String title = "";
    private String detail = "";
    private int level = 0;

    public GrammarObj(int g_id, String title, String detail, int level) {
        this.g_id = g_id;
        this.title = title;
        this.detail = detail;
        this.level = level;
    }

    public int getG_id() {
        return g_id;
    }

    public void setG_id(int g_id) {
        this.g_id = g_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
