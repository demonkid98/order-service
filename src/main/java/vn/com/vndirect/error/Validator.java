package vn.com.vndirect.error;

import java.util.ArrayList;

public abstract class Validator {

    protected BaseError errors;

    public abstract boolean validate();

    public ArrayList<String> getError(String attr) {
        return errors.get(attr);
    }

    public String getFirstError() {
        return errors.getFirst();
    }

}
