package com.mrsnow.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author MrSnow *** dz
 * @CreateTime: 2022-12-14  14:09
 **/
@Data
public class JO<T> implements Serializable {
    private static final long serialVersionUID=1312321313312L;

    private T data;
    private String extra;
}
