package com.example.quizizz.service;

import com.example.quizizz.model.*;
import com.example.quizizz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtSpring3Application implements CommandLineRunner {
    private final RoleRepository roleRepository;
    final TypeQuestionRepository typeQuestionRepository;
    final LevelQuestionRepository levelQuestionRepository;
    final CategoryQuizRepository categoryQuizRepository;
    final LevelQuizRepository levelQuizRepository;

    @Autowired
    public JwtSpring3Application(RoleRepository roleRepository, TypeQuestionRepository typeQuestionRepository, LevelQuestionRepository levelQuestionRepository, CategoryQuizRepository categoryQuizRepository, LevelQuizRepository levelQuizRepository) {
        this.roleRepository = roleRepository;
        this.typeQuestionRepository = typeQuestionRepository;
        this.levelQuestionRepository = levelQuestionRepository;
        this.categoryQuizRepository = categoryQuizRepository;
        this.levelQuizRepository = levelQuizRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(JwtSpring3Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role admin = new Role("ADMIN");
            Role teacher = new Role("TEACHER");
            Role student = new Role("STUDENT");
            roleRepository.save(admin);
            roleRepository.save(teacher);
            roleRepository.save(student);
        }
        if (typeQuestionRepository.count() == 0) {
            TypeQuestion typeQuestion1 = new TypeQuestion("Đúng sai");
            TypeQuestion typeQuestion2 = new TypeQuestion("Một đáp án");
            TypeQuestion typeQuestion3 = new TypeQuestion("Nhiều đáp án");
            typeQuestionRepository.save(typeQuestion1);
            typeQuestionRepository.save(typeQuestion2);
            typeQuestionRepository.save(typeQuestion3);
        }
        if (levelQuestionRepository.count() == 0) {
            LevelQuestion levelQuestion1 = new LevelQuestion("Dễ");
            LevelQuestion levelQuestion2 = new LevelQuestion("Trung Bình");
            LevelQuestion levelQuestion3 = new LevelQuestion("Khó");
            levelQuestionRepository.save(levelQuestion1);
            levelQuestionRepository.save(levelQuestion2);
            levelQuestionRepository.save(levelQuestion3);
        }
        if (categoryQuizRepository.count() == 0) {
            CategoryQuiz categoryQuizRepository1 = new CategoryQuiz("Toán", "Các câu hỏi về Toán");
            CategoryQuiz categoryQuizRepository2 = new CategoryQuiz("Lý", "Các câu hỏi về Lý");
            CategoryQuiz categoryQuizRepository3 = new CategoryQuiz("Hóa", "Các câu hỏi về Hóa");
            CategoryQuiz categoryQuizRepository4 = new CategoryQuiz("Sinh", "Các câu hỏi về Sinh");
            CategoryQuiz categoryQuizRepository5 = new CategoryQuiz("Văn", "Các câu hỏi về Văn");
            CategoryQuiz categoryQuizRepository6 = new CategoryQuiz("Sử", "Các câu hỏi về Sử");
            CategoryQuiz categoryQuizRepository7 = new CategoryQuiz("Địa", "Các câu hỏi về Địa");
            categoryQuizRepository.save(categoryQuizRepository1);
            categoryQuizRepository.save(categoryQuizRepository2);
            categoryQuizRepository.save(categoryQuizRepository3);
            categoryQuizRepository.save(categoryQuizRepository4);
            categoryQuizRepository.save(categoryQuizRepository5);
            categoryQuizRepository.save(categoryQuizRepository6);
            categoryQuizRepository.save(categoryQuizRepository7);
        }
        if (levelQuizRepository.count() == 0) {
            LevelQuiz levelQuiz1 = new LevelQuiz("Dễ");
            LevelQuiz levelQuiz2 = new LevelQuiz("Trung Bình");
            LevelQuiz levelQuiz3 = new LevelQuiz("Khó");
            levelQuizRepository.save(levelQuiz1);
            levelQuizRepository.save(levelQuiz2);
            levelQuizRepository.save(levelQuiz3);
        }
    }
}

