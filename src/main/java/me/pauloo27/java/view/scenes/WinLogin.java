package me.pauloo27.java.view.scenes;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import me.pauloo27.java.services.UserService;
import me.pauloo27.java.utils.AppException;
import me.pauloo27.java.view.WinBase;

public class WinLogin extends WinBase {
    private UserService userService;

    public WinLogin() {
        super("Acessar Conta");
        this.userService = new UserService();
    }

    public void setupComponents() {
        super.setupComponents();

        Image img = null;
        try {
            var storePic = ImageIO.read(this.getClass().getResource("/store.png"));
            img = storePic.getScaledInstance(200, 200, BufferedImage.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel imgStore = new JLabel(new ImageIcon(img));
        super.addAt(imgStore, 300, 50, 200, 200);

        var lblName = new JLabel("Usuário:");
        super.addAt(lblName, 10, 50);

        var fieldUsername = new JTextField();
        super.addAt(fieldUsername, 10, 70, 200, 20);

        var lblPassword = new JLabel("Senha:");
        super.addAt(lblPassword, 10, 100);

        var fieldPassword = new JPasswordField();
        super.addAt(fieldPassword, 10, 120, 200, 20);

        var btnLogin = new JButton("Login");
        super.addAt(btnLogin, 10, 150, 200, 20);

        var btnGoToRegister = new JButton("Registrar novo usuário");
        super.addAt(btnGoToRegister, 10, 180, 200, 20);

        btnGoToRegister.addActionListener((a) -> {
            var register = new WinRegister();
            register.setVisible(true);
        });

        btnLogin.addActionListener((a) -> {
            var username = fieldUsername.getText();
            var password = new String(fieldPassword.getPassword());

            try {
                this.userService.login(username, password);
                JOptionPane.showMessageDialog(this, "Login efetuado com sucesso", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                var productListWin = new WinListProduct();
                productListWin.setVisible(true);
                this.dispose();
            } catch (AppException e) {
                JOptionPane.showMessageDialog(this,
                        e.getMessage(),
                        e.getTitle(),
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
