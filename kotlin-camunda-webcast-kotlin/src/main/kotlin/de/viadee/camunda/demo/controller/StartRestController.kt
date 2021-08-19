package de.viadee.camunda.demo.controller

import org.camunda.bpm.engine.RuntimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/start")
class StartRestController @Autowired constructor(private val runtimeService: RuntimeService) {
    @PostMapping(path = ["/{message}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun startByMesssage(
        @PathVariable("message") message: String?,
        @RequestBody variables: Map<String, Any>
    ): ResponseEntity<String> {
        val vars: MutableMap<String, Any> = HashMap(variables.size + 1)
        vars["int_startBy"] = "StartRestController"

        // External variables all get the prefix 'ext_'
        for ((key, value) in variables) {
            vars["ext_$key"] = value
        }
        val pi = runtimeService.startProcessInstanceByMessage(message, vars)
        return ResponseEntity.ok(pi.processInstanceId)
    }
}