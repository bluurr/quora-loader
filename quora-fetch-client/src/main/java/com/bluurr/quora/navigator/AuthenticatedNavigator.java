package com.bluurr.quora.navigator;

import com.bluurr.quora.model.user.UserSession;
import com.bluurr.quora.extension.EnhancedDriver;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

@RequiredArgsConstructor
public class AuthenticatedNavigator {

  @Getter
  private final UserSession session;
  private final EnhancedDriver driver;

  public SearchPageNavigator searchForTerm(final @NonNull String term) {

    var target = driver.getBaseUrl() + "/search?q=" + encode(term, UTF_8) + "&type=answer";

    driver.webDriver().navigate().to(target);

    return new SearchPageNavigator(driver);
  }

  public QuestionPageNavigator getQuestionAt(final String uri) {

    driver.webDriver().navigate().to(uri);

    return new QuestionPageNavigator(driver);
  }

}
