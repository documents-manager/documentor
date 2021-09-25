package org.documentmanager.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ValidatorFalseMatcher extends TypeSafeMatcher<Object> {
  public static Matcher<Object> validateFalse() {
    return new ValidatorFalseMatcher();
  }

  @Override
  protected boolean matchesSafely(final Object item) {
    return !new ValidatorTrueMatcher().matchesSafely(item);
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText("Checks if validation fails");
  }
}
