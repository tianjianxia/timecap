package com.timecap.apiService.Do;

public class TextDo {
    private Long id;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TextDo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}

