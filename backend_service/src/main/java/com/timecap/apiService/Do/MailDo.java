package com.timecap.apiService.Do;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class MailDo {
    private Long id;
    private Long textId;
    private Long imageId;
    private String fromUser;
    private String toUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date sendDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date openDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getText() {
        return textId;
    }

    public void setText(Long id) {
        this.textId = id;
    }

    public Long getImage() {
        return imageId;
    }

    public void setImage(Long id) {
        this.imageId = id;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    @Override
    public String toString() {
        return "MailDo{" +
                "id=" + id +
                ", textId='" + textId + '\'' +
                ", imageId='" + imageId + '\'' +
                ", fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                ", sendDate=" + sendDate +
                ", openDate=" + openDate +
                '}';
    }
}
