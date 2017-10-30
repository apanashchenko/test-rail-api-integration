package com.test.rail.api.conf;

import org.aeonbits.owner.ConfigFactory;

/**
 * Created by alpa on 10/25/17
 */
public class ConfigLoader {

    private static Configuration conf;

    public static Configuration load() {
        if (conf == null) {
            conf = ConfigFactory.create(Configuration.class, System.getProperties());
        }
        return conf;
    }
}
