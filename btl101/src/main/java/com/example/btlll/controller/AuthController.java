package com.example.btlll.controller;

import com.example.btlll.entity.TaiKhoan;
import com.example.btlll.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
 @Autowired private AuthService authService;

 @GetMapping("/login") public String loginPage() { return "login"; }
 @GetMapping("/register") public String regPage() { return "register"; }
 @GetMapping("/forgot") public String forgotPage() { return "forgot"; }

 @PostMapping("/login")
 public String doLogin(String username, String password, HttpSession session) {
  TaiKhoan tk = authService.login(username, password);
  if (tk != null) { session.setAttribute("user", tk); return "redirect:/home"; }
  return "redirect:/login?error";
 }

 @PostMapping("/register")
 public String doReg(TaiKhoan tk) {
  return authService.register(tk) ? "redirect:/login?success" : "redirect:/register?error";
 }

 @PostMapping("/forgot")
 public String doForgot(String username, String newPassword) {
  return authService.updatePassword(username, newPassword) ? "redirect:/login?reset" : "redirect:/forgot?error";
 }

 @GetMapping("/logout")
 public String logout(HttpSession s) { s.invalidate(); return "redirect:/login"; }
}