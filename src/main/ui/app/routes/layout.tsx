import { Outlet, replace } from "react-router";
import { getAuthToken } from "~/components/auth";
import Header from "~/components/header";

export async function clientLoader() {
  const token = getAuthToken();
  if (token === null) {
    return replace("/login");
  }
  return null;
}

export default function Layout() {
  return (
    <>
      <Header />
      <Outlet />
    </>
  );
}
