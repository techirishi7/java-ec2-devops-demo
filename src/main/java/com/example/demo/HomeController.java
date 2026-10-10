package com.example.demo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HomeController {
  @GetMapping("/")
  public String home() {
    return "Hello from the Java Maven DevOps demo! Version: v1";
  }
  @GetMapping("/health")
  public String health() { return "UP"; }
}