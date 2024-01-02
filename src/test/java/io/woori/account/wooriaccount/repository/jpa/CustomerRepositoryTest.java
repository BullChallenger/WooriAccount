package io.woori.account.wooriaccount.repository.jpa;



import io.woori.account.wooriaccount.exception.CustomException;
import io.woori.account.wooriaccount.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.dto.user.SignUpRequestDTO;
import io.woori.account.wooriaccount.dummy.DummyCustomer;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {
	
	private Customer customer;
	
	@Autowired
	private CustomerRepository repository; 
	
	@Autowired
	TestEntityManager testEntityManager;
	 
	@BeforeEach
	public void setUp(){
		
		SignUpRequestDTO dto = new SignUpRequestDTO("test name","01011111111" , "test@test.com", "test pw");
		customer = DummyCustomer.dummy(dto);
		testEntityManager.persist(customer);
		
	}

	
	@DisplayName("메서드 명명 규칙을 사용한 id값 찾아오는 메서드 테스트 - 성공")
	@Test
	public void testFindById_success() {
		
		
		Long id = customer.getCustomerId();
		
		Customer findByIdCust = repository.findById(id).orElseThrow(
				() -> new CustomException(ErrorCode.INVALID_Customer_Login)
		);
		
		Assertions.assertThat(findByIdCust.getCustomerId()).isEqualTo(id);
	}
	
	
	@DisplayName("메서드 명명 규칙을 사용한 id값 찾아오는 메서드 테스트 - 실패")
	@Test
	public void testFindById_fail() {
				
		
		Long id = customer.getCustomerId();
		Customer findByIdCust = repository.findById(3L).orElseThrow(
				() -> new CustomException(ErrorCode.INVALID_Customer_Login)
		);
		
		Assertions.assertThat(findByIdCust.getCustomerId()).isNotEqualTo(id);
	}

	@Test
	public void testDeleteById() {
		
	}

	@Test
	public void testFindByCustomerEmail() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testSave() {
		throw new RuntimeException("not yet implemented");
	}

}
