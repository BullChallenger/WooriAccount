package io.woori.account.wooriaccount.account.repository;

import io.woori.account.wooriaccount.account.domain.entity.Account;
import org.springframework.data.repository.Repository;

/* JpaRepository, CrudRepository 대신 그냥 Repository를 상속 받아 사용합니다.
* 필요한 메서드만 정의해서 사용하기 위함이고, 인터페이스로 제공하는 메서드가 적어지기 때문에 직접 선언한 메서드에 대해서만 외부 노출을 허용합니다.
* CommonRepository 형식의 네이밍을 가진 인터페이스를 작성해 기능을 선언 후 해당 인터페이스 상속하면 해당 미완성 구현 메서드만을 사용할 수 있게 됩니다.
* */
public interface AccountRepository extends Repository<Account, Long>, CommonAccountRepository {


	
}
