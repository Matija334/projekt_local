package com.scm.scm.loadDatabase;

import com.scm.scm.contact.vao.Contact;
import com.scm.scm.tenant.services.TenantServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class LoadContacts {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TenantServices tenantServices;

    public void createContacts(String[] tenantUniqueNames) {
        mongoTemplate.dropCollection(Contact.class);

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

        mongoTemplate.save(contact1, contact1.getTenantUniqueName() + "_main");
        mongoTemplate.save(contact2, contact2.getTenantUniqueName() + "_main");
        mongoTemplate.save(contact3, contact3.getTenantUniqueName() + "_main");
        mongoTemplate.save(contact4, contact4.getTenantUniqueName() + "_main");
        System.out.println("Loaded test Contacts into the database.");

        tenantServices.addTags(contact1.getTenantUniqueName(), contact1.getTags());
        tenantServices.addTags(contact2.getTenantUniqueName(), contact2.getTags());
        tenantServices.addTags(contact3.getTenantUniqueName(), contact3.getTags());
        tenantServices.addTags(contact4.getTenantUniqueName(), contact4.getTags());
        System.out.println("Added tags from Contacts to tenants into the database.");
    }

    private List<String> selectRandomTags(List<String> tags, int count) {
        if (count >= tags.size()) {
            return new ArrayList<>(tags);
        }

        List<String> randomTags = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(tags.size());
            String randomTag = tags.get(randomIndex);
            randomTags.add(randomTag);
            tags.remove(randomIndex);
        }
        return randomTags;
    }
}
