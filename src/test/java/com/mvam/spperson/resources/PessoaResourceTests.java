package com.mvam.spperson.resources;

import com.github.javafaker.Faker;
import com.mvam.spperson.dto.PessoaDTO;
import com.mvam.spperson.mappers.PessoaMapper;
import com.mvam.spperson.resources.events.CriarRecursoEvent;
import com.mvam.spperson.services.PessoaService;
import com.mvam.spperson.services.exceptions.PessoaComMesmoCPFException;
import com.mvam.spperson.services.exceptions.RecursoNaoEncontradoException;
import com.mvam.spperson.utils.BeanUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Objects;

import static com.mvam.spperson.utils.JsonUtils.toJsonString;
import static com.mvam.spperson.utils.JsonUtils.toObject;
import static com.mvam.spperson.utils.PessoaUtils.createPessoaDTO;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class PessoaResourceTests {

    private static final PessoaMapper PESSOA_MAPPER = PessoaMapper.INSTANCE;

    private static final Faker FAKER = Faker.instance();

    private static final String API_URL_PATH = "/pessoas";

    private MockMvc mockMvc;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private ApplicationEventPublisher publisher;

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
    void quandoChamarPOSTEntaoUmaPessoaDeveSerCriada() throws Exception {
        PessoaDTO pessoaDTOInput = createPessoaDTO(null);
        PessoaDTO pessoaDTOEsperado = BeanUtils.clone(pessoaDTOInput);
        pessoaDTOEsperado.setId(FAKER.number().randomNumber());

        when(pessoaService.create(pessoaDTOInput)).thenReturn(pessoaDTOEsperado);

        MvcResult mvcResult = mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(pessoaDTOInput)))
                .andExpect(status().isCreated())
                .andReturn();

        PessoaDTO pessoaDTOAtual = toObject(mvcResult, PessoaDTO.class);
        assertEquals(pessoaDTOEsperado, pessoaDTOAtual);

        verify(pessoaService, times(1)).create(pessoaDTOInput);
        verify(publisher, times(1)).publishEvent(any(CriarRecursoEvent.class));
    }

    @Test
    void quandoChamarPOSTComCampoObrigatorioNaoInformadoEntaoDeveRetornarBadRequest() throws Exception {
        PessoaDTO pessoaDTOInput = createPessoaDTO(null);
        pessoaDTOInput.setNome(null);
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(pessoaDTOInput)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getMessage().contains("Campo obrigatorio")));

    }

    @Test
    void quandoChamarPOSTComCpfInvalidoEntaoDeveRetornarBadRequest() throws Exception {
        PessoaDTO pessoaDTOInput = createPessoaDTO(null);
        pessoaDTOInput.setCpf("011.111.33325");
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(pessoaDTOInput)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getMessage().contains("CPF precisa estar num formato valido")));

    }

    @Test
    void quandoChamarPOSTPassandoPessoaComCpfJaExistenteEntaoDeveRetornarBadRequest() throws Exception {
        PessoaDTO pessoaDTOInput = createPessoaDTO(null);
        String cpf = pessoaDTOInput.getCpf();
        when(pessoaService.create(pessoaDTOInput)).thenThrow(new PessoaComMesmoCPFException(cpf));

        assertThatThrownBy(() ->  mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(pessoaDTOInput)))
                .andExpect(status().isBadRequest()))
                .hasCause(new PessoaComMesmoCPFException(cpf));

    }

    @Test
    void quandoChamarPUTPassandoPessoaEIdExistenteDeveAtualizarComSucesso() throws Exception {
        Long id = FAKER.number().randomNumber();
        PessoaDTO pessoaDTOInput = createPessoaDTO(null);
        PessoaDTO pessoaDTOEsperado = BeanUtils.clone(pessoaDTOInput);
        pessoaDTOEsperado.setId(id);

        when(pessoaService.update(id, pessoaDTOInput)).thenReturn(pessoaDTOEsperado);

        MvcResult mvcResult = mockMvc.perform(put(API_URL_PATH + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(pessoaDTOInput)))
                .andExpect(status().isOk())
                .andReturn();

        PessoaDTO pessoaDTOAtual = toObject(mvcResult, PessoaDTO.class);
        assertEquals(pessoaDTOEsperado, pessoaDTOAtual);

        verify(pessoaService, times(1)).update(id, pessoaDTOInput);
    }

    @Test
    void quandoChamarPUTPassandoPessoaComCpfJaExistenteEntaoDeveRetornarBadRequest() throws Exception {
        Long id = FAKER.number().randomNumber();
        PessoaDTO pessoaDTOInput = createPessoaDTO(null);
        String cpf = pessoaDTOInput.getCpf();
        when(pessoaService.update(id, pessoaDTOInput)).thenThrow(new PessoaComMesmoCPFException(cpf));

        assertThatThrownBy(() ->  mockMvc.perform(put(API_URL_PATH + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(pessoaDTOInput)))
                .andExpect(status().isBadRequest()))
                .hasCause(new PessoaComMesmoCPFException(cpf));

    }

    @Test
    void quandoChamarGETPassandoIdEntaoDeveRetornarUmaPessoa() throws Exception {
        Long id = FAKER.number().randomNumber();
        PessoaDTO pessoaDTOEsperado = createPessoaDTO(id);

        when(pessoaService.findById(id)).thenReturn(pessoaDTOEsperado);

        MvcResult mvcResult = mockMvc.perform(get(API_URL_PATH + "/" + id))
                .andExpect(status().isOk())
                .andReturn();

        PessoaDTO pessoaDTOAtual = toObject(mvcResult, PessoaDTO.class);
        assertEquals(pessoaDTOEsperado, pessoaDTOAtual);
    }

    @Test
    void quandoChamarGETPassandoIdInexistenteEntaoDeveRetornarNotFound() throws Exception {
        Long id = FAKER.number().randomNumber();

        when(pessoaService.findById(id)).thenThrow(new RecursoNaoEncontradoException(id));

        assertThatThrownBy(() ->  mockMvc.perform(get(API_URL_PATH + "/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()))
                .hasCause(new RecursoNaoEncontradoException(id));

    }

    @Test
    void quandoChamarDELETEPassandoIdEntaoDeveExcluirAPessoaCorrespondente() throws Exception {
        Long id = FAKER.number().randomNumber();
        mockMvc.perform(delete(API_URL_PATH + "/" + id))
                .andExpect(status().isNoContent());

    }
}
