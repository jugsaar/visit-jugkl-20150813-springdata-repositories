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

/**
 * Interface for data access code to be implemented manually.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
interface ProductRepositoryCustom {

	/**
	 * Raises {@link Product}s prices by the given <code>factor</code>.
	 * 
	 * @param price
	 */
	void raisePricesForWinterSaleBy(double factor);
}
