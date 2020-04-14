package com.tvj.internaltool.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.tvj.internaltool.service.TeamService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerRequestParamTest {

    // @MockBean is to add mock objects to the Spring application context. The mock will replace any existing bean of the same type in the application context.
    // Important: f no bean of the same type is defined, a new one will be added!
    @MockBean
    private TeamService teamService;

    // Because WebSecurityConfig is not use for test, so PasswordEncoder have to be mocked to application context
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    private final static String currentUsername = "admin1";

    // ---------- /team/list-by-department START ----------

    @Test
    @WithMockUser(currentUsername)
    public void getTeamByDepartment_departmentIdIsBlank() throws Exception {
        // Value from client
        String departmentId = "";

        mockMvc.perform(get("/team/list-by-department").param("departmentId", departmentId))
                .andExpect(status().isBadRequest()).andReturn();
    }
    
    @Test
    @WithMockUser(currentUsername)
    public void getTeamByDepartment_departmentIdSizeInOfBounds() throws Exception {
        // Value from client
        String departmentId = "1234567890123456789012345678901234567890";

        mockMvc.perform(get("/team/list-by-department").param("departmentId", departmentId))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @WithMockUser(currentUsername)
    public void getTeamByDepartment_departmentIdSizeOutOfBounds() throws Exception {
        // Value from client
        String departmentId = "12345678901234567890123456789012345678901";

        mockMvc.perform(get("/team/list-by-department").param("departmentId", departmentId))
                .andExpect(status().isBadRequest()).andReturn();
    }
    
    // ---------- /team/list-by-department END ----------

}
