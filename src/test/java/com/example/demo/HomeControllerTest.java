package com.example.demo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
class HomeControllerTest {
  @Test void homeMessageContainsVersion() {
    HomeController controller = new HomeController();
    assertEquals("Hello from the Java Maven DevOps demo! Version: v1", controller.home());
  }
}