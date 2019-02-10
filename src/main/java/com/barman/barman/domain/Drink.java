package com.barman.barman.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "DRINKS")
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Column(name = "NAME")
	private String name;
    
    @NotNull
    @Column(name = "PUMP_ONE")
	private long pumpOne;
    
    @NotNull
    @Column(name = "PUMP_TWO")
	private long pumpTwo;
    
    @NotNull
    @Column(name = "PUMP_THREE")
	private long pumpThree;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPumpOne() {
		return pumpOne;
	}
	public void setPumpOne(long pumpOne) {
		this.pumpOne = pumpOne;
	}
	public long getPumpTwo() {
		return pumpTwo;
	}
	public void setPumpTwo(long pumpTwo) {
		this.pumpTwo = pumpTwo;
	}
	public long getPumpThree() {
		return pumpThree;
	}
	public void setPumpThree(long pumpThree) {
		this.pumpThree = pumpThree;
	}
	
	
}
