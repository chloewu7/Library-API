package app;

import interface_adapter.ViewManagerModel;
import user_manage.data_access.FileReviewDataAccessObject;
import user_manage.data_access.FileUserDataAccessObject;
import user_manage.entity.CommonReviewFactory;
import user_manage.entity.CommonUserFactory;
import user_manage.service.login.interface_adapter.LoginViewModel;
import user_manage.service.reading_review.show_my_reviews.interface_adapter.ShowMyReviewsViewModel;
import user_manage.service.reading_review.write_reviews.interface_adapter.WriteReviewsViewModel;
import user_manage.service.signup.interface_adapter.SignupViewModel;
import view.SignupView;
import view.ViewManager;
import view.WriteReviewsView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main {

    public static void main(String[] args) {

        // The main application window.
        JFrame application = new JFrame("Library Software");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setSize(1000, 600);

        //TODO: Layout might need to be changed in the future. line27-35 are tentative.
        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // create data access object
        FileUserDataAccessObject userDataAccessObject;
        FileReviewDataAccessObject reviewDataAccessObject;
        try {
            userDataAccessObject = new FileUserDataAccessObject("./users.csv", new CommonUserFactory());
            reviewDataAccessObject = new FileReviewDataAccessObject("./reviews.csv",
                    new CommonReviewFactory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // create Search View

        // create Signup View
        SignupViewModel signupViewModel = new SignupViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();

        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, signupViewModel, loginViewModel, userDataAccessObject);
        views.add(signupView, signupView.viewName);

        // create Login View

        // create Logout View

        // create WriteReview View
        WriteReviewsViewModel writeReviewsViewModel = new WriteReviewsViewModel();

        WriteReviewsView writeReviewsView = WriteReviewsUseCaseFactory.create(viewManagerModel, writeReviewsViewModel, reviewDataAccessObject);
        views.add(writeReviewsView, writeReviewsView.viewName);

        viewManagerModel.setActiveView(signupView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }

}