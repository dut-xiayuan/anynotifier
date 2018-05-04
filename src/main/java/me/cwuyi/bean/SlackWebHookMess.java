package me.cwuyi.bean;

public class SlackWebHookMess {

    public SlackWebHookMess(){}

    public SlackWebHookMess(String text) {
        this.text = text;
    }

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
