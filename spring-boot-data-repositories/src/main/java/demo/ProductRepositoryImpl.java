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

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

/**
 * Custom implementation class to implement {@link ProductRepositoryCustom}. Using the Querydsl repository base class.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
public class ProductRepositoryImpl extends QueryDslRepositorySupport implements ProductRepositoryCustom {

	private static final QProduct product = QProduct.product;

	/**
	 * Creates a new instance of {@link ProductRepositoryImpl}.
	 */
	public ProductRepositoryImpl() {
		super(Product.class);
	}

	/* (non-Javadoc)
	 * @see demo.ProductRepositoryCustom#raisePricesForWinterSaleBy(double)
	 */
	@Override
	public void raisePricesForWinterSaleBy(double factor) {

		// You can use whatever you want here, JdbcTemplate, EntityManager, etc.
		
		update(product).set(product.price, product.price.multiply(factor)).execute();
	}
}
