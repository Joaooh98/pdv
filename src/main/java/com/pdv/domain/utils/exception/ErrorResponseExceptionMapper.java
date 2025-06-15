package com.pdv.domain.utils.exception;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

import org.apache.http.HttpStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.utils.DateUtils;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 *
 * @author JoaoCP
 */
@Provider
public class ErrorResponseExceptionMapper implements ExceptionMapper<PdvException> {

    @ConfigProperty(name = "quarkus.application.name")
    String apiName;

    @Override
    public Response toResponse(PdvException ex) {

        int httpStatus;
        var error = EnumErrorCode.parseByKey(ex.getErrorCode());

        var formattedDate = DateUtils.formatDDMMYYYYHHMMSS(LocalDateTime.now());
        var exceptionResponse = new CustomerExceptionResponseDto();

        exceptionResponse.setError(ex.getMessage());
        exceptionResponse.setErrorDate(formattedDate);

        if (error != null) {
            var errorPhrase = Status.fromStatusCode(error.getHttpStatus()).getReasonPhrase();
            httpStatus = error.getHttpStatus();

            exceptionResponse.setErrorCode(error.getKey());
            exceptionResponse.setHttpCodeMessage(errorPhrase);
        } else {
            exceptionResponse.setErrorCode(ex.getErrorCode());
            httpStatus = HttpStatus.SC_BAD_REQUEST;
        }

        return Response.status(httpStatus).entity(exceptionResponse).build();
    }

    public String loggerToString(PdvException ex) {
        var stringWriter = new StringWriter();
        var printWriter = new PrintWriter(stringWriter);

        ex.printStackTrace(printWriter);

        return stringWriter.toString().trim().substring(0, 500);

    }

}
