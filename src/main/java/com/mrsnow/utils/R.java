package com.mrsnow.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author MrSnow *** dz
 * @CreateTime: 2022-12-14  14:11
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
    private static final int SUCCESS_CODE = 200;

    private static final int INFO_CODE = 10;
    private static final int LOGIN_CODE = 110;
    private static final int FAIL_CODE = -1;
    private int code;
    private T data;
    private String message;

    public R(int code, String message){
        this.code=code;
        this.message=message;
    }


    public R(T data) {
       this.data = data;
    }

    public static <E> R<E> success(E data,String message){
        return new R<E>(SUCCESS_CODE,data,message);
    }

    public static <E> R<E> success(E data){
        R r = new R();
        r.setData(data);
        r.setCode(SUCCESS_CODE);
        return r;
    }

    public static R success(){
        R r = new R();
        r.setCode(SUCCESS_CODE);
        return r;
    }

    public static  R fail(){
        R r = new R();
        r.setCode(FAIL_CODE);
        return r;
    }
    public static  R fail(int code){
        R r = new R();
        r.setCode(code);
        return r;
    }

    public static  R fail(int code,String message){
        R r = new R();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static  R fail(String message){
        return new R(FAIL_CODE,message);
    }

    public static  R info(String message){
        return new R(INFO_CODE,message);
    }

    public static  R noLogin(String message){
        return new R(LOGIN_CODE,message);
    }
}
