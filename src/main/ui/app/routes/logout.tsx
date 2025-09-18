import { redirect } from "react-router";
import { removeAuthToken } from "~/components/auth";

export async function clientAction() {
  removeAuthToken();
  throw redirect("/");
}

export default function Logout() {
  return null;
}
