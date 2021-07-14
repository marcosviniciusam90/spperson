package com.mvam.spperson.resources;

import com.github.javafaker.Faker;
import com.mvam.spperson.dto.PessoaDTO;
import com.mvam.spperson.mappers.PessoaMapper;
import com.mvam.spperson.services.PessoaService;
import com.mvam.spperson.services.exceptions.RecursoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.mvam.spperson.utils.JsonUtils.toObject;
import static com.mvam.spperson.utils.PessoaUtils.createPessoaDTO;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class PessoaResourceTests {

    private static final PessoaMapper PESSOA_MAPPER = PessoaMapper.INSTANCE;

    private static final Faker FAKER = Faker.instance();

    private static final String API_URL_PATH = "/pessoas";

    private MockMvc mockMvc;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private PessoaResource pessoaResource;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pessoaResource)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void GETShouldReturnPersonWhenIdExists() throws Exception {
        Long id = FAKER.number().randomNumber();
        PessoaDTO pessoaDTOEsperado = createPessoaDTO(id);

        when(pessoaService.findById(id)).thenReturn(pessoaDTOEsperado);

        MvcResult mvcResult = mockMvc.perform(get(API_URL_PATH + "/" + id))
                .andExpect(status().isOk())
                .andReturn();

        PessoaDTO pessoaDTOAtual = toObject(mvcResult, PessoaDTO.class);
        assertEquals(pessoaDTOEsperado.getId(), pessoaDTOAtual.getId());
        assertEquals(pessoaDTOEsperado.getNome(), pessoaDTOAtual.getNome());
        assertEquals(pessoaDTOEsperado.getCpf(), pessoaDTOAtual.getCpf());
        assertEquals(pessoaDTOEsperado.getEmail(), pessoaDTOAtual.getEmail());
        assertEquals(pessoaDTOEsperado.getDataNascimento(), pessoaDTOAtual.getDataNascimento());
    }

    @Test
    void GETShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        Long id = FAKER.number().randomNumber();

        when(pessoaService.findById(id)).thenThrow(new RecursoNaoEncontradoException(id));

        assertThatThrownBy(() ->  mockMvc.perform(get(API_URL_PATH + "/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()))
                .hasCause(new RecursoNaoEncontradoException(id));

    }

    @Test
    void DELETEShouldDeleteSpecficPersonWhenIdExists() throws Exception {
        Long id = FAKER.number().randomNumber();
        mockMvc.perform(delete(API_URL_PATH + "/" + id))
                .andExpect(status().isNoContent());

    }
}
