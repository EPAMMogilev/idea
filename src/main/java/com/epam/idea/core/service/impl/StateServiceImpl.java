package com.epam.idea.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.idea.core.service.StateService;
import com.epam.idea.core.util.State;
import com.epam.idea.logger.Log;

@Service
@Transactional
public class StateServiceImpl implements StateService {

    private final Log log = new Log(StateServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public List<String> findAll() {
        final State[] stateFromEnum = State.values();
        final List<String> states = new ArrayList<String>();

        for (final State state : stateFromEnum) {
            states.add(state.toString());
        }

        return states;
    }

}