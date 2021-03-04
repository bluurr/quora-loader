package com.bluurr.quora.page.component;

import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class InfiniteScrollIterator<R> implements Iterator<List<R>> {

  private final InfiniteScrollPage<R> page;

  private int offset = 0;
  private int previousOffset = -1;

  @Override
  public boolean hasNext() {
    return previousOffset != offset;
  }

  @Override
  public List<R> next() {

    if (previousOffset != -1) {
      // Request to trigger the scroll (First page is expected to always be loaded at zero offset)
      page.scrollNext();
    }

    var results = page.resultsWithSkip(offset);

    // Move the offset forward
    previousOffset = offset;
    offset = results.size();

    return results;
  }
}
