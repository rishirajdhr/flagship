import { Outlet, replace } from "react-router";
import { getAuthToken, getAuthUsername } from "~/components/auth";
import Header from "~/components/header";
import type { Route } from "./+types/layout";

export async function clientLoader() {
  const token = getAuthToken();
  if (token === null) {
    return replace("/login");
  }

  const username = getAuthUsername();
  if (username === null) {
    return replace("/login");
  }
  return username;
}

export default function Layout({ loaderData: username }: Route.ComponentProps) {
  return (
    <>
      <Header username={username} />
      <Outlet />
    </>
  );
}
