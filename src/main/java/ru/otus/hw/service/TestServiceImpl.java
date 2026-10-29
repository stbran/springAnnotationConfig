package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        int questionNumber = 1;
        for (var question: questions) {
            askQuestion(question, questionNumber++);
            String studentAnswer = getAnswer();
            var isAnswerValid = isAnswerValid(question, studentAnswer);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private void askQuestion(Question question, int questionNumber) {
        ioService.printFormattedLine("%d. %s", questionNumber, question.text());

        int j = 1;
        for (Answer answer : question.answers()) {
            ioService.printFormattedLine("\t%d) %s", j, answer.text());
            j++;
        }
    }

    private String getAnswer() {
        ioService.printLine("");
        ioService.printFormattedLine("Please print number your answer%n");
        return ioService.readString();
    }

    private boolean isAnswerValid(Question question, String answer) {
        int answerNumber;
        try {
            answerNumber = Integer.parseInt(answer);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Incorrect answer format\n" + e);
        }
        answerNumber--;
        return question.answers().get(answerNumber).isCorrect();
    }
}
