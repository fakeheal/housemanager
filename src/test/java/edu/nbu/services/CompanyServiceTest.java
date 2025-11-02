package edu.nbu.services;

import edu.nbu.repositories.CompanyRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@MicronautTest
class CompanyServiceTest {

    @Inject
    CompanyService companyService;

    @Inject
    CompanyRepository companyRepository;

    @Test
    void create() {
        String name = "Test Company";
        String address = "123 Test St, Test City, TC 12345";
        var company = companyService.create(name, address);
        assertNotNull(company);
        assertEquals(name, company.getName());
        assertEquals(address, company.getAddress());
        assertNotNull(company.getId());
        assertEquals(1, companyRepository.count());
    }

    @Test
    void create_throwsException_whenCompanyAlreadyExists() {
        String name = "Test Company";
        String address = "123 Test St, Test City, TC 12345";
        companyService.create(name, address);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            companyService.create("Another Company", "456 Another St, Another City, AC 67890");
        });

        String expectedMessage = "A company is already created.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void findById() {
        String name = "Test Company";
        String address = "123 Test St, Test City, TC 12345";
        var createdCompany = companyService.create(name, address);

        var foundCompany = companyService.findById();
        assertNotNull(foundCompany);
        assertEquals(createdCompany.getId(), foundCompany.getId());
        assertEquals(createdCompany.getName(), foundCompany.getName());
        assertEquals(createdCompany.getAddress(), foundCompany.getAddress());
    }
}