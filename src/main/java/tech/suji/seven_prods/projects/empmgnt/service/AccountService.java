package tech.suji.seven_prods.projects.empmgnt.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tech.suji.seven_prods.projects.empmgnt.domain.Account;
import tech.suji.seven_prods.projects.empmgnt.domain.Employee;
import tech.suji.seven_prods.projects.empmgnt.model.AccountDTO;
import tech.suji.seven_prods.projects.empmgnt.repos.AccountRepository;
import tech.suji.seven_prods.projects.empmgnt.repos.EmployeeRepository;
import tech.suji.seven_prods.util.NotFoundException;


@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final EmployeeRepository employeeRepository;

    public AccountService(final AccountRepository accountRepository,
            final EmployeeRepository employeeRepository) {
        this.accountRepository = accountRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<AccountDTO> findAll() {
        final List<Account> accounts = accountRepository.findAll(Sort.by("id"));
        return accounts.stream()
                .map(account -> mapToDTO(account, new AccountDTO()))
                .toList();
    }

    public AccountDTO get(final Long id) {
        return accountRepository.findById(id)
                .map(account -> mapToDTO(account, new AccountDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AccountDTO accountDTO) {
        final Account account = new Account();
        mapToEntity(accountDTO, account);
        return accountRepository.save(account).getId();
    }

    public void update(final Long id, final AccountDTO accountDTO) {
        final Account account = accountRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(accountDTO, account);
        accountRepository.save(account);
    }

    public void delete(final Long id) {
        accountRepository.deleteById(id);
    }

    private AccountDTO mapToDTO(final Account account, final AccountDTO accountDTO) {
        accountDTO.setId(account.getId());
        accountDTO.setBank(account.getBank());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setActive(account.getActive());
        accountDTO.setEmployee(account.getEmployee() == null ? null : account.getEmployee().getId());
        return accountDTO;
    }

    private Account mapToEntity(final AccountDTO accountDTO, final Account account) {
        account.setBank(accountDTO.getBank());
        account.setBalance(accountDTO.getBalance());
        account.setActive(accountDTO.getActive());
        final Employee employee = accountDTO.getEmployee() == null ? null : employeeRepository.findById(accountDTO.getEmployee())
                .orElseThrow(() -> new NotFoundException("employee not found"));
        account.setEmployee(employee);
        return account;
    }

}
