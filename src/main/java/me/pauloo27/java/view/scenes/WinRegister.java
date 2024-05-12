package me.pauloo27.java.view.scenes;

import javax.swing.*;

import me.pauloo27.java.services.UserService;
import me.pauloo27.java.utils.AppException;
import me.pauloo27.java.view.WinBase;

public class WinRegister extends WinBase {
    private UserService userService;

    public WinRegister() {
        super("Cadastrar Usuário");
        this.userService = new UserService();
    }

    public void setupComponents() {
        super.setupComponents();

        var lblName = new JLabel("Usuário:");
        super.addAt(lblName, 10, 50);

        var fieldUsername = new JTextField();
        super.addAt(fieldUsername, 10, 70, 200, 20);

        var lblPassword = new JLabel("Senha:");
        super.addAt(lblPassword, 10, 100);

        var fieldPassword = new JPasswordField();
        super.addAt(fieldPassword, 10, 120, 200, 20);

        var btnRegister = new JButton("Cadastrar");
        super.addAt(btnRegister, 10, 150, 200, 20);

        btnRegister.addActionListener((a) -> {
            var username = fieldUsername.getText();
            var password = new String(fieldPassword.getPassword());

            try {
                this.userService.register(username, password);
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (AppException e) {
                JOptionPane.showMessageDialog(this,
                        e.getMessage(),
                        e.getTitle(),
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
