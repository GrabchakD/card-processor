package org.pb.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageCreator<T> {

    private List<T> list;
    private Pageable pageable;

    public PageCreator(List<T> list, Pageable pageable) {
        this.list = list;
        this.pageable = pageable;
    }

    public Page<T> pageFromList() {
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}
