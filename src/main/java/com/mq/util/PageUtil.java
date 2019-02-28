package com.mq.util;

import com.mq.query.DefaultQuery;
import com.mq.vo.Page;

import java.util.List;

public class PageUtil {
    public static <E> Page<E> generatePage(List<E> data, Long total, DefaultQuery query) {
        Page<E> page = new Page<>();
        page.setDraw(query.getStart());
        page.setRecordsFiltered(Long.valueOf(data.size()));
        page.setRecordsTotal(total);
        page.setData(data);
        return page;
    }
}
