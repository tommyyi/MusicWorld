package com;

/**
 * Created by Administrator on 2016/5/20.
 */
public interface Restaurant extends Chef,Servant
{
    String getPrice(String foodName);
}
