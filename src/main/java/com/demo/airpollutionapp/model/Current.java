package com.demo.airpollutionapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Current {

    private Pollution pollution;
    
	public Current() {
  	  
    }
	
	public Current(Pollution pollution) {
		this.pollution = pollution;
    }

	public Pollution getPollution() {
		return pollution;
	}

	public void setPollution(Pollution pollution) {
		this.pollution = pollution;
	}

	@Override
	public String toString() {
		return "Current [pollution=" + pollution + ", toString()=" + super.toString() + "]";
	}
    
}
