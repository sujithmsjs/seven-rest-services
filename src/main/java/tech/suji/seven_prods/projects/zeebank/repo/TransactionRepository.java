package tech.suji.seven_prods.projects.zeebank.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.suji.seven_prods.projects.zeebank.entity.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
