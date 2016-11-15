package br.com.softcare.repositories;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.softcare.entities.User;
import br.com.softcare.enums.Availability;
import br.com.softcare.enums.Period;
import br.com.softcare.enums.Profile;

public class UserSpecs {
	
	public static Specification<User> isCareGiver(){
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.isMember(Profile.CAREGIVER, root.get("profile"));
			}
		};
	}
	
	public static Specification<User> hasAvailability(Availability availability){
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.isMember(availability,root.get("availability"));
			}
		};
	}
	
	public static Specification<User> hasPeriod(Period period){
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.isMember(period,root.get("period"));
			}
		};
	}
	
	public static Specification<User> withZipCode(String zipcode){
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.like(root.<String>get("zipCode"),"%".concat(zipcode.trim()).concat("%"));
			}
		};
	}
	
	public static Specification<User> withDistrict(String district){
		return new Specification<User>(){

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.like(root.<String>get("district"),"%".concat(district.trim()).concat("%"));
			}
		};
	}

	public static Specification<User> withCity(String city){
		return new Specification<User>(){

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.like(root.<String>get("city"),"%".concat(city.trim()).concat("%"));
			}
		};
	}
	public static Specification<User> withStreet(String street){
		return new Specification<User>(){

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.like(root.<String>get("street"),"%".concat(street.trim()).concat("%"));
			}
		};
	}
	
	public static Specification<User> withState(String state){
		return new Specification<User>(){

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.like(root.<String>get("state"),"%".concat(state.trim()).concat("%"));
			}
		};
	}
	
	public static Specification<User> withEmail(String email){
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.like(root.<String>get("email"),"%".concat(email.trim()).concat("%"));
			}
		};
	}
	
	public static Specification<User> withContact(String contact){
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.like(root.<String>get("contact"),"%".concat(contact.trim()).concat("%"));
			}
		};
	}
	
}
