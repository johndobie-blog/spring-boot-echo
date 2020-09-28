package com.johndobie.springboot.web.exception;

import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class ExceptionHelper {

    public static String formatException(Exception e) {
        StringBuilder sb = new StringBuilder();
        if (e != null) {
            sb.append(e.getClass().getName() + " with message: " + e.getMessage());
            sb.append("; stackTrace --> ");
            sb.append(getStackTrace(e));
        }
        return sb.toString();
    }

    public static String getStackTrace(Exception e) {
        String stackTrace = "No Stack Trace";
        if (e != null && e.getStackTrace() != null) {
            stackTrace = Arrays.stream(e.getStackTrace()).map(line -> line.toString()).collect(Collectors.joining("; "));
        }
        return stackTrace;
    }

}
