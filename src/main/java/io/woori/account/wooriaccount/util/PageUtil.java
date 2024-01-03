package io.woori.account.wooriaccount.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PageUtil {

    public static <T> Page<T> mergePages(Page<T> page1, Page<T> page2) {
        List<T> content = Stream.concat(page1.get().toList().stream(),
                        page2.get().toList().stream())
                .collect(Collectors.toList());

        return new PageImpl<>(content, page1.getPageable(), page1.getTotalElements() + page2.getTotalElements());
    }

}