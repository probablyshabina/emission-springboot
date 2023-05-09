package com.rootcode.labs.emissions.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Response {
    public boolean Success;
    public HttpStatus status;
    public HttpStatusCode statusCode;
    public String Message;
    public List<?> data;
}
