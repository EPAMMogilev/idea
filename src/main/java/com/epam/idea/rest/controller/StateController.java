package com.epam.idea.rest.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epam.idea.core.util.State;

@Controller
@RequestMapping("/api/v1/states")
public class StateController {

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<List<State>> getAllStates() {
        final List<State> states = Arrays.asList(State.values());
        return new ResponseEntity<>(states, HttpStatus.OK);
    }
}
