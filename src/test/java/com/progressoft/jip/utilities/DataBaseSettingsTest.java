package com.progressoft.jip.utilities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataBaseSettingsTest {

    private DataBaseSettings dataBaseSettings;

    @Before
    public void setup() {
	dataBaseSettings = DataBaseSettings.getInstance();
    }

    @Test
    public void givenDataBaseSettings_CallingUsername_ShouldReturnRoot() {
	Assert.assertEquals("root", dataBaseSettings.username());
    }

    @Test
    public void givenDataBaseSettings_CallingPassword_ShouldReturnRoot() {
	Assert.assertEquals("root", dataBaseSettings.password());
    }

    @Test
    public void givenDataBaseSettings_CallingUrl_ShouldReturnRoot() {
	Assert.assertEquals("jdbc:mysql://localhost:3306/PAYMENT_SYSTEM", dataBaseSettings.url());
    }

    @Test
    public void givenDataBaseSettings_CallingDriverClassName_ShouldReturnRoot() {
	Assert.assertEquals("com.mysql.jdbc.Driver", dataBaseSettings.driverClassName());
    }

}
