package com.example.demo;

import com.example.demo.Events;
import com.example.demo.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Autowired
    private EntityStateChangeListener entityStateChangeListener;

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
                .initial(States.STATE1)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(States.STATE1)
                    .target(States.STATE2)
                    .event(Events.EVENT1)
                    .and()
                .withExternal()
                    .source(States.STATE2)
                    .target(States.STATE1)
                    .event(Events.EVENT2);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration()
                .listener(entityStateChangeListener);
    }
}
