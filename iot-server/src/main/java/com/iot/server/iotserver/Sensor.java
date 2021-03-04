package com.iot.server.iotserver;
/**
 * @author Ali Alzubaidi
 *
 * 
 * 
 */

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.v3.oas.annotations.media.Schema;


@Entity
@Table(name = "sensor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

public class Sensor implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6609840729642210969L;

    protected Sensor() {}

    



    @Schema(description = "Unique identifier of the sensor.", 
    example = "1", required = true)
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    

    @Schema(description = "Sensor name", 
    example = "Fire detector", required = true)
    @NotBlank
    @Size(max = 100)
    private String name;

    @Schema(description = "Location of the sensor", 
    example = " 11 name Street PostCode", required = true)
    @NotBlank
    @Size(max = 100)
    private String location;

    @Schema(description = "Sensor Reading. This should be comming from the sensor", 
    example = "1", required = true)
    @NotBlank
    private double currentValue;


    @NotBlank
    private String timeStamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    
}
