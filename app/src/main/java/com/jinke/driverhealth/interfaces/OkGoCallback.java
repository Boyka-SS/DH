package com.jinke.driverhealth.interfaces;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author: fanlihao
 * @date: 2022/1/19
 */
public interface OkGoCallback {
    /**
     * 获取onSuccess中的数据
     * @param reponseData 网络请求数据
     */
    void onSuccess(String reponseData) throws IOException, ParseException;
}

