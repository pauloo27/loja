package me.pauloo27.java.services;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import me.pauloo27.java.db.repos.UserRepository;
import me.pauloo27.java.utils.AppException;

public class UserService {
    public void register(String username, String password) throws AppException {
        if (username.isEmpty() || password.isEmpty()) {
            throw new AppException("Erro", "Preencha todos os campos");
        }

        if (password.length() < 6) {
            throw new AppException("Erro", "A senha deve ter no mínimo 6 caracteres");
        }
        var repo = new UserRepository();
        try {
            repo.create(username, password);
        } catch (SQLException | NoSuchAlgorithmException e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                throw new AppException("Erro", "Nome de usuário já em uso");
            }
            e.printStackTrace();
            throw new AppException("Erro", "Algo deu errado ao criar usuário");
        }
    }

    public void login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            throw new AppException("Erro", "Preencha todos os campos");
        }

        var repo = new UserRepository();

        try {
            var user = repo.findByUsernameAndPassword(username, password);
            if (user == null) {
                throw new AppException("Erro", "Usuário ou senha inválidos");
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new AppException("Erro", "Algo deu errado ao logar");
        }
    }
}
