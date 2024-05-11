package com.scm.scm.loadDatabase;

import com.scm.scm.contact.vao.Contact;
import com.scm.scm.events.vao.Event;
import com.scm.scm.events.vao.EventState;
import com.scm.scm.support.mongoTemplate.CollectionType;
import com.scm.scm.tenant.services.TenantServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class LoadContacts {

    private static final Logger log = Logger.getLogger(LoadContacts.class.toString());

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TenantServices tenantServices;

    @Autowired
    private LoadEvents loadEvents;

    private static final SecureRandom random = new SecureRandom();

    public void createContacts(String[] tenantUniqueNames) {

        List<String> allTags = new ArrayList<>();
        allTags.add("New");
        allTags.add("Important");
        allTags.add("Old");
        allTags.add("CEO");
        allTags.add("CFO");
        allTags.add("Regular");
        allTags.add("VIP");
        allTags.add("Assistant");

        List<String> tags1 = selectRandomTags(allTags, 4);
        List<String> tags2 = selectRandomTags(allTags, 2);
        List<String> tags3 = selectRandomTags(allTags, 5);
        List<String> tags4 = selectRandomTags(allTags, 3);


        Map<String, String> props1 = new HashMap<>();
        props1.put("Name", "John");
        props1.put("Email", "john@gmail.com");
        props1.put("Phone", "+7(90)988888");
        props1.put("Address", "123 Main St");

        Map<String, String> props2 = new HashMap<>();
        props2.put("Company", "XYZ Corp");
        props2.put("Position", "Senior Marketing Manager");
        props2.put("OfficeLocation", "Downtown");

        Map<String, String> props3 = new HashMap<>();
        props3.put("Hobby", "Photography");
        props3.put("SocialMedia", "@john_photographer");
        props3.put("Website", "www.johnphotography.com");

        Map<String, String> props4 = new HashMap<>();
        props4.put("EmergencyContact", "Jane Doe");
        props4.put("Relationship", "Spouse");
        props4.put("EmergencyPhone", "+7(90)987654");


        Contact contact1 = new Contact("", "Contact 1", "user1@example.com", tenantUniqueNames[0], "", LocalDateTime.now(), tags1, props1, "");
        contact1.setId(contact1.generateId(contact1.getTitle()));
        contact1.setAttributesToString(contact1.contactAttributesToString());

        Contact contact2 = new Contact("", "Contact 2", "user1@example.com", tenantUniqueNames[0], "", LocalDateTime.now(), tags2, props2, "");
        contact2.setId(contact2.generateId(contact2.getTitle()));
        contact2.setAttributesToString(contact2.contactAttributesToString());

        Contact contact3 = new Contact("", "Contact 3", "user3@example.com", tenantUniqueNames[1], "", LocalDateTime.now(), tags3, props3, "");
        contact3.setId(contact3.generateId(contact3.getTitle()));
        contact3.setAttributesToString(contact3.contactAttributesToString());

        Contact contact4 = new Contact("", "Contact 4", "user3@example.com", tenantUniqueNames[1], "", LocalDateTime.now(), tags4, props4, "");
        contact4.setId(contact4.generateId(contact4.getTitle()));
        contact4.setAttributesToString(contact4.contactAttributesToString());

        mongoTemplate.save(contact1, contact1.getTenantUniqueName() + CollectionType.MAIN.getCollectionType());
        Event event1 = new Event(contact1.getUser(), contact1.getId(), EventState.CREATED);
        loadEvents.createEvent(event1, contact1.getTenantUniqueName());

        String exTitle = contact1.getTitle();
        contact1.setTitle("Contact 1.1");
        Event event1_1 = new Event(contact1.getUser(), contact1.getId(), EventState.UPDATED);
        event1_1.setPropKey("Title");
        event1_1.setPrevState(exTitle);
        event1_1.setCurrentState(contact1.getTitle());
        loadEvents.createEvent(event1_1, contact1.getTenantUniqueName());

        mongoTemplate.save(contact2, contact2.getTenantUniqueName() + CollectionType.MAIN.getCollectionType());
        Event event2 = new Event(contact2.getUser(), contact2.getId(), EventState.CREATED);
        loadEvents.createEvent(event2, contact2.getTenantUniqueName());

        contact2.getTags().add("NewLoad2");
        Event event2_1 = new Event(contact2.getUser(), contact2.getId(), EventState.TAG_ADD);
        event2_1.setPropKey("Tags");
        event2_1.setCurrentState("NewLoad2");
        loadEvents.createEvent(event2_1, contact2.getTenantUniqueName());

        mongoTemplate.save(contact3, contact3.getTenantUniqueName() + CollectionType.MAIN.getCollectionType());
        Event event3 = new Event(contact3.getUser(), contact3.getId(), EventState.CREATED);
        loadEvents.createEvent(event3, contact3.getTenantUniqueName());

        String exProp = contact3.getProps().get("Hobby");
        contact3.getProps().put("Hobby", "Photography, Travel");
        Event event3_1 = new Event(contact3.getUser(), contact3.getId(), EventState.PROP_ADD);
        event3_1.setPropKey("Props");
        event3_1.setPrevState(exProp);
        event3_1.setCurrentState(contact3.getProps().get("Hobby"));
        loadEvents.createEvent(event3_1, contact3.getTenantUniqueName());

        mongoTemplate.save(contact4, contact4.getTenantUniqueName() + CollectionType.MAIN.getCollectionType());
        Event event4 = new Event(contact4.getUser(), contact4.getId(), EventState.CREATED);
        loadEvents.createEvent(event4, contact4.getTenantUniqueName());

        Contact contact5 = new Contact("", "Contact 5", "user3@example.com", tenantUniqueNames[1], "", LocalDateTime.now(), tags4, props4, "");
        contact5.setId(contact5.generateId(contact5.getTitle()));
        mongoTemplate.save(contact5, contact5.getTenantUniqueName() + CollectionType.MAIN.getCollectionType());

        Event event5 = new Event(contact5.getUser(), contact5.getId(), EventState.DELETED);
        event5.setPrevState(contact5.getId());
        loadEvents.createEvent(event5, contact5.getTenantUniqueName());
        mongoTemplate.remove(contact5, contact5.getTenantUniqueName() + CollectionType.MAIN.getCollectionType());

        log.info("Loaded test Contacts into the database.");

        tenantServices.addTags(contact1.getTenantUniqueName(), contact1.getTags());
        tenantServices.addTags(contact2.getTenantUniqueName(), contact2.getTags());
        tenantServices.addTags(contact3.getTenantUniqueName(), contact3.getTags());
        tenantServices.addTags(contact4.getTenantUniqueName(), contact4.getTags());
        log.info("Added tags from Contacts to tenants into the database.");
    }

    private List<String> selectRandomTags(List<String> tags, int count) {
        if (count >= tags.size()) {
            return new ArrayList<>(tags);
        }

        List<String> randomTags = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(tags.size());
            String randomTag = tags.get(randomIndex);
            randomTags.add(randomTag);
            tags.remove(randomIndex);
        }
        return randomTags;
    }
}
