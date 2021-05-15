package com.mvam.spperson.resources.events.listeners;

import com.mvam.spperson.resources.events.CriarRecursoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class CriarRecursoListener implements ApplicationListener<CriarRecursoEvent> {

    @Override
    public void onApplicationEvent(CriarRecursoEvent event) {
        Long id = event.getId();
        HttpServletResponse response = event.getResponse();
        putHeaderLocation(id, response);
    }

    private void putHeaderLocation(Long id, HttpServletResponse response) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }
}
