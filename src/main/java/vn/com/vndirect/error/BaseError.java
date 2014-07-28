package vn.com.vndirect.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BaseError {

    private Map<String, ArrayList<String>> errors;

    public BaseError() {
        errors = new HashMap<String, ArrayList<String>>();
    }

    public boolean isEmpty() {
        for (Map.Entry<String, ArrayList<String>> entry : errors.entrySet()) {
            if (entry.getValue().size() > 0) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> get(String attr) {
        if (!errors.containsKey(attr)) {
            return new ArrayList<String>();
        }
        return errors.get(attr);
    }

    public void add(String attr, String message) {
        ArrayList<String> messages;
        if (errors.containsKey(attr)) {
            messages = errors.get(attr);
        } else {
            messages = new ArrayList<String>();
        }

        messages.add(message);
        errors.put(attr, messages);
    }

}
