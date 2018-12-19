/*
 * Copyright 2018 the original author or authors.
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

package io.traintracker.config;

import javax.annotation.PostConstruct;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapAttributeConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapIndexConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.session.HazelcastSessionProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.hazelcast.HazelcastSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;

@Configuration
public class SessionConfiguration {

    private final HazelcastSessionProperties sessionProperties;

    private final HazelcastInstance hazelcastInstance;

    public SessionConfiguration(HazelcastSessionProperties sessionProperties,
            ObjectProvider<HazelcastInstance> hazelcastInstance) {
        this.sessionProperties = sessionProperties;
        this.hazelcastInstance = hazelcastInstance.getObject();
    }

    @PostConstruct
    public void init() {
        Config config = this.hazelcastInstance.getConfig();
        String mapName = this.sessionProperties.getMapName();
        MapConfig mapConfig = config.getMapConfigOrNull(mapName);

        if (mapConfig == null) {
            // @formatter:off
            MapAttributeConfig principalNameAttributeConfig = new MapAttributeConfig()
                    .setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                    .setExtractor(PrincipalNameExtractor.class.getName());
            // @formatter:on

            MapIndexConfig principalNameIndexConfig = new MapIndexConfig(
                    HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false);

            // @formatter:off
            mapConfig = new MapConfig(mapName)
                    .addMapAttributeConfig(principalNameAttributeConfig)
                    .addMapIndexConfig(principalNameIndexConfig);
            // @formatter:on

            config.addMapConfig(mapConfig);
        }
    }

}
