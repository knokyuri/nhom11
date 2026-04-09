package com.example.btlll.service;

import com.example.btlll.entity.TaiKhoan;
import com.example.btlll.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
 @Autowired private TaiKhoanRepository repo;

 public TaiKhoan login(String u, String p) {
  TaiKhoan tk = repo.findByUsername(u);
  return (tk != null && tk.getPassword().equals(p)) ? tk : null;
 }

 public boolean register(TaiKhoan tk) {
  if (repo.findByUsername(tk.getUsername()) != null) return false;
  repo.save(tk);
  return true;
 }

 public boolean updatePassword(String user, String newPass) {
  TaiKhoan tk = repo.findByUsername(user);
  if (tk != null) {
   tk.setPassword(newPass);
   repo.save(tk);
   return true;
  }
  return false;
 }
}