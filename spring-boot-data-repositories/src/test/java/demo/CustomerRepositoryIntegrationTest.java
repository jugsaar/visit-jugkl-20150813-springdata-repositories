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

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.types.expr.BooleanExpression;

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

	/**
	 * @since Step 3.1
	 */
	@Test
	public void findsAllCustomers() {

		List<Customer> customers = repository.findAll();

		assertThat(customers).asList().hasSize(3);
	}

	/**
	 * @since Step 3.2
	 */
	@Test
	public void deletesCustomer() {

		repository.delete(1L);

		assertThat(repository.findOne(1L)).isNull();
	}

	/**
	 * @since Step 4.1
	 */
	@Test
	public void accessesCustomersPageByPage() {

		Page<Customer> result = repository.findAll(new PageRequest(/* page:*/ 1, /* page size:*/ 1));

		assertThat(result).isNotNull();
		assertThat(result.isFirst()).isFalse();
		assertThat(result.isLast()).isFalse();
		assertThat(result.getNumberOfElements()).isEqualTo(1);
	}

	/**
	 * @since Step 4.2
	 */
	@Test
	public void browseThrouhgCustomersSortedByLastnameDesc() {

		Iterable<Customer> result = repository.findAll(new Sort(Direction.DESC, "lastname"));

		assertThat(result).asList().extracting("lastname").containsExactly("Steinbach", "Friedrich", "Daub");
	}
	
	/**
	 * @since Step 8
	 */
	@Test
	public void executesQuerydslPredicate() {

		Customer dietmar = repository.findByEmailAddress("dietmar@example.org");
		Customer ralf = repository.findByEmailAddress("ralf@example.org");

		QCustomer customer = QCustomer.customer;

		BooleanExpression firstnameStartsWithDie = customer.firstname.startsWith("Die");
		BooleanExpression lastnameContainsBach = customer.lastname.contains("bach");

		Iterable<Customer> result = repository.findAll(firstnameStartsWithDie.or(lastnameContainsBach));

		assertThat(result).asList().hasSize(2);
		assertThat(result).asList().containsExactly(dietmar, ralf);
	}
}
