package com.jinke.driverhealth.services.publicparams;

import android.util.Log;

import org.junit.Test;

/**
 * @author: fanlihao
 * @date: 2022/1/16
 */
public class TestTokenParams {
    @Test
    public void testGetToken() {
        Log.d("TestTokenParams", new TokenParams().getToken().toString());
    }
}
