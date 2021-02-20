package com.bluurr.quora.page.component;

import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class InfiniteScrollIterator<R> implements Iterator<List<R>> {

  private final InfiniteScrollPage<R> page;
  private int offset = 0;

  @Override
  public boolean hasNext() {
    return page.currentElementCount() > offset;
  }

  @Override
  public List<R> next() {
    var results = page.resultsWithSkip(offset);

    // Request to trigger the scroll
    page.scrollNext();

    // Move the offset forward
    offset = results.size();

    return results;
  };

}
