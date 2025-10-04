package edu.nbu.services;

import edu.nbu.entities.Company;
import edu.nbu.exceptions.CompanyNotSetupException;
import edu.nbu.exceptions.crud.CannotCreateResourceException;
import edu.nbu.repositories.CompanyRepository;
import jakarta.inject.Inject;

public class CompanyService {
    @Inject
    CompanyRepository companyRepository;


    public Company createCompany(String name, String address) {
        if (isCompanyAlreadyCreated()) {
            throw new CannotCreateResourceException("A company is already created.");
        }

        Company company = new Company();
        company.setName(name);
        company.setAddress(address);

        companyRepository.save(company);

        return company;
    }

    public Company getCompany() {
        if (!isCompanyAlreadyCreated()) {
            throw new CompanyNotSetupException();
        }

        return companyRepository.findAll().getFirst();
    }


    private boolean isCompanyAlreadyCreated() {
        return companyRepository.count() > 0;
    }
}
