import { Outlet, replace } from "react-router";
import Header from "~/components/header";

export async function clientLoader() {
  const token = localStorage.getItem("token");
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
