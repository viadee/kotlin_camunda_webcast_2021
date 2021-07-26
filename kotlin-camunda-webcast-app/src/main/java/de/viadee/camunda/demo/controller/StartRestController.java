package de.viadee.camunda.demo.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/start")
public class StartRestController {
    private final RuntimeService runtimeService;

    @Autowired
    public StartRestController(final RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @PostMapping(path = "/{message}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> startByMesssage(@PathVariable("message") final String message, @RequestBody final Map<String, Object> variables) {
        final Map<String, Object> vars = new HashMap<>(variables.size() + 1);
        vars.put("startBy", "StartRestController");
        vars.putAll(variables);

        final ProcessInstance pi = runtimeService.startProcessInstanceByMessage(message, vars);

        return ResponseEntity.ok(pi.getProcessInstanceId());
    }
}
