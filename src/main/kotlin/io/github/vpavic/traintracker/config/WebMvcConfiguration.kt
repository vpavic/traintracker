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

package io.github.vpavic.traintracker.config

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.CookieLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@Configuration(proxyBeanMethods = false)
class WebMvcConfiguration : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LocaleChangeInterceptor())
    }

    @Bean
    fun localeResolver(): CookieLocaleResolver {
        val localeResolver = CookieLocaleResolver()
        localeResolver.cookieName = "LOCALE"
        return localeResolver
    }

    @Bean
    fun webMvcRegistrations(): WebMvcRegistrations {
        return object : WebMvcRegistrations {

            override fun getRequestMappingHandlerMapping(): RequestMappingHandlerMapping {
                val handlerMapping = RequestMappingHandlerMapping()
                handlerMapping.setUseTrailingSlashMatch(false)
                return handlerMapping
            }
        }
    }
}
