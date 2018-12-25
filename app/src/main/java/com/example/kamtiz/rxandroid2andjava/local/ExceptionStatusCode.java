package com.example.kamtiz.rxandroid2andjava.local;

import retrofit2.HttpException;

/**
 * @author Rajan Maurya
 *         On 20/08/17.
 */
public class ExceptionStatusCode {

    public static Boolean isHttp401Error(Throwable throwable) {
        return ((HttpException) throwable).code() == 401;
    }
}
