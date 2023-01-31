package br.com.timoteosoutello.cucumber;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import br.com.timoteosoutello.SpringMain;

@SpringBootTest(classes = SpringMain.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class CucumberSpringBaseConfiguration {

}
