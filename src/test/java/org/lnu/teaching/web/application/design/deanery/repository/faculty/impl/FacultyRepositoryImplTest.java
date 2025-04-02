package org.lnu.teaching.web.application.design.deanery.repository.faculty.impl;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType;
import org.junit.jupiter.api.Test;
import org.lnu.teaching.web.application.design.deanery.entity.faculty.FacultyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Sql("/db/schema.sql")
@AutoConfigureEmbeddedDatabase(type = DatabaseType.POSTGRES)
public class FacultyRepositoryImplTest {

    @Autowired
    private FacultyRepositoryImpl facultyRepository;

    @Test
    public void createExpectedFacultyIsCreated() {
        // Given
        FacultyEntity expectedEntity = new FacultyEntity() {
            {
                setName("Прикладної математики та інформатики");
                setWebsite("ami.lnu.edu.ua");
                setEmail("ami@lnu.edu.ua");
                setPhone("274-01-80, 239-41-86");
                setAddress("вул. Університетська 1, м. Львів, 79000");
                setInfo("Найкращий факультет в ЛНУ!");
            }
        };

        // When
        FacultyEntity entity = new FacultyEntity() {{
            setName("Прикладної математики та інформатики");
            setWebsite("ami.lnu.edu.ua");
            setEmail("ami@lnu.edu.ua");
            setPhone("274-01-80, 239-41-86");
            setAddress("вул. Університетська 1, м. Львів, 79000");
            setInfo("Найкращий факультет в ЛНУ!");
        }};

        FacultyEntity actualEntity = facultyRepository.create(entity);

        // Then
        assertNotNull(actualEntity.getId());

        expectedEntity.setId(actualEntity.getId());
        assertEquals(expectedEntity, actualEntity);
    }
}
