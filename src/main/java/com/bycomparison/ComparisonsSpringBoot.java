package com.bycomparison;

import java.util.List;

public class ComparisonsSpringBoot {

  public static List<Comparison> getComparisons() {
    return List
        .of(
            /*
            Comparison.empty("Favor Configuration over Code", "Spring Boot"),
            Comparison.empty("Favor ConfigurationProperties over Value", "Spring Boot"),
            Comparison.empty("Favor Properties over YAML", "Spring Boot"),
            Comparison.empty("Group Properties by Line Breaks", "Spring Boot"),
            */
            Comparison.empty("Keep Your Application Clean", "Spring Boot"),
            Comparison.empty("Keep the Context Loading Test", "Spring Boot"),
            Comparison.empty("Favor POJO Tests over Spring Boot Tests", "Spring Boot"),
            Comparison.empty("Favor Test Slices or Spring Boot Integration Tests", "Spring Boot"),
            Comparison.empty("Appropriate Test Splice", "Spring Boot"),
            Comparison.empty("Avoid Async Pitfalls", "Spring Boot"),
            Comparison.propertiesComparison("Save the Planet", "Configuration",
                List.of("Spring"),
                """
                    # BEFORE:start
                    # START_HIGHLIGHT
                    spring.main.banner-mode=console
                    # END_HIGHLIGHT
                    # BEFORE:end""",
                """
                    * some text
                    """, """
                    # AFTER:start
                    # START_HIGHLIGHT
                    spring.main.banner-mode=off
                    # END_HIGHLIGHT
                    # AFTER:end""", """
                    """, "Spring Boot"),
            Comparison.javaComparison("Favor Constructor over Field Injection", "General",
                List.of("Spring"), """
                    Have a look at the code above. 
                    We have a SpaceSuit component with a single dependency, the `OxygenMonitor`.
                    Upon start, Spring will create one instance of that class for us, providing it an instance of the OxygenMonitor, and inject the SpaceSuit instance where appropriate in other components.

                    The field where Spring should inject the OxygenMonitor dependency is marked with @Autowired and is private.
                    Because private really means private in Java, Spring needs to change the accessibility of that field at runtime so it can set its value. 
                    Depending on your security manager, this may fail, consequently preventing your app to start up in that case. 
                    Changing the field visibility to default would solve this, but would allow write access to that field, which might be even worse.

                    A field that is not marked final can be reassigned to another value anytime. 
                    So after Spring has injected the OxygenMonitor, a method within the SpaceSuit class might as well change that field, which is probably something you would not want either. 
                    Setting the field to final prevents reassigning the value, but this is no real solution here. 
                    Because it prevents Spring to inject the value in the field.

                    - Not final, can be changed within any methods later on
                    - No knowledge when the injection happens
                    - Field could be hidden somewhere in the class (although this is bad practice)""",
                """
                    The code is clearer now. 
                    The field is set through the constructor upon instance creation. 
                    And because we marked the field final it won't be able to change afterward anymore. 
                    We also get a bonus: the @Autowired annotation is optional for the constructor and can be left out.
                                    
                    With field injection, you don't feel the cost of adding another dependency. 
                    It's just another line added to the source code. 
                    It's different with constructor injection because you need to add the dependency to the constructor. 
                    The constructor becomes an overview of all dependencies of that class, and the more
                    dependencies, the larger the constructor with a lot of parameters. 
                    We developers are trained to keep
                    parameters in methods to a minimum, and this intuition will kick in for constructors, too, helping
                    you to spot classes with too many dependencies more quickly.
                        
                    There is, however, an exception to this rule. 
                    JUnit requires a no-args constructor for every test class.
                    So we can't use constructor injection in tests and have to fall back to field injection.
                                    
                    - TODO thymeleaf and MVC
                    - TODO web vs. webflux
                    """, "Spring Boot"));
  }

}
