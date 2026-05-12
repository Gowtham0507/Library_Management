package com.libraflow.config;

import com.libraflow.entity.Book;
import com.libraflow.entity.Member;
import com.libraflow.repository.BookRepository;
import com.libraflow.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public DataInitializer(BookRepository bookRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0) {
            Book b1 = new Book(); b1.setTitle("Clean Code"); b1.setAuthor("Robert C. Martin"); b1.setAvailability(true);
            Book b2 = new Book(); b2.setTitle("The Pragmatic Programmer"); b2.setAuthor("Andrew Hunt"); b2.setAvailability(true);
            Book b3 = new Book(); b3.setTitle("Design Patterns"); b3.setAuthor("Erich Gamma"); b3.setAvailability(true);
            Book b4 = new Book(); b4.setTitle("Refactoring"); b4.setAuthor("Martin Fowler"); b4.setAvailability(true);
            Book b5 = new Book(); b5.setTitle("Introduction to Algorithms"); b5.setAuthor("Thomas H. Cormen"); b5.setAvailability(true);
            Book b6 = new Book(); b6.setTitle("You Don't Know JS"); b6.setAuthor("Kyle Simpson"); b6.setAvailability(true);
            Book b7 = new Book(); b7.setTitle("The Mythical Man-Month"); b7.setAuthor("Frederick Brooks"); b7.setAvailability(true);

            bookRepository.saveAll(Arrays.asList(b1, b2, b3, b4, b5, b6, b7));
            System.out.println("✅ Dummy books loaded.");
        }

        if (memberRepository.count() == 0) {
            Member m1 = new Member(); m1.setName("John Doe"); m1.setEmail("john@example.com");
            Member m2 = new Member(); m2.setName("Jane Smith"); m2.setEmail("jane@example.com");
            Member m3 = new Member(); m3.setName("Alice Johnson"); m3.setEmail("alice@example.com");
            Member m4 = new Member(); m4.setName("Bob Williams"); m4.setEmail("bob@example.com");

            memberRepository.saveAll(Arrays.asList(m1, m2, m3, m4));
            System.out.println("✅ Dummy members loaded.");
        }
    }
}
