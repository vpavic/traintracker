/*
 * Copyright 2020 the original author or authors.
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

package io.traintracker.build;

import java.io.File;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.github.benmanes.gradle.versions.VersionsPlugin;
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.TaskContainer;

@SuppressWarnings("unused")
public class ConventionsPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.apply(action -> action.from(new File(project.getRootDir(), "gradle/dependency-versions.gradle")));
        project.getPluginManager().apply(VersionsPlugin.class);
        TaskContainer tasks = project.getTasks();
        tasks.withType(DependencyUpdatesTask.class, dependencyUpdatesTask -> {
            dependencyUpdatesTask.setGradleReleaseChannel("current");
            dependencyUpdatesTask.rejectVersionIf(candidate -> isNonStable(candidate.getCandidate().getVersion())
                    && !isNonStable(candidate.getCurrentVersion()));
        });
    }

    private static boolean isNonStable(String version) {
        boolean containsStableKeyword = Stream.of("RELEASE", "FINAL", "GA")
                .anyMatch(s -> version.toUpperCase().contains(s));
        boolean isStableVersion = Pattern.compile("^[0-9,.v-]+(-r)?$").matcher(version).matches();
        return !containsStableKeyword && !isStableVersion;
    }

}
