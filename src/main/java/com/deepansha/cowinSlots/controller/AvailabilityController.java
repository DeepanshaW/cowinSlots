package com.deepansha.cowinSlots.controller;

import com.deepansha.cowinSlots.service.CheckAvailibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.deepansha.cowinSlots.utility.JsonResponse;

@Controller
public class AvailabilityController {
    @Autowired
    public CheckAvailibilityService service;

    @RequestMapping(value="/app/availibility/district/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAvlByDistrictId(@PathVariable int id){
        try{
            return new ResponseEntity(service.checkAvlByDistrict(id), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<JsonResponse>(new JsonResponse("Exception while fetching data"),HttpStatus.OK);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/app/availability/pincode/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAvlByPinCode(@PathVariable int id) {

        try {
            return new ResponseEntity(service.checkByPinCode(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<JsonResponse>(new JsonResponse("Exception fethcing data"),
                    HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/app/availability/Alert/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchAlertData(@PathVariable int id) {

        try {
            service.refreshAvl(id,true);
            return new ResponseEntity(new JsonResponse("Success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<JsonResponse>(new JsonResponse("Exception updating alert"),
                    HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/app/forceCrone/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> forceRunCroneJob() {
        service.forceRunCron();
        return new ResponseEntity(new JsonResponse("Force Run Success"), HttpStatus.OK);
    }
}
