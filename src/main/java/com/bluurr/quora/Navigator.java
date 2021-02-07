package com.bluurr.quora;

import com.bluurr.quora.domain.user.LoginCredential;
import com.bluurr.quora.domain.user.UserSession;
import com.bluurr.quora.page.login.LoginPage;
import com.bluurr.quora.page.question.QuestionPage;
import com.bluurr.quora.page.search.SearchPage;
import com.github.webdriverextensions.Bot;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

@RequiredArgsConstructor
public class Navigator {

  private final String baseUri;
  private final LoginCredential credentials;

  public UserSession login() {

    Bot.driver().navigate().to(baseUri);

    return new LoginPage().login(credentials);
  }

  public SearchPage searchForTerm(final String term) {

      WebDriver driver = Bot.driver();

      var target = driver.getCurrentUrl() + "/search?q=" + encode(term, UTF_8) + "&type=answer";
      driver.navigate().to(target);

    return new SearchPage();
  }

  public QuestionPage openQuestion(final String location) {

    Bot.driver().navigate().to(location);
    return new QuestionPage();
  }

}
