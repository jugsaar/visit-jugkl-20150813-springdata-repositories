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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration test to show customized transaction configuration in {@link CustomerRepository}.
 * 
 * @since Step 5.2
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@DirtiesContext
public class CustomerRepositoryTransactionReconfigurationIntegrationTest {

	@Autowired CustomerRepository repository;

	/**
	 * @since Step 5.2
	 */
	@Test
	public void executesRedeclaredMethodWithCustomTransactionConfiguration() {
	
		Customer customer = new Customer("Anne", "Laurentius");
		Customer result = repository.save(customer);

		assertThat(result).isNotNull();
		assertThat(result.getId()).isNotNull();
		assertThat(result.getFirstname()).isEqualTo(customer.getFirstname());
		assertThat(result.getLastname()).isEqualTo(customer.getLastname());
	}
}
