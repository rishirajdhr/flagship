import { Outlet, redirect } from "react-router";
import { getAuthToken, getAuthUsername } from "~/components/auth";
import Header from "~/components/header";
import type { Route } from "./+types/layout";
import { authContext } from "~/middleware-context";

export const clientMiddleware: Route.ClientMiddlewareFunction[] = [
  async function clientMiddleware({ context }) {
    const token = getAuthToken();
    if (token === null) {
      throw redirect("/login");
    }

    const username = getAuthUsername();
    if (username === null) {
      throw redirect("/login");
    }

    context.set(authContext, { token, username });
  },
];

export async function clientLoader({ context }: Route.ClientLoaderArgs) {
  const auth = context.get(authContext);
  if (auth === null) {
    throw redirect("/login");
  }
  const { username } = auth;
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
