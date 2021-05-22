package io.github.vpavic.traintracker

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition

@AnalyzeClasses(packagesOf = [TrainTrackerApplication::class])
class TrainTrackerArchitectureTests {

    @ArchTest
    val domainLayerRule = ArchRuleDefinition.noClasses()
        .that().resideInAPackage("..domain..")
        .should().accessClassesThat()
        .resideInAnyPackage("..application..", "..config..", "..infrastructure..", "..interfaces..")

    @ArchTest
    val applicationLayerRule = ArchRuleDefinition.noClasses()
        .that().resideInAPackage("..application..")
        .should().accessClassesThat().resideInAnyPackage("..config..", "..infrastructure..", "..interfaces..")

    @ArchTest
    val intefacesLayerRule = ArchRuleDefinition.noClasses()
        .that().resideInAPackage("..intefaces..")
        .should().accessClassesThat().resideInAnyPackage("..config..", "..infrastructure..")

    @ArchTest
    val infrastructureLayerRule = ArchRuleDefinition.noClasses()
        .that().resideInAPackage("..infrastructure..")
        .should().accessClassesThat().resideInAnyPackage("..config..")
}
