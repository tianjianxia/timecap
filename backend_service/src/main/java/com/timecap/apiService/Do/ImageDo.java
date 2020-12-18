package com.timecap.apiService.Do;

public class ImageDo {
    private Long id;
    private String filename;



    public String getFile() {
        return filename;
    }

    public void setFile(String file) {
        this.filename = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ImageDo{" +
                "filename='" + filename + '\'' +
                '}';
    }
}
