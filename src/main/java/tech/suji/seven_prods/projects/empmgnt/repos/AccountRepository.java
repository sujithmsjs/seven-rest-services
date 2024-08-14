package tech.suji.seven_prods.projects.empmgnt.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.suji.seven_prods.projects.empmgnt.domain.Account;


public interface AccountRepository extends JpaRepository<Account, Long> {
}
