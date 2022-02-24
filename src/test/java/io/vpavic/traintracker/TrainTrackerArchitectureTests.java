package io.vpavic.traintracker;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packagesOf = TrainTrackerApplication.class)
class TrainTrackerArchitectureTests {

	@ArchTest
	ArchRule domainLayerRule = ArchRuleDefinition.noClasses()
		.that().resideInAPackage("io.vpavic.traintracker.domain..")
		.should().accessClassesThat()
		.resideInAnyPackage("io.vpavic.traintracker.application..", "io.vpavic.traintracker.infrastructure..",
				"io.vpavic.traintracker.interfaces..");

	@ArchTest
	ArchRule interfacesLayerRule = ArchRuleDefinition.noClasses()
		.that().resideInAPackage("io.vpavic.traintracker.interfaces..")
		.should().accessClassesThat().resideInAnyPackage("io.vpavic.traintracker.infrastructure..");

}
