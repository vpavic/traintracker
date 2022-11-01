package net.vpavic.traintracker;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packagesOf = TrainTrackerApplication.class)
class TrainTrackerArchitectureTests {

	@ArchTest
	@SuppressWarnings("unused")
	ArchRule domainLayerRule = ArchRuleDefinition.noClasses()
		.that().resideInAPackage("net.vpavic.traintracker.domain..")
		.should().accessClassesThat()
		.resideInAnyPackage("net.vpavic.traintracker.application..", "net.vpavic.traintracker.infrastructure..",
				"net.vpavic.traintracker.interfaces..");

	@ArchTest
	@SuppressWarnings("unused")
	ArchRule interfacesLayerRule = ArchRuleDefinition.noClasses()
		.that().resideInAPackage("net.vpavic.traintracker.interfaces..")
		.should().accessClassesThat().resideInAnyPackage("net.vpavic.traintracker.infrastructure..");

}
