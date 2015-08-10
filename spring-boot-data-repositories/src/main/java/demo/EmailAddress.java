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

import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.Assert;

/**
 * A value object abstraction of an email address.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@Embeddable
public class EmailAddress {

	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

	@Column(name = "email") private String value;

	/**
	 * Creates a new {@link EmailAddress} from the given string source.
	 * 
	 * @param emailAddress must not be {@literal null} or empty.
	 */
	public EmailAddress(String emailAddress) {
		Assert.isTrue(isValid(emailAddress), "Invalid email address!");
		this.value = emailAddress;
	}

	@SuppressWarnings("unused")
	private EmailAddress() {
		// just for JPA
	}

	/**
	 * Returns whether the given {@link String} is a valid {@link EmailAddress} which means you can safely instantiate the
	 * class.
	 * 
	 * @param candidate
	 * @return
	 */
	public static boolean isValid(String candidate) {
		return candidate == null ? false : PATTERN.matcher(candidate).matches();
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EmailAddress)) {
			return false;
		}

		EmailAddress that = (EmailAddress) obj;
		return this.value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
