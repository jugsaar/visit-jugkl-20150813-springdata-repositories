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

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository interface to access {@link Product}s.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
public interface ProductRepository extends ReadOnlyRepository<Product>, ProductRepositoryCustom {

	/**
	 * Returns a {@link Page} of {@link Product}s having a description which contains the given snippet.
	 * 
	 * @param description
	 * @param pageable
	 * @return
	 */
	Page<Product> findByDescriptionContaining(String description, Pageable pageable);

/**
	 * Returns all {@link Product}s having the given attribute.
	 * 
	 * Note: The query can be specified in the following ways:
	 * <ol>
	 * <li>via the {@link Query} annotation</li>
	 * <li>via the <code>META-INF/jpa-named-queries.properties</code> file</li>
	 * <li>via a {@code named-query} element in the <code>META-INF/orm.xml</code> file</li>
	 * </ol>
	 * 
	 * @param attribute
	 * @return
	 */
	@Query("select p from Product p where p.attributes[?1] = ?2")
	List<Product> findByAttributeAndValue(String attribute, String value);
}
