package com.mrsnow.utils;

@FunctionalInterface
public interface Setter {

    <T> void setter(T data);
}
