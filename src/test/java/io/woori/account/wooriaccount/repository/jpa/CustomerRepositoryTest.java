package io.woori.account.wooriaccount.repository.jpa;



import io.woori.account.wooriaccount.exception.CustomException;
import io.woori.account.wooriaccount.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.dto.customer.SignUpRequestDTO;
import io.woori.account.wooriaccount.dummy.DummyCustomer;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


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
		
		assertThat(findByIdCust.getCustomerId()).isEqualTo(id);
	}
	
	
	@DisplayName("메서드 명명 규칙을 사용한 id값 찾아오는 메서드 테스트 - 실패")
	@Test
	public void testFindById_fail() {
		
		Long id = customer.getCustomerId();
		Optional<Customer> op = repository.findById(3L);


		assertThat(op).isEmpty();
	}


	@DisplayName("pk로 해당 회원 삭제 테스트 - 성공")
	@Test
	public void testDeleteById_success() {

		Long id = customer.getCustomerId();
		repository.deleteById(id);

		assertThat(repository.findById(id)).isEmpty();

	}

	@DisplayName("pk로 해당 회원 삭제 테스트 - 실패")
	@Test
	public void testDeleteById_fail() {

		Long id = 3L; // 삭제한 ID
		assertThatThrownBy(() -> repository.deleteById(id)).isExactlyInstanceOf(EmptyResultDataAccessException.class);
	}

	@DisplayName("email 주소로 해당 회원 찾아오는 테스트")
	@Test
	public void testFindByCustomerEmail() {

		String email = "test@test.com";

		Optional<Customer> op = repository.findByCustomerEmail(email);

		assertThat(op.get()).isNotNull();
		Customer cust = op.get();
		assertThat(cust.getCustomerEmail()).isEqualTo(email);
	}

	@DisplayName("email 주소로 해당 회원 찾아오는 테스트 - 실패")
	@Test
	public void testFindByCustomerEmail_fail() {

		String email = "test122@test.com";

		Optional<Customer> op = repository.findByCustomerEmail(email);

		assertThat(op.isPresent()).isFalse();

	}



	@DisplayName("회원 저장 테스트")
	@Test
	public void testSave() {
		String name = customer.getCustomerName();

		Customer save = repository.save(customer);

		assertThat(save.getCustomerName()).isEqualTo(name);

	}

}
