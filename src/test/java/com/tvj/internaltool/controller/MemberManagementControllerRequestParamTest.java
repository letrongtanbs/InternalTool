package com.tvj.internaltool.controller;

import static org.mockito.Mockito.when;
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

import com.tvj.internaltool.dummy.dto.res.MemberResDtoDataDummy;
import com.tvj.internaltool.service.MemberManagementService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberManagementControllerRequestParamTest {

    // @MockBean is to add mock objects to the Spring application context. The mock will replace any existing bean of the same type in the application context.
    // Important: f no bean of the same type is defined, a new one will be added!
    @MockBean
    private MemberManagementService memberManagementService;

    // Because WebSecurityConfig is not use for test, so PasswordEncoder have to be mocked to application context
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    private final static String currentUsername = "admin1";

    // ---------- /member-management/view-member START ----------

    @Test
    @WithMockUser(currentUsername)
    public void viewMember_usernameIsBlank() throws Exception {
        // Value from client
        String username = "";

        MemberResDtoDataDummy memberResDtoDataDummy = new MemberResDtoDataDummy();

        when(memberManagementService.viewMember(username)).thenReturn(memberResDtoDataDummy.getMember1());

        mockMvc.perform(get("/member-management/view-member").param("username", username)).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @WithMockUser(currentUsername)
    public void viewMember_usernameSizeEqualssMaximumLimit() throws Exception {
        // Value from client
        String username = "12345678901234567890";

        MemberResDtoDataDummy memberResDtoDataDummy = new MemberResDtoDataDummy();

        when(memberManagementService.viewMember(username)).thenReturn(memberResDtoDataDummy.getMember1());

        mockMvc.perform(get("/member-management/view-member").param("username", username)).andExpect(status().isOk()).andReturn();
    }

    @Test
    @WithMockUser(currentUsername)
    public void viewMember_usernameSizeExceedsMaximumLimit() throws Exception {
        // Value from client
        String username = "a12345678901234567890";

        MemberResDtoDataDummy memberResDtoDataDummy = new MemberResDtoDataDummy();

        when(memberManagementService.viewMember(username)).thenReturn(memberResDtoDataDummy.getMember1());

        mockMvc.perform(get("/member-management/view-member").param("username", username)).andExpect(status().isBadRequest()).andReturn();
    }

    // ---------- /member-management/view-member END ----------

}
