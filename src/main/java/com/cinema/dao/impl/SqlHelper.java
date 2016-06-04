package com.cinema.dao.impl;

import java.util.Map;

/**
 * Created by dshvedchenko on 16.04.16.
 */
public class SqlHelper {
    public static String getConditionPartFromMapParams(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder("");
        boolean first = true;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (!first) {
                sb.append(" AND ");
            } else first = false;
            sb.append(entry.getKey()).append("= ?");
        }
        return sb.toString();
    }
}
