package com.kamilbartek.financial_system;

import com.kamilbartek.financial_system.repository.UserRepository;
import com.kamilbartek.financial_system.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DataJpaTest
class FinancialSystemApplicationTests {

    private MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeTestClass
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }
//
//    @Test
//    public void  when_create_client_then_he_has_to_be_found_in_db() throws Exception {
//        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//
//        String uri = "/createClient";
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        assertTrue(true, content);
//    }

    @Test
    public void  when_create_client_then_he_has_to_be_found_in_db() throws Exception {
        Random rd = new Random(Calendar.getInstance().getTimeInMillis());

        String username = "test" + String.valueOf(rd.nextInt(300));
        userService.createUser("test", "test", "test", "test", "test", "test", "test", "test", Calendar.getInstance().getTime(), "test ");
        assertEquals(true, userRepository.findByUsername(username).isPresent());
    }

    @Test
    public void when_creating_client_account_is_also_created(){

    }


    @Test
    public void when_send_transfer_differences_on_bilance_accounts_has_to_be_equal(){


    }

    @Test
    public void when_transfer_user_and_reciever_has_to_have_this_transfer_as_last(){

    }

    @Test
    public void when_send_transfer_with_specific_amount_users_bilance_should_be_different_by_amount_send(){

    }
    @Test
    public void when_send_transfer_to_yourself() {

    }
    @Test
    public void when_send_transfer_to_account_that_doesnt_exists(){

    }
    @Test
    public void when_send_transfer_greater_than_account_balance(){


    }
    @Test
    public void when_1000_transfers_to_and_recieve_are_made(){

    }
    @Test
    public void when_transfer_with_another_currency(){

    }



}
