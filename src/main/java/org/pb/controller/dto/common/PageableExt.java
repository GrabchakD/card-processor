package org.pb.controller.dto.common;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Data
public class PageableExt<T> {
    private Integer pagesCount;
    private String totalElements;
    private List<T> objects;

    private PageableExt(Integer pagesCount, String totalElements, List<T> objects) {
        this.pagesCount = pagesCount;
        this.totalElements = totalElements;
        this.objects = objects;
    }

    public static <T> PageableExt<T> of(Page<T> p) {
        return new PageableExt<>(p.getTotalPages(), p.getTotalElements() + "", p.getContent());
    }

    public static <T> ResponseEntity<PageableExt<T>> ofResponse(Page<T> p) {
        return p.isEmpty()
                ? ResponseEntity.status(OK).body(new PageableExt<>(0, "0", Collections.emptyList()))
                : ResponseEntity.ok(new PageableExt<>(p.getTotalPages(), p.getTotalElements() + "", p.getContent()));
    }
}
