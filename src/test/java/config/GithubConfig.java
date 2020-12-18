package config;

import org.aeonbits.owner.Config;

@org.aeonbits.owner.Config.LoadPolicy(org.aeonbits.owner.Config.LoadType.MERGE)
@org.aeonbits.owner.Config.Sources({
        "system:properties",
        "classpath:config.properties"
})
public interface GithubConfig extends Config {
    @Key("login")
    String searchLogin();

    @Key("password")
    String searchPassword();

}