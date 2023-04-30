package com.example.demo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class EntityState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stateMachineId;
    private String currentState;

    public EntityState(String stateMachineId, String currentState) {
        this.stateMachineId = stateMachineId;
        this.currentState = currentState;
    }

    public void setCurrentState(String state) {
        this.currentState = state;
    }
}
