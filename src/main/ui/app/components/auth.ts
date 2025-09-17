import { jwtDecode } from "jwt-decode";

function getAuthToken() {
  return localStorage.getItem("token");
}

function getAuthUsername() {
  const token = getAuthToken();
  if (token === null) {
    return null;
  }

  const decoded = jwtDecode(token);
  if (decoded.sub === undefined) {
    throw new Error("Invalid JWT token");
  }

  const username = decoded.sub;
  return username;
}

export { getAuthToken, getAuthUsername };
