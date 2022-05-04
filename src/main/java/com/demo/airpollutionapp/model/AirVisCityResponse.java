package com.demo.airpollutionapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AirVisCityResponse {

    private String status;
    private Data data = new Data();
    
	public AirVisCityResponse() {
  	  
    }
    
    public AirVisCityResponse(String status, Data data) {
        this.status = status;
        this.data = data;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "AirVisCityResponse [status=" + status + ", data=" + data + ", toString()=" + super.toString() + "]";
	}
       
}
