package org.lnu.teaching.web.application.design.deanery.controller.faculty;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lnu.teaching.web.application.design.deanery.dto.faculty.BaseFacultyDto;
import org.lnu.teaching.web.application.design.deanery.dto.faculty.FacultyDto;
import org.lnu.teaching.web.application.design.deanery.service.faculty.FacultyService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FacultyControllerTest {
    @Mock
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    private MockMvc mockMvc;

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(facultyController).build();
    }

    @Test
    public void create() throws Exception {
        // Given
        String expectedResponseBody = "{" +
                "\"name\":\"Прикладної математики та інформатики\"," +
                "\"website\":\"ami.lnu.edu.ua\"," +
                "\"email\":\"ami@lnu.edu.ua\"," +
                "\"phone\":\"274-01-80, 239-41-86\"," +
                "\"address\":\"вул. Університетська 1, м. Львів, 79000\"," +
                "\"info\":\"Найкращий факультет в ЛНУ!\"," +
                "\"id\":147}";

        // When
        FacultyDto facultyDto = new FacultyDto() {{
            setId(147L);
            setName("Прикладної математики та інформатики");
            setWebsite("ami.lnu.edu.ua");
            setEmail("ami@lnu.edu.ua");
            setPhone("274-01-80, 239-41-86");
            setAddress("вул. Університетська 1, м. Львів, 79000");
            setInfo("Найкращий факультет в ЛНУ!");
        }};

        when(facultyService.create(any(BaseFacultyDto.class))).thenReturn(facultyDto);

        String requestBody = "{\n" +
                "  \"name\": \"Прикладної математики та інформатики\",\n" +
                "  \"website\": \"ami.lnu.edu.ua\",\n" +
                "  \"email\": \"ami@lnu.edu.ua\",\n" +
                "  \"phone\": \"274-01-80, 239-41-86\",\n" +
                "  \"address\": \"вул. Університетська 1, м. Львів, 79000\",\n" +
                "  \"info\": \"Найкращий факультет в ЛНУ!\"\n" +
                "}";

        ResultActions resultActions = mockMvc.perform(post("/faculties")
                .characterEncoding("UTF-8")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // Then
        String actualResponseBody = resultActions
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));

        assertEquals(expectedResponseBody, actualResponseBody);

        verify(facultyService).create(any(BaseFacultyDto.class));
        verifyNoMoreInteractions(facultyService);
    }
}
