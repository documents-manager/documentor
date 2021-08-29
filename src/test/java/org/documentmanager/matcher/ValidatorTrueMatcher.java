package org.documentmanager.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import javax.validation.Validation;
import javax.validation.Validator;

public class ValidatorTrueMatcher extends TypeSafeMatcher<Object> {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static Matcher<Object> validateTrue() {
        return new ValidatorTrueMatcher();
    }

    @Override
    protected boolean matchesSafely(Object o) {
        return validator.validate(o).isEmpty();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Only valid beans");
    }
}
