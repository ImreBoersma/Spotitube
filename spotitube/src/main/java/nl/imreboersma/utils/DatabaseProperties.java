package nl.imreboersma.utils;

import java.io.IOException;
import java.util.Properties;

public class DatabaseProperties {
  private final Properties properties;

  public DatabaseProperties() {
    properties = new Properties();
    try {
      properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public String getConnectionString() {
    return properties.getProperty("connectionstring");
  }

  public String getDriver() {
    return properties.getProperty("driver");
  }
}
