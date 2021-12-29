package io.vpavic.traintracker;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packagesOf = TrainTrackerApplication.class)
class TrainTrackerArchitectureTests {

	@ArchTest
	ArchRule domainLayerRule = ArchRuleDefinition.noClasses()
		.that().resideInAPackage("..domain..")
		.should().accessClassesThat()
		.resideInAnyPackage("..application..", "..config..", "..infrastructure..", "..interfaces..");

	@ArchTest
	ArchRule applicationLayerRule = ArchRuleDefinition.noClasses()
		.that().resideInAPackage("..application..")
		.should().accessClassesThat().resideInAnyPackage("..config..", "..infrastructure..", "..interfaces..");

	@ArchTest
	ArchRule interfacesLayerRule = ArchRuleDefinition.noClasses()
		.that().resideInAPackage("..interfaces..")
		.should().accessClassesThat().resideInAnyPackage("..config..", "..infrastructure..");

	@ArchTest
	ArchRule infrastructureLayerRule = ArchRuleDefinition.noClasses()
		.that().resideInAPackage("..infrastructure..")
		.should().accessClassesThat().resideInAnyPackage("..config..");

}
