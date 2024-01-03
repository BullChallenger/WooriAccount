package io.woori.account.wooriaccount.repository.jpa;

import io.woori.account.wooriaccount.domain.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {
    private Account account;

    @Autowired
    private AccountRepository repository;

    @Autowired
    TestEntityManager testEntityManager;


    @BeforeEach
    void setUp() {


    }

    @DisplayName("")
    @Test
    public void testFindById(){


    }

    @DisplayName("")
    @Test
    public void testDeleteById(){


    }

    @DisplayName("")
    @Test
    public void testSave(){


    }


    @DisplayName("")
    @Test
    public void testFiindByAccountNumber(){


    }




}