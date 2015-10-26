package com.epam.idea.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epam.idea.core.service.StateService;
import com.epam.idea.core.util.State;

@Controller
@RequestMapping("/api/v1/states")
public class StateController {

    @Autowired
    private StateService stateService;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<List<String>> getAllStates() {
        final List<String> states = stateService.findAll();
        return new ResponseEntity<>(states, HttpStatus.OK);
    }
}
