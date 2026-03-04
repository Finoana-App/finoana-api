package com.anonymous.finoanaapi.controllers.utils;

import com.anonymous.finoanaapi.controllers.models.PageParam;
import com.anonymous.finoanaapi.controllers.models.PageSizeParam;
import java.util.function.BiFunction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageParamsToPageable implements BiFunction<PageParam, PageSizeParam, Pageable> {
  @Override
  public Pageable apply(PageParam pageParam, PageSizeParam pageSizeParam) {
    return PageRequest.of(pageParam.getValue() - 1, pageSizeParam.getValue());
  }
}
