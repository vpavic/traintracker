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

package io.github.vpavic.traintracker.build;

import java.util.List;
import java.util.regex.Pattern;

public class DependencyVersionHelper {

    private static final Pattern stablePattern = Pattern.compile("^[0-9,.v-]+(-r)?$");

    private static final List<String> stableKeywords = List.of("RELEASE", "FINAL", "GA");

    public static boolean isStable(String version) {
        boolean isStableVersion = stablePattern.matcher(version).matches();
        boolean containsStableKeyword = stableKeywords.stream().anyMatch(s -> version.toUpperCase().contains(s));
        return isStableVersion || containsStableKeyword;
    }

}
