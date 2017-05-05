package com.squareup.asynctask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/3/12.
 */

public class UrlUtils {
    private StringBuilder url;

    public UrlUtils url(String url) {
        this.url = new StringBuilder(url);
        return this;
    }
    public UrlUtils addParam(String key,String value) {
        if(url.indexOf("?")==-1){
            url.append("?");
        }
        else{
            url.append("&");
        }
        try {
            url.append(key).append("=").append(URLEncoder.encode(value, "utf-8"));


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return this;
    }
    public String build() {
        return url.toString();
    }
}
