package com.weather.api.locationconnectorservice.infrastructure.config;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.remoting.RemoteInvocationFailureException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class GeolocationDatabaseFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        byte[] body = null;
        try {
            body = IOUtils.toByteArray(response.body().asInputStream());
        } catch (IOException exception) {
            log.warn("Unable to decode the response body of the following request {}", response.request().toString());
        }

        if(response.status() == 401 || response.status() == 403){
            return new FeignException.Unauthorized("The request was not possible due to lack of access rights", response.request(), body);
        }

        if(response.status() >= 400 && response.status() < 499){
            return new FeignException.BadRequest("There was a bad request when trying to reach the api", response.request(), body);
        }

        if(response.status() >= 500 && response.status() < 599){
            return new RemoteInvocationFailureException("There was an server failure when trying to process the request", new Throwable(response.reason()));
        }

        return new RuntimeException("The request could not be processed", new Throwable(response.reason()));
    }
}