package com.mvam.spperson.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvam.spperson.dto.PessoaInsertDTO;
import com.mvam.spperson.utils.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static com.mvam.spperson.utils.PessoaUtils.createPessoaInsertDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PessoaResourceIntegrationTests{

    private static final String URL_PESSOAS = "/pessoas";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String adminUsername;
    private String adminPassword;
    private String visitorUsername;
    private String visitorPassword;

    private PessoaInsertDTO pessoaInsertDTO;

    @BeforeEach
    void setUp() throws Exception {

        adminUsername = "admin@gmail.com";
        adminPassword = "admin";
        visitorUsername = "marcos@gmail.com";
        visitorPassword = "marcos";

        pessoaInsertDTO = createPessoaInsertDTO(1L);
    }

    @Test
    void findAllByNameContainingShouldReturnPageOfUsers() throws Exception {

        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        ResultActions result =
                mockMvc.perform(get(URL_PESSOAS)
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").isNotEmpty());
        result.andExpect(jsonPath("$.content[0].nome").isNotEmpty());
        result.andExpect(jsonPath("$.content[0].email").isNotEmpty());
        result.andExpect(jsonPath("$.content[0].dataNascimento").isNotEmpty());
    }

    @Test
    void findAllByNameContainingShouldReturnPageOfUsersWithSpecifSizeTotalElementsAndTotalPages() throws Exception {

        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        ResultActions result =
                mockMvc.perform(get(URL_PESSOAS + "?page=0&linesPerPage=2")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.number").value(0));
        result.andExpect(jsonPath("$.size").value(2));
        result.andExpect(jsonPath("$.totalElements").value(5));
        result.andExpect(jsonPath("$.totalPages").value(3));
    }

    @Test
    void findAllByNameContainingShouldReturnPageOfUsersThatContainsInformedName() throws Exception {

        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        ResultActions result =
                mockMvc.perform(get(URL_PESSOAS + "?nome=Marcos")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").value(1L));
        result.andExpect(jsonPath("$.content[0].nome").value("Marcos Vinicius"));
        result.andExpect(jsonPath("$.content[0].email").value("marcosviniciusam90@gmail.com"));
        result.andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void createShouldCreateNewUserWhenLoggedAsAdminAndDataIsValid() throws Exception {

        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        String jsonBody = objectMapper.writeValueAsString(pessoaInsertDTO);

        ResultActions result =
                mockMvc.perform(post(URL_PESSOAS)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody));


        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").isNotEmpty());
        result.andExpect(jsonPath("$.nome").value(pessoaInsertDTO.getNome()));
        result.andExpect(jsonPath("$.cpf").value(pessoaInsertDTO.getCpf()));
        result.andExpect(jsonPath("$.dataNascimento").value(pessoaInsertDTO.getDataNascimento().toString()));
        result.andExpect(jsonPath("$.email").value(pessoaInsertDTO.getEmail()));
    }

    @Test
    void createShouldReturnBadRequestWhenRequiredFieldNotFilled() throws Exception {

        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        pessoaInsertDTO.setNome(null);

        String jsonBody = objectMapper.writeValueAsString(pessoaInsertDTO);

        ResultActions result =
                mockMvc.perform(post(URL_PESSOAS)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody));


        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.campos[0].nome").value("nome"));
        result.andExpect(jsonPath("$.campos[0].mensagem").value("Campo obrigatorio"));
    }

    @Test
    void createShouldReturnBadRequestWhenCPFAlreadyExists() throws Exception {

        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        pessoaInsertDTO.setCpf("899.317.520-92");

        String jsonBody = objectMapper.writeValueAsString(pessoaInsertDTO);

        ResultActions result =
                mockMvc.perform(post(URL_PESSOAS)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody));


        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.campos[0].nome").value("cpf"));
        result.andExpect(jsonPath("$.campos[0].mensagem").value("Ja existe uma pessoa cadastrada com este CPF"));
    }

    @Test
    void createShouldReturnAccessDeniedWhenLoggedAsUserWithNoPermission() throws Exception {

        String accessToken = tokenUtil.obtainAccessToken(mockMvc, visitorUsername, visitorPassword);

        String jsonBody = objectMapper.writeValueAsString(pessoaInsertDTO);

        ResultActions result =
                mockMvc.perform(post(URL_PESSOAS)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBody));


        result.andExpect(status().isForbidden());
    }
}