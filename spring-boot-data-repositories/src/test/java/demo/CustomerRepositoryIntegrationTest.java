/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package demo;

import static org.assertj.core.api.StrictAssertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link CustomerRepository}.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @since Step 2
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringApplicationConfiguration(classes = App.class)
public class CustomerRepositoryIntegrationTest {

	@Autowired CustomerRepository repository;

	/**
	 * @since Step 2.1
	 */
	@Test
	public void findsCustomerById() {

		Customer customer = repository.findOne(1L);

		assertThat(customer).isNotNull();
		assertThat(customer.getFirstname()).isEqualTo("Dietmar");
		assertThat(customer.getLastname()).isEqualTo("Friedrich");
	}

	/**
	 * @since Step 2.2
	 */
	@Test
	public void savesNewCustomer() {

		Customer customer = new Customer("Michael", "Muth");
		Customer result = repository.save(customer);

		assertThat(result).isNotNull();
		assertThat(result.getId()).isNotNull();
		assertThat(result.getFirstname()).isEqualTo(customer.getFirstname());
		assertThat(result.getLastname()).isEqualTo(customer.getLastname());
	}

	/**
	 * @since Step 2.3
	 */
	@Test
	public void savesExistingCustomer() {

		Customer dietmar = repository.findOne(1L);
		EmailAddress newEmail = new EmailAddress("dietmar@foobar.com");
		dietmar.setEmailAddress(newEmail);
		repository.save(dietmar);

		Customer result = repository.findOne(1L);

		assertThat(result).isNotNull();
		assertThat(result.getId()).isNotNull();
		assertThat(result.getFirstname()).isEqualTo(dietmar.getFirstname());
		assertThat(result.getEmailAddress()).isEqualTo(newEmail);
	}

	/**
	 * @since Step 2.4
	 */
	@Test
	public void findsCustomersByEmailAddress() {

		Customer result = repository.findByEmailAddress(new EmailAddress("ralf@example.org"));

		assertThat(result).isNotNull();
		assertThat(result.getFirstname()).isEqualTo("Ralf");
		assertThat(result.getLastname()).isEqualTo("Steinbach");
	}
}
