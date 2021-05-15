package com.mvam.spperson.resources.events;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class CriarRecursoEvent extends ApplicationEvent {

    private final Long id;
    private final HttpServletResponse response;

    public CriarRecursoEvent(Object source, Long id, HttpServletResponse response) {
        super(source);
        this.id = id;
        this.response = response;
    }

    public Long getId() {
        return id;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
