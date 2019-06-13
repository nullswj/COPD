package com.swj.copd.util;

public class StringUtil {
    public static StringBuilder repeat(StringBuilder builder,int n)
    {
        for(int i = 0; i < n; i++)
        {
            builder.append(builder);
        }
        return builder;
    }
}
