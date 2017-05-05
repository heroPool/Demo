package com.example.okimageloader.bean;

/**
 * Created by admin on 2015/11/10.
 */
public class AppBean {
    private String name;
    private String version;
    private String thumb;
    private int fileSize;
    private String intro;
    private String apk;

    public String getApk() {
        return apk;
    }

    public void setApk(String apk) {
        this.apk = apk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public AppBean() {
    }

    @Override
    public String toString() {
        return "AppBean{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", thumb='" + thumb + '\'' +
                ", fileSize=" + fileSize +
                ", intro='" + intro + '\'' +
                ", apk='" + apk + '\'' +
                '}';
    }
}
