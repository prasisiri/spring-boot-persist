package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Service;

@Service
public class EntityStateChangeListener extends StateMachineListenerAdapter<States, Events> {

    private final EntityStateRepository entityStateRepository;

    private StateMachine<States, Events> stateMachine;

    @Autowired
    public EntityStateChangeListener(EntityStateRepository entityStateRepository) {
        this.entityStateRepository = entityStateRepository;
    }

    @Override
    public void stateChanged(State<States, Events> from, State<States, Events> to) {
        if (stateMachine == null) {
            return;
        }
        EntityState entityState = entityStateRepository.findByStateMachineId(stateMachine.getId());
        if (entityState == null) {
            entityState = new EntityState(stateMachine.getId(), to.getId().name());
        } else {
            entityState.setCurrentState(to.getId().name());
        }
        entityStateRepository.save(entityState);
    }

    @Override
    public void stateMachineStarted(StateMachine<States, Events> stateMachine) {
        this.stateMachine = stateMachine;
    }
}


