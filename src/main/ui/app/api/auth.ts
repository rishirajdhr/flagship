import { withBase } from "./base";

export interface LoginCredentials {
  username: string;
  password: string;
}

export async function login(loginCredentials: LoginCredentials) {
  const result = await fetch(withBase(`/api/login`), {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(loginCredentials),
  });
  return result;
}

export async function signup(signupInformation: LoginCredentials) {
  const result = await fetch(withBase(`/api/signup`), {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(signupInformation),
  });
  return result;
}
