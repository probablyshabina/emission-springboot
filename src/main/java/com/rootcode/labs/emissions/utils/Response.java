package com.rootcode.labs.emissions.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Response {
    public boolean Success;
    public HttpStatus status;
    public HttpStatusCode statusCode;
    public String Message;
    public List<?> data;
}
