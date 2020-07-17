package com.bycomparison;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Comparisons {

    public static List<Comparison> getComparisons() {
    return List
        .of(new Comparison("Replace Small Test Files with Text Blocks", "Java",
                List.of("Java 15"),
                "", "", "", "", "", ""),
            new Comparison("Favor Constructor over Field Injection", "Spring Boot",
                List.of("Spring"), """
                @Component
                class SpaceSuit {

                    @Autowired private OxygenMonitor oxygenMonitor;
                }""", "4",
                """
                    <p>
                    Have a look at the code above. 
                    We have a SpaceSuit component with a single dependency, the OxygenMonitor.
                    Upon start, Spring will create one instance of that class for us, providing it an instance of the OxygenMonitor, and inject the SpaceSuit instance where appropriate in other components.
                    </p>

                    <p>
                    The field where Spring should inject the OxygenMonitor dependency is marked with @Autowired and is private.
                    Because private really means private in Java, Spring needs to change the accessibility of that field at runtime so it can set its value. 
                    Depending on your security manager, this may fail, consequently preventing your app to start up in that case. 
                    Changing the field visibility to default would solve this, but would allow write access to that field, which might be even worse.
                    </p>

                    <p>
                    A field that is not marked final can be reassigned to another value anytime. 
                    So after Spring has injected the OxygenMonitor, a method within the SpaceSuit class might as well change that field, which is probably something you would not want either. 
                    Setting the field to final prevents reassigning the value, but this is no real solution here. 
                    Because it prevents Spring to inject the value in the field.
                    </p>

                    <ul>
                    <li>Not final, can be changed within any methods later on</li>
                    <li>No knowledge when the injection happens</li>
                    <li>Field could be hidden somewhere in the class (although this is bad practice</li>
                    </ul>""", """
                @Component
                class SpaceSuit {

                    private final OxygenMonitor oxygenMonitor;

                    public SpaceSuit(OxygenMonitor oxygenMonitor) {
                        this.oxygenMonitor = oxygenMonitor;
                    }
                }""", "4-8", """
                <p>
                The code is clearer now. 
                The field is set through the constructor upon instance creation. 
                And because we marked the field final it won't be able to change afterward anymore. 
                We also get a bonus: the @Autowired annotation is optional for the constructor and can be left out.
                </p>
                                
                <p>
                With field injection, you don't feel the cost of adding another dependency. 
                It's just another line added to the source code. 
                It's different with constructor injection because you need to add the dependency to the constructor. 
                The constructor becomes an overview of all dependencies of that class, and the more
                dependencies, the larger the constructor with a lot of parameters. 
                We developers are trained to keep
                parameters in methods to a minimum, and this intuition will kick in for constructors, too, helping
                you to spot classes with too many dependencies more quickly.
                </p>
                    
                <p>
                There is, however, an exception to this rule. 
                JUnit requires a no-args constructor for every test class.
                So we can't use constructor injection in tests and have to fall back to field injection.
                </p>
                                
                <ul>
                <li>TODO thymeleaf and MVC</li>
                <li>TODO web vs. webflux</li>
                </ul>
                """));
  }

}
