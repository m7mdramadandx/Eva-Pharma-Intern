package com.ramadan.eva.ui.viewmodel;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.ramadan.eva.data.model.User;
import com.ramadan.eva.ui.activitiy.Dashboard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewModel extends androidx.lifecycle.ViewModel {
    public String firstName, lastName, email, phone, password, confirmPassword;
    User user;

    public void registration(View view) {
        String emailValidation = "^(.+)@(.+).(.*[a-z])$";
        String passwordValidation = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}";
        Pattern emailPattern = Pattern.compile(emailValidation);
        Pattern passwordPattern = Pattern.compile(passwordValidation);
        Matcher emailMatcher = emailPattern.matcher(email != null ? email : "Email");
        Matcher passwordMatcher = passwordPattern.matcher(password != null ? password : "Password");

        if (firstName == null || lastName == null || email == null || phone == null || password == null || confirmPassword.isEmpty()) {
            Toast.makeText(view.getContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
        } else {
            if (!emailMatcher.matches()) {
                Toast.makeText(view.getContext(), "Invalid email!", Toast.LENGTH_SHORT).show();
            } else if (phone.length() != 11) {
                Toast.makeText(view.getContext(), "Invalid phone number!", Toast.LENGTH_SHORT).show();
            } else if (!passwordMatcher.matches()) {
                Toast.makeText(view.getContext(), "Weak password!", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(view.getContext(), "Passwords are different!", Toast.LENGTH_SHORT).show();
            } else {
                user = new User(firstName, lastName, email, phone, password, "null");
                Intent dashboard = new Intent(view.getContext(), Dashboard.class);
                dashboard.putExtra("user", user);
                view.getContext().startActivity(dashboard);
                Toast.makeText(view.getContext(), "Successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
