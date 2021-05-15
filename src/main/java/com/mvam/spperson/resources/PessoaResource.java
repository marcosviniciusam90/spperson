package com.mvam.spperson.resources;

import com.mvam.spperson.dto.PessoaDTO;
import com.mvam.spperson.resources.events.CriarRecursoEvent;
import com.mvam.spperson.services.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/pessoas")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaResource {

    private final PessoaService pessoaService;

    private final ApplicationEventPublisher publisher;

    @GetMapping
    public Page<PessoaDTO> findAllPaged(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy ) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return pessoaService.findAllPaged(pageRequest);
    }

    @GetMapping("/{id}")
    public PessoaDTO findById(@PathVariable Long id) {
        return pessoaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaDTO create(@Valid @RequestBody PessoaDTO pessoaDTO, HttpServletResponse response) {
        pessoaDTO = pessoaService.create(pessoaDTO);
        publisher.publishEvent(new CriarRecursoEvent(this, pessoaDTO.getId(), response));
        return pessoaDTO;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PessoaDTO update(@PathVariable Long id, @Valid @RequestBody PessoaDTO pessoaDTO) {
        return pessoaService.update(id, pessoaDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        pessoaService.delete(id);
    }
}