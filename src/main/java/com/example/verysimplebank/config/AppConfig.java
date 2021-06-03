package com.example.verysimplebank.config;

import com.example.verysimplebank.exchangecollector.Collector;
import com.example.verysimplebank.model.Account;
import com.example.verysimplebank.model.Currency;
import com.example.verysimplebank.model.CustomUser;
import com.example.verysimplebank.model.UserRole;
import com.example.verysimplebank.services.AccountService;
import com.example.verysimplebank.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1000)
public class AppConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    public static final String ADMIN = "admin";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner demo(final UserService userService,
                                  final AccountService accountService,
                                  final Collector collector,
                                  final PasswordEncoder encoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                CustomUser customUser1 = new CustomUser(ADMIN,
                        encoder.encode("1234"),
                        UserRole.ADMIN, false);
                //Long counter = accountService.countAccounts();
                Account account = new Account(10000000L, 1000, Currency.UAH );
                //accountService.addAccount(account);
                //Long counter1 = accountService.countAccounts();
                Account account1 = new Account((long) (10000000 - 1), 1000, Currency.USD );
                //accountService.addAccount(account1);
                //Long counter2 = accountService.countAccounts();
                Account account2 = new Account((long) (10000000 - 2), 1000, Currency.EUR);
                //accountService.addAccount(account2);
                customUser1.addAccount(account);
                customUser1.addAccount(account1);
                customUser1.addAccount(account2);
                userService.addUser(customUser1);


                CustomUser customUser2 = new CustomUser("user",
                        encoder.encode("3333"),
                        UserRole.USER, false);
                //Long counter11 = accountService.countAccounts();
                Account account11 = new Account((long) (10000000 - 3), 1000, Currency.UAH );
                //accountService.addAccount(account11);
                //Long counter22 = accountService.countAccounts();
                Account account22 = new Account((long) (10000000 - 4), 1000, Currency.USD );
                //accountService.addAccount(account22);
                //Long counter33 = accountService.countAccounts();
                Account account33 = new Account((long) (10000000 - 5), 1000, Currency.EUR);
                //accountService.addAccount(account33);
                customUser2.addAccount(account11);
                customUser2.addAccount(account22);
                customUser2.addAccount(account33);
                userService.addUser(customUser2);

                collector.collect();
            }
        };
    }
}
