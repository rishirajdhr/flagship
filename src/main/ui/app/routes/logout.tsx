import { redirect } from "react-router";
import { removeAuthToken } from "~/utils/auth";

export async function clientAction() {
  removeAuthToken();
  throw redirect("/");
}

export default function Logout() {
  return null;
}
