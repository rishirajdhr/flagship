import { redirect } from "react-router";
import { logout } from "~/components/auth";

export async function clientAction() {
  logout();
  throw redirect("/");
}

export default function Logout() {
  return null;
}
