package com.iot.server.iotserver;
import java.sql.Timestamp;
/**
 * @author Ali Alzubaidi
 *
 * 
 * 
 */
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api")
@Tag(name = "sensor", description = "the sensor API")
public class SensorController {

    @Autowired
    SensorRepo sensorRepo;

    @Autowired
    AlertRepo alertRepo;

    @Operation(summary = "Add a new Sensor", description = "", tags = { "Sensor" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor created", content = @Content(schema = @Schema(implementation = Sensor.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Sensor already exists") })
    @PostMapping(value = "/Sensors")
    public ResponseEntity <Sensor> createnewSensor(@Valid @RequestParam String name, @RequestParam String location) {
        try {
            Sensor sensor = new Sensor();
            //sensor.setTimeStamp(new Timestamp(System.currentTimeMillis()).toString());
            sensor.setName(name);
            sensor.setLocation(location);
            Sensor newSensor = sensorRepo.save(sensor);
            return new ResponseEntity<>(newSensor, HttpStatus.CREATED);
          } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
          }
    }

    @Operation(summary = "find sensor by ID", description = "", tags = { "Sensor" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sensor.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Sensor does exist") })
    @GetMapping(value = "/Sensors/{id}")
    public ResponseEntity <Sensor> findById(@RequestParam long id) {
        Optional<Sensor> sensor = sensorRepo.findById(id);
        if (sensor.isPresent()) {
            return new ResponseEntity<>(sensor.get(), HttpStatus.OK);
          } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
    }



    @Operation(summary = "register Alert", description = " register new alert for a sensor", tags = { "Alert" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "alert has been registered", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sensor.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "alert already exists") })
    @PostMapping(value = "/Sensors/registerAlert")
    public ResponseEntity <Alert> registerAlert(@RequestParam long id) {
      Optional<Sensor> sensor = sensorRepo.findById(id);
        if (sensor.isPresent()) {
          Alert alert = sensor.get().registerAlert();
          alertRepo.save(alert);
            return new ResponseEntity<>(alert, HttpStatus.OK);
          } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
    }


    @Operation(summary = "Update Alert", description = "update alert status", tags = { "Alert" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "alert has been updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sensor.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "alert already exists") })
    @PutMapping(value = "/Sensors/updateAlert")
    public ResponseEntity <Alert> updateAlert(@RequestParam long sensorID, @RequestParam long alertID, @RequestParam String status) {
      Optional<Sensor> sensor = sensorRepo.findById(sensorID);
        if (sensor.isPresent()) {
          Optional<Alert> alert = alertRepo.findById(alertID);
          alert.get().alertStatus.put(status, new Timestamp(System.currentTimeMillis()).toString());
          alertRepo.save(alert.get());
            return new ResponseEntity<>(alert.get(), HttpStatus.OK);
          } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
    }

}
