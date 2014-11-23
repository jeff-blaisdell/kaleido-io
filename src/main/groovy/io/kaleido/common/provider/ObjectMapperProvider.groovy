package io.kaleido.common.provider

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.google.inject.Provider

class ObjectMapperProvider implements Provider<ObjectMapper> {

    @Override
    ObjectMapper get() {
        ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
            .registerModule(new JodaModule())

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

        return objectMapper
    }
}
