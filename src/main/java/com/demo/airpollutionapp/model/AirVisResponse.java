package com.demo.airpollutionapp.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AirVisResponse {

    private String status;
    private List<Data> data = new ArrayList<>();
    
	public AirVisResponse() {
  	  
    }
    
    public AirVisResponse(String status, List<Data> data) {
        this.status = status;
        this.data = data;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "AirVisResponse [status=" + status + ", data=" + data + ", toString()=" + super.toString() + "]";
	}
       
}
