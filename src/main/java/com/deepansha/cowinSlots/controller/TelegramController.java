//package com.deepansha.cowinSlots.controller;
//
//import com.deepansha.cowinSlots.service.TelegramMessagingService;
//import com.deepansha.cowinSlots.utility.JsonResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class TelegramController {
//
//
//    @Autowired
//    public TelegramMessagingService service;
//
//
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//
//    @RequestMapping(value = "/app/telegram/message/broadcast", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> sendMessageToAllActiveUsers(@RequestBody JsonResponse jsonResponse) {
//        log.info("Rest to sendMessageToAllActiveUsers");
//
//        try {
//            return new ResponseEntity(service.broadcastMessage(jsonResponse.getMessage()),
//                    org.springframework.http.HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("Exception occurred : {} ", e.getMessage(), e);
//            return new ResponseEntity<JsonResponse>(new JsonResponse("Exception adding alert"),
//                    HttpStatus.EXPECTATION_FAILED);
//        }
//    }
//
//    @RequestMapping(value = "/app/telegram/message/broadcast/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> sendMessageToSingleUser(@PathVariable int id, @RequestBody JsonResponse jsonResponse) {
//        log.info("Rest to sendMessageToSingleUser");
//
//        try {
//            return new ResponseEntity(service.sendMessageToId(id, jsonResponse.getMessage()),
//                    org.springframework.http.HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("Exception occurred : {} ", e.getMessage(), e);
//            return new ResponseEntity<JsonResponse>(new JsonResponse("Exception adding alert"),
//                    HttpStatus.EXPECTATION_FAILED);
//        }
//    }
//}
