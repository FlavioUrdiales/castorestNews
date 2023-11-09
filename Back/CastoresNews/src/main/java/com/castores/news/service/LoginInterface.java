package com.castores.news.service;

import com.castores.news.model.RequestUser;
import com.castores.news.model.User;

public interface LoginInterface {
	public User login(RequestUser user);
}
