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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.assertj.core.data.Percentage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link ProductRepository}.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @since Step 6
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringApplicationConfiguration(classes = App.class)
public class ProductRepositoryIntegrationTest {

	@Autowired ProductRepository repository;
	@Autowired EntityManager em;

	/**
	 * @since Step 6.1
	 */
	@Test
	public void findsAllProducts() {

		List<Product> products = repository.findAll();

		assertThat(products).asList().hasSize(3);
	}

	/**
	 * @since Step 6.2
	 */
	@Test
	public void findsAllAppleProductPaged() {

		Page<Product> products = repository.findByDescriptionContaining("Apple", new PageRequest(0, 1));

		assertThat(products.isFirst()).isTrue();
		assertThat(products.hasNext()).isTrue();

		assertThat(products).filteredOn("name", "iPad").isNotEmpty();
		assertThat(products).filteredOn("name", "Dock").isEmpty();
	}

	/**
	 * @since Step 6.3
	 */
	@Test
	public void returnsOptionalEmptyForNonExistingProduct() {

		Optional<Product> result = repository.findOne(4711L);

		assertThat(result).isEmpty();
	}

	/**
	 * @since Step 7.1
	 */
	@Test
	public void executesManuallyDeclaredQuery() {

		List<Product> products = repository.findByAttributeAndValue("connector", "plug");

		assertThat(products).filteredOn("name", "Dock").isNotEmpty();
	}

	/**
	 * @since Step 9
	 */
	@Test
	public void executesCustomlyImplementedMethod() {

		Product macbook = repository.findOne(2L).get();
		
		em.detach(macbook); // remove item from JPA cache

		repository.raisePricesForWinterSaleBy(1.2);

		Product result = repository.findOne(2L).get();

		assertThat(result.getPrice()).isCloseTo(macbook.getPrice().multiply(new BigDecimal("1.2")),Percentage.withPercentage(1));
	}
}
