package com.okan.yildirim.debeziumwithpostgres.repository;

import com.okan.yildirim.debeziumwithpostgres.domain.Customer;
import com.okan.yildirim.debeziumwithpostgres.domain.builder.CustomerBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void it_should_save_customer_and_find_by_id() {
        //given
        Date date = new Date();
        Customer customer = CustomerBuilder.aCustomer()
                .name("okan")
                .surname("yildirim")
                .birthDay(date)
                .email("okan@email.com")
                .build();
        //when
        Customer savedCustomer = customerRepository.save(customer);
        testEntityManager.flush();
        testEntityManager.clear();
        Optional<Customer> foundCustomer = customerRepository.findById(savedCustomer.getId());
        //then

        assertThat(foundCustomer.isPresent()).isTrue();
        Customer fetchedCustomer = foundCustomer.get();
        assertThat(fetchedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(fetchedCustomer.getSurname()).isEqualTo(customer.getSurname());
        assertThat(fetchedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(fetchedCustomer.getId()).isEqualTo(savedCustomer.getId());
        assertThat(fetchedCustomer.getBirthday()).isEqualToIgnoringMillis(date);
    }
}