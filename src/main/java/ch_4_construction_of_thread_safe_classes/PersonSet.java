package ch_4_construction_of_thread_safe_classes;

import java.util.HashSet;
import java.util.Set;

/*
This is an example of using the Monitor Pattern to achieve thread safety
 */
public class PersonSet {

    // A non-thread safe object (HashSet) is encapsulated within another object (PersonSet)
        // All access to it is protected by the same lock (synchronized block)
    private final Set<Person> mySet = new HashSet<>();

    private final Object lock = new Object();

    public void addPerson(Person p) {
        synchronized (lock) {
            mySet.add(p);
        }
    }

    public boolean containsPerson(Person p) {
        synchronized (lock) {
            return mySet.contains(p);
        }
    }

    public static class Person{
    }
}
