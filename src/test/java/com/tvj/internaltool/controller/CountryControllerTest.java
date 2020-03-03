package com.tvj.internaltool.controller;

import com.tvj.internaltool.service.CountryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryService countryService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    // ---------- /country/list START ----------

    @Test
    public void getAllCountry() {



//        UserLoginReqDto userLoginReqDto = new UserLoginReqDto();
//        userLoginReqDto.setUsername("admin1");
//        userLoginReqDto.setPassword("12345678");
//
//        UserLoginResDtoDataDummy adminResDto = new UserLoginResDtoDataDummy();
//        when(userService.processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword())).thenReturn(adminResDto.getAdminUserResDto1());
//
//        MvcResult result = mockMvc.perform(post("/user/login")
//                .content(new ObjectMapper().writeValueAsString(userLoginReqDto))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String jsonString = result.getResponse().getContentAsString();
//        UserLoginResDto userLoginResDto = new Gson().fromJson(jsonString, UserLoginResDto.class);
//
//        verify(userService, times(1)).processLogin(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
//        assertEquals(userLoginResDto.getToken(), adminResDto.getAdminUserResDto1().getToken());

    }

    // ---------- /country/list END ----------

}