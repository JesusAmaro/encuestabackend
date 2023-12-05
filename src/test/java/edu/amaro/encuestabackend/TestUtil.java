package edu.amaro.encuestabackend;

import java.util.ArrayList;
import java.util.Random;

import edu.amaro.encuestabackend.models.request.AnswerCreationRequestModel;
import edu.amaro.encuestabackend.models.request.PollCreationRequestModel;
import edu.amaro.encuestabackend.models.request.QuestionCreationRequestModel;
import edu.amaro.encuestabackend.models.request.UserRegisterRequestModel;

public class TestUtil {
    
    public static  UserRegisterRequestModel createValidUser(){
        UserRegisterRequestModel user = new UserRegisterRequestModel();
        user.setEmail(generateRandomString(16) + "@gmail.com");
        user.setName(generateRandomString(8));
        user.setPassword(generateRandomString(8));;
        return user;
    }

    public static PollCreationRequestModel createValidPoll(){
        ArrayList<AnswerCreationRequestModel> answers = new ArrayList<>();
        AnswerCreationRequestModel answer = new AnswerCreationRequestModel();
        answer.setContent(generateRandomString(16));
        answers.add(answer);

        ArrayList<QuestionCreationRequestModel> questions = new ArrayList<>();
        QuestionCreationRequestModel question = new QuestionCreationRequestModel();
        question.setContent(generateRandomString(16));
        question.setQuestionOrder(1);
        question.setType("CHECKBOX");
        questions.add(question);

        PollCreationRequestModel poll = new PollCreationRequestModel();
        poll.setContent(generateRandomString(16));
        poll.setOpened(true);
        poll.setQuestions(questions);

        return poll;
    }

    public static String generateRandomString(int len){
        String charrs = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk1mnopqrstuvwxyz";

        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);

        for(int i = 0; i < len; i++){
            sb.append(charrs.charAt(rnd.nextInt(charrs.length())));
        }

        return sb.toString();
    }
}
