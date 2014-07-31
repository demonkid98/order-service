package vn.com.vndirect.rest.resource;

import org.springframework.beans.factory.annotation.Autowired;

import vn.com.vndirect.app.ApplicationState;

public class ServiceResource {

    @Autowired
    private ApplicationState appState;

    public ApplicationState getAppState() {
        return appState;
    }

    public void setAppState(ApplicationState appState) {
        this.appState = appState;
    }

}
