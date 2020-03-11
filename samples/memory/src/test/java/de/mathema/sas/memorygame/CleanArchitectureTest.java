package de.mathema.sas.memorygame;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class CleanArchitectureTest {

    @Test
    public void testDependenciesDirection() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("de.mathema.sas.memorygame");
            ArchRule myRule = layeredArchitecture()
                    .layer("Domain").definedBy("de.mathema.sas.memorygame.cards",
                                                                        "de.mathema.sas.memorygame.player",
                                                                        "de.mathema.sas.memorygame.rules")

                    .layer("Gateways").definedBy("de.mathema.sas.memorygame.consolegame",
                                                                        "de.mathema.sas.memorygame.storage")

                    .layer("Application").definedBy("de.mathema.sas.memorygame")

                    .whereLayer("Domain").mayOnlyBeAccessedByLayers("Gateways", "Application")
                    .whereLayer("Gateways").mayOnlyBeAccessedByLayers("Application")
                    .whereLayer("Application").mayNotBeAccessedByAnyLayer();

            myRule.check(importedClasses);
    }
}
