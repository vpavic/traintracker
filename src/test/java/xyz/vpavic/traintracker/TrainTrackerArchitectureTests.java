package xyz.vpavic.traintracker;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packagesOf = TrainTrackerApplication.class)
class TrainTrackerArchitectureTests {

	@ArchTest
	@SuppressWarnings("unused")
	ArchRule domainLayerRule = ArchRuleDefinition.noClasses()
		.that().resideInAPackage("xyz.vpavic.traintracker.domain..")
		.should().accessClassesThat()
		.resideInAnyPackage("xyz.vpavic.traintracker.application..", "xyz.vpavic.traintracker.infrastructure..",
				"xyz.vpavic.traintracker.interfaces..");

	@ArchTest
	@SuppressWarnings("unused")
	ArchRule interfacesLayerRule = ArchRuleDefinition.noClasses()
		.that().resideInAPackage("xyz.vpavic.traintracker.interfaces..")
		.should().accessClassesThat().resideInAnyPackage("xyz.vpavic.traintracker.infrastructure..");

}
