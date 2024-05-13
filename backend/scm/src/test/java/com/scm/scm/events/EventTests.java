package com.scm.scm.events;


import com.scm.scm.events.vao.Event;
import com.scm.scm.events.vao.EventState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EventTests {

    private Event event;


    @BeforeEach
    void setUp() {
        event = new Event("id1", "user1", "contact1", EventState.CREATED, "propKey", "prevState", "currentState", LocalDateTime.now());
    }

    @Test
    void shouldInstantiateEvent() {
        assertNotNull(event);
    }

    @Test
    void shouldGenerateId() {
        String id = event.generateId();
        assertNotNull(id);
        assertTrue(id.matches("\\d+-\\d{1,5}"));
    }

    @Test
    void shouldSetAndGetUser() {
        event.setUser("user2");
        assertEquals("user2", event.getUser());
    }

    @Test
    void shouldSetAndGetContact() {
        event.setContact("contact2");
        assertEquals("contact2", event.getContact());
    }

    @Test
    void shouldSetAndGetEventState() {
        event.setEventState(EventState.UPDATED);
        assertEquals(EventState.UPDATED, event.getEventState());
    }

    @Test
    void shouldSetAndGetPropKey() {
        event.setPropKey("newPropKey");
        assertEquals("newPropKey", event.getPropKey());
    }

    @Test
    void shouldSetAndGetPrevState() {
        event.setPrevState("newPrevState");
        assertEquals("newPrevState", event.getPrevState());
    }

    @Test
    void shouldSetAndGetCurrentState() {
        event.setCurrentState("newCurrentState");
        assertEquals("newCurrentState", event.getCurrentState());
    }

    @Test
    void shouldSetAndGetEventTime() {
        LocalDateTime newTime = LocalDateTime.now();
        event.setEventTime(newTime);
        assertEquals(newTime, event.getEventTime());
    }


}
