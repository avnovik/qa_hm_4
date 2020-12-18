package config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigHelper {

    public static String getLogin() {

        return getConfig().searchLogin();
    }

    public static String getPassword() {

        return getConfig().searchPassword();
    }

    private static GithubConfig getConfig() {
        return ConfigFactory.newInstance().create(GithubConfig.class, System.getProperties());
    }

}