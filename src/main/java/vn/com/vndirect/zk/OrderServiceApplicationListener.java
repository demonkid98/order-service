package vn.com.vndirect.zk;

import org.apache.log4j.Logger;
import org.zk.ApplicationListener;

import vn.com.vndirect.app.ApplicationState;

public class OrderServiceApplicationListener implements ApplicationListener {

    private static final Logger logger = Logger.getLogger(OrderServiceApplicationListener.class);

    private ApplicationState appState;

    public void setAppState(ApplicationState appState) {
        this.appState = appState;
    }

    @Override
    public void onChange(org.zk.ApplicationState state) {
        appState.setActive(state == org.zk.ApplicationState.MASTER);
        logger.info("State: " + appState.isActive());
    }

}
