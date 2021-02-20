package com.bluurr.quora.page.component;

import java.util.List;

public interface InfiniteScrollPage<T> {

  int currentElementCount();
  List<T> resultsWithSkip(final int skip);
  void scrollNext();
}
