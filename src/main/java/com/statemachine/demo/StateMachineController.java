package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stateMachine")
public class StateMachineController {

    private final StateMachine<States, Events> stateMachine;
    private final EntityStateRepository entityStateRepository;

    @Autowired
    public StateMachineController(StateMachine<States, Events> stateMachine, EntityStateRepository entityStateRepository) {
        this.stateMachine = stateMachine;
        this.entityStateRepository = entityStateRepository;
    }

    @PostMapping("/sendEvent/{event}")
    public ResponseEntity<Void> sendEvent(@PathVariable Events event) {
        stateMachine.start();
        stateMachine.sendEvent(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/currentState")
    public ResponseEntity<String> getCurrentState() {
        State<States, Events> currentState = stateMachine.getState();
        return new ResponseEntity<>(currentState.getId().name(), HttpStatus.OK);
    }

    @GetMapping("/entityState")
    public ResponseEntity<EntityState> getEntityState() {
        EntityState entityState = entityStateRepository.findByStateMachineId(stateMachine.getId());
        if (entityState != null) {
            return new ResponseEntity<>(entityState, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

