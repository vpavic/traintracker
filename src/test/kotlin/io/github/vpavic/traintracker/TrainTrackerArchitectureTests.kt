/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
