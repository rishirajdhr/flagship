import type { Auth } from "~/types";
import { withBase } from "./base";

export async function getAllProjects(auth: Auth) {
  const result = await fetch(withBase(`/api/projects`), {
    headers: {
      Authorization: `Bearer ${auth.token}`,
    },
  });
  return result;
}
